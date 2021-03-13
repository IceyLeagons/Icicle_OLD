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

package net.iceyleagons.icicle.location.block;

import org.bukkit.block.BlockFace;

import java.util.EnumMap;

/**
 * <b>Some of the code parts are from, but not limited to https://github.com/bergerkiller/BKCommonLib/</b>
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class BlockUtils {
    public static final BlockFace[] AXIS = new BlockFace[4];
    public static final BlockFace[] RADIAL = {BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.NORTH,
            BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST};
    private static final EnumMap<BlockFace, Integer> notches = new EnumMap<>(BlockFace.class);

    static {
        for (int i = 0; i < RADIAL.length; i++) {
            notches.put(RADIAL[i], i);
        }
        for (int i = 0; i < AXIS.length; i++) {
            AXIS[i] = RADIAL[i << 1];
        }
    }

    /**
     * Gets the Notch integer representation of a BlockFace<br>
     * <b>These are the horizontal faces, which exclude up and down</b>
     *
     * @param face to get
     * @return Notch of the face
     */
    public static int faceToNotch(BlockFace face) {
        Integer notch = notches.get(face);
        return notch == null ? 0 : notch;
    }

    /**
     * Gets the Block Face at the notch index specified<br>
     * <b>These are the horizontal faces, which exclude up and down</b>
     *
     * @param notch to get
     * @return BlockFace of the notch
     */
    public static BlockFace notchToFace(int notch) {
        return RADIAL[notch & 0x7];
    }

    /**
     * Rotates a given Block Face horizontally
     *
     * @param from       face
     * @param notchCount to rotate at
     * @return rotated face
     */
    public static BlockFace rotate(BlockFace from, int notchCount) {
        return notchToFace(faceToNotch(from) + notchCount);
    }

    /**
     * Gets whether a given Block Face is sub-cardinal (such as NORTH_WEST)
     *
     * @param face to check
     * @return True if sub-cardinal, False if not
     */
    public static boolean isSubCardinal(final BlockFace face) {
        switch (face) {
            case NORTH_EAST:
            case SOUTH_EAST:
            case SOUTH_WEST:
            case NORTH_WEST:
                return true;
            default:
                return false;
        }
    }
}
