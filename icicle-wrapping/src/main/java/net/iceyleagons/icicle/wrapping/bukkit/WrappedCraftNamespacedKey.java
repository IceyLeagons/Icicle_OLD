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

package net.iceyleagons.icicle.wrapping.bukkit;

import net.iceyleagons.icicle.wrapping.utils.WrappedClass;
import org.bukkit.NamespacedKey;

/**
 * Wrapped representation of NamespacedKey
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedCraftNamespacedKey {
    static {
        WrappedClass.getCBClass("util.CraftNamespacedKey")
                .lookupMethod("toMinecraft", null, NamespacedKey.class)
                .lookupMethod(NamespacedKey.class, "fromMinecraft", null, WrappedClass.getNMSClass("MinecraftKey").getClazz())
                .lookupMethod(NamespacedKey.class, "fromString", null, String.class)
                .lookupMethod(NamespacedKey.class, "fromStringOrNull", null, String.class);
    }

    /**
     * Returns the bukkit NamespacedKey equivalent to the provided MinecraftKey.
     *
     * @param root the minecraftkey we wish to get the equivalent of.
     * @return the bukkit namespacedkey.
     */
    public static NamespacedKey fromMinecraft(Object root) {
        return (NamespacedKey) WrappedClass.getCBClass("util.CraftNamespacedKey").getMethod("fromMinecraft").invoke(null, root);
    }

    /**
     * Creates a new bukkit NamespacedKey from the provided String.
     *
     * @param string the String we wish to get the equivalent of.
     * @return the bukkit namespacedkey.
     */
    public static NamespacedKey fromString(String string) {
        return (NamespacedKey) WrappedClass.getCBClass("util.CraftNamespacedKey").getMethod("fromString").invoke(null, string);
    }

    /**
     * Acts pretty much the same way as {@link #fromString(String)} with a little twist.
     * <p>
     * Creates a new bukkit NamespacedKey from the provided String.
     *
     * @param string the String we wish to get the equivalent of.
     * @return the bukkit namespacedkey.
     */
    public static NamespacedKey fromStringOrNull(String string) {
        return (NamespacedKey) WrappedClass.getCBClass("util.CraftNamespacedKey").getMethod("fromStringOrNull").invoke(null, string);
    }

    /**
     * Converts the bukkit NamespacedKey into an NMS MinecraftKey.
     *
     * @param key the bukkit namespacedkey.
     * @return the nms minecraftkey.
     */
    public static Object toMinecraft(NamespacedKey key) {
        return WrappedClass.getCBClass("util.CraftNamespacedKey").getMethod("toMinecraft").invoke(null, key);
    }
}
