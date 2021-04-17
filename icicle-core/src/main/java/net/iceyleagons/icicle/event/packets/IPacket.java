package net.iceyleagons.icicle.event.packets;

import org.bukkit.entity.Player;

public interface IPacket {

    <T> T getFromField(String name, Class<T> wantedType);
    void setField(String name, Object value);
    void sendAll();
    void send(Player player);

}
