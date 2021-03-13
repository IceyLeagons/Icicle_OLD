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

package net.iceyleagons.icicle.wrapped.world;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import org.bukkit.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Wrapped representation of BlockPosition
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedBlockPosition {

    public static final Class<?> mc_BlockPosition;
    private static final Class<?> mc_BaseBlockPosition;
    private static Constructor<?> block_constructor;

    private static final Method base_isValidLocation;
    private static final Method base_getX;
    private static final Method base_getY;
    private static final Method base_getZ;
    private static final Method base_setX;
    private static final Method base_setY;
    private static final Method base_setZ;

    static {
        mc_BlockPosition = Reflections.getNormalNMSClass("BlockPosition");
        mc_BaseBlockPosition = Reflections.getNormalNMSClass("BaseBlockPosition");
        try {
            block_constructor = mc_BlockPosition.getDeclaredConstructor(int.class, int.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        base_isValidLocation = Reflections.getMethod(mc_BaseBlockPosition, "isValidLocation", true);
        base_getX = Reflections.getMethod(mc_BaseBlockPosition, "getX", true);
        base_getY = Reflections.getMethod(mc_BaseBlockPosition, "getY", true);
        base_getZ = Reflections.getMethod(mc_BaseBlockPosition, "getZ", true);
        base_setX = Reflections.getMethod(mc_BaseBlockPosition, "setX", true, int.class);
        base_setY = Reflections.getMethod(mc_BaseBlockPosition, "setY", true, int.class);
        base_setZ = Reflections.getMethod(mc_BaseBlockPosition, "setZ", true, int.class);
    }

    @Getter
    private final Object root;

    public WrappedBlockPosition(Object root) {
        this.root = root;
    }

    public WrappedBlockPosition(Location location) {
        this(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @SneakyThrows
    public WrappedBlockPosition(int x, int y, int z) {
        root = block_constructor.newInstance(x, y, z);
    }

    /**
     * @return whether or not this is a valid minecraft location.
     */
    public boolean isValidLocation() {
        return Reflections.invoke(base_isValidLocation, boolean.class, root);
    }

    /**
     * @return the x coordinate of this position.
     */
    public int getX() {
        return Reflections.invoke(base_getX, int.class, root);
    }

    /**
     * @return the y coordinate of this position.
     */
    public int getY() {
        return Reflections.invoke(base_getY, int.class, root);
    }

    /**
     * @return the z coordinate of this position.
     */
    public int getZ() {
        return Reflections.invoke(base_getZ, int.class, root);
    }

    /**
     * Changes the x coordinate of this position.
     *
     * @param x the new value.
     */
    public void setX(int x) {
        Reflections.invoke(base_setX, Void.class, root, x);
    }

    /**
     * Changes the y coordinate of this position.
     *
     * @param y the new value.
     */
    public void setY(int y) {
        Reflections.invoke(base_setY, Void.class, root, y);
    }

    /**
     * Changes the z coordinate of this position.
     *
     * @param z the new value.
     */
    public void setZ(int z) {
        Reflections.invoke(base_setZ, Void.class, root, z);
    }

    // TODO: directional methods.

}
