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

package net.iceyleagons.icicle.wrapped.entity;

import lombok.Getter;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftWorld;
import net.iceyleagons.icicle.wrapped.utils.WrappedClass;
import net.iceyleagons.icicle.wrapped.world.WrappedBlockPosition;
import net.iceyleagons.icicle.wrapped.world.chunk.WrappedChunk;
import org.bukkit.inventory.InventoryHolder;

import java.lang.reflect.Method;

/**
 * Wrapped representation of TileEntity
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedTileEntity {
    static {
        WrappedClass.getNMSClass("TileEntity")
                .lookupMethod("getCurrentChunk", "getChunk")
                .lookupMethod("getWorld", null)
                .lookupMethod("hasWorld", null)
                .lookupMethod("update", null)
                .lookupMethod("getPosition", null)
                .lookupMethod("invalidateBlockCache", null)
                .lookupMethod("setPosition", null, WrappedClass.getNMSClass("BlockPosition").getClazz())
                .lookupMethod("getOwner", null, boolean.class);
    }

    @Getter
    private final Object entity;

    public WrappedTileEntity(Object entity) {
        this.entity = entity;
    }

    /**
     * @return the "host" chunk.
     */
    public WrappedChunk getChunk() {
        return new WrappedChunk(WrappedClass.getNMSClass("TileEntity").getMethod("getChunk").invoke(entity));
    }

    /**
     * @return the "host" world.
     */
    public WrappedCraftWorld getWorld() {
        return null;//
        // new WrappedCraftWorld(Reflections.invoke(tile_getWorld, Object.class, entity));
    }

    /**
     * @return whether or not this tile entity belongs to any one world.
     */
    public Boolean hasWorld() {
        return null;//Reflections.invoke(tile_hasWorld, Boolean.class, entity);
    }

    /**
     * Run on ticks. I guess redstone components use this?
     */
    public void update() {
        //Reflections.invoke(tile_update, Void.class, entity);
    }

    /**
     * @return the position of this tile entity.
     */
    public WrappedBlockPosition getPosition() {
        return null;//new WrappedBlockPosition(Reflections.invoke(tile_getPosition, Object.class, entity));
    }

    /**
     * Updates the position of this tile entity.
     *
     * @param blockPosition the new position of this tile entity.
     */
    public void setPosition(WrappedBlockPosition blockPosition) {
        //Reflections.invoke(tile_setPosition, Void.class, entity, blockPosition.getRoot());
    }

    /**
     * Invalidates the block cache of this tile entity.
     */
    public void invalidateBlockCache() {
        //Reflections.invoke(tile_invalidateBlockCache, Void.class, entity);
    }

    /**
     * Same as {@link #getOwner(boolean)} with a true supplied.
     * <p>
     * Gets the owner of this tile entity. Used for chests, I guess?
     *
     * @return an {@link InventoryHolder}.
     */
    public InventoryHolder getOwner() {
        return getOwner(true);
    }

    /**
     * Gets the owner of this tile entity. Used for chests, I guess?
     *
     * @param useSnapshot whether or not to look at the current version, or a snapshot of this tile entity. I'm guessing current version would freeze up the server.
     * @return an {@link InventoryHolder}.
     */
    public InventoryHolder getOwner(boolean useSnapshot) {
        return null;//Reflections.invoke(tile_getOwner, InventoryHolder.class, entity, useSnapshot);
    }

}
