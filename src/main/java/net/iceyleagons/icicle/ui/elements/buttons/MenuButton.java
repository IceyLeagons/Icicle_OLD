package net.iceyleagons.icicle.ui.elements.buttons;

import net.iceyleagons.icicle.ui.interfaces.ClickRunnable;
import net.iceyleagons.icicle.ui.interfaces.Renderable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author TOTHTOMI
 */
public class MenuButton extends Button {

    private final ClickRunnable clickRunnable;

    /**
     * Toggle buton with a {@link ClickRunnable}
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param itemStack the Button's @link ItemStack}
     * @param toOpen {@link Renderable} to open
     */
    public MenuButton(int x, int y, ItemStack itemStack, Renderable toOpen) {
        super(x,y,itemStack);
        this.clickRunnable = event -> {
            Player player = (Player) event.getWhoClicked();
            if (toOpen == null) {
                player.closeInventory();
                return;
            }
            toOpen.setPlayer(player);
            toOpen.renderForPlayer();
        };
    }

    @Override
    public ClickRunnable getClickRunnable() {
        return this.clickRunnable;
    }
}
