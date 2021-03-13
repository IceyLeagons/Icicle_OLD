package net.iceyleagons.icicle.wrapped.packet;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.world.WrappedBlockPosition;

import java.lang.reflect.Constructor;

/**
 * Wrapped representation of PacketPlayOutBlockBreakAnimation
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.3.9-SNAPSHOT
 */
public class WrappedPacketPlayOutBlockBreakAnimation extends Packet {
    public WrappedPacketPlayOutBlockBreakAnimation(int playerId, WrappedBlockPosition blockPosition, int damage) {
        super("PacketPlayOutBlockBreakAnimation", new Class<?>[]{int.class, WrappedBlockPosition.mc_BlockPosition, int.class}, playerId, blockPosition.getRoot(), damage);
    }

    public WrappedPacketPlayOutBlockBreakAnimation(Class<?> clazz, Object instance) {
        super(clazz, instance);
    }
}
