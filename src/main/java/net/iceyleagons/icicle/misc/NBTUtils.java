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

package net.iceyleagons.icicle.misc;

import net.iceyleagons.icicle.jnbt.Tag;

import java.util.Map;

/**
 * Contains some useful things for NBT, which is not in the JNBT NBTUtils.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.7-SNAPSHOT
 */
public class NBTUtils {

    /**
     * @param items    the values of a Tag
     * @param key      the key to get
     * @param expected the wanted type
     * @param <T>      the wanted type
     * @return if the result can be casted to the wanted type it will return that otherwise null
     */
    public static <T> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) {
        if (!items.containsKey(key)) return null;

        Tag tag = items.get(key);
        if (!expected.isInstance(tag))
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());

        return expected.cast(tag);
    }
}
