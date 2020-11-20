package net.iceyleagons.icicle.ui.interfaces;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ClickRunnable {

    /**
     * Run by bukkit {@link org.bukkit.event.Listener}
     * <b>Internal use</b>
     *
     * @param event the {@link InventoryClickEvent}
     */
    void onClick(InventoryClickEvent event);

}
