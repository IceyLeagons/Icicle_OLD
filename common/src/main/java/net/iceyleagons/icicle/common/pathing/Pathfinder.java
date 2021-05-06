package net.iceyleagons.icicle.common.pathing;

import org.bukkit.Location;

public interface Pathfinder {

    Location[] findPath(Location startingPoint, Location goal, int distance);

}
