package net.iceyleagons.icicle.ui;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface UIInteractable {

    void onClick(InventoryClickEvent event, UserInterface userInterface);

}
