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

package net.iceyleagons.icicle.wrapped.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.WrappedDedicatedServer;
import net.iceyleagons.icicle.wrapped.bukkit.WrappedCraftWorld;
import net.iceyleagons.icicle.wrapped.data.WrappedDataWatcher;
import net.iceyleagons.icicle.wrapped.mojang.WrappedGameProfile;
import net.iceyleagons.icicle.wrapped.world.WrappedWorld;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author TOTHTOMI
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
                WrappedCraftWorld.mc_WorldServer, WrappedGameProfile.mojang_gameProfile, WrappedPlayerInteractManager.mc_playerInteractManager);

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

    public void teleportAndSync(double x, double y, double z) {
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
        System.out.println(mc_teleportAndSync);
        System.out.println(entityPlayer);
        Reflections.invoke(mc_teleportAndSync, Void.class, entityPlayer, x, y, z);
    }

    public Float getYaw() {
        return Reflections.get(mc_yaw, Float.class, entityPlayer);
    }

    public Float getPitch() {
        return Reflections.get(mc_pitch, Float.class, entityPlayer);
    }

    public WrappedDataWatcher getDataWatcher() {
        Object o = Reflections.invoke(mc_getDataWatcher, Object.class, entityPlayer);
        return new WrappedDataWatcher(o);
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        Reflections.invoke(mc_setLocation, Void.class, entityPlayer, x, y, z, yaw, pitch);
    }

    public Object getBukkitEntity() {
        return Reflections.invoke(mc_getBukkitEntity, Object.class, entityPlayer);
    }

    public WrappedPlayerConnection getPlayerConnection() {
        return new WrappedPlayerConnection(Reflections.get(mc_playerConnection, Object.class, entityPlayer));
    }

    public WrappedGameProfile getGameProfile() {
        return new WrappedGameProfile(Reflections.get(mc_gameProfile, Object.class, entityPlayer));
    }

    public Integer getId() {
        return Reflections.get(mc_id, Integer.class, entityPlayer);
    }

    public WrappedNetworkManager getNetworkManager() {
        return new WrappedNetworkManager(Reflections.get(mc_networkManager, Object.class, entityPlayer));
    }

    public Object getNMSWorld() {
        return Reflections.get(mc_world, Object.class, entityPlayer);
    }

    public Integer getPing() {
        return Reflections.get(mc_ping, Integer.class, entityPlayer);
    }

    public Boolean isFrozen() {
        return Reflections.invoke(mc_isFrozen, Boolean.class, entityPlayer);
    }

}
