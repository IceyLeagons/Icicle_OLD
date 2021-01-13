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

package net.iceyleagons.icicle.wrapped;

import lombok.Getter;
import net.iceyleagons.icicle.reflections.Reflections;

import java.lang.reflect.Method;

/**
 * @author TOTHTOMI
 */
public class WrappedBiomeBase {

    private static final Class<?> mc_biomebase_a;

    private static final Class<? extends Enum<?>> mc_biomebase_geography;
    private static final Method mc_geography_valueof;
    private static final Class<? extends Enum<?>> mc_biomebase_precipitation;
    private static final Method mc_precipitation_valueof;
    private static final Class<? extends Enum<?>> mc_biomebase_temperaturemodifier;
    private static final Method mc_temp_valueof;

    private static final Method biome_setDepth;
    private static final Method biome_setScale;
    private static final Method biome_setTemperature;
    private static final Method biome_setDownfall;
    private static final Method biome_setSpecialEffects;
    private static final Method biome_setMobs;
    private static final Method biome_setGeneration;
    private static final Method biome_setTemperatureModifier;
    private static final Method biome_setPrecipitation;
    private static final Method biome_setGeography;
    private static final Method biomebase_build;

    static {
        mc_biomebase_a = Reflections.getNormalNMSClass("BiomeBase$a");

        mc_biomebase_geography = (Class<? extends Enum<?>>) Reflections.getNormalNMSClass("BiomeBase$Geography");
        mc_geography_valueof = Reflections.getMethod(mc_biomebase_geography, "valueOf", true, String.class);
        mc_biomebase_precipitation = (Class<? extends Enum<?>>) Reflections.getNormalNMSClass("BiomeBase$Precipitation");
        mc_precipitation_valueof = Reflections.getMethod(mc_biomebase_precipitation, "valueOf", true, String.class);
        mc_biomebase_temperaturemodifier = (Class<? extends Enum<?>>) Reflections.getNormalNMSClass("BiomeBase$TemperatureModifier");
        mc_temp_valueof = Reflections.getMethod(mc_biomebase_temperaturemodifier, "valueOf", true, String.class);

        biome_setDepth = Reflections.getMethod(mc_biomebase_a, "", true, Float.class);
    }

    public WrappedBiomeBase(Object copyOf) {

    }


    public static enum Geography {
        NONE(Reflections.invoke(mc_geography_valueof, Object.class, null, "NONE")),
        TAIGA(Reflections.invoke(mc_geography_valueof, Object.class, null, "TAIGA")),
        EXTREME_HILLS(Reflections.invoke(mc_geography_valueof, Object.class, null, "EXTREME_HILLS")),
        JUNGLE(Reflections.invoke(mc_geography_valueof, Object.class, null, "JUNGLE")),
        MESA(Reflections.invoke(mc_geography_valueof, Object.class, null, "MESA")),
        PLAINS(Reflections.invoke(mc_geography_valueof, Object.class, null, "PLAINS")),
        SAVANNA(Reflections.invoke(mc_geography_valueof, Object.class, null, "SAVANNA")),
        ICY(Reflections.invoke(mc_geography_valueof, Object.class, null, "ICY")),
        THEEND(Reflections.invoke(mc_geography_valueof, Object.class, null, "THEEND")),
        BEACH(Reflections.invoke(mc_geography_valueof, Object.class, null, "BEACH")),
        FOREST(Reflections.invoke(mc_geography_valueof, Object.class, null, "FOREST")),
        OCEAN(Reflections.invoke(mc_geography_valueof, Object.class, null, "OCEAN")),
        DESERT(Reflections.invoke(mc_geography_valueof, Object.class, null, "DESERT")),
        RIVER(Reflections.invoke(mc_geography_valueof, Object.class, null, "RIVER")),
        SWAMP(Reflections.invoke(mc_geography_valueof, Object.class, null, "SWAMP")),
        MUSHROOM(Reflections.invoke(mc_geography_valueof, Object.class, null, "MUSHROOM")),
        NETHER(Reflections.invoke(mc_geography_valueof, Object.class, null, "NETHER"));

        @Getter
        private Object object;

        Geography(Object object) {
            this.object = object;
        }

    }

    public static enum Precipitation {
        NONE(Reflections.invoke(mc_precipitation_valueof, Object.class, null, "NONE")),
        RAIN(Reflections.invoke(mc_precipitation_valueof, Object.class, null, "RAIN")),
        SNOW(Reflections.invoke(mc_precipitation_valueof, Object.class, null, "SNOW"));

        @Getter
        private Object object;

        Precipitation(Object object) {
            this.object = object;
        }
    }

    public static enum TemperatureModifier {
        NONE(Reflections.invoke(mc_temp_valueof, Object.class, null, "NONE")),
        FROZEN(Reflections.invoke(mc_temp_valueof, Object.class, null, "FROZEN"));

        @Getter
        private Object object;

        TemperatureModifier(Object object) {
            this.object = object;
        }
    }

}
