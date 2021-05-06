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

package net.iceyleagons.icicle.common.wrapped.biome;

import lombok.Getter;
import net.iceyleagons.icicle.common.wrapped.utils.WrappedClass;
import org.bukkit.Location;

/**
 * Wrapped representation of BiomeStorage
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedBiomeStorage {

    static {
        WrappedClass.getNMSClass("BiomeStorage").lookupMethod(Void.class, "setBiome", null, int.class, int.class, int.class, WrappedClass.getNMSClass("BiomeBase").getClazz());
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
        WrappedClass.getNMSClass("BiomeStorage").getMethod("setBiome").invokeNoReturn(storage, x, y, z, biomeBase.getRoot());
    }

    /**
     * Changes the biome at the specified position to the specified biome.
     *
     * @param location  self-explanatory.
     * @param biomeBase the biome we wish to change it into.
     */
    public void setBiome(Location location, WrappedBiomeBase biomeBase) {
        setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), biomeBase);
    }

}
