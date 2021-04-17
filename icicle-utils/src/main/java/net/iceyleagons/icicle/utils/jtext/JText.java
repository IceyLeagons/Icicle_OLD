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

package net.iceyleagons.icicle.utils.jtext;

import lombok.Getter;
import net.iceyleagons.icicle.utils.commands.CommandInjectException;
import net.iceyleagons.icicle.utils.commands.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Used for mainly the click runnables.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0-SNAPSHOT
 */
public class JText implements CommandExecutor {

    public static final String COMMAND = "click";
    public static final String COMMAND_FORMAT = "click <code>";
    @Getter
    private final Map<String, Consumer<Player>> clickConsumers = new HashMap<>();

    public JText(CommandUtils commandUtils) {

        try {
            commandUtils.injectCommand("click", this);
        } catch (CommandInjectException e) {
            e.printStackTrace();
            commandUtils.getPlugin().getLogger().warning("To fix this you will need to reload the server, but it may not improve anything.");
        }
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase(COMMAND)) {
            if (args.length == 0) return true;
            if (!args[0].isEmpty()) {
                if (!(sender instanceof Player)) return true;
                Player player = (Player) sender;
                if (clickConsumers.containsKey(args[0])) {
                    clickConsumers.get(args[0]).accept(player);
                }
            }
        }
        return true;
    }

}
