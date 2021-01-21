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

package net.iceyleagons.icicle.wrapped;

import lombok.Getter;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftWorld;
import net.iceyleagons.icicle.wrapped.world.chunk.WrappedChunk;
import org.bukkit.inventory.InventoryHolder;

import java.lang.reflect.Method;

public class WrappedTileEntity {

    public static final Class<?> mc_TileEntity;
    private static final Method tile_getChunk;
    private static final Method tile_getWorld;
    private static final Method tile_hasWorld;
    private static final Method tile_update;
    private static final Method tile_getPosition;
    private static final Method tile_invalidateBlockCache;
    private static final Method tile_setPosition;
    private static final Method tile_getOwner;

    static {
        mc_TileEntity = Reflections.getNormalNMSClass("TileEntity");

        tile_getChunk = Reflections.getMethod(mc_TileEntity, "getCurrentChunk", true);
        tile_getWorld = Reflections.getMethod(mc_TileEntity, "getWorld", true);
        tile_hasWorld = Reflections.getMethod(mc_TileEntity, "hasWorld", true);
        tile_update = Reflections.getMethod(mc_TileEntity, "update", true);
        tile_getPosition = Reflections.getMethod(mc_TileEntity, "getPosition", true);
        tile_invalidateBlockCache = Reflections.getMethod(mc_TileEntity, "invalidateBlockCache", true);
        tile_setPosition = Reflections.getMethod(mc_TileEntity, "setPosition", true, WrappedBlockPosition.mc_BlockPosition);
        tile_getOwner = Reflections.getMethod(mc_TileEntity, "getOwner", true, boolean.class);
    }

    @Getter
    private final Object entity;

    public WrappedTileEntity(Object entity) {
        if (!mc_TileEntity.isInstance(entity))
            throw new IllegalArgumentException("Provided object not instance of TileEntity.");

        this.entity = entity;
    }

    public WrappedChunk getChunk() {
        return new WrappedChunk(Reflections.invoke(tile_getChunk, Object.class, entity));
    }

    public WrappedCraftWorld getWorld() {
        return new WrappedCraftWorld(Reflections.invoke(tile_getWorld, Object.class, entity));
    }

    public Boolean hasWorld() {
        return Reflections.invoke(tile_hasWorld, Boolean.class, entity);
    }

    public void update() {
        Reflections.invoke(tile_update, Void.class, entity);
    }

    public WrappedBlockPosition getPosition() {
        return new WrappedBlockPosition(Reflections.invoke(tile_getPosition, Object.class, entity));
    }

    public void setPosition(WrappedBlockPosition blockPosition) {
        Reflections.invoke(tile_setPosition, Void.class, entity, blockPosition.getRoot());
    }

    public void invalidateBlockCache() {
        Reflections.invoke(tile_invalidateBlockCache, Void.class, entity);
    }

    public InventoryHolder getOwner() {
        return getOwner(true);
    }

    public InventoryHolder getOwner(boolean useSnapshot) {
        return Reflections.invoke(tile_getOwner, InventoryHolder.class, entity, useSnapshot);
    }

}
