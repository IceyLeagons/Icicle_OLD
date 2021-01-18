/*
 * MIT License
 *
 * Copyright (c) 2021 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle.advancement;

import kotlin.Unit;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author TOTHTOMI
 */
public class AdvancementBuilder {


    /*
            NamespacedKey namespacedKey = new NamespacedKey("icicle", "test");
        NamespacedKey namespacedKey2 = new NamespacedKey("icicle", "myloll");


        /*Advancement advancement = new Advancement(namespacedKey, null,
                "minecraft:diamond", Advancement.Backgrounds.STONE, new TextComponent("Icicle"), new TextComponent("Use Icicle"),
                Advancement.Frames.GOAL,
                true, true, false);
        advancement.register();

        Advancement advancement3 = new Advancement(namespacedKey2, namespacedKey.toString(),
                "minecraft:emerald", Advancement.Backgrounds.STONE, new TextComponent("Icicle V2"), new TextComponent("Use Icicle asdasdasd"),
                Advancement.Frames.GOAL,
                true, true, false);
        advancement3.register();*/

    private final Advancement root;

    public AdvancementBuilder(String groupName, String name, Advancement.Backgrounds background,
                              String icon, String title, String description, Advancement.Frames frame,
                              boolean announceToChat, boolean showToast, boolean hidden) {
        NamespacedKey namespacedKey = new NamespacedKey(groupName, name);
        root = new Advancement(namespacedKey, null, icon, background,
                new TextComponent(ChatColor.translateAlternateColorCodes('&', title)),
                new TextComponent(ChatColor.translateAlternateColorCodes('&', description)),
                frame, announceToChat, showToast, hidden);

    }

    public Advancement addChild(String name, String icon, String title, String description, Advancement.Frames frame,
                           boolean announceToChat, boolean showToast, boolean hidden) {
        return root.addChild(name, icon, title, description, frame, announceToChat, showToast, hidden);
    }

    public void register(boolean update) {
        dfs().forEach(adv -> {
            System.out.println(adv.getId().toString());
            adv.register(update);
        });
        Bukkit.getServer().reloadData();
    }

    private List<Advancement> dfs() {
        Stack<Advancement> stack = new Stack<>();
        List<Advancement> graph = new ArrayList<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            Advancement element = stack.pop();
            if (!graph.contains(element)) graph.add(element);

            List<Advancement> children = element.getChildren();
            for (Advancement n : children) {
                if (n != null && !graph.contains(n))
                    stack.push(n);
            }

        }
        return graph;
    }

}
