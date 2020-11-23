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

package net.iceyleagons.icicle;

import lombok.Getter;
import net.iceyleagons.icicle.bungee.BungeeUtils;
import net.iceyleagons.icicle.jtext.JText;
import net.iceyleagons.icicle.misc.CommandUtils;
import net.iceyleagons.icicle.ui.GUIManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;


/**
 * Choose your desired features to be activated.
 */
public enum IcicleFeatures {

    /**
     * Enables the Bungee feature
     */
    BUNGEE(BungeeUtils::init),
    /**
     * Enables the command feature
     */
    COMMANDS(CommandUtils::init),
    /**
     * Enables the GUI feature
     */
    INVENTORIES(pl -> Bukkit.getPluginManager().registerEvents(new GUIManager(), pl)),
    /**
     * Enables the JText feature
     * <p>Requires {@link #COMMANDS}</p>
     */
    CHAT(JText::init);

    @Getter
    private final Consumer<JavaPlugin> consumer;

    IcicleFeatures(Consumer<JavaPlugin> consumer) {
        this.consumer = consumer;
    }

}
