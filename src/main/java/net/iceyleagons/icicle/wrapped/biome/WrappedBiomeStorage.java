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

package net.iceyleagons.icicle.wrapped.biome;

import lombok.Getter;
import net.iceyleagons.icicle.reflect.Reflections;
import org.bukkit.Location;

import java.lang.reflect.Method;

/**
 * Wrapped representation of BiomeStorage
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedBiomeStorage {
    private static final Class<?> mc_BiomeStorage;
    private static final Method storage_setBiome;

    static {
        mc_BiomeStorage = Reflections.getNormalNMSClass("BiomeStorage");

        storage_setBiome = Reflections.getMethod(mc_BiomeStorage, "setBiome", true, int.class, int.class, int.class, WrappedBiomeBase.mc_BiomeBase);
    }

    @Getter
    private final Object storage;

    public WrappedBiomeStorage(Object storage) {
        this.storage = storage;
    }

    /**
     * Changes the biome at the specified position to the specified biome.
     *
     * @param x         self-explanatory.
     * @param y         self-explanatory.
     * @param z         self-explanatory.
     * @param biomeBase the biome we wish to change it into.
     */
    public void setBiome(int x, int y, int z, WrappedBiomeBase biomeBase) {
        Reflections.invoke(storage_setBiome, Void.class, storage, x, y, z, biomeBase.getRoot());
    }

    /**
     * Changes the biome at the specified position to the specified biome.
     *
     * @param location  self-explanatory.
     * @param biomeBase the biome we wish to change it into.
     */
    public void setBiome(Location location, WrappedBiomeBase biomeBase) {
        Reflections.invoke(storage_setBiome, Void.class, storage, location.getBlockX(), location.getBlockY(), location.getBlockZ(), biomeBase.getRoot());
    }

}
