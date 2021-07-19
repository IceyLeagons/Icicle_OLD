package net.iceyleagons.icicle.ui.impl;

import net.iceyleagons.icicle.ui.UserInterface;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestUI extends UserInterface {

    private final int size;

    public ChestUI(JavaPlugin javaPlugin, String name, int size) {
        super(javaPlugin, name);
        this.size = size;
    }

    @Override
    protected Inventory createInventory(String name, JavaPlugin javaPlugin) {
        return javaPlugin.getServer().createInventory(null, size, getName());
    }
}
