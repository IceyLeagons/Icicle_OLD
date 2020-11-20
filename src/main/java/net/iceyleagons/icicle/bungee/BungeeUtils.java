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

package net.iceyleagons.icicle.bungee;

import com.google.common.io.ByteArrayDataOutput;
import net.iceyleagons.icicle.bungee.channel.BungeeChannel;
import net.iceyleagons.icicle.bungee.channel.BungeeChannelListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Main class handling and containing everything
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class BungeeUtils {

    private static BungeeChannelListener bungeeChannelListener = null;
    private static Map<String, BungeeChannel> bungeeChannelMap = null;
    private static JavaPlugin plugin = null;

    /**
     * Initializes Bungee messaging. Must be run first!
     *
     * @param javaPlugin the {@link JavaPlugin} to register to
     */
    public static void init(JavaPlugin javaPlugin) {
        if (bungeeChannelListener == null) bungeeChannelListener = new BungeeChannelListener();
        if (bungeeChannelMap == null) bungeeChannelMap = new HashMap<>();
        if (plugin == null) plugin = javaPlugin;


        Messenger messenger = plugin.getServer().getMessenger();;
        messenger.registerOutgoingPluginChannel(javaPlugin,"BungeeCord");
        messenger.registerIncomingPluginChannel(javaPlugin,"BungeeCord",bungeeChannelListener);
    }

    /**
     * Sends a {@link ByteArrayDataOutput} on a specific sub-channel to the BungeeCord main channel using the {@link Player}
     *
     * @param output the output
     * @param channel the sub channel
     * @param player the player
     */
    public static void send(ByteArrayDataOutput output, String channel, Player player) {
        player.sendPluginMessage(plugin,channel,output.toByteArray());
    }


    /**
     * Returns a {@link BungeeChannel} with the given name in on Optional.
     *
     * @param name the name to search
     * @return a {@link Optional} containing the {@link BungeeChannel} can be empty!
     */
    public static Optional<BungeeChannel> getChannel(String name) {
        if (bungeeChannelMap.containsKey(name)) return Optional.of(bungeeChannelMap.get(name));
        else return Optional.empty();
    }

    /**
     * Registers a channel, used internally in constructors.
     *
     * @param bungeeChannel the {@link BungeeChannel} to register.
     */
    public static void registerChannel(BungeeChannel bungeeChannel) {
        bungeeChannelMap.put(bungeeChannel.getChannelName(),bungeeChannel);
    }

}
