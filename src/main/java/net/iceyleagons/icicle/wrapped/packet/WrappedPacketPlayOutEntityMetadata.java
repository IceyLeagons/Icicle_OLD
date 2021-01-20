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

package net.iceyleagons.icicle.wrapped.packet;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.data.WrappedDataWatcher;

import java.lang.reflect.Constructor;

/**
 * @author TOTHTOMI
 */
public class WrappedPacketPlayOutEntityMetadata {

    private static final Class<?> mc_PacketPlayOutEntityMetadata;
    private static final Constructor<?> constructor;


    static {
        mc_PacketPlayOutEntityMetadata = Reflections.getNormalNMSClass("PacketPlayOutEntityMetadata");
        Class<?> mc_entityHuman = Reflections.getNormalNMSClass("EntityHuman");
        constructor = Reflections.getConstructor(mc_PacketPlayOutEntityMetadata, true, int.class, WrappedDataWatcher.class, boolean.class);

    }

    @Getter
    private final Object packet;

    @SneakyThrows
    public WrappedPacketPlayOutEntityMetadata(int i, WrappedDataWatcher dataWatcher, boolean b) {
        this.packet = constructor.newInstance(i, dataWatcher.getNmsObject(), b);
    }

}
