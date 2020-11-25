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

package net.iceyleagons.icicle.ui.guis;

import lombok.Getter;
import lombok.NonNull;
import net.iceyleagons.icicle.item.InventoryUtils;
import net.iceyleagons.icicle.ui.GUI;
import net.iceyleagons.icicle.ui.GUIClickEvent;
import net.iceyleagons.icicle.ui.GUIManager;
import net.iceyleagons.icicle.ui.GUITemplate;
import net.iceyleagons.icicle.ui.components.ComponentTemplate;
import net.iceyleagons.icicle.ui.components.impl.Button;
import net.iceyleagons.icicle.ui.components.impl.pagination.NextButton;
import net.iceyleagons.icicle.ui.components.impl.pagination.PreviousButton;
import net.iceyleagons.icicle.ui.frame.Frame;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author TOTHTOMI
 */
public abstract class BasePaginatedGUI extends BaseGUI {

    private final Map<Integer, List<Frame>> pages;

    @Getter
    int currentPage;

    public BasePaginatedGUI() {
        super();

        pages = new HashMap<>();
        currentPage = 0;
    }

    public Map<Integer, List<Frame>> getPages() {
        return this.pages;
    }

    @Override
    public boolean nextFrame() {
        if (getFrames().size() != 1 && getFrames().size() != 0) {
            currentFrame++;
            if (currentFrame >= getFrames().size())
                currentFrame = 0;
        } else
            return false;

        return true;
    }

    @Override
    public void update() {
        nextFrame();
        getPages().get(currentPage).get(currentFrame).render(super.getInventory());
    }

    public void nextPage(@NonNull ComponentTemplate template) {
        int pagesTotal = pages.size();
        if (currentPage + 1 >= pagesTotal) {
            template.setRenderAllowed(false);
            update();
            return;
        }
        template.setRenderAllowed(true);
        currentPage += 1;
        update();
    }

    public void lastPage(@NonNull ComponentTemplate template) {
        if (currentPage - 1 < 0) {
            template.setRenderAllowed(false);
            update();
            return;
        }

        template.setRenderAllowed(true);
        currentPage -= 1;
        update();
    }

    public Button getPreviousButton(@NonNull ItemStack itemStack, Sound sound) {
        return new PreviousButton(itemStack, event -> {
            if (sound != null)
                InventoryUtils.clickSound(event.getInventoryClickEvent(), sound);
            lastPage(event.getSelfComponent());
        });
    }

    public Button getNextButton(@NonNull ItemStack itemStack, Sound sound) {
        return new NextButton(itemStack, event -> {
            if (sound != null)
                InventoryUtils.clickSound(event.getInventoryClickEvent(), sound);
            nextPage(event.getSelfComponent());
        });
    }

    @Override
    public void addFrames(Integer page, @NonNull Frame... frames) {
        List<Frame> currentFrames;
        if (getPages().containsKey(page))
            currentFrames = getPages().get(page);
        else
            currentFrames = new ArrayList<>();

        currentFrames.addAll(Arrays.asList(frames));
        pages.put(page, currentFrames);
    }
}
