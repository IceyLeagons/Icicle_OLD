package net.iceyleagons.icicle.ui.interaction;

import net.iceyleagons.icicleold.utils.item.InventoryUtils;
import net.iceyleagons.icicle.ui.BaseUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

public class UIInteractionHandler implements Listener {

    private final BaseUI baseUI;

    public UIInteractionHandler(BaseUI baseUI) {
        this.baseUI = baseUI;
        baseUI.getJavaPlugin().getServer().getPluginManager().registerEvents(this, baseUI.getJavaPlugin());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            if (Arrays.equals(event.getClickedInventory().getContents(), baseUI.getTemplate().getContents())) {
                invokeIfPresentAtSlot(event.getSlot(), event);
            }
        }
    }

    private void invokeIfPresentAtSlot(int slot, InventoryClickEvent event) {
        baseUI.getElements().forEach((s, element) -> {
            if (element instanceof UIInteractable) {
                int[] coords = InventoryUtils.calculateXYFromSlot(s);
                int x = coords[0];
                int y = coords[1];

                for (int i = x; i < element.getWidth(); i++) {
                    for (int j = y; j < element.getHeight(); j++) {
                        if (slot == InventoryUtils.calculateSlotFromXY(i, j)) {
                            ((UIInteractable) element).onClicked(event);
                        }
                    }
                }
            }
        });
    }

    //TODO

}
