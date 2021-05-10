package net.iceyleagons.icicle.common.nms.interfaces.world;

import org.bukkit.Location;

public interface ILightEngine {

    void update(IBlockPosition position);
    void update(Location location);

}
