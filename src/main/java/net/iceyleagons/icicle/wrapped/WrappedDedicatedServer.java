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

package net.iceyleagons.icicle.wrapped;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflections.Reflections;
import org.bukkit.Server;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Optional;

/**
 * Contains useful functions from Minecraft's DedicatedServer's class
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.4.0-SNAPSHOT
 */
@RequiredArgsConstructor
public class WrappedDedicatedServer {

    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("##.##");

    private static final Class<?> craftServerClass;
    private static final Class<?> dedicatedServerClass;
    private static final Class<?> mcServerClass;

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

    private static final Field mc_tpsField;
    private static final Field mc_minecraftSessionService;
    private static final Field mc_gameProfileRepository;
    private static final Field mc_datapackconfiguration;
    private static final Field mc_dataPackResources;


    static {
        //Classes
        craftServerClass = Reflections.getNormalCBClass("CraftServer");
        dedicatedServerClass = Reflections.getNormalNMSClass("DedicatedServer");
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
        mc_getWorldServer = getMCMethod("getWorldServer", Object.class);
        mc_postToMainThread = getMCMethod("postToMainThread", Runnable.class);
        mc_canExecute = getMCMethod("canExecute", Object.class);
        mc_executeNext = getMCMethod("executeNext");
        mc_safeShutdown = getMCMethod("safeShutdown", boolean.class, boolean.class);

        //MC fields
        mc_tpsField = getMCField("recentTps");

        mc_minecraftSessionService = getMCField("minecraftSessionService");
        mc_gameProfileRepository = getMCField("gameProfileRepository");
        mc_datapackconfiguration = getMCField("datapackconfiguration");
        mc_dataPackResources = getMCField("dataPackResources");

    }

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

    @Getter
    private final Object dedicatedServer;

    /**
     * @param tpsTime the {@link TPSTime} to get
     * @return the TPS in that time or -1 if an error happens
     */
    public double getTPS(TPSTime tpsTime) {
        double[] tps = Reflections.get(mc_tpsField, double[].class, dedicatedServer);
        if (tps == null) return -1;
        return tps[tpsTime.getId()];
    }

    public Boolean isNotMainThread() {
        return Reflections.invoke(mc_isNotMainThread, Boolean.class, dedicatedServer);
    }

    public Thread getThread() {
        return Reflections.invoke(mc_getThread, Thread.class, dedicatedServer);
    }

    public Object getUserCache() {
        return Reflections.invoke(mc_getUserCache, Object.class, dedicatedServer);
    }

    public Object getServerPing() {
        return Reflections.invoke(mc_getServerPing, Object.class, dedicatedServer);
    }

    public Integer getIdleTimeout() {
        return Reflections.invoke(mc_getIdleTimeout, Integer.class, dedicatedServer);
    }

    public void setIdleTimeout(int i) {
        Reflections.invoke(mc_setIdleTimeout, Void.class, dedicatedServer, i);
    }

    public Object getResourcePackRepository() {
        return Reflections.invoke(mc_getResourcePackRepository, Object.class, dedicatedServer);
    }

    public Object getTagRegistry() {
        return Reflections.invoke(mc_getTagRegistry, Object.class, dedicatedServer);
    }

    public Object getLootTableRegistry() {
        return Reflections.invoke(mc_getLootTableRegistry, Object.class, dedicatedServer);
    }

    public Object getCustomRegistry() {
        return Reflections.invoke(mc_getCustomRegistry, Object.class, dedicatedServer);
    }

    public Boolean isSyncChunkWrites() {
        return Reflections.invoke(mc_isSyncChunkWrites, Boolean.class, dedicatedServer);
    }

    public Object getDefinedStructureManager() {
        return Reflections.invoke(mc_getDefinedStructureManager, Object.class, dedicatedServer);
    }

    public String getResourcePack() {
        return Reflections.invoke(mc_getResourcePack, String.class, dedicatedServer);
    }

    public String getResourcePackHash() {
        return Reflections.invoke(mc_getResourcePackHash, String.class, dedicatedServer);
    }

    public void setResourcePack(String link, String hash) {
        Reflections.invoke(mc_setResourcePack, Void.class, dedicatedServer, link, hash);
    }

    public Integer getPort() {
        return Reflections.invoke(mc_getPort, Integer.class, dedicatedServer);
    }

    public void setPort(int port) {
        Reflections.invoke(mc_setPort, Void.class, dedicatedServer, port);
    }

    public Optional<?> getModded() {
        return Reflections.invoke(mc_getModded, Optional.class, dedicatedServer);
    }

    public String getServerModName() {
        return Reflections.invoke(mc_getServerModName, String.class, dedicatedServer);
    }

    public String getVersion() {
        return Reflections.invoke(mc_getVersion, String.class, dedicatedServer);
    }

    public Object getWorldServer(Object resourceKey) {
        return Reflections.invoke(mc_getWorldServer, Object.class, dedicatedServer);
    }

    public Object postToMainThread(Runnable runnable) {
        return Reflections.invoke(mc_postToMainThread, Object.class, dedicatedServer, runnable);
    }

    public Boolean canExecute(Object tickTask) {
        return Reflections.invoke(mc_canExecute, Boolean.class, dedicatedServer, tickTask);
    }

    public Boolean executeNext() {
        return Reflections.invoke(mc_executeNext, Boolean.class, dedicatedServer);
    }

    public Boolean safeShutdown(boolean flag) {
        return safeShutdown(flag, false);
    }

    public Boolean safeShutdown(boolean flag, boolean isRestarting) {
        return Reflections.invoke(mc_safeShutdown, Boolean.class, dedicatedServer, flag, isRestarting);
    }

    public Object getMinecraftSessionService() {
        return Reflections.get(mc_minecraftSessionService, Object.class, dedicatedServer);
    }

    public Object getGameProfileRepository() {
        return Reflections.get(mc_gameProfileRepository, Object.class, dedicatedServer);
    }

    public Object getDatapackConfiguration() {
        return Reflections.get(mc_datapackconfiguration, Object.class, dedicatedServer);
    }

    public Object getDataPackResources() {
        return Reflections.get(mc_dataPackResources, Object.class, dedicatedServer);
    }



    /**
     * @return the functionData in an Object form (may be wrapped in the future)
     */
    public Object getFunctionData() {
        return Reflections.invoke(mc_getFunctionData, Object.class, dedicatedServer);
    }

    //TODO CraftBlock, CraftPlayer, CraftEntity, CraftWorld



}

