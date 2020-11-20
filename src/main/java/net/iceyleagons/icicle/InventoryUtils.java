package net.iceyleagons.icicle;

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
        return (x-1) + (y-1)*9;
    }

    /**
     * This is used to play a click sound for the player.
     * We use Sound.BLOCK_WOODEN_BUTTON_CLICK_ON
     *
     * @param event the {@link InventoryClickEvent}
     */
    public static void clickSound(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON,1f,1);
    }

}
