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

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import net.iceyleagons.icicle.bungee.channel.BungeeChannel;
import net.iceyleagons.icicle.bungee.channel.BungeeChannelListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Main class handling and containing everything
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0-SNAPSHOT
 */
@Getter
public class BungeeUtils {

    private final BungeeChannelListener bungeeChannelListener = new BungeeChannelListener(this);
    private final Map<String, BungeeChannel> bungeeChannelMap = new HashMap<>();
    private final JavaPlugin plugin;

    public BungeeUtils(JavaPlugin javaPlugin) {
        this.plugin = javaPlugin;
        Messenger messenger = plugin.getServer().getMessenger();

        messenger.registerOutgoingPluginChannel(javaPlugin, "BungeeCord");
        messenger.registerIncomingPluginChannel(javaPlugin, "BungeeCord", bungeeChannelListener);
    }

    /**
     * Sends a {@link ByteArrayDataOutput} on a specific sub-channel to the BungeeCord main channel using the {@link Player}
     *
     * @param output  the output
     * @param channel the sub channel
     * @param player  the player
     */
    public void send(ByteArrayDataOutput output, String channel, Player player) {
        player.sendPluginMessage(plugin, channel, output.toByteArray());
    }


    /**
     * Sends a {@link ByteArrayDataOutput} on a specific sub-channel to the BungeeCord main channel using the {@link Player}
     *
     * @param output  the output
     * @param channel the sub channel
     */
    public void send(ByteArrayDataOutput output, String channel) {
        Objects.requireNonNull(Iterables.getFirst(Bukkit.getOnlinePlayers(), null)).sendPluginMessage(plugin, channel, output.toByteArray());
    }

    /**
     * Returns a {@link BungeeChannel} with the given name in on Optional.
     *
     * @param name the name to search
     * @return a {@link Optional} containing the {@link BungeeChannel} can be empty!
     */
    public Optional<BungeeChannel> getChannel(String name) {
        if (bungeeChannelMap.containsKey(name)) return Optional.of(bungeeChannelMap.get(name));
        else return Optional.empty();
    }

    /**
     * Registers a channel, used internally in constructors.
     *
     * @param bungeeChannel the {@link BungeeChannel} to register.
     */
    public void registerChannel(BungeeChannel bungeeChannel) {
        bungeeChannelMap.put(bungeeChannel.getChannelName(), bungeeChannel);
    }

    public void kick(String reason, Player... players) {
        for (Player player : players) {
            kickPlayer(reason, player);
        }
    }

    private void kickPlayer(String reason, Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(player.getName());
        out.writeUTF(reason);

        send(out, "BungeeCord");
    }

    public void connectPlayers(String server, Player... players) {
        for (Player player : players)
            connectPlayer(server, player);
    }

    private void connectPlayer(String server, Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");
        out.writeUTF(player.getName());
        out.writeUTF(server);
        send(out, "BungeeCord");
    }

}
