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

package net.iceyleagons.icicle;

import net.iceyleagons.icicle.item.ItemFactory;
import net.iceyleagons.icicle.ui.components.impl.pagination.SimpleButton;
import net.iceyleagons.icicle.ui.frame.Frame;
import net.iceyleagons.icicle.ui.guis.SimpleGUI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

/**
 * @author TOTHTOMI
 */
public class TestCommand implements CommandExecutor {

    final SimpleGUI baseGUI;

    public TestCommand() throws ExecutionException, InterruptedException {
        baseGUI = new SimpleGUI();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                Frame frame = new Frame();
                frame.registerComponent(baseGUI.getPreviousButton(ItemFactory.newFactory(Material.ARROW).setDisplayName("&9< Previous").build(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON), 1, 1).get();
                frame.registerComponent(baseGUI.getNextButton(ItemFactory.newFactory(Material.ARROW).setDisplayName("&9Next >").build(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON), 2, 1).get();


                frame.registerComponent(new SimpleButton(ItemFactory.newFactory(Material.WOODEN_SHOVEL).build(), event -> {

                }), 3 + i+j, 1).get();

                baseGUI.addFrames(i, frame);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            ((Player) sender).openInventory(baseGUI.getInventory());
            baseGUI.update();
        }

        return true;
    }
}
