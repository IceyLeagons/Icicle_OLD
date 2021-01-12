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

package net.iceyleagons.icicle.storage.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Used for easy multi storage handling.
 * This will be gradually extended to support multiple data types in the future.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.0-SNAPSHOT"
 */
@Getter
@AllArgsConstructor
public enum DataType {

    /**
     * Represents a {@link String}
     */
    STRING(String.class, "LONGTEXT"),
    /**
     * Represents an {@link Integer}
     */
    INTEGER(Integer.class, "INTEGER"),
    /**
     * Represents a {@link Boolean}
     */
    BOOLEAN(Boolean.class, "TINYINT(1)"),
    /**
     * Represents a {@link Long}
     */
    LONG(Long.class, "BIGINT");

    private final Class<?> javaRepresentation;
    private final String mysqlParameter;

}
