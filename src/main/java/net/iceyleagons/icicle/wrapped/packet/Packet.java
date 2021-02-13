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

package net.iceyleagons.icicle.wrapped.packet;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.player.WrappedCraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/**
 * (in preparation)
 *
 * @author TOTHTOMI
 */
@Getter
public abstract class Packet {

    private static boolean setup = false;
    private static Class<?> nms_Class;
    private static Constructor<?> constructor;

    private final Object packet;

    @SneakyThrows
    protected Packet(String clazz, Object... parameters) {
        setupStatic(clazz, parameters);
        this.packet = constructor.newInstance(parameters);
    }

    public void sendAll() {
        Bukkit.getServer().getOnlinePlayers().forEach(this::send);
    }

    public void send(Player player) {
        send(WrappedCraftPlayer.from(player));
    }

    public void send(WrappedCraftPlayer wrappedCraftPlayer) {
        wrappedCraftPlayer.getHandle().getPlayerConnection().sendPacket(getPacket());
    }

    private static Class<?>[] getClassesFromParameters(Object... params) {
        Class<?>[] classes = new Class<?>[params.length];

        for (int i = 0; i < params.length; i++) {
            classes[i] = params[i].getClass();
        }

        return classes;
    }

    private static void setupStatic(String clazz, Object... params) {
        if (!setup) {
            setup = true;
            nms_Class = Reflections.getNormalNMSClass(clazz);
            constructor = Reflections.getConstructor(nms_Class, true, getClassesFromParameters(params));
        }
    }
}
