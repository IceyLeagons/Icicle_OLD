package net.iceyleagons.icicle.event.packets.enums;

/**
 * An enum containing the possible directions this packet could go in.
 *
 * @author Gábe
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
public enum PacketDirection {
    /**
     * We caught this packet going towards the client.
     */
    IN,
    /**
     * We caught this packet going towards the server.
     */
    OUT
}