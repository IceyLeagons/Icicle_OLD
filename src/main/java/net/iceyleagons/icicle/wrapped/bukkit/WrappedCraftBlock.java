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

package net.iceyleagons.icicle.wrapped.bukkit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.biome.WrappedBiomeBase;
import net.iceyleagons.icicle.wrapped.registry.WrappedIRegistry;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

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

    private static final Class<?> craftBlockClass;

    private static final Method breakNaturally;
    private static final Method biomeBaseToBiome;
    private static final Method biomeToBiomeBase;

    static {
        craftBlockClass = Reflections.getNormalCBClass("block.CraftBlock");

        Class<?> registry = Reflections.getNormalNMSClass("IRegistry");
        Class<?> biomeBase = Reflections.getNormalNMSClass("BiomeBase");

        breakNaturally = Reflections.getMethod(craftBlockClass, "breakNaturally", true, ItemStack.class, boolean.class);
        biomeBaseToBiome = Reflections.getMethod(craftBlockClass, "biomeBaseToBiome", true, registry, biomeBase);
        biomeToBiomeBase = Reflections.getMethod(craftBlockClass, "biomeToBiomeBase", true, registry, Biome.class);
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
        return Reflections.invoke(biomeBaseToBiome, Biome.class, craftBlockClass, WrappedIRegistry.BIOME, biomeBase.getRoot());
    }

    /**
     * Converts a bukkit Biome into a wrapped BiomeBase.
     *
     * @param biome the bukkit biome.
     * @return the wrapped biomebase representation of the bukkit biome.
     */
    public static WrappedBiomeBase biomeToBiomeBase(Biome biome) {
        return new WrappedBiomeBase(Reflections.invoke(biomeToBiomeBase, Object.class, craftBlockClass, WrappedIRegistry.BIOME, biome));
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
        breakNaturally.invoke(block, itemStack, triggerSound);
    }

}
