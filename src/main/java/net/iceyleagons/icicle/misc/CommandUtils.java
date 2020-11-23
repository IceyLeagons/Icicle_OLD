/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicle.misc;

import lombok.NonNull;
import net.iceyleagons.icicle.reflections.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * Contains handy functions regarding commands.
 *
 * @author TOTHTOMI
 * @version 1.1.4-SNAPSHOT
 * @since 1.1.4-SNAPSHOT
 */
public class CommandUtils {

    private static JavaPlugin plugin = null;

    /**
     * Initializes the CommandUtils with the given JavaPlugin
     *
     * @param javaPlugin the {@link JavaPlugin}
     */
    public static void init(JavaPlugin javaPlugin) {
        if (plugin == null) plugin = javaPlugin;
    }

    /**
     * Injects a command. What it means, that you don't have to enter it into the plugin.yml
     *
     * @param command         the command
     * @param commandExecutor the commandExecutor
     */
    public static void injectCommand(@NonNull String command, @NonNull CommandExecutor commandExecutor) {
        injectCommand(command, commandExecutor, null, null, null, null, null, null);
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
     */
    public static void injectCommand(@NonNull String command, @NonNull CommandExecutor commandExecutor,
                                     TabCompleter tabCompleter, String usage, String description,
                                     String permission, String permissionMessage,
                                     List<String> aliases) {
        if (plugin == null) throw new IllegalStateException("CommandUtils is not initialized!");
        try {
            final Field bukkitCommandMap = Reflections.getField(plugin.getServer().getClass(), "commandMap", true);
            if (bukkitCommandMap == null) throw new RuntimeException("Could not inject command! Error at line 54");

            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            PluginCommand pluginCommand = c.newInstance(command, plugin);
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
            throw new RuntimeException("Could not inject command! Error at line 54", e);
        }
    }

}
