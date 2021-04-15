package net.iceyleagons.icicle.ui.openers.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.ui.BaseUI;
import net.iceyleagons.icicle.ui.openers.UIOpeningHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class PerPlayerHandler extends UIOpeningHandler implements Listener {

    private final LoadingCache<HumanEntity, Inventory> clones = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .maximumSize(150)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(new CacheLoader<HumanEntity, Inventory>() {
                @Override
                public Inventory load(HumanEntity ignored) {
                    return makeCloneOfTemplate();
                }
            });

    private final Map<HumanEntity, Inventory> opened = new HashMap<>();

    public PerPlayerHandler(BaseUI baseUI) {
        super(baseUI);
        super.getUi().getJavaPlugin().getServer().getPluginManager().registerEvents(this, super.getUi().getJavaPlugin());
    }

    @SneakyThrows
    @Override
    public void openForPlayer(Player player) {
        Inventory inventory = clones.get(player);

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

    private Inventory makeCloneOfTemplate() {
        return Bukkit.createInventory(null, super.getUi().getSize(), super.getUi().getTitle());
    }
}
