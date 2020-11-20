package net.iceyleagons.icicle.ui.elements.buttons;

import net.iceyleagons.icicle.InventoryUtils;
import net.iceyleagons.icicle.ui.Menu;
import net.iceyleagons.icicle.ui.interfaces.ClickRunnable;
import net.iceyleagons.icicle.ui.interfaces.UIElement;
import org.bukkit.inventory.ItemStack;

/**
 * Everything that's clickable shoudl extend this.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class Button implements UIElement {

    private final int slot;
    private ItemStack itemStack;
    private Menu menu;

    /**
     * @param x x coordinate
     * @param y y coordinate
     * @param itemStack the {@link ItemStack}
     */
    public Button(int x, int y, ItemStack itemStack) {
        this(InventoryUtils.calculateSlotFromXY(x,y),itemStack);
    }

    /**
     * @param slot the slot
     * @param itemStack the {@link ItemStack}
     */
    public Button(int slot,ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
        menu = null;
    }

    /**
     * Set internally, don't mess with it :)
     */
    @Override
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public Menu getMenu() {
        return menu;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public abstract ClickRunnable getClickRunnable();
}
