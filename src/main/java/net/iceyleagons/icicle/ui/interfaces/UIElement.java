package net.iceyleagons.icicle.ui.interfaces;

import net.iceyleagons.icicle.ui.Menu;
import org.bukkit.inventory.ItemStack;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UIElement {

    /**
     * Sets the menu of this {@link UIElement} (it's parent)
     * <b>Internal use!</b>
     *
     * @param menu {@link Menu} to set
     */
    void setMenu(Menu menu);

    /**
     * @return the {@link Menu} parent of this element
     */
    Menu getMenu();

    /**
     * @return the slot of this element
     */
    int getSlot();

    /**
     * Sets the {@link ItemStack} of this element. Requires {@link Menu#update()} afterwards.
     *
     * @param itemStack to set to
     */
    void setItemStack(ItemStack itemStack);

    /**
     * @return the {@link ItemStack} of this element
     */
    ItemStack getItemStack();

    /**
     * @return the {@link ClickRunnable} of this element
     */
    ClickRunnable getClickRunnable();

}
