package net.iceyleagons.icicle.wrapping.packet;

import net.iceyleagons.icicle.wrapping.world.WrappedBlockPosition;

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
