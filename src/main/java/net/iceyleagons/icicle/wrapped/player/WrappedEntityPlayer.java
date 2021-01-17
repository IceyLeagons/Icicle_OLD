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
 * @author TOTHTOMI
 */
@RequiredArgsConstructor
@Getter
public class WrappedEntityPlayer {

    private static final Class<?> entityPlayerClass;
    private static final Class<?> mc_Entity;

    private static final Field mc_playerConnection;
    private static final Field mc_networkManager;
    private static final Field mc_world;
    private static final Method mc_isFrozen;
    private static final Field mc_ping;

    static {
        entityPlayerClass = Reflections.getNormalNMSClass("EntityPlayer");
        mc_Entity = Reflections.getNormalNMSClass("Entity");

        mc_playerConnection = getMCField("playerConnection");
        mc_networkManager = getMCField("networkManager");
        mc_world = Reflections.getField(mc_Entity, "world", true);
        mc_isFrozen = getMCMethod("isFrozen");
        mc_ping = getMCField("ping");
    }

    private final Object entityPlayer;

    private static Method getMCMethod(String name, Class<?>... parameterTypes) {
        return Reflections.getMethod(entityPlayerClass, name, true, parameterTypes);
    }

    private static Field getMCField(String name) {
        return Reflections.getField(entityPlayerClass, name, true);
    }

    public static WrappedEntityPlayer from(Object handle) {
        return new WrappedEntityPlayer(handle);
    }

    public WrappedPlayerConnection getPlayerConnection() {
        return new WrappedPlayerConnection(Reflections.get(mc_playerConnection, Object.class, entityPlayer));
    }

    public WrappedNetworkManager getNetworkManager() {
        return new WrappedNetworkManager(Reflections.get(mc_networkManager, Object.class, entityPlayer));
    }

    public Object getNMSWorld() {
        return Reflections.get(mc_world, Object.class, entityPlayer);
    }

    public Integer getPing() {
        return Reflections.get(mc_ping, Integer.class, entityPlayer);
    }

    public Boolean isFrozen() {
        return Reflections.invoke(mc_isFrozen, Boolean.class, entityPlayer);
    }

}
