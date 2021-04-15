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

package net.iceyleagons.icicle.wrapping.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapped representation of Item
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
@RequiredArgsConstructor
@Getter
public class WrappedItem {

    private static final Class<?> mc_Item;
    private static final Map<Object, Integer> burnMap;

    private static final Field mc_maxStackSize;


    static {
        mc_Item = Reflections.getNormalNMSClass("Item");

        Class<?> mc_EntityFurnace = Reflections.getNormalNMSClass("TileEntityFurnace");
        burnMap = setupBurnMap(mc_EntityFurnace);

        mc_maxStackSize = Reflections.getField(mc_Item, "maxStackSize", true);

    }

    private final Object nmsItem;

    @SuppressWarnings("unchecked")
    private static Map<Object, Integer> setupBurnMap(Class<?> mc_EntityFurnace) {
        Method method = Reflections.getMethod(mc_EntityFurnace, "f", true);
        if (method == null) return new HashMap<>();
        return (Map<Object, Integer>) Reflections.invoke(method, Map.class, null);
    }

    /**
     * @return the maximum stack size for this item.
     */
    public Integer getMaxStackSize() {
        return Reflections.get(mc_maxStackSize, Integer.class, nmsItem);
    }

    /**
     * Changes the maximum stack size (can go above 64).
     * However since Minecraft's protocol sends/reads item counts as a (signed) byte, this is limited to only 127!
     * <p>
     * There's a working work-around around this, but it requires entities so we refrain from doing so.
     *
     * @param value the value to set min. 0 max. 127
     */
    public void setMaxStackSize(int value) {
        if (value < -127) return;
        if (value > 127)
            throw new IllegalArgumentException("Maximum stack size is limited to 127 due to the MC protocol");

        Reflections.set(mc_maxStackSize, nmsItem, value);
    }

    /**
     * @return the burn time of this item.
     */
    public int getBurnTime() {
        return burnMap.getOrDefault(nmsItem, 0);
    }
}
