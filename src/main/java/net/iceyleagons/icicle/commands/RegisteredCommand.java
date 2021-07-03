package net.iceyleagons.icicle.commands;

import lombok.Getter;
import net.iceyleagons.icicle.annotations.commands.Alias;
import net.iceyleagons.icicle.annotations.commands.checks.Optional;
import net.iceyleagons.icicle.annotations.commands.checks.PermissionCheck;
import net.iceyleagons.icicle.annotations.commands.checks.PlayerOnly;
import net.iceyleagons.icicle.utils.Asserts;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RegisteredCommand {

    @Getter
    private final String name;
    @Getter
    private final String[] aliases;

    private final Method method;
    private final Object commandParent;


    private final RegisteredCommandManager commandManager;
    private final PlayerOnly playerOnly;
    private final PermissionCheck permission;

    private final int requiredParamCount;

    public RegisteredCommand(RegisteredCommandManager parent, String name, Object commandParent, Method method, @Nullable Alias alias, @Nullable PlayerOnly playerOnly, @Nullable PermissionCheck permission) {
        Asserts.notNull(parent, "RegisteredCommandManager must not be null!");
        Asserts.notNull(name, "Command name must not be null!");
        Asserts.notNull(method, "Command method must not be null!");
        Asserts.notNull(commandParent, "Command parent must not be null!");

        this.commandManager = parent;
        this.name = name;
        this.playerOnly = playerOnly;
        this.permission = permission;
        this.aliases = getAliases(alias);

        this.commandParent = commandParent;
        this.method = method;
        this.method.setAccessible(true);

        this.requiredParamCount = getRequiredArgsLength();
    }

    public void execute(@NotNull CommandSender commandSender, @NotNull String[] args) throws Exception {
        if (playerOnly != null && !(commandSender instanceof Player)) {
            sendMessage(commandSender, getPlayerOnlyResponse(commandSender));
            return;
        }

        if (permission != null && !(commandSender.hasPermission(permission.value()) || commandSender.isOp())) {
            sendMessage(commandSender, getPermissionResponse(commandSender));
            return;
        }

        if (args.length < requiredParamCount) {
            System.out.println("Too few! Current: " + args.length);
            System.out.println("Required: " + requiredParamCount);
            sendMessage(commandSender, commandManager.getTooFewArgs());
            return;
        } else if (args.length > requiredParamCount) {
            sendMessage(commandSender, commandManager.getTooManyArgs());
            return;
        }

        Object[] parameters = commandManager.parse(args, method.getParameters(), commandSender);
        if (parameters == null) throw new IllegalStateException();

        Object returned = method.invoke(commandParent, parameters);
        if (!(returned instanceof String)) return;

        sendMessage(commandSender, (String) returned);
    }

    private int getRequiredArgsLength() {
        return (int) Arrays.stream(method.getParameters())
                .filter(p -> !p.isAnnotationPresent(Optional.class))
                .filter(p -> !p.isAnnotationPresent(net.iceyleagons.icicle.annotations.commands.CommandSender.class)).count();
    }

    private String getPlayerOnlyResponse(CommandSender commandSender) {
        Asserts.notNull(playerOnly, "getPlayerOnlyResponse called while playerOnly is null, this should not happen!");

        return playerOnly.usingLocalization() ? commandManager.getLocalizationManager().getTranslation(playerOnly.error(), commandSender) : permission.error();
    }

    private String getPermissionResponse(CommandSender commandSender) {
        Asserts.notNull(permission, "getPermissionResponse called while permission is null, this should not happen!");

        return permission.usingLocalization() ? commandManager.getLocalizationManager().getTranslation(permission.error(), commandSender) : permission.error();
    }

    private static String[] getAliases(Alias alias) {
        if (alias == null) return new String[0];

        return alias.value();
    }

    private static void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
