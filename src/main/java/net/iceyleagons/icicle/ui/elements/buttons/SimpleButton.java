package net.iceyleagons.icicle.ui.elements.buttons;

import net.iceyleagons.icicle.ui.interfaces.ClickRunnable;
import org.bukkit.inventory.ItemStack;

/**
 * Simplest form of a {@link Button}
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */

public class SimpleButton extends Button{

    private final ClickRunnable clickRunnable;

    /**
     * Toggle buton with a {@link ClickRunnable}
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param itemStack the Button's @link ItemStack}
     * @param clickRunnable {@link ClickRunnable}
     */
    public SimpleButton(int x, int y, ItemStack itemStack,ClickRunnable clickRunnable) {
        super(x, y, itemStack);
        this.clickRunnable = clickRunnable;
    }

    @Override
    public ClickRunnable getClickRunnable() {
        return this.clickRunnable;
    }
}
