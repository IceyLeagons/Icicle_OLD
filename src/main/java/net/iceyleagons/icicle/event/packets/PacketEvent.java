package net.iceyleagons.icicle.event.packets;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.iceyleagons.icicle.event.packets.enums.PacketDirection;
import net.iceyleagons.icicle.event.packets.enums.PacketType;
import org.bukkit.entity.Player;

/**
 * A generic event for all packet related events.
 *
 * @author Gábe
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
@RequiredArgsConstructor
@Getter
public class PacketEvent {

    /**
     * The player that we caught this packet on.
     */
    @NonNull
    private final Player player;
    /**
     * The nms packet object.
     */
    @NonNull
    private final Object nmsPacket;
    /**
     * The direction this packet is going.
     */
    @NonNull
    private final PacketDirection direction;
    /**
     * The type of this packet.
     */
    @NonNull
    private final PacketType packetType;
    /**
     * Whether or not this packet is cancelled.
     */
    @Setter
    private boolean cancelled = false;

}