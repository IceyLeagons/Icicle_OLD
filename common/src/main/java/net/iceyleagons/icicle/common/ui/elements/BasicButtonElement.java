package net.iceyleagons.icicle.common.ui.elements;

import net.iceyleagons.icicle.common.ui.interaction.UIInteractable;
import net.iceyleagons.icicle.common.ui.render.UIRenderable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class BasicButtonElement implements UIRenderable, UIInteractable {

    private final ItemStack placeholder;
    private final Consumer<InventoryClickEvent> onClick;

    public BasicButtonElement(ItemStack placeholder, Consumer<InventoryClickEvent> onClick) {
        this.placeholder = placeholder;
        this.onClick = onClick;
    }

    @Override
    public void onClicked(InventoryClickEvent event) {
        this.onClick.accept(event);
    }

    @Override
    public void render(ItemStack[][] toRender) {
        toRender[0][0] = placeholder;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }
}
