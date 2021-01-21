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
import net.iceyleagons.icicle.wrapped.world.chunk.WrappedChunk;

import java.lang.reflect.Constructor;

/**
 * Wrapped representation PacketPlayOutMapChunk
 *
 * @author Gabe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedPacketPlayOutMapChunk {

    private static final Class<?> mc_PacketPlayOutMapChunk;
    private static Constructor<?> chunk_instance;

    static {
        mc_PacketPlayOutMapChunk = Reflections.getNormalNMSClass("PacketPlayOutMapChunk");

        try {
            chunk_instance = mc_PacketPlayOutMapChunk.getDeclaredConstructor(WrappedChunk.mc_Chunk, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Getter
    private final Object packet;

    @SneakyThrows
    public WrappedPacketPlayOutMapChunk(WrappedChunk chunk, int priority) {


        this.packet = chunk_instance.newInstance(chunk.getChunk(), priority);
    }

    public WrappedPacketPlayOutMapChunk(WrappedChunk chunk) {
        this(chunk, 20);
    }

}
