package net.iceyleagons.icicle.ui.render;

import org.bukkit.inventory.ItemStack;

public interface UIRenderable {

    /**
     * @return the size of the object on the x coordinate
     */
    int getWidth();

    /**
     * @return the size of the object on the y coordinate
     */
    int getHeight();

    /**
     * The passed ItemStack matrix has the following properties:
     *
     * - It should be treated as a relative coordinate space aka. the [0][0] is the starting position.
     * - The sizes are the following [{@link #getWidth()}][{@link #getHeight()}]
     *
     * @param toRender an ItemStack[][] to be modified.
     */
    void render(ItemStack[][] toRender);

}
