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

package net.iceyleagons.icicle.bungee.message;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.AllArgsConstructor;
import net.iceyleagons.icicle.bungee.BungeeUtils;
import net.iceyleagons.icicle.bungee.channel.BungeeChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Contains a {@link BungeeMessage} and a {@link BungeeChannel}, basically an object that links these two together.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0-SNAPSHOT
 */
@AllArgsConstructor
public class RegisteredBungeeMessage {

    private final BungeeMessage bungeeMessage;
    private final BungeeChannel bungeeChannel;

    /**
     * Send the message to BungeeCord main channel with the first online player found in {@link Bukkit#getOnlinePlayers()}.
     */
    public void send() {
        send(Iterables.getFirst(Bukkit.getOnlinePlayers(), null));
    }

    /**
     * Send the message to BungeeCord main channel with a specific {@link Player}
     *
     * @param player the sender
     */
    public void send(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(bungeeMessage.getId());
        out.writeUTF(bungeeMessage.getMessage());
        BungeeUtils.send(out, bungeeChannel.getChannelName(), player);
    }

}
