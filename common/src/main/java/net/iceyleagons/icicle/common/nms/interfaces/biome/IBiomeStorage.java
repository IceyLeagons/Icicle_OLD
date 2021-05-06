package net.iceyleagons.icicle.common.nms.interfaces.biome;

import net.iceyleagons.icicle.common.nms.interfaces.IWrappable;
import org.bukkit.Location;

public interface IBiomeStorage extends IWrappable {

    Object getObject();

    void setBiome(int x, int y, int z, IBiomeBase biome);

    default void setBiome(Location location, IBiomeBase biome) {
        setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), biome);
    }

}
