package net.iceyleagons.icicle.event.packets.enums;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * The currently implemented types of packets we catch.
 *
 * @author Gábe
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
public enum PacketType {
    PLAY_OUT_ENTITY_HEAD_ROTATION, PLAY_OUT_BLOCK_BREAK_ANIMATION,
    PLAY_OUT_ENTITY_DESTROY, PLAY_OUT_ENTITY_METADATA, PLAY_OUT_MAP_CHUNK,
    PLAY_OUT_NAMED_ENTITY_SPAWN, PLAY_OUT_PLAYER_INFO, PLAY_OUT_ENTITY;

    @Nullable
    public static PacketType getFromPacket(Object packet) {
        return Arrays.stream(values()).filter(type -> packet.getClass().getSimpleName().toLowerCase().contains(type.name().replace("_", "").toLowerCase())).findAny().orElse(null);
    }
}