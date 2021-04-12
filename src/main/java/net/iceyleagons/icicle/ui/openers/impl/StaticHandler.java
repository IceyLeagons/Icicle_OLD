package net.iceyleagons.icicle.ui.openers.impl;

import lombok.SneakyThrows;
import net.iceyleagons.icicle.ui.BaseUI;
import net.iceyleagons.icicle.ui.openers.UIOpeningHandler;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class StaticHandler extends UIOpeningHandler implements Listener {

    private final Map<HumanEntity, Inventory> opened = new HashMap<>();

    public StaticHandler(BaseUI baseUI) {
        super(baseUI);
        super.getUi().getJavaPlugin().getServer().getPluginManager().registerEvents(this, super.getUi().getJavaPlugin());
    }

    @SneakyThrows
    @Override
    public void openForPlayer(Player player) {
        Inventory inventory = super.getUi().getTemplate();

        opened.put(player, inventory);
        player.openInventory(inventory);
    }

    @Override
    public Collection<Inventory> getOpenedInventories() {
        return opened.values();
    }

    @Override
    public Map<HumanEntity, Inventory> getOpenedWithPlayers() {
        return this.opened;
    }

    @Override
    public Optional<Inventory> getInventory(Player player) {
        return Optional.ofNullable(opened.get(player));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        opened.remove(event.getPlayer());
    }
}
