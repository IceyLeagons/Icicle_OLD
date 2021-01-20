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

import java.awt.*;
import java.lang.reflect.Method;

/**
 * @author TOTHTOMI
 */
public class WrappedBiomeFog {

    private static final Class<?> mc_biomefog_a;
    private static final Class<?> mc_biomefog_grasscolor;
    private static final Class<?> mc_biomeparticles;
    private static final Class<?> mc_soundeffect;
    private static final Class<?> mc_cavesoundsettings;
    private static final Class<?> mc_cavesound;
    private static final Class<?> mc_music;

    private static final Method biome_setFogColor;
    private static final Method biome_setWaterColor;
    private static final Method biome_setWaterFogColor;
    private static final Method biome_setSkyColor;
    private static final Method biome_setGrassColor;
    private static final Method biome_setParticles;
    private static final Method biome_setAmbientSounds;
    private static final Method biome_setMoodSounds;
    private static final Method biome_setAdditionalSounds;
    private static final Method biome_setMusic;
    private static final Method biome_fog_create;

    static {
        mc_biomefog_a = Reflections.getNormalNMSClass("BiomeFog$a");
        mc_biomefog_grasscolor = Reflections.getNormalNMSClass("BiomeFog$GrassColor");
        mc_biomeparticles = Reflections.getNormalNMSClass("BiomeParticles");
        mc_soundeffect = Reflections.getNormalNMSClass("SoundEffect");
        mc_cavesoundsettings = Reflections.getNormalNMSClass("CaveSoundSettings");
        mc_cavesound = Reflections.getNormalNMSClass("CaveSound");
        mc_music = Reflections.getNormalNMSClass("Music");

        biome_setFogColor = Reflections.getMethod(mc_biomefog_a, "a", true, int.class);
        biome_setWaterColor = Reflections.getMethod(mc_biomefog_a, "b", true, int.class);
        biome_setWaterFogColor = Reflections.getMethod(mc_biomefog_a, "c", true, int.class);
        biome_setSkyColor = Reflections.getMethod(mc_biomefog_a, "d", true, int.class);
        biome_setGrassColor = Reflections.getMethod(mc_biomefog_a, "a", true, mc_biomefog_grasscolor);
        biome_setParticles = Reflections.getMethod(mc_biomefog_a, "a", true, mc_biomeparticles);
        biome_setAmbientSounds = Reflections.getMethod(mc_biomefog_a, "a", true, mc_soundeffect);
        biome_setMoodSounds = Reflections.getMethod(mc_biomefog_a, "a", true, mc_cavesoundsettings);
        biome_setAdditionalSounds = Reflections.getMethod(mc_biomefog_a, "a", true, mc_cavesound);
        biome_setMusic = Reflections.getMethod(mc_biomefog_a, "a", true, mc_music);
        biome_fog_create = Reflections.getMethod(mc_biomefog_a, "a", true);
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

    public static class Builder {

        Object root;
        WrappedBiomeBase.Geography geography;
        WrappedBiomeBase.Precipitation precipitation;
        WrappedBiomeBase.TemperatureModifier temperatureModifier;

        @SneakyThrows
        private Builder() {
            this.root = mc_biomefog_a.getDeclaredConstructor().newInstance();
        }

        public static WrappedBiomeFog.Builder create() {
            return new WrappedBiomeFog.Builder();
        }

        public WrappedBiomeFog.Builder setFogColor(Color color) {
            this.root = Reflections.invoke(biome_setFogColor, Object.class, root, getColor(color));
            return this;
        }

        public WrappedBiomeFog.Builder setWaterColor(Color color) {
            this.root = Reflections.invoke(biome_setWaterColor, Object.class, root, getColor(color));
            return this;
        }

        public WrappedBiomeFog.Builder setWaterFogColor(Color color) {
            this.root = Reflections.invoke(biome_setWaterFogColor, Object.class, root, getColor(color));
            return this;
        }

        public WrappedBiomeFog.Builder setSkyColor(Color color) {
            this.root = Reflections.invoke(biome_setSkyColor, Object.class, root, getColor(color));
            return this;
        }

        public WrappedBiomeFog.Builder setGrassColor(Object color) {
            this.root = Reflections.invoke(biome_setGrassColor, Object.class, root, color);
            return this;
        }

        public WrappedBiomeFog.Builder setParticles(Object particle) {
            this.root = Reflections.invoke(biome_setParticles, Object.class, root, particle);
            return this;
        }

        public WrappedBiomeFog.Builder setAmbientSounds(Object sounds) {
            this.root = Reflections.invoke(biome_setAmbientSounds, Object.class, root, sounds);
            return this;
        }

        public WrappedBiomeFog.Builder setMoodSounds(Object sounds) {
            this.root = Reflections.invoke(biome_setMoodSounds, Object.class, root, sounds);
            return this;
        }

        public WrappedBiomeFog.Builder setAdditionalSounds(Object sounds) {
            this.root = Reflections.invoke(biome_setAdditionalSounds, Object.class, root, sounds);
            return this;
        }

        public WrappedBiomeFog.Builder setMusic(Object music) {
            this.root = Reflections.invoke(biome_setMusic, Object.class, root, music);
            return this;
        }

        public WrappedBiomeFog build() {
            return new WrappedBiomeFog(Reflections.invoke(biome_fog_create, Object.class, root));
        }
    }

}
