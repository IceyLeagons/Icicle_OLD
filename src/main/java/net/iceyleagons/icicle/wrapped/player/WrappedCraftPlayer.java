/*
 * MIT License
 *
 * Copyright (c) 2021 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle.wrapped.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.iceyleagons.icicle.reflect.Reflections;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

/**
 * @author TOTHTOMI
 */
@AllArgsConstructor
@Getter
public class WrappedCraftPlayer {

    private static final Class<?> craftPlayerClass;

    private static final Field cb_getConversationTracker;

    private static final Method cb_getProtocolVersion;
    private static final Method cb_getVirtualHost;
    //private static final Method cb_sendActionbar;
    private static final Method cb_getHandle;


    static {
        craftPlayerClass = Reflections.getNormalCBClass("entity.CraftPlayer");

        cb_getConversationTracker = getCBField("conversationTracker");
        cb_getProtocolVersion = getCBMethod("getProtocolVersion");
        cb_getVirtualHost = getCBMethod("getVirtualHost");
        //cb_sendActionbar = getCBMethod("sendActionbar", String.class);
        cb_getHandle = getCBMethod("getHandle");
    }

    private final Player bukkitPlayer;

    private static Method getCBMethod(String name, Class<?>... parameterTypes) {
        return Reflections.getMethod(craftPlayerClass, name, true, parameterTypes);
    }

    private static Field getCBField(String name) {
        return Reflections.getField(craftPlayerClass, name, true);
    }

    public static WrappedCraftPlayer from(Player player) {
        return new WrappedCraftPlayer(player);
    }

    public Object getConversationTracker() {
        return Reflections.get(cb_getConversationTracker, Object.class, bukkitPlayer);
    }

    public Integer getProtocolVersion() {
        return Reflections.invoke(cb_getProtocolVersion, Integer.class, bukkitPlayer);
    }

    public InetSocketAddress getVirtualHost() {
        return Reflections.invoke(cb_getVirtualHost, InetSocketAddress.class, bukkitPlayer);
    }

    /*public void sendActionbar(String message) {
        Reflections.invoke(cb_sendActionbar, Void.class, bukkitPlayer, message);
    }*/

    public WrappedEntityPlayer getHandle() {
        return WrappedEntityPlayer.from(Reflections.invoke(cb_getHandle, Object.class, bukkitPlayer));
    }


}
