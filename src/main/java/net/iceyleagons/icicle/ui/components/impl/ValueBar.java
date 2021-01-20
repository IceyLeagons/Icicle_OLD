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

package net.iceyleagons.icicle.ui.components.impl;

import lombok.Getter;
import lombok.Setter;
import net.iceyleagons.icicle.item.ItemFactory;
import net.iceyleagons.icicle.ui.GUIClickEvent;
import net.iceyleagons.icicle.ui.components.Component;
import net.iceyleagons.icicle.ui.components.ComponentTemplate;
import net.iceyleagons.icicle.ui.components.OrientableComponent;
import net.iceyleagons.icicle.ui.components.VariableSizeComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author TOTHTOMI
 */
@Component(
        id = "value_bar",
        width = -1, //Takes up one slot
        height = -1 // /\
)
public class ValueBar implements ComponentTemplate, OrientableComponent, VariableSizeComponent {

    @Getter
    private final double maxValue;
    @Getter
    private final int bars;
    @Getter
    @Setter
    private double value;
    @Getter
    @Setter
    private int rotation;
    @Getter
    @Setter
    private int width;
    @Getter
    @Setter
    private int height;
    @Getter
    @Setter
    private Orientation orientation = Orientation.HORIZONTAL;
    @Getter
    private int x;
    @Getter
    private int y;
    @Setter
    private boolean renderAllowed = true;

    public ValueBar(int maxValue, int bars) {
        this.maxValue = maxValue;
        this.bars = bars;
        updateWidthHeight();
    }

    public void updateWidthHeight() {
        switch (getOrientation()) {
            case VERTICAL:
                height = bars;
                width = 1;
                break;
            case HORIZONTAL:
                width = bars;
                height = 1;
                break;
        }
    }

    @Override
    public void render(ItemStack[][] toRender) {
        updateWidthHeight();
        ItemStack[] stacks = getItemStacksForCurrentValue();

        switch (getOrientation()) {
            case HORIZONTAL:
                for (int i = 0; i < maxValue; i++) {
                    toRender[i][0] = stacks[i];
                }
                break;
            case VERTICAL:
                for (int i = 0; i < maxValue; i++) {
                    toRender[0][i] = stacks[i];
                }
                break;
        }
    }

    private ItemStack[] getItemStacksForCurrentValue() {
        ItemStack[] itemStacks = new ItemStack[bars];

        double divide = getMaxValue() / bars;

        double barsToFill = Math.round(value / divide);


        for (int i = 0; i < bars; i++)
            itemStacks[i] = ItemFactory.newFactory(Material.GRAY_STAINED_GLASS_PANE).build();

        for (int i = 0; i < barsToFill; i++)
            itemStacks[i] = ItemFactory.newFactory(Material.RED_STAINED_GLASS_PANE).build();


        return itemStacks;
    }

    @Override
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean renderAllowed() {
        return this.renderAllowed;
    }

    @Override
    public void onClick(GUIClickEvent clickEvent) {
        clickEvent.getGUI().update();
    }
}
