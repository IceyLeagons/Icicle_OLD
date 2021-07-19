package net.iceyleagons.icicle.ui.elements;

import org.bukkit.inventory.ItemStack;

public class SimplePane extends BasicPane {

    private final ItemStack itemStack;

    public SimplePane(int w, int h, ItemStack itemStack) {
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
