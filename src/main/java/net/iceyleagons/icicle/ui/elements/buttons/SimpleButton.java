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

import net.iceyleagons.icicle.ui.interfaces.ClickRunnable;
import org.bukkit.inventory.ItemStack;

/**
 * Simplest form of a {@link Button}
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */

public class SimpleButton extends Button{

    private final ClickRunnable clickRunnable;

    /**
     * Toggle buton with a {@link ClickRunnable}
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param itemStack the Button's @link ItemStack}
     * @param clickRunnable {@link ClickRunnable}
     */
    public SimpleButton(int x, int y, ItemStack itemStack,ClickRunnable clickRunnable) {
        super(x, y, itemStack);
        this.clickRunnable = clickRunnable;
    }

    @Override
    public ClickRunnable getClickRunnable() {
        return this.clickRunnable;
    }
}
