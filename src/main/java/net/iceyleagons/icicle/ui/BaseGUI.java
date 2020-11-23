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
import net.iceyleagons.icicle.ui.components.Component;
import net.iceyleagons.icicle.ui.components.ComponentTemplate;
import net.iceyleagons.icicle.ui.frame.Frame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author TOTHTOMI
 */
@GUI(title = "faszom")
public class BaseGUI implements GUITemplate, Listener {

    @Getter
    int currentFrame;
    private final List<Frame> frames;
    @Getter
    private final Inventory inventory;

    public BaseGUI() {
        GUI gui = getClass().getAnnotation(GUI.class);
        if (gui.type().equals(InventoryType.CHEST))
            inventory = Bukkit.createInventory(null, gui.height() * 9, gui.title());
        else
            inventory = Bukkit.createInventory(null, gui.type(), gui.title());

        frames = new ArrayList<>();
        GUIManager.registerGUI(this);
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public void nextFrame() {
        Frame f = frames.get(currentFrame);
        inventory.clear();
        f.render(inventory);
        if (getFrames().size() != 1 && getFrames().size() != 0) {
            currentFrame++;
            if (currentFrame >= getFrames().size())
                currentFrame = 0;
        }
    }

    @Override
    public void update() {
        nextFrame();
    }

    @Override
    public void renderForPlayer(Player player) {

    }

    @Override
    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    @EventHandler
    public void onClick(InventoryClickEvent clickEvent) {
        if (!Objects.equals(clickEvent.getClickedInventory(), inventory)) return;

        int slot = clickEvent.getSlot();
        Frame frame = frames.get(currentFrame);
        frame.onClick(slot).thenAccept(componentTemplate -> {
            if (componentTemplate != null) componentTemplate.onClick().accept(this, clickEvent);
        });
    }
}
