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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * In case of SQL it represents a row in case of mongo it represents a document etc.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since  1.3.0-SNAPSHOT"
 */
@RequiredArgsConstructor
@Getter
public class ContainerData {

    private final DataType[] dataTypes;
    private final String[] keys;
    private final Object[] values;
    private final long id;

    /**
     * Returns the value of a given key.
     * Key must be ine the keys String[]
     *
     * @param key the key
     * @return the found value or null
     */
    public Object getValue(String key) {
        int index = getIndex(key);
        if (index == -1) return null;

        return dataTypes[index].getJavaRepresentation().cast(values[index]);
    }

    /**
     * Returns the index of a key
     *
     * @param toGet they key to find the index of
     * @return the index of that key
     */
    private int getIndex(String toGet) {
        for (int i = 0; i < keys.length; i++){
            String key = keys[i];
            if (key.equals(toGet)) return i;
        }
        return -1;
    }

}
