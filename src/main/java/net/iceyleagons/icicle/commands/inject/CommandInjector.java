package net.iceyleagons.icicle.commands.inject;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class CommandInjector {

    private final RegisteredIciclePlugin registeredIciclePlugin;


    /**
     * Injects a command. What it means, that you don't have to enter it into the plugin.yml
     *
     * @param command         the command
     * @param commandExecutor the commandExecutor
     * @throws CommandInjectException if errors happen during the injection
     */
    public void injectCommand(@NonNull String command, @NonNull CommandExecutor commandExecutor) throws CommandInjectException {
        injectCommand(command, commandExecutor, null, null, null, null, null, null);
    }

    /**
     * Injects a command. What it means, that you don't have to enter it into the plugin.yml
     *
     * @param command         the command
     * @param commandExecutor the commandExecutor
     * @param tabCompleter    the tabCompleter
     * @throws CommandInjectException if errors happen during the injection
     */
    public void injectCommand(@NonNull String command, @NonNull CommandExecutor commandExecutor, @NonNull TabCompleter tabCompleter) throws CommandInjectException {
        injectCommand(command, commandExecutor, tabCompleter, null, null, null, null, null);
    }

    /**
     * Injects a command. What it means, that you don't have to enter it into the plugin.yml
     *
     * @param command           the command (required)
     * @param commandExecutor   the commandExecutor (required)
     * @param tabCompleter      the tabCompleter (optional)
     * @param usage             usage text (optional)
     * @param description       description of the command (optional)
     * @param permission        permission for the command (optional)
     * @param permissionMessage insufficient permission error messaga (optional)
     * @param aliases           aliases for the command (optional)
     * @throws CommandInjectException if errors happen during the injection
     */
    public void injectCommand(@NonNull String command, @NonNull CommandExecutor commandExecutor,
                              TabCompleter tabCompleter, String usage, String description,
                              String permission, String permissionMessage,
                              List<String> aliases) throws CommandInjectException {
        try {
            final Field bukkitCommandMap = getCommandMap();
            if (bukkitCommandMap == null) throw new CommandInjectException(command, "CommandMap is unavailable");

            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            PluginCommand pluginCommand = c.newInstance(command, registeredIciclePlugin.getJavaPlugin());
            if (tabCompleter != null) pluginCommand.setTabCompleter(tabCompleter);
            if (usage != null) pluginCommand.setUsage(usage);
            if (aliases != null) pluginCommand.setAliases(aliases);
            if (description != null) pluginCommand.setDescription(description);
            if (permission != null) pluginCommand.setPermission(permission);
            if (permissionMessage != null) pluginCommand.setPermissionMessage(permissionMessage);
            pluginCommand.setExecutor(commandExecutor);
            pluginCommand.setAliases(Collections.emptyList());
            commandMap.register(command, pluginCommand);
        } catch (Exception e) {
            throw new CommandInjectException(command, e);
        }
    }

    private Field getCommandMap()  {
        Field f = null;
        try {
            f = registeredIciclePlugin.getJavaPlugin().getServer().getClass().getField("commandMap");
            f.setAccessible(true);
        } catch (Exception ignored) { }

        return f;
    }
}
