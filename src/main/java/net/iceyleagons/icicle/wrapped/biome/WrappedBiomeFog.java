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

package net.iceyleagons.icicle.wrapped.biome;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.utils.WrappedClass;

import java.awt.*;
import java.lang.reflect.Method;

/**
 * Wrapped representation of BiomeFog
 *
 * @author Gábe
 * @version 1.1.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedBiomeFog {
    static {
        WrappedClass.getNMSClass("BiomeFog$a").lookupMethod("a", "setFogColor", int.class)
                .lookupMethod("b", "setWaterColor", int.class)
                .lookupMethod("c", "setWaterFogColor", int.class)
                .lookupMethod("d", "SkyColor", int.class)
                .lookupMethod("e", "setFoliageColor", int.class)
                .lookupMethod("f", "setGrassColor", int.class)
                .lookupMethod("a", "setGrassColorModifier", WrappedClass.getNMSClass("BiomeFog$GrassColor").getClazz())
                .lookupMethod("a", "setParticles", WrappedClass.getNMSClass("BiomeParticles").getClazz())
                .lookupMethod("a", "setSoundEffects", WrappedClass.getNMSClass("SoundEffect").getClazz())
                .lookupMethod("a", "setMoodSounds", WrappedClass.getNMSClass("CaveSoundSettings").getClazz())
                .lookupMethod("a", "setAdditionalSounds", WrappedClass.getNMSClass("CaveSound").getClazz())
                .lookupMethod("a", "setMusic", WrappedClass.getNMSClass("Music").getClazz())
                .lookupMethod("a", "build");
    }

    @Getter
    private Object root;

    public WrappedBiomeFog(Object root) {
        this.root = root;
    }

    private static int getColor(Color color) {
        int rgb = color.getRed();
        rgb = (rgb << 8) + color.getGreen();
        rgb = (rgb << 8) + color.getBlue();

        return rgb;
    }

    /**
     * A factory used for creating biome fogs.
     *
     * @author Gábe
     * @version 1.1.0
     * @since 1.3.3-SNAPSHOT
     */
    public static class Builder {

        private Object root;

        @SneakyThrows
        private Builder() {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").newInstance();
        }

        public static WrappedBiomeFog.Builder create() {
            return new WrappedBiomeFog.Builder();
        }

        /**
         * Changes the color of the fog.
         *
         * @param color self-explanatory.
         * @return this.
         */
        public WrappedBiomeFog.Builder setFogColor(Color color) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setFogColor").invoke(root, getColor(color));
            return this;
        }

        /**
         * Changes the color of the water.
         *
         * @param color self-explanatory.
         * @return this.
         */
        public WrappedBiomeFog.Builder setWaterColor(Color color) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setWaterColor").invoke(root, getColor(color));
            return this;
        }

        /**
         * Changes the color of the fog underwater.
         *
         * @param color self-explanatory.
         * @return this.
         */
        public WrappedBiomeFog.Builder setWaterFogColor(Color color) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setWaterFogColor").invoke(root, getColor(color));
            return this;
        }

        /**
         * Changes the color of the sky.
         *
         * @param color self-explanatory.
         * @return this.
         */
        public WrappedBiomeFog.Builder setSkyColor(Color color) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setSkyColor").invoke(root, getColor(color));
            return this;
        }

        /**
         * Changes the color of the foliage.
         *
         * @param color self-explanatory.
         * @return this.
         */
        public WrappedBiomeFog.Builder setFoliageColor(Color color) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setFoliageColor").invoke(root, getColor(color));
            return this;
        }

        /**
         * Changes the color of the grass.
         *
         * @param color self-explanatory.
         * @return this.
         */
        public WrappedBiomeFog.Builder setGrassColor(Color color) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setGrassColor").invoke(root, getColor(color));
            return this;
        }

        /**
         * NOT YET IMPLEMENTED.
         *
         * @return this.
         */
        public WrappedBiomeFog.Builder setGrassColorModifier(Object color) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setGrassColorModifier").invoke(root, color);
            return this;
        }

        /**
         * NOT YET IMPLEMENTED.
         *
         * @return this.
         */
        public WrappedBiomeFog.Builder setParticles(Object particle) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setParticles").invoke(root, particle);
            return this;
        }

        /**
         * NOT YET IMPLEMENTED.
         *
         * @return this.
         */
        public WrappedBiomeFog.Builder setAmbientSounds(Object sounds) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setAmbientSounds").invoke(root, sounds);
            return this;
        }

        /**
         * NOT YET IMPLEMENTED.
         *
         * @return this.
         */
        public WrappedBiomeFog.Builder setMoodSounds(Object sounds) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setMoodSounds").invoke(root, sounds);
            return this;
        }

        /**
         * NOT YET IMPLEMENTED.
         *
         * @return this.
         */
        public WrappedBiomeFog.Builder setAdditionalSounds(Object sounds) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setAdditionalSounds").invoke(root, sounds);
            return this;
        }

        /**
         * NOT YET IMPLEMENTED.
         *
         * @return this.
         */
        public WrappedBiomeFog.Builder setMusic(Object music) {
            this.root = WrappedClass.getNMSClass("BiomeFog$a").getMethod("setMusic").invoke(root, music);
            return this;
        }

        /**
         * @return the complete BiomeFog with the settings provided beforehand.
         */
        public WrappedBiomeFog build() {
            return new WrappedBiomeFog(WrappedClass.getNMSClass("BiomeFog$a").getMethod("build").invoke(root));
        }
    }

}
