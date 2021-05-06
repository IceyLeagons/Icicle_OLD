package net.iceyleagons.icicle.common.nms.interfaces.biome;

import net.iceyleagons.icicle.common.nms.interfaces.IWrappable;
import org.bukkit.NamespacedKey;

public interface IBiomeBase extends IWrappable {

    Object getObject();

    IBiomeBase register(NamespacedKey identification);

    Builder newBuilder();

    interface Builder extends IWrappable {
        Builder setMobs(Object mobData);

        Builder setDepth(float depth);

        Builder setScale(float scale);

        Builder setTemperature(float temperature);

        Builder setDownfall(float downfall);

        Builder setFog(IBiomeFog fog);

        default Builder setSpecialEffects(IBiomeFog specialEffects) {
            return setFog(specialEffects);
        }

        default Builder setColors(IBiomeFog colors) {
            return setFog(colors);
        }

        default Builder setColours(IBiomeFog colours) {
            return setFog(colours);
        }

        default Builder setBiomeProperties(IBiomeFog properties) {
            return setFog(properties);
        }

        Builder setTemperatureModifier(TemperatureModifier temperatureModifier);

        Builder setPrecipitation(Precipitation precipitation);

        Builder setGeography(Geography geography);

        IBiomeBase build();
    }

    enum Geography {
        NONE, TAIGA, EXTREME_HILLS, JUNGLE, MESA,
        PLAINS, SAVANNA, ICY, THE_END,
        BEACH, FOREST, OCEAN, DESERT,
        RIVER, SWAMP, MUSHROOM, NETHER
    }

    enum Precipitation {
        NONE, RAIN, SNOW
    }

    enum TemperatureModifier {
        NONE, FROZEN
    }

}
