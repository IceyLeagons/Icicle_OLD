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
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.WrappedBlockPosition;
import net.iceyleagons.icicle.wrapped.biome.WrappedBiomeBase;
import net.iceyleagons.icicle.wrapped.packet.WrappedPacketPlayOutMapChunk;
import net.iceyleagons.icicle.wrapped.player.WrappedCraftPlayer;
import net.iceyleagons.icicle.wrapped.registry.WrappedIRegistry;
import net.iceyleagons.icicle.wrapped.world.WrappedWorld;
import net.iceyleagons.icicle.wrapped.world.chunk.WrappedChunk;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WrappedCraftWorld {

    public static final Class<?> mc_World, mc_WorldServer;
    private static final Class<?> mc_CraftWorld, mc_IRegistryCustom;
    private static final Method world_isLoaded, world_getChunkAtWorldCoords, world_getHandle, nmsWorld_getWorld, ws_getRegistry, registry_b, add_Entity;
    private static final Field bukkit_nmsWorld;

    static {
        mc_CraftWorld = Reflections.getNormalCBClass("CraftWorld");
        mc_World = Reflections.getNormalNMSClass("World");
        mc_WorldServer = Reflections.getNormalNMSClass("WorldServer");
        mc_IRegistryCustom = Reflections.getNormalNMSClass("IRegistryCustom");

        world_isLoaded = Reflections.getMethod(mc_World, "isLoaded", true, WrappedBlockPosition.mc_BlockPosition);
        world_getChunkAtWorldCoords = Reflections.getMethod(mc_World, "getChunkAtWorldCoords", true, WrappedBlockPosition.mc_BlockPosition);
        world_getHandle = Reflections.getMethod(mc_CraftWorld, "getHandle", true);
        nmsWorld_getWorld = Reflections.getMethod(mc_World, "getWorld", true);
        ws_getRegistry = Reflections.getMethod(mc_WorldServer, "r", true);
        registry_b = Reflections.getMethod(mc_IRegistryCustom, "b", true, WrappedIRegistry.mc_ResourceKey);
        add_Entity = Reflections.getMethod(mc_CraftWorld, "addEntity", true,
                Reflections.getNormalNMSClass("Entity"), CreatureSpawnEvent.SpawnReason.class);

        bukkit_nmsWorld = Reflections.getField(mc_CraftWorld, "world", true);
    }

    @Getter
    public final Object craftWorld;

    public WrappedCraftWorld(Object root) {
        if (root instanceof World) {
            craftWorld = mc_CraftWorld.cast(root);
            return;
        }

        if (root.getClass().isInstance(mc_World)) {
            craftWorld = Reflections.invoke(nmsWorld_getWorld, Object.class, root);
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

    public WrappedChunk setBiome(int x, int y, int z, WrappedBiomeBase wrappedBiomeBase) {
        /*WrappedBiomeBase biomeBase = new WrappedBiomeBase(WrappedIRegistry
                .get(Reflections.invoke(registry_b, Object.class, getCustomRegistry(), WrappedIRegistry.BIOME),
                        WrappedCraftNamespacedKey.toMinecraft(new NamespacedKey(namespace, key))));*/
        WrappedBlockPosition blockPosition = new WrappedBlockPosition(x, y, z);
        WrappedChunk chunk = null;

        if (isLoaded(blockPosition) && (chunk = getChunkAtWorldCoords(blockPosition)).getChunk() != null) {
            chunk.getBiomeIndex().setBiome(x >> 2, y >> 2, z >> 2, wrappedBiomeBase);
            chunk.markDirty();
        }

        return chunk;
    }

    public WrappedWorld getHandle() {
        return new WrappedWorld(Reflections.invoke(world_getHandle, Object.class, craftWorld));
    }

    public Object addEntity(Object entity, CreatureSpawnEvent.SpawnReason spawnReason) {
        return Reflections.invoke(add_Entity, Object.class, craftWorld, entity, spawnReason);
    }

    public void sendUpdate(Player player, WrappedChunk chunk) {
        WrappedCraftPlayer.from(player).getHandle().getPlayerConnection().sendPacket(new WrappedPacketPlayOutMapChunk(chunk).getPacket());
    }

}
