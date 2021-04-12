package net.iceyleagons.icicle.commands.system;

import lombok.Getter;
import net.iceyleagons.icicle.commands.annotations.Command;
import net.iceyleagons.icicle.commands.annotations.CommandContainer;
import net.iceyleagons.icicle.commands.CommandInjectException;
import net.iceyleagons.icicle.commands.CommandUtils;
import net.iceyleagons.icicle.plugin.RegisteredPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Getter
public class PluginCommandManager implements CommandExecutor, TabCompleter {

    private final RegisteredPlugin registeredPlugin;
    private final CommandUtils commandUtils;
    private final Map<String, RegisteredCommand> commands = new HashMap<>();
    private final Map<Class<?>, CommandParameterHandlerTemplate> parameterHandlers = new HashMap<>();

    private final Map<String, String> messages = new HashMap<>();

    public PluginCommandManager(RegisteredPlugin registeredPlugin) {
        this.registeredPlugin = registeredPlugin;
        this.commandUtils = new CommandUtils(registeredPlugin.getJavaPlugin());
        insertDefaultMessages();
    }

    private void insertDefaultMessages() {
        messages.put("permission", "&cWe're sorry, but you do not have sufficient permissions, to perfom this command!");

        messages.put("player_only", "&cPlayer only command!");
        messages.put("arguments_few", "&cToo few arguments!");
        messages.put("arguments_many", "&cToo many arguments!");
        messages.put("exception", "&cAn internal exception happened! (See console for details)");

        messages.put("parameter_number_invalid", "&cParameter expected to be a number.");
        messages.put("parameter_player_not_found", "&cPlayer not found. Are they online?");
        messages.put("parameter_uuid_invalid", "&CInvalid UUID format!");
    }

    public void registerCommandContainer(Object o) {
        if (o.getClass().isAnnotationPresent(CommandContainer.class)) {
            Arrays.stream(o.getClass().getDeclaredMethods())
                    .peek(method -> method.setAccessible(true))
                    .forEach(method -> {
                        Command annotation = method.getAnnotation(Command.class);
                        RegisteredCommand registeredCommand = new RegisteredCommand(o, method, annotation);

                        commands.put(annotation.value(), registeredCommand);
                        Arrays.stream(annotation.aliases()).forEach(alias -> commands.put(alias, registeredCommand));

                        try {
                            commandUtils.injectCommand(annotation.value(), this, this,
                                    annotation.usage().isEmpty() ? null : annotation.usage(),
                                    annotation.description().isEmpty() ? null : annotation.description(),
                                    annotation.permission().isEmpty() ? null : annotation.permission(),
                                    ChatColor.translateAlternateColorCodes('&', messages.get("permission")),
                                    Arrays.asList(annotation.aliases().clone()));
                        } catch (CommandInjectException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        String cmd = command.getName().toLowerCase(Locale.ROOT);
        if (commands.containsKey(cmd)) {
            handleCommand(commands.get(cmd), sender, args);
        }
        return true;
    }

    public void sendMessage(CommandSender sender, String key) {
        if (messages.containsKey(key)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.get(key)));
        }
    }

    private void handleCommand(RegisteredCommand command, CommandSender sender, String[] args) {
        Command annotation = command.getAnnotation();
        Object object = command.getObject();
        Method method = command.getMethod();

        if (annotation.playerOnly() && !(sender instanceof Player)) {
            sendMessage(sender, "player_only");
            return;

        }

        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];

        if (args.length < parameterTypes.length - 1) {
            sendMessage(sender, "arguments_few");
            return;
        } else if (args.length > parameterTypes.length - 1) {
            sendMessage(sender, "arguments_many");
            return;
        }

        int commandArgCounter = 0;
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];

            if (type.equals(CommandSender.class)) {
                parameters[i] = sender;
                continue;
            }

            Object handled = handleParameter(type, sender, args[commandArgCounter++]);
            if (handled == null) {
                return;
            }

            parameters[i] = handled;
        }

        try {
            method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            sendMessage(sender, "exception");
        }

    }

    private Object handleParameter(Class<?> type, CommandSender sender, String arg) {
        if (parameterHandlers.containsKey(type)) {
            return parameterHandlers.get(type).parseFromSting(arg, sender, type, this);
        }

        return null;
        /*
        if (type == String.class) {
            return arg;

        } else if (type == Integer.class) {
            if (!MathUtils.isNumber(arg)) {
                sendMessage(sender, "parameter_number_invalid");
                return null;
            }

            return Integer.parseInt(arg);
        } else if (type == Player.class) {
            Player player = Bukkit.getPlayer(arg);
            if (player == null) {
                sendMessage(sender, "parameter_player_not_found");
                return null;
            }

            return player;
        }

        return null;

         */
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String alias, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        String token = (args.length == 0 ? "" : args[args.length - 1]);
        StringUtil.copyPartialMatches(token, commands.keySet(), completions);
        Collections.sort(completions);
        return completions;
    }
}
