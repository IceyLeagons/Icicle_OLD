package net.iceyleagons.icicle.ui.elements.buttons.types;

import net.iceyleagons.icicle.ui.UserInterface;
import net.iceyleagons.icicle.ui.elements.buttons.Button;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public class BasicButton extends Button {

    private final BiConsumer<InventoryClickEvent, UserInterface> onClick;

    public BasicButton(int w, int h, ItemStack itemStack, BiConsumer<InventoryClickEvent, UserInterface> onClick) {
        super(w, h, itemStack);
        this.onClick = onClick;
    }

    @Override
    public void onClick(InventoryClickEvent event, UserInterface userInterface) {
        this.onClick.accept(event, userInterface);
    }
}
