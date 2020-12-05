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

package net.iceyleagons.icicle.ui.components;

import net.iceyleagons.icicle.ui.GUIClickEvent;
import net.iceyleagons.icicle.ui.GUITemplate;
import org.bukkit.inventory.ItemStack;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.2.0-SNAPSHOT
 */
public interface ComponentTemplate {

    /**
     * Used for rendering. ex. toRender[0][0] = item
     * [0][0] is the slot where it's placed
     *
     * @param toRender
     */
    void render(ItemStack[][] toRender);

    /**
     * Sets the X and Y coordinates for this template.
     * Invoked internally.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    void setXY(int x, int y);

    /**
     * @return true if the component should be rendered false to "hide" it
     */
    boolean renderAllowed();

    /**
     * Sets whether the component should be rendered or not
     * Requires {@link GUITemplate#update()}
     *
     * @param value if true then it will get rendered, otherwise no
     */
    void setRenderAllowed(boolean value);

    /**
     * @return the X coordinate of the component
     */
    int getX();

    /**
     * @return the Y coordinate of the component
     */
    int getY();

    /**
     * Run when a player clicks on the component.
     * The {@link org.bukkit.event.inventory.InventoryClickEvent} is cancelled by default.
     *
     * @param clickEvent the clickEvent
     */
    void onClick(GUIClickEvent clickEvent);


}
