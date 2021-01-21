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

package net.iceyleagons.icicle;

import lombok.SneakyThrows;
import net.iceyleagons.icicle.misc.commands.CommandUtils;
import net.iceyleagons.icicle.npc.NPC;
import net.iceyleagons.icicle.ui.components.impl.ValueBar;
import net.iceyleagons.icicle.ui.frame.Frame;
import net.iceyleagons.icicle.ui.guis.BasicGUI;
import net.iceyleagons.icicle.wrapped.biome.WrappedBiomeBase;
import net.iceyleagons.icicle.wrapped.biome.WrappedBiomeFog;
import net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftWorld;
import net.iceyleagons.icicle.wrapped.player.WrappedEntityPlayer;
import net.iceyleagons.icicle.wrapped.world.chunk.WrappedChunk;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author TOTHTOMI
 */
public class Test extends JavaPlugin {

    public static WrappedBiomeBase biomeBase;

    @Override
    public void onLoad() {
        biomeBase = WrappedBiomeBase.Builder.create()
                .setBiomeProperties(WrappedBiomeFog.Builder.create()
                        .setWaterColor(Color.GREEN)
                        .setWaterFogColor(Color.PINK)
                        .setSkyColor(Color.CYAN)
                        .setFogColor(Color.BLACK)
                        .build())
                .setDepth(0.05f)
                .setDownfall(0.1f)
                .setGeography(WrappedBiomeBase.Geography.JUNGLE)
                .setPrecipitation(WrappedBiomeBase.Precipitation.SNOW)
                .setTemperature(1.25f)
                .setTemperatureModifier(WrappedBiomeBase.TemperatureModifier.FROZEN)
                .setScale(0.25f)
                .setMobs()
                .setGeneration()
                .build()
                .register(new NamespacedKey("fe", "newww"));

        super.onLoad();
    }

    private BasicGUI getGUI() {
        BasicGUI basicGUI = new BasicGUI();

        Frame frame = new Frame();
        ValueBar valueBar = new ValueBar(5, 5);
        valueBar.setValue(3);
        frame.registerComponent(valueBar, 2, 2);
        basicGUI.addFrames(0, frame);
        return basicGUI;
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        Icicle.init(this, IcicleFeatures.COMMANDS, IcicleFeatures.INVENTORIES, IcicleFeatures.PRELOAD_REFLECTION);

        BasicGUI basicGUI = getGUI();

        CommandUtils.injectCommand("npc", (commandSender, command, s, strings) -> {
            Player player = (Player) commandSender;
            //NPC npc = new NPC(new WrappedGameProfile(Mojang.getUUID("DRSENKIHAZI"), "DRSENKIHAZI"), player.getLocation());
            //npc.spawn();
            NPC npc = new NPC(ChatColor.RED + "" + ChatColor.BOLD + "Bob", "PredatorTTV_");
            WrappedEntityPlayer entityPlayer = npc.setup(player.getLocation());
            NPC.spawnNPCPacket(entityPlayer, player);
            return true;
        });

        CommandUtils.injectCommand("gui", (commandSender, command, s, strings) -> {
            Player player = (Player) commandSender;
            basicGUI.openForPlayers(player);
            return true;
        });

        CommandUtils.injectCommand("icicle", (commandSender, command, s, strings) -> {
            Player player = (Player) commandSender;

            String namespace = "fe";
            String key = "aaa" + ThreadLocalRandom.current().nextInt(10);

            biomeBase = WrappedBiomeBase.Builder.create()
                    .setBiomeProperties(WrappedBiomeFog.Builder.create()
                            .setWaterColor(Color.GREEN)
                            .setWaterFogColor(Color.PINK)
                            .setSkyColor(Color.CYAN)
                            .setFogColor(Color.BLACK)
                            .build())
                    .setDepth(0.05f)
                    .setDownfall(0.1f)
                    .setGeography(WrappedBiomeBase.Geography.JUNGLE)
                    .setPrecipitation(WrappedBiomeBase.Precipitation.SNOW)
                    .setTemperature(1.25f)
                    .setTemperatureModifier(WrappedBiomeBase.TemperatureModifier.FROZEN)
                    .setScale(0.25f)
                    .setMobs()
                    .setGeneration()
                    .build()
                    .register(new NamespacedKey(namespace, key));

            /*
            WrappedCraftWorld craftWorld = new WrappedCraftWorld(player.getWorld());

            WrappedChunk wrappedChunk = craftWorld.setBiome(player.getLocation().getBlockX(),
                    player.getLocation().getBlockY(),
                    player.getLocation().getBlockZ(),
                    biomeBase);
            //craftWorld.sendUpdate(player, wrappedChunk);
            //craftWorld.sendUpdate(player, wrappedChunk);
             */

            return true;
        });

        super.onEnable();
    }
}
