package net.iceyleagons.icicle.ui;

import lombok.Getter;
import net.iceyleagons.icicle.item.InventoryUtils;
import net.iceyleagons.icicle.ui.interaction.UIInteractionHandler;
import net.iceyleagons.icicle.ui.openers.UIOpeningHandler;
import net.iceyleagons.icicle.ui.openers.impl.PerPlayerHandler;
import net.iceyleagons.icicle.ui.openers.impl.StaticHandler;
import net.iceyleagons.icicle.ui.render.UIRenderable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BaseUI {

    private final Inventory template;
    private final int size;
    private final String title;

    private final JavaPlugin javaPlugin;

    private final UIOpeningHandler uiOpeningHandler;
    private final UIInteractionHandler uiInteractionHandler;

    private final Map<Integer, UIRenderable> elements = new HashMap<>();

    public BaseUI(JavaPlugin javaPlugin, int size, String title, boolean perPlayer) {
        this.javaPlugin = javaPlugin;
        this.size = size;
        this.title = title;
        this.template = Bukkit.createInventory(null, size, title);

        this.uiOpeningHandler = perPlayer ? new PerPlayerHandler(this) : new StaticHandler(this);
        this.uiInteractionHandler = new UIInteractionHandler(this);
    }

    public void addElement(int x, int y, UIRenderable element) {
        int slot = InventoryUtils.calculateSlotFromXY(x, y);

        elements.put(slot, element);
    }

    public void open(Player player) {
        uiOpeningHandler.openForPlayer(player);
    }

    public void update() {
        ItemStack[] items = render();

        template.setContents(items);
        if (!(uiOpeningHandler instanceof StaticHandler))
            uiOpeningHandler.getOpenedInventories().forEach(inv -> inv.setContents(items));
    }

    public ItemStack[] render() {
        ItemStack[] items = new ItemStack[template.getSize()];

        elements.forEach((slot, element) -> {
            ItemStack[][] toRender = new ItemStack[element.getWidth()][element.getHeight()];
            element.render(toRender);

            int[] slotCoords = InventoryUtils.calculateXYFromSlot(slot);
            int x = slotCoords[0];
            int y = slotCoords[1];

            for (int i = 0; i < element.getWidth(); i++) {
                for (int j = 0; j < element.getHeight(); j++) {
                    ItemStack is = toRender[i][j];
                    items[InventoryUtils.calculateSlotFromXY(x+i, y+j)] = is;
                }
            }
        });

        return items;
    }
}
