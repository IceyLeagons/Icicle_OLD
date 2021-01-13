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

import lombok.SneakyThrows;
import net.iceyleagons.icicle.wrapped.TPSTime;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Contains operations regarding the servers TPS
 *
 * @author TOTHTOMI
 * @version 1.1.4
 * @since 1.0.0
 */
public class TPSUtils {

    public static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("##.##");

    private static Object server = null;
    private static Field tpsField = null;


    /**
     * Sets up the server instance
     */
    private static void setupServerInstance() throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = Reflections.getNormalNMSClass("MinecraftServer");
        server = Objects.requireNonNull(Reflections.getMethod(clazz, "getServer", true, null)).invoke(null);

    }

    /**
     * Sets up the TPS field
     *
     * @throws NoSuchFieldException if the field not exists
     */
    private static void setupTpsField() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        if (server == null) setupServerInstance();
        tpsField = server.getClass().getField("recentTps");
    }

    /**
     * This will return the TPS for the specific {@link TPSTime}
     *
     * @param tpsTime the {@link TPSTime}
     * @return the TPS for that tpsTime
     */
    @SneakyThrows
    public static double getTPS(TPSTime tpsTime) {
        if (tpsField == null) setupTpsField();
        return ((double[]) tpsField.get(server))[tpsTime.getId()];
    }

    /**
     * This will return the TPS as a string formatted with the given {@link DecimalFormat}
     *
     * @param tpsTime       the {@link TPSTime}
     * @param decimalFormat the {@link DecimalFormat}
     * @return the formatted string
     */
    public static String getTPSString(TPSTime tpsTime, DecimalFormat decimalFormat) {
        return decimalFormat.format(getTPS(tpsTime));
    }

    /**
     * This will return the TPS as a string formatted with the format of ##.##
     *
     * @param tpsTime the {@link TPSTime}
     * @return the formatted string
     */
    public static String getTPSString(TPSTime tpsTime) {
        return getTPSString(tpsTime, DEFAULT_DECIMAL_FORMAT);
    }

}
