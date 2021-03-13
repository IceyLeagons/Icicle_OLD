package net.iceyleagons.icicle.commands;

import lombok.Getter;
import net.iceyleagons.icicle.annotations.commands.Command;
import net.iceyleagons.icicle.annotations.commands.CommandContainer;
import net.iceyleagons.icicle.math.MathUtils;
import net.iceyleagons.icicle.registry.RegisteredPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
public class PluginCommandManager implements CommandExecutor {

    private final RegisteredPlugin registeredPlugin;
    private final CommandUtils commandUtils;
    private final Map<String, RegisteredCommand> commands = new HashMap<>();

    public PluginCommandManager(RegisteredPlugin registeredPlugin) {
        this.registeredPlugin = registeredPlugin;
        this.commandUtils = new CommandUtils(registeredPlugin.getJavaPlugin());
    }

    public void registerCommandContainer(Object o) {
        if (o.getClass().isAnnotationPresent(CommandContainer.class)) {
            Arrays.stream(o.getClass().getDeclaredMethods())
                    .peek(method -> method.setAccessible(true))
                    .forEach(method -> {
                        Command annotation = method.getAnnotation(Command.class);
                        RegisteredCommand registeredCommand = new RegisteredCommand(o, method, annotation);

                        commands.put(annotation.value(), registeredCommand);

                        try {
                            commandUtils.injectCommand(annotation.value(), this);
                        } catch (CommandInjectException e) {
                            e.printStackTrace();
                        }

                        Arrays.stream(annotation.aliases()).forEach(alias -> {
                            try {
                                commandUtils.injectCommand(alias, this);
                            } catch (CommandInjectException e) {
                                e.printStackTrace();
                            }
                            commands.put(alias, registeredCommand);
                        });
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

    private void handleCommand(RegisteredCommand command, CommandSender sender, String[] args) {
        Command annotation = command.getAnnotation();
        Object object = command.getObject();
        Method method = command.getMethod();

        if (annotation.playerOnly()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Player only command!");
                return;
            }
        }

        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];

        if (args.length < parameterTypes.length - 1) {
            sender.sendMessage("Too few arguments!");
            return;
        } else if (args.length > parameterTypes.length - 1) {
            sender.sendMessage("Too many arguments!");
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
                sender.sendMessage("An exception happened!");
                return;
            }

            parameters[i] = handled;
        }

        try {
            Object returnedResponse = method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            sender.sendMessage("An exception happened! 2");
        }

    }

    private Object handleParameter(Class<?> type, CommandSender sender, String arg) {
        if (type == String.class) {
            return arg;
        } else if (type == Integer.class) {
            if (!MathUtils.isNumber(arg)) {
                sender.sendMessage("Invalid number!");
                return null;
            }

        } else if (type == Player.class) {

        }

        return null;
    }
}
