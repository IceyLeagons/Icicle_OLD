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

import net.iceyleagons.icicle.common.reflect.Reflections;
import net.iceyleagons.icicle.common.wrapped.bukkit.WrappedCraftNamespacedKey;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Method;

/**
 * Wrapped representation of RegistryGeneration
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedRegistryGeneration {

    private static final Class<?> mc_RegistryGeneration;
    private static final Method gen_register;

    static {
        mc_RegistryGeneration = Reflections.getNormalNMSClass("RegistryGeneration");

        gen_register = Reflections.getMethod(mc_RegistryGeneration, "a", true, WrappedIRegistry.mc_IRegistry, WrappedIRegistry.mc_MinecraftKey, Object.class);
    }

    /**
     * Register an Object into the provided IReqistry on the specified key.
     *
     * @param registry an IRegistry object.
     * @param key      the key to register the object into.
     * @param obj      the object to register.
     * @return the registered object.
     */
    public static <T> T register(Object registry, NamespacedKey key, T obj) {
        return (T) Reflections.invoke(gen_register, Object.class, null, registry, WrappedCraftNamespacedKey.toMinecraft(key), obj);
    }

}
