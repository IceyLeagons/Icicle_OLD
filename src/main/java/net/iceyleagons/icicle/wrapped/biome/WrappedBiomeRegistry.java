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

package net.iceyleagons.icicle.wrapped.biome;

import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.registry.WrappedIRegistry;
import net.iceyleagons.icicle.wrapped.registry.WrappedResourceKey;
import net.iceyleagons.icicle.wrapped.utils.WrappedClass;

import java.lang.reflect.Method;

/**
 * Wrapped representation of BiomeRegistry
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedBiomeRegistry {

    static {
        WrappedClass.getNMSClass("BiomeRegistry").lookupMethod("a", null, int.class, WrappedClass.getNMSClass("ResourceKey").getClazz(), WrappedClass.getNMSClass("BiomeBase").getClazz());
    }

    /**
     * Registers a biome into this registry.
     *
     * @param id          the id we wish to register it into.
     * @param resourceKey the resource key of the registrar.
     * @param biomeBase   the biome base we wish to register.
     * @return the provided biome base.
     */
    public static WrappedBiomeBase register(int id, WrappedResourceKey resourceKey, WrappedBiomeBase biomeBase) {
        return new WrappedBiomeBase(WrappedClass.getNMSClass("BiomeRegistry").getMethod("a").invoke(null, id, resourceKey, biomeBase));
    }

}
