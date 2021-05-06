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

package net.iceyleagons.icicle.common.wrapped.mojang;

import lombok.Getter;
import net.iceyleagons.icicle.common.reflect.Reflections;

import java.lang.reflect.Method;

/**
 * Wrapped representation of PropertyMap
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedPropertyMap {

    //private static final Class<?> mc_propertyMap;
    private static final Class<?> forwardingMultimap;

    private static final Method put;

    static {
        //mc_propertyMap = Reflections.getNormalClass("com.mojang.authlib.properties.PropertyMap");
        forwardingMultimap = Reflections.getNormalClass("com.google.common.collect.ForwardingMultimap");
        assert forwardingMultimap != null;
        put = Reflections.getMethod(forwardingMultimap, "put", true, Object.class, Object.class);
    }

    @Getter
    private final Object nmsObject;

    public WrappedPropertyMap(Object from) {
        this.nmsObject = from;
    }

    /**
     * Puts the specified value at the specified key.
     *
     * @param key   self-explanatory.
     * @param value self-explanatory.
     */
    public void put(Object key, Object value) {
        Reflections.invoke(put, Void.class, nmsObject, key, value);
    }
}
