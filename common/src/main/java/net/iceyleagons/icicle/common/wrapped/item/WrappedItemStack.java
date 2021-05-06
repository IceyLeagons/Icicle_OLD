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

package net.iceyleagons.icicle.common.wrapped.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.common.reflect.Reflections;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

/**
 * Wrapped representation of ItemStack (NMS)
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
@RequiredArgsConstructor
@Getter
public class WrappedItemStack {
    private static final Class<?> cb_CraftItemStack;
    private static final Class<?> mc_ItemStack;

    private static final Method cb_asNMSCopy;
    private static final Method cb_asBukkitCopy;

    private static final Method mc_getRepairCost;
    private static final Method mc_setRepairCost;

    private static final Method mc_getItem;

    static {
        cb_CraftItemStack = Reflections.getNormalCBClass("inventory.CraftItemStack");
        mc_ItemStack = Reflections.getNormalNMSClass("ItemStack");

        cb_asNMSCopy = Reflections.getMethod(cb_CraftItemStack, "asNMSCopy", true, ItemStack.class);
        cb_asBukkitCopy = Reflections.getMethod(cb_CraftItemStack, "asBukkitCopy", true, mc_ItemStack);


        mc_getRepairCost = Reflections.getMethod(mc_ItemStack, "getRepairCost", true);
        mc_setRepairCost = Reflections.getMethod(mc_ItemStack, "setRepairCost", true, int.class);
        mc_getItem = Reflections.getMethod(mc_ItemStack, "getItem", true);

    }

    private final Object nmsItemStack;

    /**
     * Instantiates a WrappedItemStack from a bukkit ItemStack.
     *
     * @param itemStack bukkit ItemStack we wish to convert.
     * @return a wrapped itemstack.
     */
    public static WrappedItemStack asNMSCopy(ItemStack itemStack) {
        Object object = Reflections.invoke(cb_asNMSCopy, Object.class, cb_CraftItemStack, itemStack);
        if (object == null) return null;

        return new WrappedItemStack(object);
    }

    /**
     * Converts a wrapped itemstack into a bukkit ItemStack.
     *
     * @param wrappedItemStack self-explanatory.
     * @return a bukkit itemstack.
     */
    public static ItemStack asBukkitCopy(WrappedItemStack wrappedItemStack) {
        return Reflections.invoke(cb_asBukkitCopy, ItemStack.class, cb_CraftItemStack, wrappedItemStack.getNmsItemStack());
    }

    /**
     * @return the item for this itemstack.
     */
    public WrappedItem getItem() {
        Object o = Reflections.invoke(mc_getItem, Object.class, nmsItemStack);
        return o == null ? null : new WrappedItem(o);
    }

    /**
     * @return the repair cost of this itemstack.
     */
    public Integer getRepairCost() {
        return Reflections.invoke(mc_getRepairCost, Integer.class, nmsItemStack);
    }

    /**
     * Changes the repair cost of this itemstack to the specified value.
     *
     * @param cost the new repair cost.
     */
    public void setRepairCost(int cost) {
        Reflections.invoke(mc_setRepairCost, Void.class, nmsItemStack, cost);
    }

    /**
     * @return bukkit itemstack clone of this.
     */
    public ItemStack getBukkitCopy() {
        return asBukkitCopy(this);
    }

}
