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

package net.iceyleagons.icicle.bungee.channel;

import com.google.common.io.ByteArrayDataInput;
import lombok.Data;
import net.iceyleagons.icicle.bungee.BungeeUtils;
import net.iceyleagons.icicle.bungee.message.BungeeMessage;
import net.iceyleagons.icicle.bungee.message.BungeeMessageHandler;
import net.iceyleagons.icicle.bungee.message.RegisteredBungeeMessage;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Used for creating specific sub channels under the BungeeCord main channel.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0-SNAPSHOT
 */
@Data
public class BungeeChannel {

    private final String channelName;
    private final Map<String, BungeeMessageHandler> handlerMap;

    /**
     * Creates a new {@link BungeeChannel} and registers it with {@link BungeeUtils#registerChannel(BungeeChannel)}
     *
     * @param channelName the id of the channel must be unique!s
     */
    public BungeeChannel(String channelName) {
        this.channelName = channelName;
        this.handlerMap = new HashMap<>();
        BungeeUtils.registerChannel(this);
    }

    /**
     * Registers a message handler, in order to properly handle incoming messages.
     *
     * @param bungeeMessageHandler the {@link BungeeMessageHandler} to register
     */
    public void registerMessageHandler(BungeeMessageHandler bungeeMessageHandler) {
        handlerMap.put(bungeeMessageHandler.getMessageId(), bungeeMessageHandler);
    }

    /**
     * Creates a new {@link RegisteredBungeeMessage}
     *
     * @param bungeeMessage the {@link BungeeMessage} to create from
     * @return the created {@link RegisteredBungeeMessage}
     */
    public RegisteredBungeeMessage registerBungeeMessage(BungeeMessage bungeeMessage) {
        return new RegisteredBungeeMessage(bungeeMessage, this);
    }

    /**
     * This method will run the appropriate {@link BungeeMessageHandler} from the incoming data.
     * It's run by: {@link org.bukkit.plugin.messaging.PluginMessageListener#onPluginMessageReceived(String, Player, byte[])}
     *
     * @param player the {@link Player}
     * @param input  the {@link ByteArrayDataInput}
     */
    public void onBungeeChannelMessage(Player player, ByteArrayDataInput input) {
        String messageId = input.readUTF();
        String message = input.readUTF();
        if (handlerMap.containsKey(messageId)) {
            handlerMap.get(messageId).handle(message, player);
        }
    }
}
