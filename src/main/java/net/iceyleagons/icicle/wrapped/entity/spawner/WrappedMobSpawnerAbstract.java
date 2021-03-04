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

package net.iceyleagons.icicle.wrapped.entity.spawner;

import lombok.Getter;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftNamespacedKey;
import net.iceyleagons.icicle.wrapped.world.WrappedBlockPosition;
import net.iceyleagons.icicle.wrapped.world.WrappedWorld;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Wrapped representation of MobSpawnerAbstract
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.8-SNAPSHOT
 */
public class WrappedMobSpawnerAbstract {

    private static final Class<?> mc_MobSpawnerAbstract;

    private static final Method mc_isActivated;
    private static final Method mc_setMobName;
    private static final Method mc_setSpawnData;
    private static final Method mc_getMobName;
    private static final Method mc_getWorld; // #a()
    private static final Method mc_getPosition; // #b()
    private static final Method mc_resetTimer;
    private static final Method mc_compareNBT;
    private static final Method mc_parseNBT;

    private static final Field mc_spawnCount;
    private static final Field mc_spawnRange;
    private static final Field mc_spawnDelay;
    private static final Field mc_minSpawnDelay;
    private static final Field mc_maxSpawnDelay;
    private static final Field mc_maxNearbyEntities;
    private static final Field mc_requiredPlayerRange;

    static {
        Class<?> mc_NBTTagCompound = Reflections.getNormalNMSClass("NBTTagCompound");

        mc_MobSpawnerAbstract = Reflections.getNormalNMSClass("MobSpawnerAbstract");

        mc_isActivated = Reflections.getMethod(mc_MobSpawnerAbstract, "isActivated", true);
        mc_setMobName = Reflections.getMethod(mc_MobSpawnerAbstract, "setMobName", true, Reflections.getNormalNMSClass("EntityTypes"));
        mc_setSpawnData = Reflections.getMethod(mc_MobSpawnerAbstract, "setSpawnData", true, Reflections.getNormalNMSClass("MobSpawnerData"));
        mc_getMobName = Reflections.getMethod(mc_MobSpawnerAbstract, "getMobName", true);
        mc_getWorld = Reflections.getMethod(mc_MobSpawnerAbstract, "a", true);
        mc_getPosition = Reflections.getMethod(mc_MobSpawnerAbstract, "b", true);
        mc_resetTimer = Reflections.getMethod(mc_MobSpawnerAbstract, "resetTimer", true);
        mc_parseNBT = Reflections.getMethod(mc_MobSpawnerAbstract, "a", true, mc_NBTTagCompound);
        mc_compareNBT = Reflections.getMethod(mc_MobSpawnerAbstract, "b", true, mc_NBTTagCompound);

        mc_spawnCount = Reflections.getField(mc_MobSpawnerAbstract, "spawnCount", true);
        mc_spawnRange = Reflections.getField(mc_MobSpawnerAbstract, "spawnRange", true);
        mc_spawnDelay = Reflections.getField(mc_MobSpawnerAbstract, "spawnDelay", true);
        mc_minSpawnDelay = Reflections.getField(mc_MobSpawnerAbstract, "minSpawnDelay", true);
        mc_maxSpawnDelay = Reflections.getField(mc_MobSpawnerAbstract, "maxSpawnDelay", true);
        mc_maxNearbyEntities = Reflections.getField(mc_MobSpawnerAbstract, "maxNearbyEntities", true);
        mc_requiredPlayerRange = Reflections.getField(mc_MobSpawnerAbstract, "requiredPlayerRange", true);
    }

    @Getter
    private final Object root;

    public WrappedMobSpawnerAbstract(Object root) {
        this.root = root;
    }

    /**
     * @return whether or not this spawner is activated.
     */
    public Boolean isActivated() {
        return Reflections.invoke(mc_isActivated, Boolean.class, root);
    }

    /**
     * @param object is a minecraftkey.
     */
    public void setMobName(Object object) {
        Reflections.invoke(mc_setMobName, Void.class, root, object);
    }

    /**
     * Same as {@link #setMobName(Object)}.
     *
     * @param namespacedKey the namespace of the mob name.
     */
    public void setMobName(NamespacedKey namespacedKey) {
        Reflections.invoke(mc_setMobName, Void.class, root, WrappedCraftNamespacedKey.toMinecraft(namespacedKey));
    }

    /**
     * NOT CURRENTLY IMPLEMENTED.
     *
     * @param object the mobspawnerdata we wish to change it to.
     */
    public void setSpawnData(Object object) {
        Reflections.invoke(mc_setSpawnData, Void.class, root, object);
    }

    /**
     * @return a minecraftkey of the mobs name.
     */
    public Object getMobName() {
        return Reflections.invoke(mc_getMobName, Object.class, root);
    }

    /**
     * Same as {@link #getMobName()}.
     *
     * @return the result of {@link #getMobName()} but as a {@link NamespacedKey}.
     */
    public NamespacedKey getMobNameBukkit() {
        return WrappedCraftNamespacedKey.fromMinecraft(getMobName());
    }

    /**
     * @return the world this mob spawner is located at.
     */
    public WrappedWorld getWorld() {
        return new WrappedWorld(Reflections.invoke(mc_getWorld, Object.class, root));
    }

    /**
     * @return the position this mob spawner occupies.
     */
    public WrappedBlockPosition getPosition() {
        return new WrappedBlockPosition(Reflections.invoke(mc_getPosition, Object.class, root));
    }

    /**
     * Resets the spawn timer for mobs.
     */
    public void resetTimer() {
        Reflections.invoke(mc_resetTimer, Void.class, root);
    }

    /**
     * Parses the provided NBTTagCompound, and loads it into this MSA.
     *
     * @param nbt the NBTTagCompound we wish to parse.
     */
    public void parseNBT(Object nbt) {
        Reflections.invoke(mc_parseNBT, Void.class, root, nbt);
    }

    /**
     * Compares the current nbttagcompound to the given one.
     *
     * @param nbt an nbttagcompound.
     * @return the differences?
     */
    public Object compareNBT(Object nbt) {
        return Reflections.invoke(mc_compareNBT, Object.class, root, nbt);
    }

    // Fields.

    /**
     * @return the number of entities spawned per spawn interval.
     */
    public Integer getSpawnCount() {
        return Reflections.get(mc_spawnCount, Integer.class, root);
    }

    /**
     * Changes the spawn count to the specified value.
     *
     * @param value the value we wish to change it to.
     */
    public void setSpawnCount(int value) {
        Reflections.set(mc_spawnCount, root, value);
    }

    /**
     * @return the range that entities spawn in.
     */
    public Integer getSpawnRange() {
        return Reflections.get(mc_spawnRange, Integer.class, root);
    }

    /**
     * Changes the spawn range to the specified value.
     *
     * @param value the value we wish to change it to.
     */
    public void setSpawnRange(int value) {
        Reflections.set(mc_spawnRange, root, value);
    }

    /**
     * @return the delay between spawns.
     */
    public Integer getSpawnDelay() {
        return Reflections.get(mc_spawnDelay, Integer.class, root);
    }

    /**
     * Changes the spawn delay to the specified value.
     *
     * @param value the value we wish to change it to.
     */
    public void setSpawnDelay(int value) {
        Reflections.set(mc_spawnDelay, root, value);
    }

    /**
     * @return the minimum delay between spawns.
     */
    public Integer getMinSpawnDelay() {
        return Reflections.get(mc_minSpawnDelay, Integer.class, root);
    }

    /**
     * Changes the minimum amount of delay between spawns to the specified value.
     *
     * @param value the value we wish to change it to.
     */
    public void setMinSpawnDelay(int value) {
        Reflections.set(mc_minSpawnDelay, root, value);
    }

    /**
     * @return the maximum delay between spawns.
     */
    public Integer getMaxSpawnDelay() {
        return Reflections.get(mc_maxSpawnDelay, Integer.class, root);
    }

    /**
     * Changes the maximum amount of delay between spawns to the specified value.
     *
     * @param value the value we wish to change it to.
     */
    public void setMaxSpawnDelay(int value) {
        Reflections.set(mc_maxSpawnDelay, root, value);
    }

    /**
     * @return the maximum amount of nearby entities.
     */
    public Integer getMaxNearbyEntities() {
        return Reflections.get(mc_maxNearbyEntities, Integer.class, root);
    }

    /**
     * Changes the maximum amount of entities permitted nearby to the specified value.
     *
     * @param value the value we wish to change it to.
     */
    public void setMaxNearbyEntities(int value) {
        Reflections.set(mc_maxNearbyEntities, root, value);
    }

    /**
     * @return the range that a player needs to be in for entities to spawn.
     */
    public Integer getRequiredPlayerRange() {
        return Reflections.get(mc_requiredPlayerRange, Integer.class, root);
    }

    /**
     * Changes the required range for players to be in to the specified value.
     *
     * @param value the value we wish to change it to.
     */
    public void setRequiredPlayerRange(int value) {
        Reflections.set(mc_requiredPlayerRange, root, value);
    }

}
