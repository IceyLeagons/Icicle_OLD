package net.iceyleagons.icicle.common.ui.openers;

import lombok.Getter;
import net.iceyleagons.icicle.common.ui.BaseUI;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public abstract class UIOpeningHandler {

    @Getter
    private final BaseUI ui;

    public UIOpeningHandler(BaseUI baseUI) {
        this.ui = baseUI;
    }

    public abstract void openForPlayer(Player player);
    public abstract Collection<Inventory> getOpenedInventories();
    public abstract Map<HumanEntity, Inventory> getOpenedWithPlayers();
    public abstract Optional<Inventory> getInventory(Player player);

}
