package net.iceyleagons.icicle.ui.elements;

import net.iceyleagons.icicle.InventoryUtils;
import net.iceyleagons.icicle.ui.Menu;
import net.iceyleagons.icicle.ui.interfaces.ClickRunnable;
import net.iceyleagons.icicle.ui.interfaces.UIElement;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author TOTHTOMI
 * @deprecated Buggy AF don't use.
 */
public class Input implements UIElement {

    private final int slot;
    private final List<Material> allowedMaterials;
    private Menu menu;
    private ItemStack itemStack;
    private final Consumer<ItemStack> entered;

    public Input(int x, int y, Consumer<ItemStack> entered, Material... allowedMaterials) {
        this(InventoryUtils.calculateSlotFromXY(x, y), entered, allowedMaterials);
    }

    public Input(int slot, Consumer<ItemStack> entered, Material... allowedMaterials) {
        this.slot = slot;
        this.allowedMaterials = Arrays.asList(allowedMaterials.clone());
        this.allowedMaterials.forEach(System.out::println);
        this.menu = null;
        this.itemStack = new ItemStack(Material.AIR);
        this.entered = entered;
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
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public boolean canTake(ItemStack itemStack) {
        if (itemStack == null) return false;
        return this.allowedMaterials.contains(itemStack.getType());
    }

    @Override
    public ClickRunnable getClickRunnable() {
        return event -> {
            ItemStack itemStack = (event.getCurrentItem() != null) ? event.getCurrentItem() : event.getCursor();
            if (canTake(itemStack)) {
                if (event.getSlot() == getSlot()) {
                    event.setCancelled(false);
                    assert itemStack != null;
                    entered.accept(itemStack.clone());
                    getMenu().getInventory().setItem(getSlot(), new ItemStack(Material.AIR));
                    getMenu().update();
                }
            }

        };
    }
}
