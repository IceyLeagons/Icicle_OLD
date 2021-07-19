package net.iceyleagons.icicle.ui.elements.buttons.types;

import lombok.Getter;
import net.iceyleagons.icicle.ui.UserInterface;
import net.iceyleagons.icicle.ui.elements.buttons.Button;
import net.iceyleagons.icicle.utils.TriConsumer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CycleButton extends Button {

    private final TriConsumer<InventoryClickEvent, UserInterface, CycleButton> onClick;
    private final ItemStack[] options;

    private ItemStack current;

    @Getter
    private int selected = 0;

    public CycleButton(int w, int h, ItemStack[] options, TriConsumer<InventoryClickEvent, UserInterface, CycleButton> onClick) {
        super(w, h, options[0]);
        this.options = options;
        this.onClick = onClick;
    }

    public void setSelected(int selected) {
        this.selected = selected;
        this.current = this.options[selected];
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
        setSelected(getNext());
        userInterface.update();

        this.onClick.accept(event, userInterface, this);
    }

    private int getNext() {
        if (selected >= this.options.length) selected = 0;
        return selected += 1;
    }
}
