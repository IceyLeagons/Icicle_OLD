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

package net.iceyleagons.icicle.wrapping.mojang;

import com.mojang.brigadier.StringReader;
import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Wrapped representation of MojangsonParser
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.8-SNAPSHOT
 */
public class WrappedMojangsonParser {

    private static final Class<?> mc_mojangson;

    private static final Constructor<?> parser_constructor;

    private static final Method parser_parseStatic;
    private static final Method parser_parse;
    private static final Method parser_parseLiteral;
    private static final Method parser_parseArray;

    static {
        mc_mojangson = Reflections.getNormalNMSClass("MojangsonParser");

        parser_constructor = Reflections.getConstructor(mc_mojangson, true, StringReader.class);

        parser_parseStatic = Reflections.getMethod(mc_mojangson, "parse", true, String.class);
        parser_parseLiteral = Reflections.getMethod(mc_mojangson, "parseLiteral", true, String.class);
        parser_parseArray = Reflections.getMethod(mc_mojangson, "parseArray", true);
        parser_parse = Reflections.getMethod(mc_mojangson, "a", true);
    }

    @Getter
    private final Object root;

    @SneakyThrows
    public WrappedMojangsonParser(StringReader stringReader) {
        this.root = parser_constructor.newInstance(stringReader);
    }

    public WrappedMojangsonParser(String string) {
        this(new StringReader(string));
    }

    /**
     * Parses the provided string into an NBTTagCompound.
     *
     * @param nbt the string to parse.
     * @return the parse result.
     */
    // NBTTagCompound
    public static Object parse(String nbt) {
        return Reflections.invoke(parser_parseStatic, Object.class, null, nbt);
    }

    /**
     * Parses the string provided in the constructor.
     *
     * @return the parse result. (an nbttagcompound)
     */
    // NBTTagCompound
    public Object parse() {
        return Reflections.invoke(parser_parse, Object.class, root);
    }

    /**
     * Parses the string provided, literally.
     *
     * @param nbt the string we wish to parse.
     * @return the parse result. (an NBTBase)
     */
    // NBTBase
    public Object parseLiteral(String nbt) {
        return Reflections.invoke(parser_parseLiteral, Object.class, root, nbt);
    }

    /**
     * Parses the string provided in the constructor as an array.
     *
     * @return the parse result. (an NBTBase, containing an array)
     */
    // NBTBase
    public Object parseArray() {
        return Reflections.invoke(parser_parseArray, Object.class, root);
    }

}
