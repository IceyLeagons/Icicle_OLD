/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Original source from: https://www.spigotmc.org/threads/region-cuboid.329859/
 * Modified and documented by: TOTHTOMI
 *
 * @author Tristiisch74 & TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class Cuboid {

    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;
    private final double xMinCentered;
    private final double xMaxCentered;
    private final double yMinCentered;
    private final double yMaxCentered;
    private final double zMinCentered;
    private final double zMaxCentered;
    private final World world;

    /**
     * This will create a new {@link Cuboid} of off the given points.
     *
     * @param point1 startPoint
     * @param point2 endPoint
     */
    public Cuboid(@NonNull final Location point1, @NonNull final Location point2) {
        this.xMin = Math.min(point1.getBlockX(), point2.getBlockX());
        this.xMax = Math.max(point1.getBlockX(), point2.getBlockX());

        this.yMin = Math.min(point1.getBlockY(), point2.getBlockY());
        this.yMax = Math.max(point1.getBlockY(), point2.getBlockY());

        this.zMin = Math.min(point1.getBlockZ(), point2.getBlockZ());
        this.zMax = Math.max(point1.getBlockZ(), point2.getBlockZ());

        this.world = point1.getWorld();

        this.xMinCentered = this.xMin + 0.5;
        this.xMaxCentered = this.xMax + 0.5;

        this.yMinCentered = this.yMin + 0.5;
        this.yMaxCentered = this.yMax + 0.5;

        this.zMinCentered = this.zMin + 0.5;
        this.zMaxCentered = this.zMax + 0.5;
    }

    /**
     * @return a {@link HashSet} of all the blocks in the cuboid
     */
    public Set<Block> blockList() {
        final ArrayList<Block> blocks = new ArrayList<>(this.getTotalBlockSize());
        for(int x = this.xMin; x <= this.xMax; ++x) {
            for(int y = this.yMin; y <= this.yMax; ++y) {
                for(int z = this.zMin; z <= this.zMax; ++z) {
                    final Block b = this.world.getBlockAt(x, y, z);
                    blocks.add(b);
                }
            }
        }
        return new HashSet<>(blocks);
    }

    /**
     * @return the center location of the cuboid
     */
    public Location getCenter() {
        double x = (this.xMax-this.xMin) / 2.0 + this.xMin;
        double y = (this.yMax - this.yMin) / 2.0 + this.yMin;
        double z = (this.zMax - this.zMin) / 2.0 + this.zMin;
        return new Location(this.world,x,y,z);
    }

    /**
     * @return the distance between point1 and point2 ({@link Cuboid#Cuboid(Location, Location)})
     */
    public double getDistance() {
        return this.getPoint1().distance(this.getPoint2());
    }

    /**
     * @return the distance squared between point1 and point2 ({@link Cuboid#getPoint1()} {@link Cuboid#getPoint2()}
     */
    public double getDistanceSquared() {
        return this.getPoint1().distanceSquared(this.getPoint2());
    }

    /**
     * @return the height of the cuboid
     */
    public int getHeight() {
        return this.yMax - this.yMin + 1;
    }

    /**
     * @return point1
     */
    public Location getPoint1() {
        return new Location(this.world, this.xMin, this.yMin, this.zMin);
    }

    /**
     * @return point2
     */
    public Location getPoint2() {
        return new Location(this.world, this.xMax, this.yMax, this.zMax);
    }

    /**
     * @return a random location inside the cuboid
     */
    public Location getRandomLocation() {
        final Random rand = ThreadLocalRandom.current();
        final int x = rand.nextInt(Math.abs(this.xMax - this.xMin) + 1) + this.xMin;
        final int y = rand.nextInt(Math.abs(this.yMax - this.yMin) + 1) + this.yMin;
        final int z = rand.nextInt(Math.abs(this.zMax - this.zMin) + 1) + this.zMin;
        return new Location(this.world, x, y, z);
    }

    /**
     * @return the block capacity of the cuboid
     */
    public int getTotalBlockSize() {
        return this.getHeight() * this.getXWidth() * this.getZWidth();
    }

    /**
     * @return width of the cuboid on the X axis
     */
    public int getXWidth() {
        return this.xMax - this.xMin + 1;
    }

    /**
     * @return width of the cuboid on the Z axis
     */
    public int getZWidth() {
        return this.zMax - this.zMin + 1;
    }

    /**
     * Used for checking whether the given {@link Location} is inside the cuboid
     *
     * @param loc the location to check
     * @return true if it's inside the cuboid
     */
    public boolean isIn(final Location loc) {
        return loc.getWorld() == this.world && loc.getBlockX() >= this.xMin && loc.getBlockX() <= this.xMax && loc.getBlockY() >= this.yMin && loc.getBlockY() <= this.yMax && loc
                .getBlockZ() >= this.zMin && loc.getBlockZ() <= this.zMax;
    }

    /**
     * Used for checking whether the given {@link Player} is inside the cuboid
     *
     * @param player the location to check
     * @return true if it's inside the cuboid
     */
    public boolean isIn(final Player player) {
        return this.isIn(player.getLocation());
    }
}