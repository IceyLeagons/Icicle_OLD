/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle.misc.maps;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

/**
 * Don't crash your server! Use this to handle map rendering properly and efficiently.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.2.0-SNAPSHOT
 */
public class BetterMapRenderer extends MapRenderer {

    private final MapUpdateListener mapUpdateListener;
    private boolean updated = false;

    /**
     * @param mapUpdateListener the {@link MapUpdateListener}
     */
    public BetterMapRenderer(MapUpdateListener mapUpdateListener) {
        this.mapUpdateListener = mapUpdateListener;
        update();
    }

    /**
     * Call this when you make an update, and it will be rendered next time!
     * <p>Note that the {@link MapUpdateListener#render(MapView, MapCanvas, Player)}
     * will only run when you've called this function</p>
     */
    public void update() {
        updated = true;
    }

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        if (updated) {
            mapUpdateListener.render(map, canvas, player);
            updated = false;
        }
    }

}
