package net.iceyleagons.icicle.location;

import net.iceyleagons.icicle.utils.Asserts;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to detect {@link LivingEntity}s in front of an entity.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public final class RayCast {

    /**
     * Will shoot a ray and return the first target it hits.
     *
     * @param entity   the entity to shoot from
     * @param yOffset  the yOffset (if you don't satisfied with the entity's y level)
     * @param distance the maximum distance it should travel, keep in mind, that too high value may cause the server to crash
     * @return the first {@link LivingEntity} it hits or null
     */
    public static LivingEntity shootRay(@NotNull Entity entity, int yOffset, int distance) {
        Asserts.notNull(entity, "Entity must not be null!");

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
