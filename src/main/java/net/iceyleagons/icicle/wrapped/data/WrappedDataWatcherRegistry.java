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

package net.iceyleagons.icicle.wrapped.data;

import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Field;

/**
 * @author TOTHTOMI
 */
public class WrappedDataWatcherRegistry {


    private static final Class<?> mc_dataWatcherRegistry;
    private static final Field a, c;

    static {
        mc_dataWatcherRegistry = Reflections.getNormalNMSClass("DataWatcherRegistry");
        a = Reflections.getField(mc_dataWatcherRegistry, "a", true);
        c = Reflections.getField(mc_dataWatcherRegistry, "c", true);
    }

    public static Object a() {
        return Reflections.get(a, Object.class, null);
    }

    public static Object c() {
        return Reflections.get(c, Object.class, null);
    }

}
