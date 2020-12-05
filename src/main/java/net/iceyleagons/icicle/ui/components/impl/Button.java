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

import net.iceyleagons.icicle.ui.GUIClickEvent;
import net.iceyleagons.icicle.ui.components.ComponentTemplate;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Contains all basic functions for a button.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.2.0-SNAPSHOT
 */
public abstract class Button implements ComponentTemplate {

    private final ItemStack placeholder;
    private final Consumer<GUIClickEvent> onClick;
    private int x,y;
    private boolean render;

    public Button(ItemStack placeholder, Consumer<GUIClickEvent> onClick) {
        this.placeholder = placeholder;
        this.onClick = onClick;
        this.render = true;
    }

    @Override
    public boolean renderAllowed() {
        return render;
    }

    @Override
    public void setRenderAllowed(boolean value) {
        this.render = value;
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
    public void onClick(GUIClickEvent guiClickEvent) {
        this.onClick.accept(guiClickEvent);
    }

}
