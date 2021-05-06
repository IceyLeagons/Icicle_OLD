package net.iceyleagons.icicle.common.nms.interfaces;

import net.iceyleagons.icicle.common.wrapped.WrappedDedicatedServer;

import java.util.Optional;

public interface IDedicatedServer extends IWrappable {

    //TODO replace Objects with appropriate interfaces once they're created and implemented

    //TODO move TPSTime out from WrappedDe...
    double getTPS(WrappedDedicatedServer.TPSTime tpsTime);

    boolean isNotMainThread();
    Thread getThread();
    Object getUserCache();
    Object getServerPing();

    int getIdleTimeout();
    void setIdleTimeout(int value);

    Object getResourcePackRepository();

    Object getTagRegistry();

    Object getLootTableRegistry();

    Object getServerConnection();

    Object getCustomRegistry();

    boolean isSyncChunkWrites();

    Object getDefinedStructureManager();

    String getResourcePack();

    String getResourcePackHash();

    void setResourcePack(String link, String hash);

    int getPort();
    void setPort(int port);

    Optional<?> getModded();
    String getServerModName();

    String getVersion();

    Object getWorldServer(Object resourceKey);

    Object postToMainThread(Runnable runnable);

    boolean canExecute(Object tickTask);

    boolean executeNext();

    boolean safeShutdown(boolean flag);
    boolean safeShutdown(boolean flag, boolean isRestarting);

    Object getMinecraftSessionService();
    Object getGameProfileRepository();

    Object getDatapackConfiguration();
    Object getDataPackResources();

    Object getPlayerList();

    Object getFunctionData();



}
