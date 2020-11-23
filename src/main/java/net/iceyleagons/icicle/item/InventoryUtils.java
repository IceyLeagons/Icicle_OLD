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

package net.iceyleagons.icicle.item;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Contains operations regarding {@link org.bukkit.inventory.Inventory}s
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class InventoryUtils {

    /**
     * This is used to bring a 2D coordinate system for the inventory.
     * X and Y must start with 1. (1 is subtracted from them)
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the calculated slot
     */
    public static int calculateSlotFromXY(int x, int y) {
        return (x - 1) + (y - 1) * 9;
    }

    /**
     * This is used to play a click sound for the player.
     * We use Sound.BLOCK_WOODEN_BUTTON_CLICK_ON
     *
     * @param event the {@link InventoryClickEvent}
     */
    public static void clickSound(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1f, 1);
    }

}
