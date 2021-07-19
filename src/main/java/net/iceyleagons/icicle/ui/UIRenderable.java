package net.iceyleagons.icicle.ui;

import org.bukkit.inventory.ItemStack;

public interface UIRenderable {

    void render(ItemStack[][] items);

    boolean isVisible();

    int getWidth();
    int getHeight();

    int getX();
    int getY();

    void setX(int x);
    void setY(int y);
}
