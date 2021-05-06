package net.iceyleagons.icicle.common.nms.interfaces.biome;

import net.iceyleagons.icicle.common.nms.interfaces.IWrappable;

import java.awt.*;

public interface IBiomeFog extends IWrappable {

    Object getObject();

    default int toMCColor(Color color) {
        int rgb = color.getRed();
        rgb = (rgb << 8) + color.getGreen();
        rgb = (rgb << 8) + color.getBlue();

        return rgb;
    }

    Builder newBuilder();

    interface Builder {
        Builder setFogColor(Color color);

        Builder setWaterColor(Color color);

        Builder setWaterFogColor(Color color);

        Builder setSkyColor(Color color);

        Builder setFoliageColor(Color color);

        Builder setGrassColor(Color color);

        IBiomeFog build();
    }

}
