package net.iceyleagons.icicle.ui.buttons;

import net.iceyleagons.icicle.ui.BasicPane;
import net.iceyleagons.icicle.ui.UIInteractable;
import net.iceyleagons.icicle.ui.UserInterface;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class Button extends BasicPane implements UIInteractable {

    public Button(int w, int h) {
        super(w, h);
    }

    @Override
    public void onClick(InventoryClickEvent event, UserInterface userInterface) {

    }
}
