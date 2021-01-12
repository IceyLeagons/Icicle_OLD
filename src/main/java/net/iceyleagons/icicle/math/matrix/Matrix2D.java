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

package net.iceyleagons.icicle.math.matrix;

import lombok.Getter;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class Matrix2D {

    @Getter
    private final int width;
    @Getter
    private final int height;

    private final double[][] matrix;

    /**
     * @param width  the width of the matrix
     * @param height the height of the matrix
     */
    public Matrix2D(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new double[height][width];
    }

    /**
     * Adds the two matrices together. Will not change the original matrices!
     *
     * @param a {@link Matrix2D} a
     * @param b {@link Matrix2D} b
     * @return the resulting {@link Matrix2D}
     */
    public static Matrix2D add(Matrix2D a, Matrix2D b) {
        if (!a.areDimensionsEqualTo(b))
            throw new IllegalArgumentException("Passed matrix should have equal dimensions (width, height)! ");

        Matrix2D result = new Matrix2D(a.getHeight(), a.getWidth());

        for (int row = 0; row < a.getHeight(); row++) {
            for (int column = 0; column < a.getWidth(); column++) {
                result.set(column, row, a.get(column, row) + b.get(column, row));
            }
        }

        return result;
    }

    /**
     * Subtracts the two matrices together. Will not change the original matrices!
     *
     * @param a {@link Matrix2D} a
     * @param b {@link Matrix2D} b
     * @return the resulting {@link Matrix2D}
     */
    public static Matrix2D subtract(Matrix2D a, Matrix2D b) {
        if (!a.areDimensionsEqualTo(b))
            throw new IllegalArgumentException("Passed matrix should have equal dimensions (width, height)! ");

        Matrix2D result = new Matrix2D(a.getHeight(), a.getWidth());

        for (int row = 0; row < a.getHeight(); row++) {
            for (int column = 0; column < a.getWidth(); column++) {
                result.set(column, row, a.get(column, row) - b.get(column, row));
            }
        }

        return result;
    }

    /**
     * Multiplies the two matrices together. Will not change the original matrices!
     *
     * @param a {@link Matrix2D} a
     * @param b {@link Matrix2D} b
     * @return the resulting {@link Matrix2D}
     */
    public static Matrix2D multiply(Matrix2D a, Matrix2D b) {
        Matrix2D result = new Matrix2D(a.getHeight(), b.getWidth());

        int ah = a.getHeight();
        int aw = a.getWidth();
        int bh = b.getHeight();
        int bw = b.getWidth();

        for (int i = 0; i < ah; i++) {
            for (int j = 0; j < bw; j++) {

                double sum = 0;
                for (int k = 0; k < aw; k++)
                    sum += a.get(k, i) * b.get(j, k);

                result.set(j, i, sum);
            }
        }

        return result;
    }

    /**
     * Sets the value at position supplied
     *
     * @param row    the row
     * @param column the column
     * @param value  the value to set to
     * @return the {@link Matrix2D} for chaining
     */
    public Matrix2D set(int column, int row, double value) {
        matrix[row][column] = value;
        return this;
    }

    /**
     * Sets the value of the row to the supplied values
     *
     * @param row    the row to set
     * @param values the values to set to
     * @return the {@link Matrix2D} for chaining
     */
    public Matrix2D setRow(int row, double... values) {
        if (values.length != matrix[row].length) {
            throw new IllegalArgumentException("Given values' size does not match" +
                    "the matrix size!");
        }

        int i = 0;
        for (double value : values) {
            matrix[row][i++] = value;
        }
        return this;
    }

    /**
     * Fills in the entire matrix with the given default value
     *
     * @param defaultValue the default value
     * @return the this for chaining
     */
    public Matrix2D fillDefault(double defaultValue) {
        for (int row = 0; row < this.getHeight(); row++) {
            for (int column = 0; column < this.getWidth(); column++) {
                set(column, row, defaultValue);
            }
        }
        return this;
    }

    /**
     * Makes a copy of this matrix.
     *
     * @return the resulting matrix
     */
    public Matrix2D makeCopy() {
        Matrix2D matrix2D = new Matrix2D(getWidth(), getHeight());

        for (int row = 0; row < this.getHeight(); row++) {
            for (int column = 0; column < this.getWidth(); column++) {
                matrix2D.set(column, row, this.get(column, row));
            }
        }

        return matrix2D;
    }

    /**
     * @param column the column
     * @param row    the row
     * @return the value present in the supplied row and column
     */
    public double get(int column, int row) {
        return matrix[row][column];
    }

    /**
     * @param row the row to get
     * @return the values in the row
     */
    public double[] getRow(int row) {
        return matrix[row];
    }

    /**
     * Prints out a row with the values appended with ","
     *
     * @param row the row to print
     * @return the formatted string
     */
    private String printRow(int row) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < width; i++) {
            stringBuilder.append(get(i, row)).append(", ");
        }
        return stringBuilder.toString();
    }

    /**
     * @return the entire matrix as a formatted string
     */
    public String prettyPrint() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            stringBuilder.append(printRow(i));
            if (i + 1 != height) stringBuilder.append("\n");
        }

        String result = stringBuilder.toString();
        return result.substring(0, result.length() - 2);
    }

    /**
     * Checks whether the height and width of the supplied matrix and this matrix are equal.
     *
     * @param toCompareTo the {@link Matrix2D} to compare to
     * @return true if they're equals false otherwise
     */
    public boolean areDimensionsEqualTo(Matrix2D toCompareTo) {
        if (this.getWidth() != toCompareTo.getWidth())
            return false;

        return this.getHeight() == toCompareTo.getHeight();
    }
}
