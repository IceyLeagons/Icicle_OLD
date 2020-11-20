package net.iceyleagons.icicle.ui.elements.buttons;

import net.iceyleagons.icicle.ui.elements.Pane;
import net.iceyleagons.icicle.ui.interfaces.ClickRunnable;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Toggle button
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class ToggleButton extends Button{

    private final ClickRunnable clickRunnable;
    private final ItemStack off;
    private final ItemStack on;
    private final Pane pane;

    private final Consumer<Boolean> booleanConsumer;
    private boolean enabled = false;


    /**
     * Toggle buton with self display. <p>It will update itself</p>
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param off off {@link ItemStack}
     * @param on on {@link ItemStack}
     * @param clickRunnable {@link ClickRunnable}
     * @param result consumer, will be run every update
     */
    public ToggleButton(int x, int y, ItemStack off, ItemStack on, ClickRunnable clickRunnable, Consumer<Boolean> result) {
        super(x, y, off);
        this.off = off;
        this.on = on;
        this.clickRunnable = clickRunnable;
        this.booleanConsumer = result;
        this.pane = null;
    }

    /**
     * Toggle button with {@link Pane} display <p>The {@link Pane} will be updated not the button</p>
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param button the button's {@link ItemStack}
     * @param off off {@link ItemStack}
     * @param on on {@link ItemStack}
     * @param pane {@link Pane} to use
     * @param clickRunnable {@link ClickRunnable}
     * @param result consumer, will be run every update
     */
    public ToggleButton(int x, int y, ItemStack button, ItemStack off, ItemStack on,  Pane pane, ClickRunnable clickRunnable, Consumer<Boolean> result) {
        super(x, y, button);
        this.off = off;
        this.on = on;
        this.clickRunnable = clickRunnable;
        this.booleanConsumer = result;
        this.pane = pane;
        updateItem();
    }

    /**
     * Sets item, updates inventory
     */
    private void updateItem() {
        if (pane == null) super.setItemStack(enabled ? on : off);
        else pane.setItemStack(enabled ? on : off);
        if (super.getMenu() != null)
        super.getMenu().update();
    }

    @Override
    public ClickRunnable getClickRunnable() {
        return event -> {
            enabled = !enabled;
            updateItem();
            booleanConsumer.accept(enabled);
            clickRunnable.onClick(event);
        };
    }
}
