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

package net.iceyleagons.icicle.schematic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;

/**
 * @author TOTHTOMI
 */
@RequiredArgsConstructor
public class Schematic {

    private final short[] blocks;
    private final byte[] data;

    @Getter
    private final short width;
    @Getter
    private final short height;
    @Getter
    private final short length;


    /**
     * Pastes the schematic. Does not load in entities, used only for blocks atm!
     *
     * @param location                the location to paste to
     * @param yOffset                 the y coordinate offsate
     * @param ignoreAir               if true the pasting will not place down air blocks
     * @param keepOccupiedSolidBlocks if true the already placed solid blocks will not be affected.
     * @param applyPhysics            whether to apply physics to the placed block
     */
    public void paste(Location location, int yOffset, boolean ignoreAir, boolean keepOccupiedSolidBlocks, boolean applyPhysics) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < length; z++) {

                    int i = y * width * length + z * width + x;
                    int block = blocks[i] & 0xFF;
                    int blockData = data[i] & 0xFF;

                    String bData = SchematicLoader.ids.get(block + ":" + blockData);

                    int xCoord = location.getBlockX() - (width >> 1) + x;
                    int yCoord = location.getBlockY() + yOffset + y;
                    int zCoord = location.getBlockZ() - (length >> 1) + z;

                    Block block1 = new Location(location.getWorld(), xCoord, yCoord, zCoord).getBlock();
                    BlockData blockData1 = Bukkit.createBlockData(bData);
                    if (ignoreAir && blockData1.getMaterial().isAir()) continue;
                    if (keepOccupiedSolidBlocks && !block1.getType().isAir()) continue;

                    block1.setBlockData(blockData1, applyPhysics);
                }
            }
        }
    }

    /**
     * Pastes the schematic. Does not load in entities, used only for blocks atm!
     *
     * @param chunkData               the {@link org.bukkit.generator.ChunkGenerator.ChunkData} to modify
     * @param x                       the x coordinate
     * @param y                       the y coordinate
     * @param z                       the z coordinate
     * @param yOffset                 the y coordinate offsate
     * @param ignoreAir               if true the pasting will not place down air blocks
     * @param keepOccupiedSolidBlocks if true the already placed solid blocks will not be affected.
     */
    public void paste(ChunkGenerator.ChunkData chunkData, int x, int y, int z, int yOffset, boolean ignoreAir, boolean keepOccupiedSolidBlocks) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < length; k++) {

                    int index = j * width * length + k * width + i;
                    int block = blocks[index] & 0xFF;
                    int blockData = data[index] & 0xFF;

                    String bData = SchematicLoader.ids.get(block + ":" + blockData);

                    int xCoord = x - (width >> 1) + i;
                    int yCoord = y + yOffset + j;
                    int zCoord = z - (length >> 1) + k;

                    BlockData blockData1 = Bukkit.createBlockData(bData);
                    if (ignoreAir && blockData1.getMaterial().isAir()) continue;
                    if (keepOccupiedSolidBlocks && !chunkData.getType(xCoord, yCoord, zCoord).isAir()) continue;

                    chunkData.setBlock(xCoord, yCoord, zCoord, blockData1);
                }
            }
        }
    }
}
