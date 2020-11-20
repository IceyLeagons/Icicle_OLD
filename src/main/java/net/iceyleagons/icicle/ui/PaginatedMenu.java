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
import net.iceyleagons.icicle.InventoryUtils;
import net.iceyleagons.icicle.item.ItemFactory;
import net.iceyleagons.icicle.ui.elements.buttons.SimpleButton;
import net.iceyleagons.icicle.ui.interfaces.ClickRunnable;
import net.iceyleagons.icicle.ui.interfaces.Renderable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TOTHTOMI
 */
public class PaginatedMenu implements Renderable {

    private final Map<Integer,Menu> pages;
    private int currentOpened = 0;
    private final ClickRunnable back;
    private final ClickRunnable forward;
    private final ClickRunnable close;
    @Setter
    @Getter
    private Player player;
    @Getter
    private Menu originatedFrom;

    public PaginatedMenu(@NonNull Player player, Menu originatedFrom) {
        pages = new HashMap<>();
        this.player = player;
        this.originatedFrom = originatedFrom;
        back = event -> {
            InventoryUtils.clickSound(event);
            if (pages.size() > 0) {
                if (currentOpened-1 >= 0) {
                    currentOpened-=1;
                    renderForPlayer();
                }
            }
        };
        forward = event -> {
            InventoryUtils.clickSound(event);
            if (pages.size() > 0) {
                if (currentOpened+1 < pages.size()) {
                    currentOpened+=1;
                    renderForPlayer();
                }
            }
        };
        close = event -> {
            InventoryUtils.clickSound(event);
            if (originatedFrom == null)
                event.getWhoClicked().closeInventory();
            else {
                originatedFrom.setPlayer(player);
                originatedFrom.renderForPlayer();
            }

        };
        this.player = player;
    }

    public void addPage(Menu menu) {
        menu.setPlayer(player);
        populateWithControls(menu);
        pages.put(pages.size(),menu);
    }

    public void populateWithControls(Menu menu) {
        ItemStack backItem = ItemFactory.newFactory(Material.ARROW).hideAttributes().setDisplayName("&9&l< Back").build();
        ItemStack forwardItem = ItemFactory.newFactory(Material.ARROW).hideAttributes().setDisplayName("&9&lForward >").build();
        ItemStack closeItem = ItemFactory.newFactory(Material.BOOK).hideAttributes().setDisplayName("&9&lClose").build();

        if (currentOpened-1 >= 0) addControl(6,1, backItem,this.back,menu);
        else removeControl(6,1,menu);

        addControl(6,5, closeItem,this.close,menu);

        if (currentOpened+1 < pages.size()) addControl(6,9, forwardItem,this.forward,menu); //TODO It only works with inventories with 54 size
        else removeControl(6,9,menu);
    }

    private void addControl(int x, int y, ItemStack itemStack, ClickRunnable clickRunnable, Menu menu) {
        menu.getElements().put(InventoryUtils.calculateSlotFromXY(x,y),new SimpleButton(x,y,itemStack,clickRunnable)); //Overwriting the currently existing UIElement there
    }

    private void removeControl(int x, int y, Menu menu) {
        menu.getElements().remove(InventoryUtils.calculateSlotFromXY(x,y));
    }

    @Override
    public void renderForPlayer() {
        populateWithControls(pages.get(currentOpened));
        pages.get(currentOpened).renderForPlayer();
    }

    public PaginatedMenu createCloneForPlayer(Player player) {
        PaginatedMenu paginatedMenu = new PaginatedMenu(getPlayer(),getOriginatedFrom());
        pages.values().forEach(paginatedMenu::addPage);
        return paginatedMenu;
    }

}
