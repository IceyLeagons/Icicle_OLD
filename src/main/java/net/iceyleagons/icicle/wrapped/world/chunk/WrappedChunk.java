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
import net.iceyleagons.icicle.wrapped.WrappedBlockPosition;
import net.iceyleagons.icicle.wrapped.WrappedTileEntity;
import net.iceyleagons.icicle.wrapped.biome.WrappedBiomeStorage;
import net.iceyleagons.icicle.wrapped.world.WrappedLightEngine;
import org.bukkit.Chunk;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WrappedChunk {

    public static final Class<?> mc_Chunk, bukkit_CraftChunk;
    private static final Method chunk_markDirty, chunk_getBiomeIndex, chunk_getLightEngine, chunk_getTileEntity, chunk_getTileEntityImmediately,
            chunk_setTileEntity, chunk_setLoaded, chunk_getTileEntities, chunk_setNeedsSaving, chunk_isNeedsSaving, chunk_getPos, bukkit_getHandle;

    static {
        mc_Chunk = Reflections.getNormalNMSClass("Chunk");
        bukkit_CraftChunk = Reflections.getNormalCBClass("CraftChunk");

        chunk_markDirty = Reflections.getMethod(mc_Chunk, "markDirty", true);
        chunk_getBiomeIndex = Reflections.getMethod(mc_Chunk, "getBiomeIndex", true);
        chunk_getLightEngine = Reflections.getMethod(mc_Chunk, "e", true);
        chunk_getTileEntity = Reflections.getMethod(mc_Chunk, "getTileEntity", true, WrappedBlockPosition.mc_BlockPosition);
        chunk_getTileEntityImmediately = Reflections.getMethod(mc_Chunk, "getTileEntityImmediately", true, WrappedBlockPosition.mc_BlockPosition);
        chunk_setTileEntity = Reflections.getMethod(mc_Chunk, "setTileEntity", true, WrappedBlockPosition.mc_BlockPosition, WrappedTileEntity.mc_TileEntity);
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

    public static WrappedChunk fromBukkit(Chunk chunk) {
        return new WrappedChunk(Reflections.invoke(bukkit_getHandle, Object.class, bukkit_CraftChunk.cast(chunk)));
    }

    public void markDirty() {
        Reflections.invoke(chunk_markDirty, Void.class, chunk);
    }

    public WrappedBiomeStorage getBiomeIndex() {
        return new WrappedBiomeStorage(Reflections.invoke(chunk_getBiomeIndex, Object.class, chunk));
    }

    public WrappedLightEngine getLightEngine() {
        return new WrappedLightEngine(Reflections.invoke(chunk_getLightEngine, Object.class, chunk));
    }

    public WrappedTileEntity getTileEntity(WrappedBlockPosition position) {
        return new WrappedTileEntity(Reflections.invoke(chunk_getTileEntity, Object.class, chunk, position.getRoot()));
    }

    public WrappedTileEntity getTileEntityImmediately(WrappedBlockPosition position) {
        return new WrappedTileEntity(Reflections.invoke(chunk_getTileEntityImmediately, Object.class, chunk, position.getRoot()));
    }

    public void setTileEntity(WrappedBlockPosition position, WrappedTileEntity tileEntity) {
        Reflections.invoke(chunk_setTileEntity, Void.class, chunk, position.getRoot(), tileEntity.getEntity());
    }

    public void setLoaded(boolean flag) {
        Reflections.invoke(chunk_setLoaded, Void.class, chunk, flag);
    }

    public Map<WrappedBlockPosition, WrappedTileEntity> getTileEntities() {
        Map<WrappedBlockPosition, WrappedTileEntity> newMap = new HashMap<>();
        Reflections.invoke(chunk_getTileEntities, Map.class, chunk).forEach((pos, entity) -> newMap.put(new WrappedBlockPosition(pos), new WrappedTileEntity(entity)));

        return newMap;
    }

    public boolean isNeedsSaving() {
        return Reflections.invoke(chunk_isNeedsSaving, boolean.class, chunk);
    }

    public void setNeedsSaving(boolean flag) {
        Reflections.invoke(chunk_setNeedsSaving, Void.class, chunk, flag);
    }

    public WrappedChunkCoordIntPair getPos() {
        return new WrappedChunkCoordIntPair(Reflections.invoke(chunk_getPos, Object.class, chunk));
    }

    public WrappedChunkCoordIntPair getPosition() {
        return getPos();
    }

}
