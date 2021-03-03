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

package net.iceyleagons.icicle.time;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author TOTHTOMI
 */
public class TimeParsingUtils {

    public static final Pattern pattern = Pattern.compile("((\\d*\\.)?\\d+)([Y,M,W,D,m,h])");

    public static LocalDateTime parseFromNow(String input) {
        Matcher matcher = pattern.matcher(input);
        LocalDateTime localDateTime = LocalDateTime.now();
        while (matcher.find()) {
            String value = matcher.group(1).trim();
            String token = matcher.group(3).trim();

            TemporalAmount temporalAmount = parse(value + token);
            localDateTime = localDateTime.plus(temporalAmount);
        }

        return localDateTime;
    }

    private static TemporalAmount parse(String feString) {
        if (Character.isUpperCase(feString.charAt(feString.length() - 1))) {
            return Period.parse("P" + feString);
        } else {
            return Duration.parse("PT" + feString);
        }
    }

}
