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
import net.iceyleagons.icicle.wrapped.entity.WrappedTileEntity;
import org.bukkit.block.TileState;

import java.lang.reflect.Method;

/**
 * Wrapped representation of CraftBlockEntityState
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.8-SNAPSHOT
 */
public class WrappedCraftBlockEntityState {

    private static final Class<?> bukkit_CraftBlockEntityState;

    private static final Method bukkit_getTileEntity;

    static {
        bukkit_CraftBlockEntityState = Reflections.getNormalCBClass("block.CraftBlockEntityState");

        bukkit_getTileEntity = Reflections.getMethod(bukkit_CraftBlockEntityState, "getTileEntity", true);
    }

    @Getter
    private final Object root;

    public WrappedCraftBlockEntityState(TileState tileState) {
        this.root = bukkit_CraftBlockEntityState.cast(tileState);
    }

    /**
     * @return the tile entity object for this tile state.
     */
    public WrappedTileEntity getTileEntity() {
        return new WrappedTileEntity(Reflections.invoke(bukkit_getTileEntity, Object.class, root));
    }

}
