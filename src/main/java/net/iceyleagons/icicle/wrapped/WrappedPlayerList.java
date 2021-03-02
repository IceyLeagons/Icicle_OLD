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

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Method;

/**
 * Wrapped representation of the NMS class: PlayerList
 *
 * @author Gábe
 * @version 1.0
 * @since 1.3.8-SNAPSHOT
 */
@RequiredArgsConstructor
public class WrappedPlayerList {

    @NonNull
    private final Object root;

    private static final Class mc_playerList;

    private static final Method mc_reload;
    private static final Method mc_getViewDistance;
    private static final Method mc_setViewDistance; // #a(int)
    private static final Method mc_getMaxPlayers;
    private static final Method mc_setMaxPlayers;
    private static final Method mc_savePlayers;
    private static final Method mc_getPlayerCount;

    static {
        mc_playerList = Reflections.getNormalNMSClass("PlayerList");

        mc_reload = Reflections.getMethod(mc_playerList, "reload", true);
        mc_getViewDistance = Reflections.getMethod(mc_playerList, "getViewDistance", true);
        mc_setViewDistance = Reflections.getMethod(mc_playerList, "a", true, int.class);
        mc_getMaxPlayers = Reflections.getMethod(mc_playerList, "getMaxPlayers", true);
        mc_setMaxPlayers = Reflections.getMethod(mc_playerList, "setMaxPlayers", true, int.class);
        mc_savePlayers = Reflections.getMethod(mc_playerList, "savePlayers", true);
        mc_getPlayerCount = Reflections.getMethod(mc_playerList, "getPlayerCount", true);
    }

    /**
     * @return the number of players online.
     */
    public int getPlayerCount() {
        return Reflections.invoke(mc_getPlayerCount, int.class, root);
    }

    /**
     * @return the render distance of the server. Can be changed dynamically with {@link #setViewDistance(int)}.
     */
    public int getViewDistance() {
        return Reflections.invoke(mc_getViewDistance, int.class, root);
    }

    /**
     * @return the amount of players the server was told to allow. Can be changed dynamically with {@link #setMaxPlayers(int)}.
     */
    public int getMaxPlayers() {
        return Reflections.invoke(mc_getMaxPlayers, int.class, root);
    }

    /**
     * Changes the maximum amount of players to the specified value.
     *
     * @param value self-explanatory.
     */
    public void setMaxPlayers(int value) {
        Reflections.invoke(mc_setMaxPlayers, Object.class, root, value);
    }

    /**
     * Changes the render distance of the server to the specified value.
     *
     * @param value self-explanatory.
     */
    public void setViewDistance(int value) {
        Reflections.invoke(mc_setViewDistance, Object.class, root, value);
    }

    /**
     * Saves all players' data.
     */
    public void savePlayers() {
        Reflections.invoke(mc_savePlayers, Object.class, root);
    }

    /**
     * Reloads datapacks.
     */
    public void reload() {
        Reflections.invoke(mc_reload, Object.class, root);
    }

}
