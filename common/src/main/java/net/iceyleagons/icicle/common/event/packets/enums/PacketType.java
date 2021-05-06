package net.iceyleagons.icicle.common.event.packets.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import common.wrapped.packet.*;
import net.iceyleagons.icicle.common.wrapped.packet.*;
import net.iceyleagons.icicle.wrapped.packet.*;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * The currently implemented types of packets we catch.
 *
 * @author Gábe
 * @version 1.1.0
 * @since 2.0.0-SNAPSHOT
 */
@AllArgsConstructor
@Getter
public enum PacketType {
    PLAY_OUT_ENTITY_HEAD_ROTATION(WrappedPacketPlayOutEntityHeadRotation.class),
    PLAY_OUT_BLOCK_BREAK_ANIMATION(WrappedPacketPlayOutBlockBreakAnimation.class),
    PLAY_OUT_ENTITY_DESTROY(WrappedPacketPlayOutEntityDestroy.class),
    PLAY_OUT_ENTITY_METADATA(WrappedPacketPlayOutEntityMetadata.class),
    PLAY_OUT_MAP_CHUNK(WrappedPacketPlayOutMapChunk.class),
    PLAY_OUT_NAMED_ENTITY_SPAWN(WrappedPacketPlayOutNamedEntitySpawn.class),
    PLAY_OUT_PLAYER_INFO(WrappedPacketPlayOutPlayerInfo.class),
    PLAY_OUT_ENTITY_LOOK(WrappedPacketPlayOutEntity.PacketPlayOutEntityLook.class);

    private Class<? extends Packet> packetClass;

    @Nullable
    public static PacketType getFromPacket(Object packet) {
        return Arrays.stream(values()).filter(type -> packet.getClass().getSimpleName().toLowerCase().contains(type.name().replace("_", "").toLowerCase())).findAny().orElse(null);
    }
}