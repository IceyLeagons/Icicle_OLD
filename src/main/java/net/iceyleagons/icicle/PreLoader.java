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

/**
 * @author TOTHTOMI
 */
public class PreLoader {

    public static void preload() {
        System.out.println("Preloading WorldEdit database...");
        preloadWorldEditDB();
        System.out.println("Preloading finished!");

        System.out.println("Preloading reflections...");
        preloadReflections();
        System.out.println("Preloading finished!");
    }

    public static void preloadWorldEditDB() {
        try {
            long timeNow = System.currentTimeMillis();
            Class.forName("net.iceyleagons.icicle.schematic.SchematicLoader");
            System.out.printf("Elapsed time: %s ms.%n", System.currentTimeMillis() - timeNow);
        } catch (ClassNotFoundException e) {
            // Somehow loading these classes failed..? How?
            e.printStackTrace();
        }
    }

    public static void preloadReflections() {
        try {
            long timeNow = System.currentTimeMillis();
            Class.forName("net.iceyleagons.icicle.wrapped.world.WrappedBlockPosition");
            Class.forName("net.iceyleagons.icicle.wrapped.WrappedDedicatedServer");
            Class.forName("net.iceyleagons.icicle.wrapped.WrappedTileEntity");

            Class.forName("net.iceyleagons.icicle.wrapped.registry.WrappedResourceKey");
            Class.forName("net.iceyleagons.icicle.wrapped.registry.WrappedIRegistryWritable");
            Class.forName("net.iceyleagons.icicle.wrapped.registry.WrappedIRegistry");
            Class.forName("net.iceyleagons.icicle.wrapped.registry.WrappedIRegistryCustom");
            Class.forName("net.iceyleagons.icicle.wrapped.registry.WrappedRegistryGeneration");

            Class.forName("net.iceyleagons.icicle.wrapped.world.WrappedLightEngine");
            Class.forName("net.iceyleagons.icicle.wrapped.world.WrappedWorld");
            Class.forName("net.iceyleagons.icicle.wrapped.world.chunk.WrappedChunk");
            Class.forName("net.iceyleagons.icicle.wrapped.world.chunk.WrappedChunkCoordIntPair");

            Class.forName("net.iceyleagons.icicle.wrapped.player.WrappedCraftPlayer");
            Class.forName("net.iceyleagons.icicle.wrapped.player.WrappedEntityPlayer");
            Class.forName("net.iceyleagons.icicle.wrapped.player.WrappedNetworkManager");
            Class.forName("net.iceyleagons.icicle.wrapped.player.WrappedPlayerConnection");

            Class.forName("net.iceyleagons.icicle.wrapped.packet.WrappedPacketPlayOutMapChunk");
            Class.forName("net.iceyleagons.icicle.wrapped.packet.WrappedPacketPlayOutEntity");
            Class.forName("net.iceyleagons.icicle.wrapped.packet.WrappedPacketPlayOutEntityDestroy");
            Class.forName("net.iceyleagons.icicle.wrapped.packet.WrappedPacketPlayOutEntityHeadRotation");
            Class.forName("net.iceyleagons.icicle.wrapped.packet.WrappedPacketPlayOutEntityMetadata");
            Class.forName("net.iceyleagons.icicle.wrapped.packet.WrappedPacketPlayOutNamedEntitySpawn");
            Class.forName("net.iceyleagons.icicle.wrapped.packet.WrappedPacketPlayOutPlayerInfo");

            Class.forName("net.iceyleagons.icicle.wrapped.item.WrappedItem");
            Class.forName("net.iceyleagons.icicle.wrapped.item.WrappedItemStack");

            Class.forName("net.iceyleagons.icicle.wrapped.mojang.WrappedGameProfile");
            Class.forName("net.iceyleagons.icicle.wrapped.mojang.WrappedProperty");
            Class.forName("net.iceyleagons.icicle.wrapped.mojang.WrappedPropertyMap");

            Class.forName("net.iceyleagons.icicle.wrapped.data.WrappedDataWatcher");
            Class.forName("net.iceyleagons.icicle.wrapped.data.WrappedDataWatcherObject");
            Class.forName("net.iceyleagons.icicle.wrapped.data.WrappedDataWatcherRegistry");

            Class.forName("net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftNamespacedKey");
            Class.forName("net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftBlock");
            Class.forName("net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftWorld");
            Class.forName("net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftChatMessage");

            Class.forName("net.iceyleagons.icicle.wrapped.biome.WrappedBiomeBase");
            Class.forName("net.iceyleagons.icicle.wrapped.biome.WrappedBiomeFog");
            Class.forName("net.iceyleagons.icicle.wrapped.biome.WrappedBiomeRegistry");
            Class.forName("net.iceyleagons.icicle.wrapped.biome.WrappedBiomeStorage");

            System.out.printf("Elapsed time: %s ms.%n", System.currentTimeMillis() - timeNow);
        } catch (ClassNotFoundException e) {
            // Somehow loading these classes failed..? How?
            e.printStackTrace();
        }
    }

}
