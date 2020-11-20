/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle.ui.elements.buttons;

import net.iceyleagons.icicle.InventoryUtils;
import net.iceyleagons.icicle.ui.Menu;
import net.iceyleagons.icicle.ui.interfaces.ClickRunnable;
import net.iceyleagons.icicle.ui.interfaces.UIElement;
import org.bukkit.inventory.ItemStack;

/**
 * Everything that's clickable shoudl extend this.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class Button implements UIElement {

    private final int slot;
    private ItemStack itemStack;
    private Menu menu;

    /**
     * @param x x coordinate
     * @param y y coordinate
     * @param itemStack the {@link ItemStack}
     */
    public Button(int x, int y, ItemStack itemStack) {
        this(InventoryUtils.calculateSlotFromXY(x,y),itemStack);
    }

    /**
     * @param slot the slot
     * @param itemStack the {@link ItemStack}
     */
    public Button(int slot,ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
        menu = null;
    }

    /**
     * Set internally, don't mess with it :)
     */
    @Override
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public Menu getMenu() {
        return menu;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public abstract ClickRunnable getClickRunnable();
}
