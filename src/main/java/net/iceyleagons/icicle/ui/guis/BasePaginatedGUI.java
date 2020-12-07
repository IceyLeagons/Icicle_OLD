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
import net.iceyleagons.icicle.ui.components.ComponentTemplate;
import net.iceyleagons.icicle.ui.components.impl.Button;
import net.iceyleagons.icicle.ui.components.impl.buttons.SimpleButton;
import net.iceyleagons.icicle.ui.frame.Frame;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.2.0-SNAPSHOT
 */
public abstract class BasePaginatedGUI extends BaseGUI {

    @Getter
    private final Map<Integer, List<Frame>> pages;

    @Getter
    private int currentPage;

    /**
     * Creates a new BasicPaginatedGUI instance
     */
    public BasePaginatedGUI() {
        super();

        pages = new HashMap<>();
        currentPage = 0;
    }

    /**
     * Skips to the next frame. See: {@link BaseGUI#nextFrame()}
     *
     * @return whether there was a next frame or not
     */
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

    /**
     * Update the inventory
     */
    @Override
    public void update() {
        nextFrame();
        getPages().get(currentPage).get(currentFrame).render(super.getInventory());
    }

    /**
     * Goes to the next page
     *
     * @param template OPTIONAL! clicked template
     */
    public void nextPage(ComponentTemplate template) {
        int pagesTotal = pages.size();
        if (currentPage + 1 >= pagesTotal) {
            if (template != null) template.setRenderAllowed(false);
            update();
            return;
        }
        template.setRenderAllowed(true);
        currentPage += 1;
        update();
    }

    /**
     * Goes to the previous page
     *
     * @param template OPTIONAL! clicked template
     */
    public void lastPage(ComponentTemplate template) {
        if (currentPage - 1 < 0) {
            if (template != null) template.setRenderAllowed(false);
            update();
            return;
        }

        template.setRenderAllowed(true);
        currentPage -= 1;
        update();
    }

    /**
     * Creates a new back button
     *
     * @param itemStack the itemstack of the button
     * @param sound     the sound it makes when clicked
     * @return the back button
     */
    public Button getPreviousButton(@NonNull ItemStack itemStack, Sound sound) {
        return new SimpleButton(itemStack, event -> {
            if (sound != null)
                InventoryUtils.clickSound(event.getInventoryClickEvent(), sound);
            lastPage(event.getSelfComponent());
        });
    }

    /**
     * Creates a new next button
     *
     * @param itemStack the itemstack of the button
     * @param sound     the sound it makes when clicked
     * @return the next button
     */
    public Button getNextButton(@NonNull ItemStack itemStack, Sound sound) {
        return new SimpleButton(itemStack, event -> {
            if (sound != null)
                InventoryUtils.clickSound(event.getInventoryClickEvent(), sound);
            nextPage(event.getSelfComponent());
        });
    }

    /**
     * Adds frames to specified pages.
     *
     * @param page   which page to add the frames to
     * @param frames the frames to add
     * @throws IllegalArgumentException if the given page is incorrect
     */
    @Override
    public void addFrames(Integer page, @NonNull Frame... frames) throws IllegalArgumentException {
        if (page >= 0) throw new IllegalArgumentException("Page must not be negative");

        List<Frame> currentFrames;
        if (getPages().containsKey(page))
            currentFrames = getPages().get(page);
        else
            currentFrames = new ArrayList<>();

        currentFrames.addAll(Arrays.asList(frames));
        pages.put(page, currentFrames);
    }
}
