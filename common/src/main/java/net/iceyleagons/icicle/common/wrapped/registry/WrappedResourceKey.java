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

package net.iceyleagons.icicle.common.wrapped.registry;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.common.reflect.Reflections;
import net.iceyleagons.icicle.common.wrapped.bukkit.WrappedCraftNamespacedKey;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Method;

/**
 * Wrapped representation of ResourceKey
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedResourceKey {

    public static final Class<?> mc_ResourceKey;
    private static final Method resource_constructor;
    private static final Method resource_biome;

    static {
        mc_ResourceKey = Reflections.getNormalNMSClass("ResourceKey");
        resource_constructor = Reflections.getMethod(mc_ResourceKey, "a", true, WrappedIRegistry.mc_MinecraftKey);
        resource_biome = Reflections.getMethod(mc_ResourceKey, "a", true, mc_ResourceKey, WrappedIRegistry.mc_MinecraftKey);
    }

    @Getter
    private final Object resourceKey;

    public WrappedResourceKey(String namespace, String name) {
        this(new NamespacedKey(namespace, name));
    }

    @SneakyThrows
    public WrappedResourceKey(NamespacedKey namespacedKey) {
        this.resourceKey = Reflections.invoke(resource_constructor, Object.class, null, WrappedCraftNamespacedKey.toMinecraft(namespacedKey));
    }

    @SneakyThrows
    public WrappedResourceKey(Object root, NamespacedKey namespacedKey) {
        this.resourceKey = Reflections.invoke(resource_biome, Object.class, null, root, WrappedCraftNamespacedKey.toMinecraft(namespacedKey));
    }

}
