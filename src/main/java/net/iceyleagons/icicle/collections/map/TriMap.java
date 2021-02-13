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

package net.iceyleagons.icicle.collections.map;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class TriMap<A, B, C> {

    private final Map<A, Map.Entry<B, C>> map;

    public TriMap(int initialSize) {
        this.map = new HashMap<>(initialSize);
    }

    public TriMap() {
        this(10);
    }

    public Map.Entry<B, C> getEntry(A key) {
        return map.get(key);
    }

    public C getThirdEntry(A key) {
        return getEntry(key).getValue();
    }

    public B getSecondEntry(A key) {
        return getEntry(key).getKey();
    }

    public Map.Entry<B, C> put(A key, B value1, C value2) {
        Map.Entry<B, C> entry = new AbstractMap.SimpleEntry<>(value1, value2);
        map.put(key, entry);
        return entry;
    }

    public boolean containsKey(A key) {
        return map.containsKey(key);
    }

}
