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

package net.iceyleagons.icicle.wrapping.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapping.WrappedDedicatedServer;
import net.iceyleagons.icicle.wrapping.data.WrappedDataWatcher;
import net.iceyleagons.icicle.wrapping.mojang.WrappedGameProfile;
import net.iceyleagons.icicle.wrapping.utils.WrappedClass;
import net.iceyleagons.icicle.wrapping.world.WrappedWorld;
import org.bukkit.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Wrapped representation EntityPlayer
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
@RequiredArgsConstructor
@Getter
public class WrappedEntityPlayer {

    public static final Class<?> entityPlayerClass;
    private static final Class<?> mc_Entity;

    private static final Field mc_playerConnection;
    private static final Field mc_networkManager;
    private static final Field mc_world;
    private static final Method mc_isFrozen;
    private static final Field mc_ping;
    private static final Field mc_gameProfile;
    private static final Field mc_id;
    private static final Field mc_yaw;
    private static final Field mc_pitch;
    private static final Method mc_setLocation;
    private static final Method mc_getBukkitEntity;
    private static final Method mc_teleportAndSync;
    private static final Method mc_getDataWatcher;

    private static final Constructor<?> constructor;

    static {
        entityPlayerClass = Reflections.getNormalNMSClass("EntityPlayer");
        mc_Entity = Reflections.getNormalNMSClass("Entity");
        Class<?> entityHuman = Reflections.getNormalNMSClass("EntityHuman");

        mc_playerConnection = getMCField("playerConnection");
        mc_getBukkitEntity = Reflections.getMethod(mc_Entity, "getBukkitEntity", true);
        mc_networkManager = getMCField("networkManager");
        mc_getDataWatcher = Reflections.getMethod(mc_Entity, "getDataWatcher", true);
        mc_world = Reflections.getField(mc_Entity, "world", true);
        mc_isFrozen = getMCMethod("isFrozen");
        mc_ping = getMCField("ping");
        mc_id = Reflections.getField(mc_Entity, "id", true);
        mc_yaw = Reflections.getField(mc_Entity, "yaw", true);
        mc_pitch = Reflections.getField(mc_Entity, "pitch", true);
        mc_gameProfile = Reflections.getField(entityHuman, "bJ", true);
        mc_teleportAndSync = Reflections.getMethod(mc_Entity, "teleportAndSync", true, double.class, double.class, double.class);
        mc_setLocation = Reflections.getMethod(mc_Entity, "setLocation", true, double.class, double.class, double.class, float.class, float.class);

        constructor = Reflections.getConstructor(entityPlayerClass, true, WrappedDedicatedServer.mcServerClass,
                WrappedClass.getNMSClass("World").getClazz(), WrappedGameProfile.mojang_gameProfile, WrappedPlayerInteractManager.mc_playerInteractManager);

        //public EntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager)
    }

    private final Object entityPlayer;

    @SneakyThrows
    public WrappedEntityPlayer(WrappedDedicatedServer dedicatedServer, WrappedWorld world, WrappedGameProfile gameProfile, WrappedPlayerInteractManager interactManager) {
        this.entityPlayer = constructor.newInstance(dedicatedServer.getDedicatedServer(), world.getWorld(), gameProfile.getNmsObject(), interactManager.getNmsObject());
    }

    private static Method getMCMethod(String name, Class<?>... parameterTypes) {
        return Reflections.getMethod(entityPlayerClass, name, true, parameterTypes);
    }

    private static Field getMCField(String name) {
        return Reflections.getField(entityPlayerClass, name, true);
    }

    public static WrappedEntityPlayer from(Object handle) {
        return new WrappedEntityPlayer(handle);
    }

    /**
     * Teleports and synchronizes the players client to the specified position.
     *
     * @param x self-explanatory.
     * @param y self-explanatory.
     * @param z self-explanatory.
     */
    public void teleportAndSync(double x, double y, double z) {
        Reflections.invoke(mc_teleportAndSync, Void.class, entityPlayer, x, y, z);
    }

    /**
     * Teleports and synchronizes the players client to the specified position.
     *
     * @param location self-explanatory.
     */
    public void teleportAndSync(Location location) {
        teleportAndSync(location.getX(), location.getY(), location.getZ());
    }

    /**
     * @return the yaw of this player.
     */
    public Float getYaw() {
        return Reflections.get(mc_yaw, Float.class, entityPlayer);
    }

    /**
     * @return the pitch of this player.
     */
    public Float getPitch() {
        return Reflections.get(mc_pitch, Float.class, entityPlayer);
    }

    /**
     * @return the data watcher assigned to this entity.
     */
    public WrappedDataWatcher getDataWatcher() {
        Object o = Reflections.invoke(mc_getDataWatcher, Object.class, entityPlayer);
        return new WrappedDataWatcher(o);
    }

    /**
     * Changes the location of this player to the provided position.
     *
     * @param x     self-explanatory.
     * @param y     self-explanatory.
     * @param z     self-explanatory.
     * @param yaw   self-explanatory.
     * @param pitch self-explanatory.
     */
    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        Reflections.invoke(mc_setLocation, Void.class, entityPlayer, x, y, z, yaw, pitch);
    }

    /**
     * Changes the location of this player to the provided position.
     *
     * @param location self-explanatory.
     */
    public void setLocation(Location location) {
        Reflections.invoke(mc_setLocation, Void.class, entityPlayer, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    /**
     * @return returns this nms class as a bukkit entity. Probably should be a Player.
     */
    public Object getBukkitEntity() {
        return Reflections.invoke(mc_getBukkitEntity, Object.class, entityPlayer);
    }

    /**
     * @return the player connection of this player. Used for sending packets and such.
     */
    public WrappedPlayerConnection getPlayerConnection() {
        return new WrappedPlayerConnection(Reflections.get(mc_playerConnection, Object.class, entityPlayer));
    }

    /**
     * @return the game profile of this player.
     */
    public WrappedGameProfile getGameProfile() {
        return new WrappedGameProfile(Reflections.get(mc_gameProfile, Object.class, entityPlayer));
    }

    /**
     * @return the id of this entity.
     */
    public Integer getId() {
        return Reflections.get(mc_id, Integer.class, entityPlayer);
    }

    /**
     * @return the network manager that's managing this player.
     */
    public WrappedNetworkManager getNetworkManager() {
        return new WrappedNetworkManager(Reflections.get(mc_networkManager, Object.class, entityPlayer));
    }

    /**
     * @return the world this player resides in right now.
     */
    public WrappedWorld getNMSWorld() {
        return new WrappedWorld(Reflections.get(mc_world, Object.class, entityPlayer));
    }

    /**
     * @return the ping of this player.
     */
    public Integer getPing() {
        return Reflections.get(mc_ping, Integer.class, entityPlayer);
    }

    /**
     * @return whether or not the player is frozen. Probably packet-freeze.
     */
    public Boolean isFrozen() {
        return Reflections.invoke(mc_isFrozen, Boolean.class, entityPlayer);
    }

}
