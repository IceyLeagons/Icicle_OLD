package net.iceyleagons.icicle.pathing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.iceyleagons.icicle.wrapped.entity.WrappedCraftCreature;
import net.iceyleagons.icicle.wrapped.entity.WrappedEntityInsentient;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;


public class PathExecutor {

    private final Entity entity;
    private final WrappedEntityInsentient entityInsentient;
    private final Location[] path;
    @Setter
    @Getter
    private double speed;

    private BukkitRunnable bukkitRunnable;

    public PathExecutor(Entity entity, Location[] path, double speed) {
        this.entity = entity;
        this.path = path;
        this.speed = speed;
        entityInsentient = new WrappedEntityInsentient(new WrappedCraftCreature(entity));
    }

    public void execute() {

        this.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
    }

    private void moveEntity(Location location) {
        entityInsentient.getNavigation().move(location.getX(), location.getY(), location.getZ(), speed * 1.25);
    }

    private void terminate() {
        bukkitRunnable.cancel();
    }

}
