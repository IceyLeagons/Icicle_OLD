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

package net.iceyleagons.icicle.reflections;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Contains operations regarding ping.
 *
 * @author TOTHTOMI
 * @version 1.1.4
 * @since 1.0.0
 */
public class PingUtils {

    /**
     * This method is used to get the {@link Player}'s ping
     *
     * @param player the player
     * @return the ping (if -1 then an error happened)
     */
    public static int getPing(Player player) {
        Class<?> playerClazz = player.getClass();
        Method getHandle = Reflections.getMethod(playerClazz,"getHandle",true,null);
        if (getHandle == null) return -1;

        try {
            Object craftPlayer = getHandle.invoke(player);
            Field field = Reflections.getField(craftPlayer.getClass(),"ping",true);

            int ping = field.getInt(craftPlayer);
            return Math.max(ping, 0); //We don't want negative here, because that will indicate an error in our case.
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return -1;
        }

    }

}
