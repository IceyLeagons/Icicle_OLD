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
     * This will reverse our {@link #calculateSlotFromXY(int, int)} and spit out an int array where
     * element 0 is the X coordinate and element 1 is the Y coordinate
     *
     * @param slot the inventory slot
     * @return an int array (0 -> X, 1 -> Y)
     */
    public static int[] calculateXYFromSlot(int slot) {
        return new int[] { slot % 9 + 1, slot / 9 + 1 };
    }

    /**
     * This is used to play a click sound for the player.
     * We use Sound.BLOCK_WOODEN_BUTTON_CLICK_ON
     *
     * @param event the {@link InventoryClickEvent}
     * @param sound the {@link Sound} to play
     */
    public static void clickSound(InventoryClickEvent event, Sound sound) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(event.getWhoClicked().getLocation(), sound, 1f, 1);
    }
}
