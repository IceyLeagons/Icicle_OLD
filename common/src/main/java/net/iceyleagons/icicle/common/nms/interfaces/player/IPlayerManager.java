package net.iceyleagons.icicle.common.nms.interfaces.player;

public interface IPlayerManager {

    INetworkManager getNetworkManager();

    // TODO: #sendPacket(IPacket)

    long getLastPing();

    void setLastPing(long ping);

    void setPendingPing(boolean isPending);

    boolean isPendingPing();

}
