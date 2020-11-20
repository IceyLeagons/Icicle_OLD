package net.iceyleagons.icicle.bungee;

import com.google.common.io.ByteArrayDataOutput;
import net.iceyleagons.icicle.bungee.channel.BungeeChannel;
import net.iceyleagons.icicle.bungee.channel.BungeeChannelListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Main class handling and containing everything
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class BungeeUtils {

    private static BungeeChannelListener bungeeChannelListener = null;
    private static Map<String, BungeeChannel> bungeeChannelMap = null;
    private static JavaPlugin plugin = null;

    /**
     * Initializes Bungee messaging. Must be run first!
     *
     * @param javaPlugin the {@link JavaPlugin} to register to
     */
    public static void init(JavaPlugin javaPlugin) {
        if (bungeeChannelListener == null) bungeeChannelListener = new BungeeChannelListener();
        if (bungeeChannelMap == null) bungeeChannelMap = new HashMap<>();
        if (plugin == null) plugin = javaPlugin;


        Messenger messenger = plugin.getServer().getMessenger();;
        messenger.registerOutgoingPluginChannel(javaPlugin,"BungeeCord");
        messenger.registerIncomingPluginChannel(javaPlugin,"BungeeCord",bungeeChannelListener);
    }

    /**
     * Sends a {@link ByteArrayDataOutput} on a specific sub-channel to the BungeeCord main channel using the {@link Player}
     *
     * @param output the output
     * @param channel the sub channel
     * @param player the player
     */
    public static void send(ByteArrayDataOutput output, String channel, Player player) {
        player.sendPluginMessage(plugin,channel,output.toByteArray());
    }


    /**
     * Returns a {@link BungeeChannel} with the given name in on Optional.
     *
     * @param name the name to search
     * @return a {@link Optional} containing the {@link BungeeChannel} can be empty!
     */
    public static Optional<BungeeChannel> getChannel(String name) {
        if (bungeeChannelMap.containsKey(name)) return Optional.of(bungeeChannelMap.get(name));
        else return Optional.empty();
    }

    /**
     * Registers a channel, used internally in constructors.
     *
     * @param bungeeChannel the {@link BungeeChannel} to register.
     */
    public static void registerChannel(BungeeChannel bungeeChannel) {
        bungeeChannelMap.put(bungeeChannel.getChannelName(),bungeeChannel);
    }

}
