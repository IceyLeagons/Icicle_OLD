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

import lombok.SneakyThrows;
import net.iceyleagons.icicle.common.reflect.Reflections;

import java.lang.reflect.Method;

/**
 * Wrapped representation of IRegistry
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedIRegistry {

    public static final Class<?> mc_ResourceKey;
    public static final Class<?> mc_IRegistry;
    public static final Class<?> mc_MinecraftKey;
    public static final Class<?> mc_RegistryGeneration;
    public static final Object WORLDGEN_BIOME;
    public static final Object BIOME;
    public static final Object DIMENSION;
    private static final Method registry_get;

    static {
        mc_RegistryGeneration = Reflections.getNormalNMSClass("RegistryGeneration");
        mc_ResourceKey = Reflections.getNormalNMSClass("ResourceKey");
        mc_IRegistry = Reflections.getNormalNMSClass("IRegistry");
        mc_MinecraftKey = Reflections.getNormalNMSClass("MinecraftKey");
        registry_get = Reflections.getMethod(mc_IRegistry, "get", true, mc_MinecraftKey);

        WORLDGEN_BIOME = Reflections.get(Reflections.getField(mc_RegistryGeneration, "WORLDGEN_BIOME", true), Object.class, null);
        BIOME = Reflections.get(Reflections.getField(mc_IRegistry, "ay", true), Object.class, null);
        DIMENSION = Reflections.get(Reflections.getField(mc_IRegistry, "K", true), Object.class, null);
    }

    /**
     * Retrieves an IRegistry from the root with the specified minecraft key.
     * <p>
     * Currently pretty useless, since we don't handle minecraft keys. (AS OF NOW! Subject to change)
     *
     * @param root         self-explanatory.
     * @param minecraftKey self-explanatory.
     * @return the IRegistry searched for.
     */
    @SneakyThrows
    public static Object get(Object root, Object minecraftKey) {
        return registry_get.invoke(root, minecraftKey);
    }

}
