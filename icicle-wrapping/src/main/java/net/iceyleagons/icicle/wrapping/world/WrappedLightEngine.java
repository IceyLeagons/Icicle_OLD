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

package net.iceyleagons.icicle.wrapping.world;

import lombok.Getter;
import net.iceyleagons.icicle.utils.Reflections;

import java.lang.reflect.Method;

/**
 * Wrapped representation of LightEngine
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedLightEngine {

    private static final Class<?> mc_LightEngine;
    private static final Method light_update;

    static {
        mc_LightEngine = Reflections.getNormalNMSClass("LightEngine");

        light_update = Reflections.getMethod(mc_LightEngine, "a", true, WrappedBlockPosition.mc_BlockPosition);
    }

    @Getter
    private final Object engine;

    public WrappedLightEngine(Object engine) {
        this.engine = engine;
    }

    /**
     * Tells the light engine to update at a specified location. (blockPosition)
     *
     * @param blockPosition the location to update the light at.
     */
    public void update(WrappedBlockPosition blockPosition) {
        Reflections.invoke(light_update, Void.class, engine, blockPosition.getRoot());
    }

}
