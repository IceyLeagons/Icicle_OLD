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

package net.iceyleagons.icicle.ui.components.impl;

import net.iceyleagons.icicle.ui.GUITemplate;
import net.iceyleagons.icicle.ui.components.Component;
import net.iceyleagons.icicle.ui.components.ComponentTemplate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

/**
 * @author TOTHTOMI
 */
@Component(
        id = "button",
        width = 1, //Takes up one slot
        height = 1 // /\
)
public class Button implements ComponentTemplate {

    private final ItemStack placeholder;
    private final BiConsumer<GUITemplate, InventoryClickEvent> onClick;
    private int x,y;

    public Button(ItemStack placeholder, BiConsumer<GUITemplate, InventoryClickEvent> onClick) {
        this.placeholder = placeholder;
        this.onClick = onClick;
    }

    @Override
    public void render(ItemStack[][] toRender) {
        toRender[0][0] = placeholder;
    }

    @Override
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public BiConsumer<GUITemplate, InventoryClickEvent> onClick() {
        return this.onClick;
    }

}
