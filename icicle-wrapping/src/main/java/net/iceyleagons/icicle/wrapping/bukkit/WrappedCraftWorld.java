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

package net.iceyleagons.icicle.wrapping.bukkit;

import lombok.Getter;
import net.iceyleagons.icicle.wrapping.biome.WrappedBiomeBase;
import net.iceyleagons.icicle.wrapping.packet.WrappedPacketPlayOutMapChunk;
import net.iceyleagons.icicle.wrapping.player.WrappedCraftPlayer;
import net.iceyleagons.icicle.wrapping.registry.WrappedIRegistryCustom;
import net.iceyleagons.icicle.wrapping.utils.WrappedClass;
import net.iceyleagons.icicle.wrapping.world.WrappedBlockPosition;
import net.iceyleagons.icicle.wrapping.world.WrappedWorld;
import net.iceyleagons.icicle.wrapping.world.chunk.WrappedChunk;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Wrapped representation CraftWorld
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedCraftWorld {
    static {
        WrappedClass.getNMSClass("World")
                .lookupMethod("isLoaded", null, WrappedClass.getNMSClass("BlockPosition").getClazz())
                .lookupMethod("getChunkAtWorldCoords", null, WrappedClass.getNMSClass("BlockPosition").getClazz())
                .lookupMethod("getWorld", null);
        WrappedClass.getCBClass("CraftWorld")
                .lookupMethod("getHandle", null)
                .lookupMethod("addEntity", null, WrappedClass.getNMSClass("Entity").getClazz(), CreatureSpawnEvent.SpawnReason.class)
                .lookupField("world");
        WrappedClass.getNMSClass("WorldServer")
                .lookupMethod("r", "getRegistry");
    }

    @Getter
    public final Object craftWorld;

    public WrappedCraftWorld(Object root) {
        if (root instanceof World) {
            craftWorld = WrappedClass.getCBClass("CraftWorld").cast(root);
            return;
        }

        if (WrappedClass.getNMSClass("World").isInstance(root)) {
            craftWorld = WrappedClass.getNMSClass("World").getMethod("getWorld").invoke(root);
            return;
        }

        if (WrappedClass.getCBClass("CraftWorld").isInstance(root)) {
            craftWorld = root;
            return;
        }

        if (root instanceof String)
            craftWorld = new WrappedCraftWorld(Bukkit.getWorld((String) root)).getCraftWorld();

        throw new IllegalArgumentException("Root not instance of CraftWorld nor String");
    }

    /**
     * @param blockPosition the position we want to check at.
     * @return whether or not that chunk containing the aforementioned position is loaded.
     */
    public Boolean isLoaded(WrappedBlockPosition blockPosition) {
        return (Boolean) WrappedClass.getNMSClass("World").getMethod("isLoaded").invoke(WrappedClass.getCBClass("CraftWorld").getField("world").get(craftWorld), blockPosition.getRoot());
    }

    /**
     * @return the custom registry of this world.
     */
    public WrappedIRegistryCustom getCustomRegistry() {
        return new WrappedIRegistryCustom(WrappedClass.getNMSClass("World").getMethod("getRegistry").invoke(WrappedClass.getCBClass("CraftWorld").getField("world").get(craftWorld)));
    }

    /**
     * @param blockPosition the position we want to get the chunk of.
     * @return the chunk at the provided position.
     */
    public WrappedChunk getChunkAtWorldCoords(WrappedBlockPosition blockPosition) {
        return new WrappedChunk(WrappedClass.getNMSClass("World").getMethod("getChunkAtWoorlCoords").invoke(WrappedClass.getCBClass("CraftWorld").getField("world").get(craftWorld), blockPosition.getRoot()));
    }

    /**
     * Same as {@link #setBiome(WrappedBlockPosition, WrappedBiomeBase)}.
     * <p>
     * Changes the biome at the specified location to the specified wrapped biome.
     *
     * @param x                self-explanatory.
     * @param y                self-explanatory.
     * @param z                self-explanatory.
     * @param wrappedBiomeBase the biome we want to change it to.
     * @return the chunk we changed the biome in.
     */
    public WrappedChunk setBiome(int x, int y, int z, WrappedBiomeBase wrappedBiomeBase) {
        return setBiome(new WrappedBlockPosition(x, y, z), wrappedBiomeBase);
    }

    /**
     * Same as {@link #setBiome(WrappedBlockPosition, WrappedBiomeBase)}.
     * <p>
     * Changes the biome at the specified location to the specified wrapped biome.
     *
     * @param location         self-explanatory.
     * @param wrappedBiomeBase the biome we want to change it to.
     * @return the chunk we changed the biome in.
     */
    public WrappedChunk setBiome(Location location, WrappedBiomeBase wrappedBiomeBase) {
        return setBiome(new WrappedBlockPosition(location), wrappedBiomeBase);
    }

    /**
     * Changes the biome at the specified location to the specified wrapped biome.
     *
     * @param blockPosition    self-explanatory.
     * @param wrappedBiomeBase the biome we want to change it to.
     * @return the chunk we changed the biome in.
     */
    public WrappedChunk setBiome(WrappedBlockPosition blockPosition, WrappedBiomeBase wrappedBiomeBase) {
        WrappedChunk chunk = null;

        if (isLoaded(blockPosition) && (chunk = getChunkAtWorldCoords(blockPosition)).getChunk() != null) {
            chunk.getBiomeIndex().setBiome(blockPosition.getX() >> 2, blockPosition.getY() >> 2, blockPosition.getZ() >> 2, wrappedBiomeBase);
            chunk.markDirty();
        }

        return chunk;
    }

    /**
     * @return the nms handle of this craftworld.
     */
    public WrappedWorld getHandle() {
        return new WrappedWorld(WrappedClass.getCBClass("CraftWorld").getMethod("getHandle").invoke(craftWorld));
    }

    /**
     * Adds a new entity to this world.
     *
     * @param entity      entity to add.
     * @param spawnReason the reason for it's spawning.
     * @return the entity.
     */
    public Object addEntity(Object entity, CreatureSpawnEvent.SpawnReason spawnReason) {
        return WrappedClass.getCBClass("CraftWorld").getMethod("addEntity").invoke(craftWorld, entity, spawnReason);
    }

    /**
     * Updates the chunk for the specified player.
     * <p>
     * VERIFIED NOT WORKING! May cause crash client-side!
     *
     * @param player the player we wish to send the update to.
     * @param chunk  the chunk we want to update.
     */
    public void sendUpdate(Player player, WrappedChunk chunk) {
        WrappedCraftPlayer.from(player).getHandle().getPlayerConnection().sendPacket(new WrappedPacketPlayOutMapChunk(chunk).getPacket());
    }

}
