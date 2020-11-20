/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicle.ui;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.iceyleagons.icicle.ui.elements.Input;
import net.iceyleagons.icicle.ui.interfaces.Renderable;
import net.iceyleagons.icicle.ui.interfaces.UIElement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class Menu implements Listener, Renderable {

    private final String name;
    private final int size;
    private final Map<Integer, UIElement> elements;
    private final ItemStack placeholder;
    private Inventory inventory = null;
    @Setter
    private Player player;
    private final JavaPlugin javaPlugin;

    /**
     * Creates a new menu
     *
     * @param name name of the menu (title)
     * @param size size of the menu (9,18,27,36,45,54)
     * @param placeholder the placeholder (this will be the default; nullable)
     * @param player the menu holder
     * @param javaPlugin the javaplugin
     */
    public Menu(@NonNull String name, @NonNull int size, ItemStack placeholder, @NonNull  Player player, @NonNull JavaPlugin javaPlugin) {
        this.name = name;
        this.size = size;
        this.player = player;
        this.placeholder = placeholder;
        this.javaPlugin = javaPlugin;
        this.elements = new HashMap<>();
        javaPlugin.getServer().getPluginManager().registerEvents(this,javaPlugin);
    }


    /**
     * Creates a new menu.
     * Used internally, since this constructor does not require {@link Player} however it's needed.
     * Use {@link Menu#setPlayer(Player)}.
     *
     * @param name name of the menu (title)
     * @param size size of the menu (9,18,27,36,45,54)
     * @param placeholder the placeholder (this will be the default; nullable)
     * @param javaPlugin the javaplugin
     */
    public Menu(@NonNull String name, @NonNull int size, ItemStack placeholder, @NonNull JavaPlugin javaPlugin) {
        this.name = name;
        this.size = size;
        this.javaPlugin = javaPlugin;
        this.placeholder = placeholder;
        this.elements = new HashMap<>();
        javaPlugin.getServer().getPluginManager().registerEvents(this,javaPlugin);
    }

    /**
     * Register a new {@link UIElement}
     *
     * @param uiElement the uiElement to register
     */
    public void addUIElement(@NonNull UIElement uiElement) {
        uiElement.setMenu(this);
        elements.put(uiElement.getSlot(),uiElement);
    }

    /**
     * Updates the inventory
     */
    public void update() {
        if (inventory == null) return;
        inventory.clear();
        insertElements();
    }

    /**
     * Fill out the inventory with the {@link UIElement}s
     */
    private void insertElements() {
        if (placeholder != null) for (int i = 0; i < getSize(); i++) inventory.setItem(i, placeholder);
        elements.values().forEach(element -> inventory.setItem(element.getSlot(), element.getItemStack()));
    }

    /**
     * Renders the UI for the player
     */
    @Override
    public void renderForPlayer() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, getSize(), getName());
            insertElements();
        }

        if (getPlayer() != null) {
            getPlayer().openInventory(inventory);
        }
    }

    /**
     * Used to check for whether the Inventory can accept a certain ItemStack. Used for input fields
     *
     * @param itemStack to check for
     * @return true if it can accept it, false otherwise
     */
    public boolean canAccept(ItemStack itemStack) {
        boolean value = false;
        for (UIElement uiElement : elements.values()) {
            if (uiElement instanceof Input) {
                if (!value) value = ((Input)uiElement).canTake(itemStack);

            }
        }
        return value;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        //redundant checks to prevent any sorts of issues.
        if (player.getOpenInventory().getType() != InventoryType.CHEST) return;
        if (!player.equals(getPlayer())) return;
        if (!event.getInventory().equals(inventory)) return;
        boolean canAccept = canAccept(event.getCurrentItem());
        if (!elements.containsKey(event.getSlot())) {
            if (!canAccept){
                event.setCancelled(true);
                return;
            }
        }

        if (Objects.equals(event.getClickedInventory(), inventory) || canAccept) {
            event.setCancelled(false);
            UIElement uiElement = elements.get(event.getSlot());
            if (uiElement != null) {
                if (uiElement.getClickRunnable() != null) {
                    event.setCancelled(true);
                    uiElement.getClickRunnable().onClick(event);
                }
            }
        }
    }

    /**
     * Creates a clone of this menu for the specified player
     *
     * @param player the {@link Player} to create the menu for
     * @return the created {@link Menu}
     */
    public Menu createCloneForPlayer(Player player) {
        Menu menu = new Menu(getName(),getSize(),getPlaceholder(),getPlayer(),javaPlugin);
        getElements().values().forEach(menu::addUIElement);

        menu.setPlayer(player);
        return menu;
    }

}
