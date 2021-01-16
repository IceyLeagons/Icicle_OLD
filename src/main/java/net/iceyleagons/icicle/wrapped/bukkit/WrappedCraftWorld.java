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

package net.iceyleagons.icicle.wrapped.bukkit;

import lombok.Getter;
import net.iceyleagons.icicle.reflections.Reflections;
import net.iceyleagons.icicle.wrapped.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WrappedCraftWorld {

    private static final Class<?> mc_CraftWorld, mc_World, mc_WorldServer, mc_IRegistryCustom;
    private static final Method world_isLoaded, world_getChunkAtWorldCoords, ws_getRegistry, registry_b;
    private static final Field bukkit_nmsWorld;

    static {
        mc_CraftWorld = Reflections.getNormalCBClass("CraftWorld");
        mc_World = Reflections.getNormalNMSClass("World");
        mc_WorldServer = Reflections.getNormalNMSClass("WorldServer");
        mc_IRegistryCustom = Reflections.getNormalNMSClass("IRegistryCustom");

        world_isLoaded = Reflections.getMethod(mc_World, "isLoaded", true, WrappedBlockPosition.mc_BlockPosition);
        world_getChunkAtWorldCoords = Reflections.getMethod(mc_World, "getChunkAtWorldCoords", true, WrappedBlockPosition.mc_BlockPosition);
        ws_getRegistry = Reflections.getMethod(mc_WorldServer, "r", true);
        registry_b = Reflections.getMethod(mc_IRegistryCustom, "b", true, WrappedIRegistry.mc_ResourceKey);

        bukkit_nmsWorld = Reflections.getField(mc_CraftWorld, "world", true);
    }

    @Getter
    public final Object craftWorld;

    public WrappedCraftWorld(Object root) {
        if (root instanceof World) {
            craftWorld = mc_CraftWorld.cast(root);
            return;
        }

        if (mc_CraftWorld.isInstance(root)) {
            craftWorld = root;
            return;
        }

        if (root instanceof String)
            craftWorld = mc_CraftWorld.cast(Bukkit.getWorld((String) root));

        throw new IllegalArgumentException("Root not instance of CraftWorld nor String");
    }

    public boolean isLoaded(WrappedBlockPosition blockPosition) {
        return Reflections.invoke(world_isLoaded, Boolean.class, Reflections.get(bukkit_nmsWorld, Object.class, craftWorld), blockPosition.getRoot());
    }

    public Object getCustomRegistry() {
        return Reflections.invoke(ws_getRegistry, Object.class, Reflections.get(bukkit_nmsWorld, Object.class, craftWorld));
    }

    public WrappedChunk getChunkAtWorldCoords(WrappedBlockPosition blockPosition) {
        return new WrappedChunk(Reflections.invoke(world_getChunkAtWorldCoords, Object.class, Reflections.get(bukkit_nmsWorld, Object.class, craftWorld), blockPosition.getRoot()));
    }

    public WrappedBiomeBase setBiome(int x, int y, int z, String namespace, String key) {
        /*WrappedBiomeBase biomeBase = WrappedBiomeBase.Builder.create()
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
                .build();*/

        WrappedBiomeBase biomeBase = new WrappedBiomeBase(WrappedIRegistry
                .get(Reflections.invoke(registry_b, Object.class, getCustomRegistry(), WrappedIRegistry.BIOME),
                        WrappedCraftNamespacedKey.toMinecraft(new NamespacedKey(namespace, key))));
        WrappedBlockPosition blockPosition = new WrappedBlockPosition(x, y, z);
        WrappedChunk chunk;

        if (isLoaded(blockPosition) && (chunk = getChunkAtWorldCoords(blockPosition)).getChunk() != null) {
            chunk.getBiomeIndex().setBiome(x >> 2, y >> 2, z >> 2, biomeBase);
            chunk.markDirty();
        }

        return biomeBase;
    }

}
