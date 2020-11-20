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

package net.iceyleagons.icicle.ui.interfaces;

import net.iceyleagons.icicle.ui.Menu;
import org.bukkit.inventory.ItemStack;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UIElement {

    /**
     * Sets the menu of this {@link UIElement} (it's parent)
     * <b>Internal use!</b>
     *
     * @param menu {@link Menu} to set
     */
    void setMenu(Menu menu);

    /**
     * @return the {@link Menu} parent of this element
     */
    Menu getMenu();

    /**
     * @return the slot of this element
     */
    int getSlot();

    /**
     * Sets the {@link ItemStack} of this element. Requires {@link Menu#update()} afterwards.
     *
     * @param itemStack to set to
     */
    void setItemStack(ItemStack itemStack);

    /**
     * @return the {@link ItemStack} of this element
     */
    ItemStack getItemStack();

    /**
     * @return the {@link ClickRunnable} of this element
     */
    ClickRunnable getClickRunnable();

}
