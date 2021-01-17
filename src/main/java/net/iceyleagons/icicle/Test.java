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
import net.iceyleagons.icicle.advancement.Advancement;
import net.iceyleagons.icicle.misc.commands.CommandUtils;
import net.iceyleagons.icicle.wrapped.WrappedBiomeBase;
import net.iceyleagons.icicle.wrapped.WrappedBiomeFog;
import net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftWorld;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

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

    @SneakyThrows
    @Override
    public void onEnable() {
        Icicle.init(this, IcicleFeatures.COMMANDS);

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

            WrappedCraftWorld craftWorld = new WrappedCraftWorld(player.getWorld());
            craftWorld.sendUpdate(player,
                    craftWorld.setBiome(player.getLocation().getBlockX(),
                            player.getLocation().getBlockY(),
                            player.getLocation().getBlockZ(),
                            namespace, key));

            return true;
        });

        super.onEnable();
    }
}
