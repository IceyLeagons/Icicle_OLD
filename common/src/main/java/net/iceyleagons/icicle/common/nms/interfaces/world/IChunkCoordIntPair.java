package net.iceyleagons.icicle.common.nms.interfaces.world;

import org.bukkit.Location;

public interface IChunkCoordIntPair {
    int getBlockX();

    int getBlockZ();

    IBlockPosition asPosition();

    Location asLocation();

    IBlockPosition asPositionDown();

    Location asLocationDown();

    int getRegionX();

    int getRegionZ();
}
