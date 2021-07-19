package net.iceyleagons.icicle.ui;

import lombok.Getter;
import net.iceyleagons.icicle.item.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class UserInterface implements Listener {

    private final JavaPlugin javaPlugin;
    private final String name;

    private final Map<Integer, UIRenderable> renderableList = new HashMap<>();
    private final Map<Player, Inventory> opened = new HashMap<>();

    public UserInterface(JavaPlugin javaPlugin, String name) {
        this.javaPlugin = javaPlugin;
        this.name = name;

        javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
    }

    public void open(Player player) {
        Inventory inventory = createInventory(name, javaPlugin);
        inventory.setContents(renderContents(inventory.getSize()));

        opened.put(player, inventory);
        player.openInventory(inventory);
    }

    public void update() {
        opened.keySet().forEach(this::update);
    }

    public void update(Player player) {
        if (this.opened.containsKey(player)) {
            Inventory inventory = this.opened.get(player);
            inventory.setContents(renderContents(inventory.getSize()));
        }
    }

    public void addElement(UIRenderable uiRenderable, int x, int y) {
        uiRenderable.setX(x);
        uiRenderable.setY(y);

        renderableList.put(InventoryUtils.calculateSlotFromXY(x, y), uiRenderable);
    }

    public ItemStack[] renderContents(int size) {
        ItemStack[] contents = new ItemStack[size];

        for (UIRenderable uiRenderable : renderableList.values()) {
            if (!uiRenderable.isVisible()) continue;
            renderUIRenderable(uiRenderable, contents);
        }

        return contents;
    }

    private void renderUIRenderable(UIRenderable uiRenderable, ItemStack[] contents) {
        ItemStack[][] toRender = new ItemStack[uiRenderable.getWidth()][uiRenderable.getHeight()];
        uiRenderable.render(toRender);

        int slot = InventoryUtils.calculateSlotFromXY(uiRenderable.getX(), uiRenderable.getY());

        for (int j = 0; j < toRender.length; j++) {
            for (int k = 0; k < toRender[j].length; k++) {
                int x = j + 1;
                int y = k + 1;
                int fromUtils = InventoryUtils.calculateSlotFromXY(x, y);
                int slotToAdd = slot + fromUtils;

                contents[slotToAdd] = toRender[j][k];
            }
        }
    }

    protected abstract Inventory createInventory(String name, JavaPlugin javaPlugin);

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (this.opened.containsKey(player)) {
            Inventory inventory = this.opened.get(player);

            if (event.getInventory().equals(inventory)) {
                event.setCancelled(true);
                int slot = event.getSlot();
                UIRenderable uiRenderable = this.renderableList.get(slot);

                if (uiRenderable instanceof UIInteractable) {
                    ((UIInteractable) uiRenderable).onClick(event, this);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        this.opened.remove(player);
    }
}
