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

package net.iceyleagons.icicle.wrapping.world.chunk;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapping.world.WrappedBlockPosition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Wrapped representation of ChunkCoordIntPair
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedChunkCoordIntPair {

    private static final Class<?> mc_ChunkCoordIntPair;
    private static final Method pair_getBlockX;
    private static final Method pair_getBlockZ;
    private static final Method pair_asPosition;
    private static final Method pair_asPositionDown;
    private static final Method pair_getRegionX;
    private static final Method pair_getRegionZ;
    private static Constructor<?> pair_xz;
    private static Constructor<?> pair_position;
    private static Constructor<?> pair_long;

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

    /**
     * @return this int pair's x coordinate.
     */
    public Integer getBlockX() {
        return Reflections.invoke(pair_getBlockX, Integer.class, pair);
    }

    /**
     * @return this int pair's z coordinate.
     */
    public Integer getBlockZ() {
        return Reflections.invoke(pair_getBlockZ, Integer.class, pair);
    }

    /**
     * @return this int pair as a block position.
     */
    public WrappedBlockPosition asPosition() {
        return new WrappedBlockPosition(Reflections.invoke(pair_asPosition, Object.class, pair));
    }

    /**
     * No idea what this is, looked useful.
     *
     * @return this int pair as a block position.
     */
    public WrappedBlockPosition asPositionDown() {
        return new WrappedBlockPosition(Reflections.invoke(pair_asPositionDown, Object.class, pair));
    }

    /**
     * @return the region files' x.
     */
    public Integer getRegionX() {
        return Reflections.invoke(pair_getRegionX, Integer.class, pair);
    }

    /**
     * @return the region files' z.
     */
    public Integer getRegionZ() {
        return Reflections.invoke(pair_getRegionZ, Integer.class, pair);
    }

}
