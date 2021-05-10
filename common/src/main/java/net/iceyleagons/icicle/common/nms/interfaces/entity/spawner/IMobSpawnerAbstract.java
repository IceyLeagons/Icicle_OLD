package net.iceyleagons.icicle.common.nms.interfaces.entity.spawner;

import net.iceyleagons.icicle.common.nms.interfaces.world.IBlockPosition;
import net.iceyleagons.icicle.common.nms.interfaces.world.IWorld;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;

public interface IMobSpawnerAbstract {

    boolean isActivated();

    NamespacedKey getMobName();

    void setMobName(NamespacedKey name);

    IWorld getWorld();

    IBlockPosition getPosition();

    Location getLocation();

    void resetTimer();

    void parseNBT(Object nbt);

    int getSpawnCount();

    int getSpawnRange();

    int getSpawnDelay();

    int getMinSpawnDelay();

    int getMaxSpawnDelay();

    int getMaxNearbyEntities();

    int getRequiredPlayers();

    void setSpawnCount(int spawnCount);

    void setSpawnRange(int spawnRange);

    void setSpawnDelay(int spawnDelay);

    void setMinSpawnDelay(int minSpawnDelay);

    void setMaxSpawnDelay(int maxSpawnDelay);

    void setMaxNearbyEntities(int maxNearbyEntities);

    void setRequiredPlayers(int requiredPlayers);

}
