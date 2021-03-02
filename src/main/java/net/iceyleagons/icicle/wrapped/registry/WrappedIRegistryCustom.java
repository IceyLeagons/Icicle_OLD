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

package net.iceyleagons.icicle.wrapped.registry;

import lombok.Getter;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Method;

/**
 * Wrapped representation of IRegistryCustom
 * <p>
 * {@link Dimension} is a subclass of this.
 *
 * @author Gabe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedIRegistryCustom {

    private static final Class<?> mc_IRegistryCustom;
    private static final Class<?> mc_IRegistryCustom_Dimension;
    private static final Method registry_getWritable;

    static {
        mc_IRegistryCustom = Reflections.getNormalNMSClass("IRegistryCustom");
        mc_IRegistryCustom_Dimension = Reflections.getNormalNMSClass("IRegistryCustom$Dimension");

        registry_getWritable = Reflections.getMethod(mc_IRegistryCustom, "b", true, WrappedResourceKey.mc_ResourceKey);
    }

    @Getter
    private final Object root;

    public WrappedIRegistryCustom(Object root) {
        this.root = root;
    }

    /**
     * Converts the provided key into a writable key. (if possible)
     *
     * @param key key we want to convert.
     * @return an instance of IRegistryWritable.
     */
    public WrappedIRegistryWritable getWritable(Object key) {
        return new WrappedIRegistryWritable(Reflections.invoke(registry_getWritable, Object.class, root, key));
    }

    /**
     * @return this as a {@link Dimension}
     */
    public Dimension asDimension() {
        if (mc_IRegistryCustom_Dimension.isInstance(root))
            return new Dimension(root);

        throw new UnsupportedOperationException("Not a Dimension!");
    }

    /**
     * @author Gábe
     * @version 1.0.0
     * @since 1.3.3-SNAPSHOT
     */
    public static class Dimension extends WrappedIRegistryCustom {

        public Dimension(Object dimension) {
            super(dimension);
        }

    }

}
