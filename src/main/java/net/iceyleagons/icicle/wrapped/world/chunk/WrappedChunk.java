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

package net.iceyleagons.icicle.wrapped.world.chunk;

import lombok.Getter;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.biome.WrappedBiomeStorage;
import net.iceyleagons.icicle.wrapped.entity.WrappedTileEntity;
import net.iceyleagons.icicle.wrapped.utils.WrappedClass;
import net.iceyleagons.icicle.wrapped.world.WrappedBlockPosition;
import net.iceyleagons.icicle.wrapped.world.WrappedLightEngine;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapped representation of Chunk
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedChunk {

    public static final Class<?> mc_Chunk;
    private static final Class<?> bukkit_CraftChunk;
    private static final Method chunk_markDirty;
    private static final Method chunk_getBiomeIndex;
    private static final Method chunk_getLightEngine;
    private static final Method chunk_getTileEntity;
    private static final Method chunk_getTileEntityImmediately;
    private static final Method chunk_setTileEntity;
    private static final Method chunk_setLoaded;
    private static final Method chunk_getTileEntities;
    private static final Method chunk_setNeedsSaving;
    private static final Method chunk_isNeedsSaving;
    private static final Method chunk_getPos;
    private static final Method bukkit_getHandle;

    static {
        mc_Chunk = Reflections.getNormalNMSClass("Chunk");
        bukkit_CraftChunk = Reflections.getNormalCBClass("CraftChunk");

        chunk_markDirty = Reflections.getMethod(mc_Chunk, "markDirty", true);
        chunk_getBiomeIndex = Reflections.getMethod(mc_Chunk, "getBiomeIndex", true);
        chunk_getLightEngine = Reflections.getMethod(mc_Chunk, "e", true);
        chunk_getTileEntity = Reflections.getMethod(mc_Chunk, "getTileEntity", true, WrappedBlockPosition.mc_BlockPosition);
        chunk_getTileEntityImmediately = Reflections.getMethod(mc_Chunk, "getTileEntityImmediately", true, WrappedBlockPosition.mc_BlockPosition);
        chunk_setTileEntity = Reflections.getMethod(mc_Chunk, "setTileEntity", true, WrappedBlockPosition.mc_BlockPosition, WrappedClass.getNMSClass("TileEntity").getClazz());
        chunk_setLoaded = Reflections.getMethod(mc_Chunk, "setLoaded", true, boolean.class);
        chunk_getTileEntities = Reflections.getMethod(mc_Chunk, "getTileEntities", true);
        chunk_setNeedsSaving = Reflections.getMethod(mc_Chunk, "setNeedsSaving", true, boolean.class);
        chunk_isNeedsSaving = Reflections.getMethod(mc_Chunk, "isNeedsSaving", true);
        chunk_getPos = Reflections.getMethod(mc_Chunk, "getPos", true);
        bukkit_getHandle = Reflections.getMethod(bukkit_CraftChunk, "getHandle", true);
    }

    @Getter
    private final Object chunk;

    public WrappedChunk(Object chunk) {
        this.chunk = chunk;
    }

    /**
     * Converts a bukkit chunk into a wrapped NMS chunk.
     *
     * @param chunk the bukkit chunk we want to convert.
     * @return the bukkit chunks handle. (nms chunk)
     */
    public static WrappedChunk fromBukkit(Chunk chunk) {
        return new WrappedChunk(Reflections.invoke(bukkit_getHandle, Object.class, bukkit_CraftChunk.cast(chunk)));
    }

    /**
     * Marks a chunk as dirty.
     * <p>
     * Used when we want light updates, or we changed blocks. Basically marks the chunk as one that should be sent to the players asap.
     */
    public void markDirty() {
        Reflections.invoke(chunk_markDirty, Void.class, chunk);
    }

    /**
     * @return the biomeindex of this chunk.
     */
    public WrappedBiomeStorage getBiomeIndex() {
        return new WrappedBiomeStorage(Reflections.invoke(chunk_getBiomeIndex, Object.class, chunk));
    }

    /**
     * @return the light engine of this chunk.
     */
    public WrappedLightEngine getLightEngine() {
        return new WrappedLightEngine(Reflections.invoke(chunk_getLightEngine, Object.class, chunk));
    }

    /**
     * @param position the position of the tile entity we want to get.
     * @return a wrapped tile entity if found, null otherwise.
     */
    public WrappedTileEntity getTileEntity(WrappedBlockPosition position) {
        return new WrappedTileEntity(Reflections.invoke(chunk_getTileEntity, Object.class, chunk, position.getRoot()));
    }

    /**
     * Same as {@link #getTileEntity(WrappedBlockPosition)}
     *
     * @param location the location of the tile entity we want to get.
     * @return a wrapped tile entity if found, null otherwise.
     */
    public WrappedTileEntity getTileEntity(Location location) {
        return getTileEntity(new WrappedBlockPosition(location));
    }

    /**
     * @param position the position of the tile entity we want to get.
     * @return a wrapped tile entity if found, null otherwise.
     */
    public WrappedTileEntity getTileEntityImmediately(WrappedBlockPosition position) {
        return new WrappedTileEntity(Reflections.invoke(chunk_getTileEntityImmediately, Object.class, chunk, position.getRoot()));
    }

    /**
     * Same as {@link #getTileEntityImmediately(WrappedBlockPosition)}
     *
     * @param location the location of the tile entity we want to get.
     * @return a wrapped tile entity if found, null otherwise.
     */
    public WrappedTileEntity getTileEntityImmediately(Location location) {
        return getTileEntityImmediately(new WrappedBlockPosition(location));
    }

    /**
     * Makes the specified position be inhabited by a tile entity.
     *
     * @param position   the position we want to change.
     * @param tileEntity the entity we want to place there.
     */
    public void setTileEntity(WrappedBlockPosition position, WrappedTileEntity tileEntity) {
        Reflections.invoke(chunk_setTileEntity, Void.class, chunk, position.getRoot(), tileEntity.getEntity());
    }

    /**
     * @param flag not really sure what this flag is. Probably "force."
     */
    public void setLoaded(boolean flag) {
        Reflections.invoke(chunk_setLoaded, Void.class, chunk, flag);
    }

    /**
     * @return the tile entities and their respective positions in this chunk.
     */
    public Map<WrappedBlockPosition, WrappedTileEntity> getTileEntities() {
        Map<WrappedBlockPosition, WrappedTileEntity> newMap = new HashMap<>();
        Reflections.invoke(chunk_getTileEntities, Map.class, chunk).forEach((pos, entity) -> newMap.put(new WrappedBlockPosition(pos), new WrappedTileEntity(entity)));

        return newMap;
    }

    /**
     * @return whether or not this chunk needs to be saved. In other words, were there changes in this chunk?
     */
    public boolean isNeedsSaving() {
        return Reflections.invoke(chunk_isNeedsSaving, boolean.class, chunk);
    }

    /**
     * Changes the needsSaving boolean.
     *
     * @param flag whether or not this chunk needs to be saved.
     */
    public void setNeedsSaving(boolean flag) {
        Reflections.invoke(chunk_setNeedsSaving, Void.class, chunk, flag);
    }

    /**
     * @return the position of this chunk.
     */
    public WrappedChunkCoordIntPair getPos() {
        return new WrappedChunkCoordIntPair(Reflections.invoke(chunk_getPos, Object.class, chunk));
    }

    /**
     * Same as {@link #getPos()}
     *
     * @return the position of this chunk.
     */
    public WrappedChunkCoordIntPair getPosition() {
        return getPos();
    }

}
