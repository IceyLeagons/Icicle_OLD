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

package net.iceyleagons.icicle.common.wrapped.biome;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.common.wrapped.registry.WrappedIRegistry;
import net.iceyleagons.icicle.common.wrapped.registry.WrappedIRegistryCustom;
import net.iceyleagons.icicle.common.wrapped.registry.WrappedResourceKey;
import net.iceyleagons.icicle.common.wrapped.WrappedDedicatedServer;
import net.iceyleagons.icicle.common.wrapped.utils.WrappedClass;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

/**
 * Wrapped representation of BiomeBase
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedBiomeBase {
    static {
        WrappedClass.getNMSClass("BiomeSettingsMobs")
                .lookupField("b");
        WrappedClass.getNMSClass("BiomeSettingsGeneration")
                .lookupField("b");

        WrappedClass.getNMSClass("BiomeBase$Geography")
                .lookupMethod("valueOf", null, String.class);
        WrappedClass.getNMSClass("BiomeBase$Precipitation")
                .lookupMethod("valueOf", null, String.class);
        WrappedClass.getNMSClass("BiomeBase$TemperatureModifier")
                .lookupMethod("valueOf", null, String.class);

        WrappedClass.getNMSClass("BiomeBase$a")
                .lookupMethod("a", "setDepth", float.class)
                .lookupMethod("b", "setScale", float.class)
                .lookupMethod("c", "setTemperature", float.class)
                .lookupMethod("d", "setDownfall", float.class)
                .lookupMethod("a", "setSpecialEffects", WrappedClass.getNMSClass("BiomeFog").getClazz())
                .lookupMethod("a", "setMobs", WrappedClass.getNMSClass("BiomeSettingsMobs").getClazz())
                .lookupMethod("a", "setGeneration", WrappedClass.getNMSClass("BiomeSettingsGeneration").getClazz())
                .lookupMethod("a", "setTemperatureModifier", WrappedClass.getNMSClass("BiomeBase$TemperatureModifier").getClazz())
                .lookupMethod("a", "setPrecipitation", WrappedClass.getNMSClass("BiomeBase$Precipitation").getClazz())
                .lookupMethod("a", "setGeography", WrappedClass.getNMSClass("BiomeBase$Geography").getClazz())
                .lookupMethod("a", "build");
    }

    @Getter
    private Object root;

    public WrappedBiomeBase(Object root) {
        this.root = root;
    }

    /**
     * Registers this biome with the specified id.
     *
     * @param namespace the id of this biome.
     * @return this biome.
     */
    public WrappedBiomeBase register(NamespacedKey namespace) {
        WrappedIRegistryCustom customRegistry = WrappedDedicatedServer.from(Bukkit.getServer()).getCustomRegistry();
        customRegistry.getWritable(WrappedIRegistry.BIOME).register(new WrappedResourceKey(WrappedIRegistry.BIOME, namespace), root);

        return this;
    }

    /**
     * Wrapped representation of the inner-class Geography.
     *
     * @author Gábe
     * @version 1.0.0
     * @since 1.3.3-SNAPSHOT
     */
    public static enum Geography {
        NONE("NONE"),
        TAIGA("TAIGA"),
        EXTREME_HILLS("EXTREME_HILLS"),
        JUNGLE("JUNGLE"),
        MESA("MESA"),
        PLAINS("PLAINS"),
        SAVANNA("SAVANNA"),
        ICY("ICY"),
        THE_END("THEEND"),
        BEACH("BEACH"),
        FOREST("FOREST"),
        OCEAN("OCEAN"),
        DESERT("DESERT"),
        RIVER("RIVER"),
        SWAMP("SWAMP"),
        MUSHROOM("MUSHROOM"),
        NETHER("NETHER");

        @Getter
        private final Object object;

        Geography(String value) {
            this.object = WrappedClass.getNMSClass("BiomeBase$Geography").getMethod("valueOf").invoke(null, value);
        }
    }

    /**
     * Wrapped representation of the inner-class Precipitation.
     *
     * @author Gábe
     * @version 1.0.0
     * @since 1.3.3-SNAPSHOT
     */
    public static enum Precipitation {
        NONE("NONE"),
        RAIN("RAIN"),
        SNOW("SNOW");

        @Getter
        private final Object object;

        Precipitation(String value) {
            this.object = WrappedClass.getNMSClass("BiomeBase$Precipitation").getMethod("valueOf").invoke(null, value);
        }
    }

    /**
     * Wrapped representation of the inner-class TemperatureModifier.
     *
     * @author Gábe
     * @version 1.0.0
     * @since 1.3.3-SNAPSHOT
     */
    public static enum TemperatureModifier {
        NONE("NONE"),
        FROZEN("FROZEN");

        @Getter
        private final Object object;

        TemperatureModifier(String value) {
            this.object = WrappedClass.getNMSClass("BiomeBase$TemperatureModifier").getMethod("valueOf").invoke(null, value);
        }
    }

    /**
     * Builder for BiomeBase's.
     *
     * @author Gábe
     * @version 1.0.0
     * @since 1.3.3-SNAPSHOT
     */
    public static class Builder {

        private final Object root;

        @SneakyThrows
        private Builder() {
            this.root = WrappedClass.getNMSClass("BiomeBase$a").newInstance();
            setMobs();
            setGeneration();
        }

        /**
         * @return a new builder instance.
         */
        public static Builder create() {
            return new Builder();
        }

        /**
         * Changes the geography to the provided value.
         *
         * @param geography the value we wish to change it to.
         * @return this.
         */
        public Builder setGeography(Geography geography) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setGeography").invokeNoReturn(root, geography.getObject());
            return this;
        }

        /**
         * Changes the precipitation to the provided value.
         *
         * @param precipitation the value we wish to change it to.
         * @return this.
         */
        public Builder setPrecipitation(Precipitation precipitation) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setPrecipitation").invokeNoReturn(root, precipitation.getObject());
            return this;
        }

        /**
         * Changes the temperature modifier to the provided value.
         *
         * @param temperatureModifier the value we wish to change it to.
         * @return this.
         */
        public Builder setTemperatureModifier(TemperatureModifier temperatureModifier) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setTemperatureModifier").invokeNoReturn(root, temperatureModifier.getObject());
            return this;
        }

        /**
         * Changes the downfall to the provided value.
         *
         * @param downfall the value we wish to change it to.
         * @return this.
         */
        public Builder setDownfall(float downfall) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setDownfall").invokeNoReturn(root, downfall);
            return this;
        }

        /**
         * Changes the temperature to the provided value.
         *
         * @param temperature the value we wish to change it to.
         * @return this.
         */
        public Builder setTemperature(float temperature) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setTemperature").invokeNoReturn(root, temperature);
            return this;
        }

        /**
         * Changes the effects to the provided value.
         *
         * @param effects the value we wish to change it to.
         * @return this.
         */
        public Builder setSpecialEffects(WrappedBiomeFog effects) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setSpecialEffects").invokeNoReturn(root, effects.getRoot());
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

        /**
         * Changes the depth to the provided value.
         *
         * @param depth the value we wish to change it to.
         * @return this.
         */
        public Builder setDepth(float depth) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setDepth").invokeNoReturn(root, depth);
            return this;
        }

        /**
         * Changes the scale to the provided value.
         *
         * @param scale the value we wish to change it to.
         * @return this.
         */
        public Builder setScale(float scale) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setScale").invokeNoReturn(root, scale);
            return this;
        }

        /**
         * Sets mobs to the default mobs.
         *
         * @return this.
         */
        public Builder setMobs() {
            setMobs(WrappedClass.getNMSClass("BiomeSettingsMobs").getField("b").get(null));
            return this;
        }

        /**
         * Sets the generation to the default generation.
         *
         * @return this.
         */
        public Builder setGeneration() {
            setGeneration(WrappedClass.getNMSClass("BiomeSettingsGeneration").getField("b").get(null));
            return this;
        }

        /**
         * Changes the mob data to the provided value.
         *
         * @param mobData the value we wish to change it to.
         * @return this.
         */
        public Builder setMobs(Object mobData) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setMobs").invokeNoReturn(root, mobData);
            return this;
        }

        /**
         * Changes the generation data to the provided value.
         *
         * @param generationData the value we wish to change it to.
         * @return this.
         */
        public Builder setGeneration(Object generationData) {
            WrappedClass.getNMSClass("BiomeBase$a").getMethod("setGeneration").invokeNoReturn(root, generationData);
            return this;
        }

        /**
         * @return the biome base with the provided settings.
         */
        public WrappedBiomeBase build() {
            return new WrappedBiomeBase(WrappedClass.getNMSClass("BiomeBase$a").getMethod("build").invoke(root));
        }
    }
}
