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

package net.iceyleagons.icicle.wrapped.world.chunk;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.WrappedBlockPosition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class WrappedChunkCoordIntPair {

    private static final Class<?> mc_ChunkCoordIntPair;
    private static Constructor<?> pair_xz, pair_position, pair_long;
    private static final Method pair_getBlockX, pair_getBlockZ, pair_asPosition, pair_asPositionDown, pair_getRegionX, pair_getRegionZ;

    static {
        mc_ChunkCoordIntPair = Reflections.getNormalNMSClass("ChunkCoordIntPair");
        try {
            pair_xz = mc_ChunkCoordIntPair.getDeclaredConstructor(int.class, int.class);
            pair_position = mc_ChunkCoordIntPair.getDeclaredConstructor(WrappedBlockPosition.mc_BlockPosition);
            pair_long = mc_ChunkCoordIntPair.getDeclaredConstructor(long.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        pair_getBlockX = Reflections.getMethod(mc_ChunkCoordIntPair, "getBlockX", true);
        pair_getBlockZ = Reflections.getMethod(mc_ChunkCoordIntPair, "getBlockZ", true);
        pair_asPosition = Reflections.getMethod(mc_ChunkCoordIntPair, "asPosition", true);
        pair_asPositionDown = Reflections.getMethod(mc_ChunkCoordIntPair, "l", true);
        pair_getRegionX = Reflections.getMethod(mc_ChunkCoordIntPair, "getRegionX", true);
        pair_getRegionZ = Reflections.getMethod(mc_ChunkCoordIntPair, "getRegionZ", true);
    }

    @Getter
    private final Object pair;

    @SneakyThrows
    public WrappedChunkCoordIntPair(int x, int z) {
        this.pair = pair_xz.newInstance(x, z);
    }

    @SneakyThrows
    public WrappedChunkCoordIntPair(WrappedBlockPosition blockPosition) {
        this.pair = pair_position.newInstance(blockPosition.getRoot());
    }

    @SneakyThrows
    public WrappedChunkCoordIntPair(long pair) {
        this.pair = pair_long.newInstance(pair);
    }

    public WrappedChunkCoordIntPair(Object pair) {
        this.pair = pair;
    }

    public int getBlockX() {
        return Reflections.invoke(pair_getBlockX, int.class, pair);
    }

    public int getBlockZ() {
        return Reflections.invoke(pair_getBlockZ, int.class, pair);
    }

    public WrappedBlockPosition asPosition() {
        return new WrappedBlockPosition(Reflections.invoke(pair_asPosition, Object.class, pair));
    }

    public WrappedBlockPosition asPositionDown() {
        return new WrappedBlockPosition(Reflections.invoke(pair_asPositionDown, Object.class, pair));
    }

    public int getRegionX() {
        return Reflections.invoke(pair_getRegionX, int.class, pair);
    }

    public int getRegionZ() {
        return Reflections.invoke(pair_getRegionZ, int.class, pair);
    }

}
