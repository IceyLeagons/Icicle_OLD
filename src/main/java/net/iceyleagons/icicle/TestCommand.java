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
import net.iceyleagons.icicle.misc.SchedulerUtils;
import net.iceyleagons.icicle.ui.BaseGUI;
import net.iceyleagons.icicle.ui.GUIManager;
import net.iceyleagons.icicle.ui.components.impl.Button;
import net.iceyleagons.icicle.ui.frame.Frame;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author TOTHTOMI
 */
public class TestCommand implements CommandExecutor {

    final BaseGUI baseGUI;

    public TestCommand() throws ExecutionException, InterruptedException {



        baseGUI = new BaseGUI();

        for (int i = 0; i < 9; i++) {
            Frame frame = new Frame();
            frame.registerComponent(new Button(ItemFactory.newFactory(Material.GOLD_BLOCK).build(),
                    (gui, event) -> {
                        //event.getWhoClicked().sendMessage("Clicked!");
                    }), 1+i, 1).get();
            baseGUI.addFrame(frame);
        }
        //baseGUI.addFrame(frame2);
        //baseGUI.addFrame(frame3);

        GUIManager.registerGUI(baseGUI);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            //baseGUI.update();
            ((Player) sender).openInventory(baseGUI.getInventory());
        }

        return true;
    }
}
