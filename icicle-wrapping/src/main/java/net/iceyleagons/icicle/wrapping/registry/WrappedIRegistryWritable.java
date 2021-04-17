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

package net.iceyleagons.icicle.wrapping.registry;

import lombok.Getter;
import net.iceyleagons.icicle.utils.Reflections;

import java.lang.reflect.Method;

/**
 * Wrapped representation of IRegistryWritable
 *
 * @author Gabe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedIRegistryWritable<T> {

    private static final Class<?> mc_IRegistryWritable;
    private static final Class<?> mojang_lifecycle;
    private static final Method writable_write;
    private static final Method life_getStable;

    static {
        mc_IRegistryWritable = Reflections.getNormalNMSClass("IRegistryWritable");
        mojang_lifecycle = Reflections.getNormalClass("com.mojang.serialization.Lifecycle");

        writable_write = Reflections.getMethod(mc_IRegistryWritable, "a", true, WrappedResourceKey.mc_ResourceKey, Object.class, mojang_lifecycle);
        assert mojang_lifecycle != null;
        life_getStable = Reflections.getMethod(mojang_lifecycle, "stable", true);
    }

    @Getter
    private final Object writable;

    public WrappedIRegistryWritable(Object root) {
        if (mc_IRegistryWritable.isInstance(root)) {
            writable = mc_IRegistryWritable.cast(root);
            return;
        }

        throw new IllegalArgumentException("Not instance of IRegistryWritable.class");
    }

    /**
     * Registers an object into the specified resource key.
     * <p>
     * <b>This registers with the STABLE lifecycle.</b>
     *
     * @param resourceKey this is the key.
     * @param register    this is the object we wish to register.
     */
    public void register(WrappedResourceKey resourceKey, Object register) {
        Object lifecycle = Reflections.invoke(life_getStable, mojang_lifecycle, null);

        Reflections.invoke(writable_write, Void.class, writable,
                resourceKey.getResourceKey(),
                register,
                lifecycle);
    }

}
