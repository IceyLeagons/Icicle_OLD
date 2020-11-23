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

package net.iceyleagons.icicle.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contains useful methods regarding {@link java.util.Date} and Time
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateUtils {

    /**
     * @return the current time in the format of yyyy-MM-dd - HH:mm:ss
     */
    public static String getCurrentLocalTime() {
        return getCurrentLocalTime("yyyy-MM-dd - HH:mm:ss");
    }

    /**
     * @param format is the format (ex. yyyy-MM-dd - HH:mm:ss)
     * @return the current time with the given format
     */
    public static String getCurrentLocalTime(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Return the suffix for the date. Ex it's the 1st of .... or the 3rd of ....
     *
     * @param value the number to generated the suffix for
     * @return the suffix. (Does not contain the number!)
     */
    public static String getDateSuffix(int value) {
        int hunRem = value % 100;
        int tenRem = value % 10;

        if (hunRem - tenRem == 10) {
            return "th";
        }
        switch (tenRem) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

}
