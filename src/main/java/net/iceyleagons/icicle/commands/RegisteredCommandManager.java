package net.iceyleagons.icicle.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.commands.Alias;
import net.iceyleagons.icicle.annotations.commands.CommandManager;
import net.iceyleagons.icicle.annotations.commands.Subcommand;
import net.iceyleagons.icicle.annotations.commands.SubcommandManager;
import net.iceyleagons.icicle.annotations.commands.checks.Optional;
import net.iceyleagons.icicle.annotations.commands.checks.PermissionCheck;
import net.iceyleagons.icicle.annotations.commands.checks.PlayerOnly;
import net.iceyleagons.icicle.commands.inject.CommandInjectException;
import net.iceyleagons.icicle.localization.LocalizationManager;
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

    //TODO flag parameters (ex.: -t 1h) | @Sender annotation instead of just only checking for CommandSender this way it can be a Player as well and the backend will automatically cast it

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

        for (int i = 0; i < params.length; i++) {
            Parameter parameter = params[i];
            if (args.length < i) {
                 if (parameter.isAnnotationPresent(Optional.class))
                    break;
                 else
                     return null;
            }

            Class<?> paramType = parameter.getType();
            String value = args[i];

            if (paramType.equals(CommandSender.class)) {
                parameters[i] = sender;
            } else if (paramType.equals(int.class) || paramType.equals(Integer.class)) {
                //TODO send parseError message
                if (Checkers.isInteger(value)) {
                    parameters[i] = Integer.parseInt(value);
                }
            } else if (paramType.equals(short.class) || paramType.equals(Short.class)) {
                //TODO send parseError message
                if (Checkers.isShort(value)) {
                    parameters[i] = Short.parseShort(value);
                }
            } else if (paramType.equals(long.class) || paramType.equals(Long.class)) {
                //TODO send parseError message
                if (Checkers.isLong(value)) {
                    parameters[i] = Long.parseLong(value);
                }
            } else if (paramType.equals(double.class) || paramType.equals(Double.class)) {
                //TODO send parseError message
                if (Checkers.isDouble(value)) {
                    parameters[i] = Double.parseDouble(value);
                }
            } else if (paramType.equals(float.class) || paramType.equals(Float.class)) {
                //TODO send parseError message
                if (Checkers.isFloat(value)) {
                    parameters[i] = Float.parseFloat(value);
                }
            } else if (paramType.equals(boolean.class) || paramType.equals(Boolean.class)) {
                //TODO send parseError message
                parameters[i] = Boolean.parseBoolean(value);
            } else {
                //TODO rest paramTypes
            }
        }

        return parameters;
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
            }
        } else if (subcommandManagers.containsKey(subc)) {
            try {
                subcommandManagers.get(subc).execute(sender, newArgs);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "An internal exception occured!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + getInvalidCmd());
            return true;
        }





        return true;
    }
}
