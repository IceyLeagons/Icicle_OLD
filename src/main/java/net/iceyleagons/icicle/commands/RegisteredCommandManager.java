package net.iceyleagons.icicle.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.iceyleagons.icicle.Icicle;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.commands.Alias;
import net.iceyleagons.icicle.annotations.commands.CommandManager;
import net.iceyleagons.icicle.annotations.commands.Subcommand;
import net.iceyleagons.icicle.annotations.commands.SubcommandManager;
import net.iceyleagons.icicle.annotations.commands.checks.PermissionCheck;
import net.iceyleagons.icicle.annotations.commands.checks.PlayerOnly;
import net.iceyleagons.icicle.commands.inject.CommandInjectException;
import net.iceyleagons.icicle.localization.LocalizationManager;
import net.iceyleagons.icicle.utils.Asserts;
import net.iceyleagons.icicle.utils.Checkers;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class RegisteredCommandManager implements CommandExecutor {

    private final RegisteredIciclePlugin registeredIciclePlugin;
    private final LocalizationManager localizationManager = new LocalizationManager();


    private final Map<String, RegisteredCommand> commands = new HashMap<>();
    private final Map<String, RegisteredSubcommandManager> subcommandManagers;

    private final String name;
    private final String tooManyArgs;
    private final String tooFewArgs;
    private final String invalidCmd;

    private final Object origin;
    private final Class<?> originType; //due to a bytecode editor, this is not the same as origin.getClass()

    //TODO flag parameters (ex.: -t 1h)

    public RegisteredCommandManager(RegisteredIciclePlugin plugin, CommandManager commandManager, Object origin, Class<?> originType) throws CommandInjectException {
        this.name = commandManager.value();
        this.tooManyArgs = commandManager.tooManyArgumentsText();;
        this.tooFewArgs = commandManager.tooFewArgumentsText();
        this.invalidCmd = commandManager.invalidCommand();

        this.registeredIciclePlugin = plugin;
        this.origin = origin;
        this.originType = originType;

        scanCommands(origin, this.commands);
        this.subcommandManagers = getSubcommandManagers();
        this.registeredIciclePlugin.getCommandInjector().injectCommand(this.name, this);
    }

    public void scanCommands(Object origin, Map<String, RegisteredCommand> commands) {
        registeredIciclePlugin.getClassScanner().getMethodsAnnotatedWithInsideClazz(originType, net.iceyleagons.icicle.annotations.commands.Command.class).forEach(c -> {
            String cmd = c.getAnnotation(net.iceyleagons.icicle.annotations.commands.Command.class).value();
            RegisteredCommand registeredCommand = new RegisteredCommand(this, cmd, origin, c, c.getAnnotation(Alias.class), c.getAnnotation(PlayerOnly.class), c.getAnnotation(PermissionCheck.class));

            commands.put(cmd, registeredCommand);
        });
    }

    private Map<String, RegisteredSubcommandManager> getSubcommandManagers() {
        return Arrays.stream(originType.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Subcommand.class))
                .filter(f -> f.getType().isAnnotationPresent(SubcommandManager.class))
                .map(f -> {
                    try {
                        String cmd = f.getType().getAnnotation(SubcommandManager.class).value();
                        Object instance = f.getType().getDeclaredConstructor().newInstance();

                        return new RegisteredSubcommandManager(cmd, registeredIciclePlugin, this, instance);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    return null;
                })
                .filter(Objects::nonNull).collect(Collectors.toMap(RegisteredSubcommandManager::getName, m -> m));
    }



    @Nullable
    public Object[] parse(String[] args, Parameter[] params, CommandSender sender) {
        Object[] parameters = new Object[params.length];

        int argsCount = 0;
        for (int i = 0; i < params.length; i++) {
            Class<?> type = params[i].getType();

            Icicle.debug("[CommandManager] Parsing type: " + type.getName());
            Icicle.debug("[CommandManager] I: " + i + " AC: " + argsCount);

            if (params[i].isAnnotationPresent(net.iceyleagons.icicle.annotations.commands.CommandSender.class)) {
                Asserts.isAssignable(CommandSender.class, type, "Unsupported @CommandSender type: " + type.getName());

                parameters[i] = type.cast(sender);
                continue;
            }

            Object obj = parsePrimitives(type, args[argsCount++]);
            parameters[i] = obj;

            if (obj != null)
                Icicle.debug("[CommandManager] Parsed type " + obj.getClass().getName());
        }

        return parameters;
    }

    private Object parsePrimitives(Class<?> type, String argument) {
        if (type.equals(int.class) || type.equals(Integer.class)) {
            //TODO send parseError message
            if (Checkers.isInteger(argument)) {
                return Integer.parseInt(argument);
            }
        } else if (type.equals(short.class) || type.equals(Short.class)) {
            //TODO send parseError message
            if (Checkers.isShort(argument)) {
                return Short.parseShort(argument);
            }
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            //TODO send parseError message
            if (Checkers.isLong(argument)) {
                return Long.parseLong(argument);
            }
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            //TODO send parseError message
            if (Checkers.isDouble(argument)) {
                return Double.parseDouble(argument);
            }
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            //TODO send parseError message
            if (Checkers.isFloat(argument)) {
                return Float.parseFloat(argument);
            }
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            //TODO send parseError message
            return Boolean.parseBoolean(argument);
        } else {
            //TODO rest paramTypes
        }
        return argument;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase(name)) return true;
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getTooFewArgs()));
            return true;
        }

        String subc = args[0];
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);

        if (commands.containsKey(subc)) {
            try {
                commands.get(subc).execute(sender, newArgs);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "An internal exception occured!");
                e.printStackTrace();
            }
        } else if (subcommandManagers.containsKey(subc)) {
            try {
                subcommandManagers.get(subc).execute(sender, newArgs);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "An internal exception occured!");
                e.printStackTrace();
            }
        } else {
            sender.sendMessage(ChatColor.RED + getInvalidCmd());
            return true;
        }
        return true;
    }
}
