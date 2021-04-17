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

package net.iceyleagons.icicle.wrapping;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.utils.Reflections;
import net.iceyleagons.icicle.wrapping.registry.WrappedIRegistryCustom;
import net.iceyleagons.icicle.wrapping.registry.WrappedResourceKey;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Contains useful functions from Minecraft's DedicatedServer class
 *
 * @author TOTHTOMI
 * @version 1.1.0
 * @since 1.4.0-SNAPSHOT
 */
@RequiredArgsConstructor
public class WrappedDedicatedServer {

    public static final Class<?> craftServerClass;
    public static final Class<?> mcServerClass;
    private static final Method cb_getServer;
    private static final Method mc_getFunctionData;
    private static final Method mc_isNotMainThread;
    private static final Method mc_getThread;
    private static final Method mc_getUserCache;
    private static final Method mc_getServerPing;
    private static final Method mc_getIdleTimeout;
    private static final Method mc_setIdleTimeout;
    private static final Method mc_getResourcePackRepository;
    private static final Method mc_getTagRegistry;
    private static final Method mc_getLootTableRegistry;
    private static final Method mc_getCustomRegistry;
    private static final Method mc_isSyncChunkWrites;
    private static final Method mc_getDefinedStructureManager;
    private static final Method mc_getResourcePack;
    private static final Method mc_getResourcePackHash;
    private static final Method mc_setResourcePack;
    private static final Method mc_getPort;
    private static final Method mc_setPort;
    private static final Method mc_getModded;
    private static final Method mc_getServerModName;
    private static final Method mc_getVersion;
    private static final Method mc_getWorldServer;
    private static final Method mc_postToMainThread;
    private static final Method mc_canExecute;
    private static final Method mc_executeNext;
    private static final Method mc_safeShutdown;
    private static final Method mc_getServerConnection;
    private static final Method mc_getPlayerList;

    private static final Field mc_tpsField;
    private static final Field mc_minecraftSessionService;
    private static final Field mc_gameProfileRepository;
    private static final Field mc_datapackconfiguration;
    private static final Field mc_dataPackResources;


    static {
        //Classes
        craftServerClass = Reflections.getNormalCBClass("CraftServer");
        mcServerClass = Reflections.getNormalNMSClass("MinecraftServer");

        //CraftBukkit methods
        cb_getServer = Reflections.getMethod(craftServerClass, "getServer", true);

        //CraftBukkit fields

        //MC methods
        mc_getFunctionData = getMCMethod("getFunctionData");
        mc_isNotMainThread = getMCMethod("isNotMainThread");
        mc_getThread = getMCMethod("getThread");
        mc_getUserCache = getMCMethod("getUserCache");
        mc_getServerPing = getMCMethod("getServerPing");
        mc_getIdleTimeout = getMCMethod("getIdleTimeout");
        mc_setIdleTimeout = getMCMethod("setIdleTimeout", int.class);
        mc_getResourcePackRepository = getMCMethod("getResourcePackRepository");
        mc_getTagRegistry = getMCMethod("getTagRegistry");
        mc_getLootTableRegistry = getMCMethod("getLootTableRegistry");
        mc_getCustomRegistry = getMCMethod("getCustomRegistry");
        mc_isSyncChunkWrites = getMCMethod("isSyncChunkWrites");
        mc_getDefinedStructureManager = getMCMethod("getDefinedStructureManager");
        mc_getResourcePack = getMCMethod("getResourcePack");
        mc_getResourcePackHash = getMCMethod("getResourcePackHash");
        mc_setResourcePack = getMCMethod("setResourcePack", String.class, String.class);
        mc_getPort = getMCMethod("getPort");
        mc_setPort = getMCMethod("setPort", int.class);
        mc_getModded = getMCMethod("getModded");
        mc_getServerModName = getMCMethod("getServerModName");
        mc_getVersion = getMCMethod("getVersion");
        mc_getWorldServer = getMCMethod("getWorldServer", WrappedResourceKey.mc_ResourceKey);
        mc_postToMainThread = getMCMethod("postToMainThread", Runnable.class);
        mc_canExecute = getMCMethod("canExecute", Reflections.getNormalNMSClass("TickTask"));
        mc_getServerConnection = getMCMethod("getServerConnection");
        mc_executeNext = getMCMethod("executeNext");
        mc_safeShutdown = getMCMethod("safeShutdown", boolean.class, boolean.class);
        mc_getPlayerList = getMCMethod("getPlayerList");

        //MC fields
        mc_tpsField = getMCField("recentTps");

        mc_minecraftSessionService = getMCField("minecraftSessionService");
        mc_gameProfileRepository = getMCField("gameProfileRepository");
        mc_datapackconfiguration = getMCField("datapackconfiguration");
        mc_dataPackResources = getMCField("dataPackResources");

    }

    @Getter
    private final Object dedicatedServer;

    private static Method getMCMethod(String name, Class<?>... parameterTypes) {
        return Reflections.getMethod(mcServerClass, name, true, parameterTypes);
    }

    private static Field getMCField(String name) {
        return Reflections.getField(mcServerClass, name, true);
    }

    private static Object getMCServer(Server server) {
        return Reflections.invoke(cb_getServer, Object.class, server);
    }

    public static WrappedDedicatedServer from(Server server) {
        return new WrappedDedicatedServer(getMCServer(server));
    }

    /**
     * @param tpsTime the {@link TPSTime} to get
     * @return the TPS in that time or -1 if an error happens
     */
    public double getTPS(TPSTime tpsTime) {
        double[] tps = Reflections.get(mc_tpsField, double[].class, dedicatedServer);
        if (tps == null) return -1;
        return tps[tpsTime.ordinal()];
    }

    /**
     * @return whether the executing thread is the main thread or not.
     */
    public Boolean isNotMainThread() {
        return Reflections.invoke(mc_isNotMainThread, Boolean.class, dedicatedServer);
    }

    /**
     * @return the main thread of the server.
     */
    public Thread getThread() {
        return Reflections.invoke(mc_getThread, Thread.class, dedicatedServer);
    }

    /**
     * @return the user cache of this server. CURRENTLY NOT WRAPPED.
     */
    public Object getUserCache() {
        return Reflections.invoke(mc_getUserCache, Object.class, dedicatedServer);
    }

    /**
     * @return the ping of the server? Weird stuff.
     */
    public Object getServerPing() {
        return Reflections.invoke(mc_getServerPing, Object.class, dedicatedServer);
    }

    /**
     * @return the time a player needs to be idle for them to be kicked.
     */
    public Integer getIdleTimeout() {
        return Reflections.invoke(mc_getIdleTimeout, Integer.class, dedicatedServer);
    }

    /**
     * Changes the idle timeout to the specified value.
     *
     * @param i the value we wish to change it to.
     */
    public void setIdleTimeout(int i) {
        Reflections.invoke(mc_setIdleTimeout, Void.class, dedicatedServer, i);
    }

    /**
     * @return the resource pack repository. CURRENTLY NOT WRAPPED.
     */
    public Object getResourcePackRepository() {
        return Reflections.invoke(mc_getResourcePackRepository, Object.class, dedicatedServer);
    }

    /**
     * @return the tag registry. CURRENTLY NOT WRAPPED.
     */
    public Object getTagRegistry() {
        return Reflections.invoke(mc_getTagRegistry, Object.class, dedicatedServer);
    }

    /**
     * @return the loot table registry. CURRENTLY NOT WRAPPED.
     */
    public Object getLootTableRegistry() {
        return Reflections.invoke(mc_getLootTableRegistry, Object.class, dedicatedServer);
    }

    /**
     * @return the server connection. This is used for packets. CURRENTLY NOT WRAPPED.
     */
    public Object getServerConnection() {
        return Reflections.invoke(mc_getServerConnection, Object.class, dedicatedServer);
    }

    /**
     * @return the custom registry for this server. Used for dimensions and stuff.
     */
    public WrappedIRegistryCustom.Dimension getCustomRegistry() {
        return new WrappedIRegistryCustom(Reflections.invoke(mc_getCustomRegistry, Object.class, dedicatedServer)).asDimension();
    }

    /**
     * @return whether the server is writing chunks synchronously or not.
     */
    public Boolean isSyncChunkWrites() {
        return Reflections.invoke(mc_isSyncChunkWrites, Boolean.class, dedicatedServer);
    }

    /**
     * @return the structure manager. CURRENTLY NOT WRAPPED.
     */
    public Object getDefinedStructureManager() {
        return Reflections.invoke(mc_getDefinedStructureManager, Object.class, dedicatedServer);
    }

    /**
     * @return the resource pack url. Derived from one of {@link #getResourcePackRepository()}'s method.
     */
    public String getResourcePack() {
        return Reflections.invoke(mc_getResourcePack, String.class, dedicatedServer);
    }

    /**
     * @return the resource pack hash. Derived from one of {@link #getResourcePackRepository()}'s method.
     */
    public String getResourcePackHash() {
        return Reflections.invoke(mc_getResourcePackHash, String.class, dedicatedServer);
    }

    /**
     * Changes the server's resource pack to the specified resource pack.
     *
     * @param link direct download link to the resource pack.
     * @param hash expected hash of the resource pack.
     */
    public void setResourcePack(String link, String hash) {
        Reflections.invoke(mc_setResourcePack, Void.class, dedicatedServer, link, hash);
    }

    /**
     * @return the port of the server.
     */
    public Integer getPort() {
        return Reflections.invoke(mc_getPort, Integer.class, dedicatedServer);
    }

    /**
     * Changes the port of this server. I don't think this updates the socket tho.
     *
     * @param port the new port.
     */
    public void setPort(int port) {
        Reflections.invoke(mc_setPort, Void.class, dedicatedServer, port);
    }

    /**
     * @return Will always return an optional.
     */
    public Optional<?> getModded() {
        return Reflections.invoke(mc_getModded, Optional.class, dedicatedServer);
    }

    /**
     * @return the name of the server software. If you're using paper, then "Paper", if spigot, then "Spigot."
     */
    public String getServerModName() {
        return Reflections.invoke(mc_getServerModName, String.class, dedicatedServer);
    }

    /**
     * @return the version of this server.
     */
    public String getVersion() {
        return Reflections.invoke(mc_getVersion, String.class, dedicatedServer);
    }

    /**
     * Retrieves the world server for the specified resource key.
     *
     * @param resourceKey the key of the world.
     * @return a world server.
     */
    public Object getWorldServer(Object resourceKey) {
        return Reflections.invoke(mc_getWorldServer, Object.class, dedicatedServer);
    }

    /**
     * Executes the given runnable on the main thread.
     *
     * @param runnable the runnable we want to run.
     * @return A TickTask as far as I can remember.
     */
    public Object postToMainThread(Runnable runnable) {
        return Reflections.invoke(mc_postToMainThread, Object.class, dedicatedServer, runnable);
    }

    /**
     * @param tickTask an object representing a ticktask.
     * @return whether or not we can execute that task.
     */
    public Boolean canExecute(Object tickTask) {
        return Reflections.invoke(mc_canExecute, Boolean.class, dedicatedServer, tickTask);
    }

    /**
     * Execute the next tick.
     *
     * @return whether or not it was successful.
     */
    public Boolean executeNext() {
        return Reflections.invoke(mc_executeNext, Boolean.class, dedicatedServer);
    }

    /**
     * Same as {@link #safeShutdown(boolean, boolean)}.
     * <p>
     * Shuts down the server safely.
     *
     * @param flag no idea what this flag is.
     * @return whether or not the server shutdown.
     */
    public Boolean safeShutdown(boolean flag) {
        return safeShutdown(flag, false);
    }

    /**
     * Shuts down the server safely.
     *
     * @param flag         no idea what this flag is.
     * @param isRestarting whether or not the server is restarting
     * @return whether or not the server shutdown.
     */
    public Boolean safeShutdown(boolean flag, boolean isRestarting) {
        return Reflections.invoke(mc_safeShutdown, Boolean.class, dedicatedServer, flag, isRestarting);
    }

    /**
     * @return the minecraft session service. CURRENTLY NOT WRAPPED.
     */
    public Object getMinecraftSessionService() {
        return Reflections.get(mc_minecraftSessionService, Object.class, dedicatedServer);
    }

    /**
     * @return the game profile repository. CURRENTLY NOT WRAPPED.
     */
    public Object getGameProfileRepository() {
        return Reflections.get(mc_gameProfileRepository, Object.class, dedicatedServer);
    }

    /**
     * @return the datapack configuration. CURRENTLY NOT WRAPPED.
     */
    public Object getDatapackConfiguration() {
        return Reflections.get(mc_datapackconfiguration, Object.class, dedicatedServer);
    }

    /**
     * @return the datapack resources. CURRENTLY NOT WRAPPED.
     */
    public Object getDataPackResources() {
        return Reflections.get(mc_dataPackResources, Object.class, dedicatedServer);
    }

    /**
     * @return the object representing the player list.
     */
    public WrappedPlayerList getPlayerList() {
        return new WrappedPlayerList(Reflections.invoke(mc_getPlayerList, Object.class, dedicatedServer));
    }


    /**
     * @return the functionData in an Object form (may be wrapped in the future)
     */
    public Object getFunctionData() {
        return Reflections.invoke(mc_getFunctionData, Object.class, dedicatedServer);
    }

    //TODO CraftBlock, CraftPlayer, CraftEntity, CraftWorld

    /**
     * @author TOTHTOMI
     * @version 1.0.0
     * @since 1.1.3
     */
    @Getter
    public enum TPSTime {
        /**
         * Used to check the TPS for the last minute
         */
        LAST_MINUTE,
        /**
         * Used to check the TPS for the last five minutes
         */
        FIVE_MINUTES,
        /**
         * Used to check the TPS for the last fifteen minutes
         */
        FIFTEEN_MINUTES
    }

}

