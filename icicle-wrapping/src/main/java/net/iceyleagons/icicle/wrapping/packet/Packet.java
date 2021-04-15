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

package net.iceyleagons.icicle.wrapping.packet;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapping.player.WrappedCraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Packet abstract class for wrapping
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.4.0-SNAPSHOT
 */
@Getter
public abstract class Packet {

    private static boolean setup = false;
    private static Class<?> nms_Class;
    private static Constructor<?> constructor;
    private static final Map<String, Field> fields = new HashMap<>();

    private final Object packet;

    @SneakyThrows
    protected Packet(String clazz, Class<?>[] parameterTypes, Object... parameters) {
        setupStatic(clazz, parameterTypes);
        this.packet = constructor.newInstance(parameters);
    }

    protected Packet(Class<?> clazz, Constructor<?> constructor1, Object instance) {
        nms_Class = clazz;
        constructor = constructor1;

        this.packet = instance;
    }

    protected Packet(Class<?> clazz, Object instance) {
        this(clazz, null, instance);
    }

    @SneakyThrows
    protected Packet(String clazz, Object... parameters) {
        setupStatic(clazz, parameters);
        this.packet = constructor.newInstance(parameters);
    }

    private static Class<?>[] getClassesFromParameters(Object... params) {
        Class<?>[] classes = new Class<?>[params.length];

        for (int i = 0; i < params.length; i++) {
            classes[i] = params[i].getClass();
        }

        return classes;
    }

    private static void setupStatic(Class<?> clazz, Constructor<?> constructor1) {
        if (!setup) {
            setup = true;
            nms_Class = clazz;
            constructor = constructor1;
        }
    }

    private static void setupStatic(String clazz, Class<?>... paramTypes) {
        if (!setup) {
            setup = true;
            nms_Class = Reflections.getNormalNMSClass(clazz);
            constructor = Reflections.getConstructor(nms_Class, true, paramTypes);
        }
    }

    private static void setupStatic(String clazz, Object... params) {
        if (!setup) {
            setup = true;
            nms_Class = Reflections.getNormalNMSClass(clazz);
            constructor = Reflections.getConstructor(nms_Class, true, getClassesFromParameters(params));
        }
    }

    public <T> T getFromField(String name, Class<T> wantedType) {
        return Reflections.get(getField(name), wantedType, packet);
    }

    public void setField(String name, Object value) {
        Reflections.set(getField(name), packet, value);
    }

    private Field getField(String name) {
        if (!fields.containsKey(name)) {
            Field field = Reflections.getField(nms_Class, name, true);
            fields.put(name, field);
            return field;
        }

        return fields.get(name);
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
}
