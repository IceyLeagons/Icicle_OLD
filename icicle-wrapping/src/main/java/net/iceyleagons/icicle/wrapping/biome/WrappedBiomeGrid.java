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

package net.iceyleagons.icicle.wrapping.biome;

import net.iceyleagons.icicle.wrapping.utils.WrappedClass;
import org.bukkit.generator.ChunkGenerator;

/**
 * Wrapping the CraftBukkit CustomBiomeGrid embedded class.
 *
 * @author Gábe
 * @version 1.0
 * @since 1.3.8-SNAPSHOT
 */
public class WrappedBiomeGrid {
    static {
        WrappedClass.getCBClass("generator.CustomChunkGenerator$CustomBiomeGrid").lookupField(Object.class, "biome");
    }

    /**
     * Gets the biome storage field from the given biomegrid.
     *
     * @param grid self-explanatory.
     * @return wrapped biome storage.
     */
    public static WrappedBiomeStorage getBiomeStorage(ChunkGenerator.BiomeGrid grid) {
        return new WrappedBiomeStorage(WrappedClass.getCBClass("generator.CustomChunkGenerator$CustomBiomeGrid").getField("biome").get(grid));
    }

}
