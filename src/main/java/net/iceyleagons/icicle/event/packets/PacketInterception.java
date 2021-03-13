package net.iceyleagons.icicle.event.packets;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.event.Events;
import net.iceyleagons.icicle.event.packets.enums.PacketDirection;
import net.iceyleagons.icicle.event.packets.enums.PacketType;
import net.iceyleagons.icicle.wrapped.packet.Packet;
import net.iceyleagons.icicle.wrapped.player.WrappedCraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the interception of packets.
 *
 * @author Gábe
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
public class PacketInterception {

    private static final List<Player> injected = new ArrayList<>();

    /**
     * Injects an interceptor into the players packet channel.
     *
     * @param player the player we wish to inject into.
     */
    @SneakyThrows
    public static void injectPlayer(Player player) {
        if (injected.contains(player))
            throw new RuntimeException("Cannot inject player twice!");

        injected.add(player);
        Channel channel = WrappedCraftPlayer.from(player).getHandle().getNetworkManager().getChannel();
        channel.pipeline().addBefore("packet_handler", "icicle_handler", new ChannelDuplexHandler() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                if (!interceptPacket(player, msg, PacketDirection.OUT))
                    super.write(ctx, msg, promise);
            }

            @Override
            public void channelRead(@NotNull ChannelHandlerContext ctx, @NotNull Object msg) throws Exception {
                if (!interceptPacket(player, msg, PacketDirection.IN))
                    super.channelRead(ctx, msg);
            }
        });

    }

    /**
     * Intercepts a captured packet and returns whether or not we can cancel it.
     *
     * @param player    the player whose channel we caught this packet on.
     * @param packet    the packet we caught.
     * @param direction what direction the packet was going.
     * @return whether or not we can cancel this packet.
     * @deprecated internal use only!
     */
    @Deprecated
    public static boolean interceptPacket(Player player, Object packet, PacketDirection direction) {
        // Packets may be null sometimes. Filter them out.
        if (packet == null)
            return true;

        // Do stuff.
        PacketType packetType = PacketType.getFromPacket(packet);
        try {
            return Events.handlePacket(packetType, new PacketEvent(player, packet, (Packet) packetType.getPacketClass().getConstructor(Class.class, Object.class).newInstance(packet.getClass(), packet), direction, packetType));
        } catch (NullPointerException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ignored) {
            // Ignored. Not every packet is currently implemented.
        }

        return false;
    }
}
