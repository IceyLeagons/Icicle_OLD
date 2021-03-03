package net.iceyleagons.icicle.wrapped.packet;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.wrapped.world.WrappedBlockPosition;

import java.lang.reflect.Constructor;

public class WrappedPacketPlayOutBlockBreakAnimation {
    private static final Class<?> mc_PacketPlayOutBlockBreakAnimation;

    private static final Constructor<?> packet_constructor;

    static {
        mc_PacketPlayOutBlockBreakAnimation = Reflections.getNormalNMSClass("PacketPlayOutBlockBreakAnimation");

        packet_constructor = Reflections.getConstructor(mc_PacketPlayOutBlockBreakAnimation, true, int.class, WrappedBlockPosition.mc_BlockPosition, int.class);
    }

    @Getter
    private final Object packet;

    @SneakyThrows
    public WrappedPacketPlayOutBlockBreakAnimation(int playerId, WrappedBlockPosition blockPosition, int damage) {
        this.packet = packet_constructor.newInstance(playerId, blockPosition.getRoot(), damage);
    }
}
