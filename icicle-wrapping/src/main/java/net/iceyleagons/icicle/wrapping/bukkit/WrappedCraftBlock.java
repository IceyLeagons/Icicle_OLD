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

package net.iceyleagons.icicle.wrapping.bukkit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.wrapping.biome.WrappedBiomeBase;
import net.iceyleagons.icicle.wrapping.utils.WrappedClass;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * Wrapped representation of CraftBlock.
 * Contains useful functions, that are not implemented in the {@link Block} interface directly
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.4.0-SNAPSHOT
 */
@RequiredArgsConstructor
public class WrappedCraftBlock {
    static {
        WrappedClass.getCBClass("block.CraftBlock")
                .lookupMethod("breakNaturally", null, ItemStack.class, boolean.class)
                .lookupMethod(Biome.class, "biomeBaseToBiome", null, WrappedClass.getNMSClass("IRegistry").getClazz(), WrappedClass.getNMSClass("BiomeBase").getClazz())
                .lookupMethod("biomeToBiomeBase", null, WrappedClass.getNMSClass("IRegistry").getClazz(), Biome.class);
    }

    @Getter
    private final Block block;

    public static WrappedCraftBlock from(Block block) {
        return new WrappedCraftBlock(block);
    }

    /**
     * Converts a wrapped biomebase to a bukkit biome.
     *
     * @param biomeBase the wrapped biome.
     * @return null if it's a custom biome, the bukkit Biome if it's a vanilla biome.
     */
    public static Biome biomeBaseToBiome(WrappedBiomeBase biomeBase) {
        return (Biome) WrappedClass.getCBClass("block.CraftBlock").getMethod("biomeBaseToBiome").invoke(null, biomeBase.getRoot());
    }

    /**
     * Converts a bukkit Biome into a wrapped BiomeBase.
     *
     * @param biome the bukkit biome.
     * @return the wrapped biomebase representation of the bukkit biome.
     */
    public static WrappedBiomeBase biomeToBiomeBase(Biome biome) {
        return new WrappedBiomeBase(WrappedClass.getCBClass("block.CraftBlock").getMethod("biomeToBiomeBase").invoke(null, biome));
    }

    /**
     * Self-explanatory, eh?
     * <p>
     * Breaks the block with the sound enabled.
     *
     * @param itemStack    the item stack we want to drop.
     * @param triggerSound whether or not we want it to play the block-break sound.
     */
    @SneakyThrows
    public void breakNaturally(ItemStack itemStack, boolean triggerSound) {
        WrappedClass.getCBClass("block.CraftBlock").getMethod("breakNaturally").invokeNoReturn(block, itemStack, triggerSound);
    }

}
