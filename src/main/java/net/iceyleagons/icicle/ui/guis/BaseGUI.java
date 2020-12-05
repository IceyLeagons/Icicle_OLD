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
import net.iceyleagons.icicle.ui.GUI;
import net.iceyleagons.icicle.ui.GUIManager;
import net.iceyleagons.icicle.ui.GUITemplate;
import net.iceyleagons.icicle.ui.frame.Frame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.*;

/**
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since 1.2.0-SNAPSHOT
 */
public abstract class BaseGUI implements GUITemplate {

    @Getter
    protected int currentFrame;
    private final List<Frame> frames;
    @Getter
    private final Inventory inventory;
    private final List<Player> opened;

    /**
     * Create a new BaseGUI instance
     */
    public BaseGUI() {
        GUI gui = getClass().getAnnotation(GUI.class);
        if (gui.type().equals(InventoryType.CHEST))
            inventory = Bukkit.createInventory(null, gui.height() * 9, gui.title());
        else
            inventory = Bukkit.createInventory(null, gui.type(), gui.title());

        frames = new ArrayList<>();
        opened = new ArrayList<>();
        GUIManager.registerGUI(this);
    }

    /**
     * Returns a list of all the frames.
     *
     * @return list of frames
     */
    public List<Frame> getFrames() {
        return frames;
    }

    /**
     * Skips to the next frame.
     *
     * @return whether there was a next frame or not
     */
    public boolean nextFrame() {
        Frame f = frames.get(currentFrame);
        inventory.clear();
        f.render(inventory);
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
        // We call next frame cause it does everything we need it to do.
        nextFrame();
    }

    /**
     * Open for specified players
     *
     * @param players whom to open it to
     */
    @Override
    public void openForPlayers(Player... players) {
        for (Player player : players) {
            opened.add(player);
            player.openInventory(getInventory());
        }
    }

    /**
     * Adds frames to the gui
     *
     * @param ignore not used in this class
     * @param frames the frames to add
     */
    @Override
    public void addFrames(Integer ignore, Frame... frames) {
        this.frames.addAll(Arrays.asList(frames));
    }
}
