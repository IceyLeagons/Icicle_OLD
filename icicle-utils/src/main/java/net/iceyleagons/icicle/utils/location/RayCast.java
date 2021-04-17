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

package net.iceyleagons.icicle.utils.location;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to detect {@link LivingEntity}s in front of an entity.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class RayCast {

    /**
     * Will shoot a ray and return the first target it hits.
     *
     * @param entity   the entity to shoot from
     * @param yOffset  the yOffset (if you don't satisfied with the entity's y level)
     * @param distance the maximum distance it should travel, keep in mind, that too high value may cause the server to crash
     * @return the first {@link LivingEntity} it hits or null
     */
    public static LivingEntity shootRay(Entity entity, int yOffset, int distance) {
        List<Entity> nearbyE = entity.getNearbyEntities(distance, distance, distance);
        BlockIterator bItr = new BlockIterator(entity.getLocation(), yOffset, distance);


        List<LivingEntity> entities = nearbyE.stream()
                .filter(e -> e instanceof LivingEntity)
                .map(LivingEntity.class::cast)
                .collect(Collectors.toList());

        int count = 0;
        while (bItr.hasNext()) {
            if (count++ > distance + 10) break; //failsafe

            Block block = bItr.next();
            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();

            for (LivingEntity e : entities) {
                Location loc = e.getLocation();
                double ex = loc.getX();
                double ey = loc.getY();
                double ez = loc.getZ();

                if ((bx - .75 <= ex && ex <= bx + 1.75)
                        && (bz - .75 <= ez && ez <= bz + 1.75)
                        && (by - 1 <= ey && ey <= by + 2.5)) {
                    return e;
                }
            }
        }
        return null;
    }

}
