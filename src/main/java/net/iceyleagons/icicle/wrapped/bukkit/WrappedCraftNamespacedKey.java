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

package net.iceyleagons.icicle.wrapped.bukkit;

import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.registry.WrappedIRegistry;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Method;

/**
 * Wrapped representation NamespacedKey
 *
 * @author Gabe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedCraftNamespacedKey {

    private static final Class<?> bukkit_CraftNamespacedKey;

    private static final Method nm_toMinecraft;
    private static final Method nm_fromMinecraft;
    private static final Method nm_fromString;
    private static final Method nm_fromStringOrNull;

    static {
        bukkit_CraftNamespacedKey = Reflections.getNormalCBClass("util.CraftNamespacedKey");

        nm_toMinecraft = Reflections.getMethod(bukkit_CraftNamespacedKey, "toMinecraft", true, NamespacedKey.class);
        nm_fromMinecraft = Reflections.getMethod(bukkit_CraftNamespacedKey, "fromMinecraft", true, WrappedIRegistry.mc_MinecraftKey);
        nm_fromString = Reflections.getMethod(bukkit_CraftNamespacedKey, "fromString", true, String.class);
        nm_fromStringOrNull = Reflections.getMethod(bukkit_CraftNamespacedKey, "fromStringOrNull", true, String.class);
    }

    public static NamespacedKey fromMinecraft(Object root) {
        return Reflections.invoke(nm_fromMinecraft, NamespacedKey.class, null, root);
    }

    public static NamespacedKey fromString(String string) {
        return Reflections.invoke(nm_fromString, NamespacedKey.class, null, string);
    }

    public static NamespacedKey fromStringOrNull(String string) {
        return Reflections.invoke(nm_fromStringOrNull, NamespacedKey.class, null, string);
    }

    public static Object toMinecraft(NamespacedKey key) {
        return Reflections.invoke(nm_toMinecraft, Object.class, null, key);
    }
}
