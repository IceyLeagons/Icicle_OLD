package net.iceyleagons.icicle.ui.elements.buttons;

import net.iceyleagons.icicle.ui.UIInteractable;
import net.iceyleagons.icicle.ui.elements.BasicPane;
import org.bukkit.inventory.ItemStack;

public abstract class Button extends BasicPane implements UIInteractable {

    private final ItemStack itemStack;

    public Button(int w, int h, ItemStack itemStack) {
        super(w, h);
        this.itemStack = itemStack;
    }

    @Override
    public void render(ItemStack[][] items) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                items[i][j] = itemStack.clone();
            }
        }
    }
}
