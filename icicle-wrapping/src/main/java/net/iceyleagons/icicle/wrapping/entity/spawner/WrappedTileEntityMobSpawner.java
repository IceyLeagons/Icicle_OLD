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

package net.iceyleagons.icicle.wrapping.entity.spawner;

import net.iceyleagons.icicle.utils.Reflections;
import net.iceyleagons.icicle.wrapping.entity.WrappedTileEntity;

import java.lang.reflect.Method;

/**
 * Wrapped representation of TileEntityMobSpawner.
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.8-SNAPSHOT
 */
public class WrappedTileEntityMobSpawner extends WrappedTileEntity {

    private static final Class<?> mc_TileEntityMobSpawner;

    private static final Method mc_getSpawner;

    static {
        mc_TileEntityMobSpawner = Reflections.getNormalNMSClass("TileEntityMobSpawner");

        mc_getSpawner = Reflections.getMethod(mc_TileEntityMobSpawner, "getSpawner", true);
    }

    public WrappedTileEntityMobSpawner(Object entity) {
        super(entity);
    }

    public WrappedTileEntityMobSpawner(WrappedTileEntity entity) {
        super(entity.getEntity());
    }

    /**
     * @return the mob spawner abstract object for this spawner tile entity.
     */
    public WrappedMobSpawnerAbstract getSpawner() {
        return new WrappedMobSpawnerAbstract(Reflections.invoke(mc_getSpawner, Object.class, getEntity()));
    }


}
