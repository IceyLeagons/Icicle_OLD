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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Wrapped representation of PlayerConnection
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
@RequiredArgsConstructor
@Getter
public class WrappedPlayerConnection {

    private static final Class<?> pConnectionClass;

    private static final Field mc_networkManager;

    private static final Method mc_getLastPing;
    private static final Method mc_setLastPing;
    private static final Method mc_setPendingPing;
    private static final Method mc_isPendingPing;
    private static final Method mc_sendPacket;

    static {
        pConnectionClass = Reflections.getNormalNMSClass("PlayerConnection");

        mc_networkManager = Reflections.getField(pConnectionClass, "networkManager", true);
        mc_getLastPing = getMCMethod("getLastPing");
        mc_setLastPing = getMCMethod("setLastPing", long.class);
        mc_setPendingPing = getMCMethod("setPendingPing", boolean.class);
        mc_isPendingPing = getMCMethod("isPendingPing");
        mc_sendPacket = getMCMethod("sendPacket", Reflections.getNormalNMSClass("Packet"));
    }

    private final Object playerConnection;

    private static Method getMCMethod(String name, Class<?>... parameterTypes) {
        return Reflections.getMethod(pConnectionClass, name, true, parameterTypes);
    }

    /**
     * @return the network manager that is managing this connection.
     */
    public WrappedNetworkManager getNetworkManager() {
        return new WrappedNetworkManager(Reflections.get(mc_networkManager, Object.class, playerConnection));
    }

    /**
     * Send a packet down this connection.
     *
     * @param packet self-explanatory.
     */
    public void sendPacket(Object packet) {
        Reflections.invoke(mc_sendPacket, Void.class, playerConnection, packet);
    }

    /**
     * @return this players last ping.
     */
    public Long getLastPing() {
        return Reflections.invoke(mc_getLastPing, Long.class, playerConnection);
    }

    /**
     * Essentially fakes the last ping of this player.
     *
     * @param lastPing the new value it should return.
     */
    public void setLastPing(long lastPing) {
        Reflections.invoke(mc_setLastPing, Void.class, playerConnection, lastPing);
    }

    /**
     * Set whether or not the players ping is still pending.
     *
     * @param isPending the new value.
     */
    public void setPendingPing(boolean isPending) {
        Reflections.invoke(mc_setPendingPing, Void.class, playerConnection, isPending);
    }

    /**
     * @return whether or not we're still awaiting a response from the player.
     */
    public Boolean isPendingPing() {
        return Reflections.invoke(mc_isPendingPing, Boolean.class, playerConnection);
    }

}
