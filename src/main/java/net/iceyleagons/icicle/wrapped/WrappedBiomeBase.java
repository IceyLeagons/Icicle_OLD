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
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Gabe
 */
public class WrappedBiomeBase {

    public static final Class<?> mc_BiomeBase;
    private static final Class<?> mc_biomebase_a, mc_biomefog, mc_biomesettingsmobs, mc_biomesettingsgeneration;

    private static final Field generation_b, mobs_b;

    private static final Class<? extends Enum<?>> mc_biomebase_geography, mc_biomebase_precipitation, mc_biomebase_temperaturemodifier;
    private static final Method mc_geography_valueof, mc_precipitation_valueof, mc_temp_valueof;

    private static final Method biome_setDepth, biome_setScale, biome_setTemperature, biome_setDownfall, biome_setSpecialEffects,
            biome_setMobs, biome_setGeneration, biome_setTemperatureModifier, biome_setPrecipitation, biome_setGeography,
            biomebase_build;

    static {
        mc_BiomeBase = Reflections.getNormalNMSClass("BiomeBase");

        mc_biomefog = Reflections.getNormalNMSClass("BiomeFog");
        mc_biomebase_a = Reflections.getNormalNMSClass("BiomeBase$a");
        mc_biomesettingsmobs = Reflections.getNormalNMSClass("BiomeSettingsMobs");
        mc_biomesettingsgeneration = Reflections.getNormalNMSClass("BiomeSettingsGeneration");

        generation_b = Reflections.getField(mc_biomesettingsgeneration, "b", true);
        mobs_b = Reflections.getField(mc_biomesettingsmobs, "b", true);

        mc_biomebase_geography = (Class<? extends Enum<?>>) Reflections.getNormalNMSClass("BiomeBase$Geography");
        mc_geography_valueof = Reflections.getMethod(mc_biomebase_geography, "valueOf", true, String.class);
        mc_biomebase_precipitation = (Class<? extends Enum<?>>) Reflections.getNormalNMSClass("BiomeBase$Precipitation");
        mc_precipitation_valueof = Reflections.getMethod(mc_biomebase_precipitation, "valueOf", true, String.class);
        mc_biomebase_temperaturemodifier = (Class<? extends Enum<?>>) Reflections.getNormalNMSClass("BiomeBase$TemperatureModifier");
        mc_temp_valueof = Reflections.getMethod(mc_biomebase_temperaturemodifier, "valueOf", true, String.class);

        biome_setDepth = Reflections.getMethod(mc_biomebase_a, "a", true, float.class);
        biome_setScale = Reflections.getMethod(mc_biomebase_a, "b", true, float.class);
        biome_setTemperature = Reflections.getMethod(mc_biomebase_a, "c", true, float.class);
        biome_setDownfall = Reflections.getMethod(mc_biomebase_a, "d", true, float.class);
        biome_setSpecialEffects = Reflections.getMethod(mc_biomebase_a, "a", true, mc_biomefog);
        biome_setMobs = Reflections.getMethod(mc_biomebase_a, "a", true, mc_biomesettingsmobs);
        biome_setGeneration = Reflections.getMethod(mc_biomebase_a, "a", true, mc_biomesettingsgeneration);
        biome_setTemperatureModifier = Reflections.getMethod(mc_biomebase_a, "a", true, mc_biomebase_temperaturemodifier);
        biome_setPrecipitation = Reflections.getMethod(mc_biomebase_a, "a", true, mc_biomebase_precipitation);
        biome_setGeography = Reflections.getMethod(mc_biomebase_a, "a", true, mc_biomebase_geography);
        biomebase_build = Reflections.getMethod(mc_biomebase_a, "a", true);
    }

    @Getter
    private Object root;

    public WrappedBiomeBase(Object root) {
        this.root = root;
    }

    private static int num = 200;

    public WrappedBiomeBase register(NamespacedKey namespace) {
        // return new WrappedBiomeBase(WrappedRegistryGeneration.register(WrappedIRegistry.BIOME, namespace, root));
        // return WrappedBiomeRegistry.register(num++, new WrappedResourceKey(WrappedIRegistry.BIOME, namespace), this);

        WrappedIRegistryCustom customRegistry = new WrappedIRegistryCustom(WrappedDedicatedServer.from(Bukkit.getServer()).getCustomRegistry());
        WrappedIRegistryWritable writable = customRegistry.getWritable(WrappedIRegistry.BIOME);
        writable.register(new WrappedResourceKey(WrappedIRegistry.BIOME, namespace), root);

        return this;
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
        private final Object object;

        Geography(Object object) {
            this.object = object;
        }
    }

    public static enum Precipitation {
        NONE(Reflections.invoke(mc_precipitation_valueof, Object.class, null, "NONE")),
        RAIN(Reflections.invoke(mc_precipitation_valueof, Object.class, null, "RAIN")),
        SNOW(Reflections.invoke(mc_precipitation_valueof, Object.class, null, "SNOW"));

        @Getter
        private final Object object;

        Precipitation(Object object) {
            this.object = object;
        }
    }

    public static enum TemperatureModifier {
        NONE(Reflections.invoke(mc_temp_valueof, Object.class, null, "NONE")),
        FROZEN(Reflections.invoke(mc_temp_valueof, Object.class, null, "FROZEN"));

        @Getter
        private final Object object;

        TemperatureModifier(Object object) {
            this.object = object;
        }
    }

    public static class Builder {

        private Object root;

        @SneakyThrows
        private Builder() {
            this.root = mc_biomebase_a.getDeclaredConstructor().newInstance();
            setMobs();
            setGeneration();
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder setGeography(Geography geography) {
            Reflections.invoke(biome_setGeography, Void.class, root, geography.getObject());
            return this;
        }

        public Builder setPrecipitation(Precipitation precipitation) {
            Reflections.invoke(biome_setPrecipitation, Void.class, root, precipitation.getObject());
            return this;
        }

        public Builder setTemperatureModifier(TemperatureModifier temperatureModifier) {
            Reflections.invoke(biome_setTemperatureModifier, Void.class, root, temperatureModifier.getObject());
            return this;
        }

        public Builder setDownfall(float downfall) {
            Reflections.invoke(biome_setDownfall, Void.class, root, downfall);
            return this;
        }

        public Builder setTemperature(float temperature) {
            Reflections.invoke(biome_setTemperature, Void.class, root, temperature);
            return this;
        }

        public Builder setSpecialEffects(WrappedBiomeFog effects) {
            Reflections.invoke(biome_setSpecialEffects, Void.class, root, effects.getRoot());
            return this;
        }

        /**
         * Same as {@link #setSpecialEffects(WrappedBiomeFog)}
         *
         * @param fog self-explanatory.
         */
        public Builder setFog(WrappedBiomeFog fog) {
            setSpecialEffects(fog);
            return this;
        }

        /**
         * Same as {@link #setSpecialEffects(WrappedBiomeFog)}
         *
         * @param properties self-explanatory.
         */
        public Builder setBiomeProperties(WrappedBiomeFog properties) {
            setSpecialEffects(properties);
            return this;
        }

        public Builder setDepth(float depth) {
            Reflections.invoke(biome_setDepth, Void.class, root, depth);
            return this;
        }

        public Builder setScale(float scale) {
            Reflections.invoke(biome_setScale, Void.class, root, scale);
            return this;
        }

        /**
         * Sets mobs to the default mobs.
         *
         * @return this
         */
        public Builder setMobs() {
            setMobs(Reflections.get(mobs_b, Object.class, null));
            return this;
        }

        /**
         * Sets the generation to the default generation.
         *
         * @return this
         */
        public Builder setGeneration() {
            setGeneration(Reflections.get(generation_b, Object.class, null));
            return this;
        }

        // TODO: FINISH THIS ASAP
        public Builder setMobs(Object mobData) {
            Reflections.invoke(biome_setMobs, Void.class, root, mobData);
            return this;
        }

        // TODO: FINISH THIS ASAP
        public Builder setGeneration(Object generationData) {
            Reflections.invoke(biome_setGeneration, Void.class, root, generationData);
            return this;
        }

        public WrappedBiomeBase build() {
            return new WrappedBiomeBase(Reflections.invoke(biomebase_build, Object.class, root));
        }
    }
}
