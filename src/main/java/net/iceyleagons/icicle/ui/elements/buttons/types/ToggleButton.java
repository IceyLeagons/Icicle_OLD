package net.iceyleagons.icicle.ui.elements.buttons.types;

import lombok.Getter;
import net.iceyleagons.icicle.ui.UserInterface;
import net.iceyleagons.icicle.ui.elements.buttons.Button;
import net.iceyleagons.icicle.utils.TriConsumer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ToggleButton extends Button {

    private final TriConsumer<InventoryClickEvent, UserInterface, ToggleButton> onClick;

    private final ItemStack on;
    private final ItemStack off;

    private ItemStack current;

    @Getter
    private boolean toggled = false;

    public ToggleButton(int w, int h, ItemStack on, ItemStack off, TriConsumer<InventoryClickEvent, UserInterface, ToggleButton> onClick) {
        super(w, h, off);

        this.on = on;
        this.off = off;
        this.current = off;

        this.onClick = onClick;
    }

    public void updateState(boolean toggled) {
        this.toggled = toggled;
        this.current = this.toggled ? this.on : this.off;
    }

    @Override
    public void render(ItemStack[][] items) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                items[i][j] = this.current.clone();
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event, UserInterface userInterface) {
        updateState(!isToggled());
        userInterface.update();
        this.onClick.accept(event, userInterface, this);
    }
}
