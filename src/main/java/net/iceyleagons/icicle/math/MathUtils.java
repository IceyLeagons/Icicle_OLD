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

package net.iceyleagons.icicle.math;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Integer.MAX_VALUE;

/**
 * Contains useful stuff regarding Math and numbers
 *
 * @author Gabe&TOTHTOMI
 * @version 2.5.0
 * @since 1.1.4-SNAPSHOT
 */
public strictfp class MathUtils {

    /**
     * Creates a vector from the given locations.
     *
     * @param from the starting point
     * @param to   the goal
     * @return a {@link Vector}
     */
    public static Vector getVector(Location from, Location to) {
        double dX = from.getX() - to.getX();
        double dY = from.getY() - to.getY();
        double dZ = from.getZ() - to.getZ();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        double X = Math.sin(pitch) * Math.cos(yaw);
        double Y = Math.sin(pitch) * Math.sin(yaw);
        double Z = Math.cos(pitch);

        return new Vector(X, Z, Y);
    }

    /**
     * Returns the average of the entered doubles
     *
     * @param values the values
     * @return the average otherwise 0
     */
    public static double getAverage(double... values) {
        return Arrays.stream(values).average().orElse(0);
    }

    /**
     * @param value the number to check
     * @param min   minimum of the range
     * @param max   maxmimum of the range
     * @return true if the number is between the minimum and the maximum number
     */
    public static boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Return the appropriate suffix for a number.
     * Ex. 1 item, 2 item<b>s</b>
     *
     * @param value the number
     * @return "s" if it's more than one, otherwise a blank string
     */
    public static String getPronumeral(int value) {
        return value == 1 ? "" : "s";
    }

    public static double offset2d(Entity a, Entity b) {
        return offset2d(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset2d(Location a, Location b) {
        return offset2d(a.toVector(), b.toVector());
    }

    public static double offset2d(Vector a, Vector b) {
        return a.setY(0).subtract(b.setY(0)).length();
    }

    public static double offset(Location a, Location b) {
        return offset(a.toVector(), b.toVector());
    }

    public static double offset(Entity a, Entity b) {
        return offset(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset(Vector a, Vector b) {
        return a.subtract(b).length();
    }

    /**
     * A better way to generate random integers.
     *
     * @param lowerBound  the lowerBound
     * @param higherBound the higherBound
     * @return the generated random number
     */
    public static int random(Optional<Integer> lowerBound, Optional<Integer> higherBound) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        if (lowerBound.isPresent() && higherBound.isPresent())
            return random.nextInt(higherBound.get() - lowerBound.get()) + lowerBound.get();
        else return lowerBound.map(integer -> random.nextInt(MAX_VALUE - integer) + integer).orElseGet(random::nextInt);
    }

    /**
     * Normalizes the given value with the given bounds.
     * Using formula of (value - min) / (max - min)
     *
     * @param value to normalize
     * @param min   minimum
     * @param max   maxmimum
     * @return the normalized value
     */
    public static double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public static double round(double value, int precision, RoundingMode mode) {
        return new BigDecimal(String.valueOf(value)).round(new MathContext(precision, mode)).doubleValue(); //bigdecimal created with string due to standards
    }

    public static int roundToInt(double value, int precision, RoundingMode mode) {
        return new BigDecimal(String.valueOf(value)).round(new MathContext(precision, mode)).intValue(); //bigdecimal created with string due to standards
    }

    /**
     * Calculates the euclidean disatnce between these two vectors(vectors can be any size, 2d,3d,4d etc.)
     *
     * @param vectorA vectorA
     * @param vectorB vectorB
     * @return the euclidean distance between them
     */
    public static double euclideanDistance(double[] vectorA, double[] vectorB) {
        double dist = 0;
        for (int i = 0; i <= vectorA.length - 1; i++)
            dist += Math.pow(vectorA[i] - vectorB[i], 2);
        return Math.sqrt(dist);
    }

    public static void applyFunc(double[] doubleArray, Function<Double, Double> func) {
        for (int i = 0; i <= doubleArray.length - 1; i++)
            doubleArray[i] = func.apply(doubleArray[i]);
    }

    public static double[] add(double[] vectorA, double[] vectorB) {
        double[] output = new double[vectorA.length];
        for (int i = 0; i <= vectorA.length - 1; i++)
            output[i] = vectorA[i] + vectorB[i];
        return output;
    }

    public static double[] subtract(double[] vectorA, double[] vectorB) {
        return add(vectorA, opposite(vectorB));
    }

    public static double[] opposite(double[] vector) {
        return multiply(vector, -1);
    }

    /**
     * Multiply each double of the array with the provided factor
     *
     * @param vector double array to multiply
     * @param factor factor to multiply by
     * @return the multiplication results
     */
    public static double[] multiply(double[] vector, double factor) {
        double[] output = vector.clone();
        applyFunc(output, e -> e * factor);
        return output;
    }

    /**
     * Calculates the delta of a list of doubles.
     *
     * @param doubleList the list to calculate the delta of
     * @return delta
     */
    public static List<Double> calculateDelta(List<Double> doubleList) {
        // Cannot calculate delta of a list that is too small.
        if (doubleList.size() <= 1)
            throw new IllegalArgumentException();

        List<Double> out = new ArrayList<>();

        for (int i = 1; i <= doubleList.size() - 1; i++)
            out.add(doubleList.get(i) - doubleList.get(i - 1));

        return out;
    }

    /**
     * Converts a float list to a double list.
     *
     * @param floatList the list to convert
     * @return converted list
     */
    public static List<Double> toDoubleList(List<Float> floatList) {
        return floatList.stream().map(e -> (double) e).collect(Collectors.toList());
    }

    /**
     * Calculates the mean of the provided double list.
     *
     * @param angles the list to calculate the mean of
     * @return mean
     */
    public static double mean(List<Double> angles) {
        return angles.stream().mapToDouble(e -> e).sum() / angles.size();
    }

    /**
     * Calculates the standard deviation of the provided double list.
     *
     * @param angles the list to calculate the deviation of
     * @return standard deviation
     */
    public static double stddev(List<Double> angles) {
        double mean = mean(angles);
        double output = 0;
        for (double angle : angles)
            output += Math.pow(angle - mean, 2);
        return output / angles.size();
    }

    /**
     * According to my benchmarks you will usually do this in the same amount of time
     * than "regular" way of doing this.
     * <p>
     * However at scenarios, where it will be called 1000x times a second it will be useful.
     * <p>
     * Based completely off of: https://en.wikipedia.org/wiki/Fast_inverse_square_root
     *
     * @param number the number to find the inv. sqrt. for
     * @return the inv. sqrt. !APPROXIMATION! of the number
     */
    public static float fastInvSqrt(float number) {
        float localNumber = number;
        float xh = 0.5f * localNumber;

        int i = Float.floatToIntBits(localNumber);
        i = 0x5f3759df - (i >> 1);
        localNumber = Float.intBitsToFloat(i);

        localNumber *= (1.5f - xh * localNumber * localNumber);
        localNumber *= (1.5f - xh * localNumber * localNumber);
        localNumber *= (1.5f - xh * localNumber * localNumber);
        return localNumber;
    }

    /**
     * Clamps the value between the min and max values
     *
     * @param value to clamp
     * @param min   the minimum value
     * @param max   the maximum value
     * @return value if it's between min and max otherwise min or max
     */
    public static float clamp(float value, float min, float max) {
        return value < min ? min : (Math.min(value, max));
    }
}
