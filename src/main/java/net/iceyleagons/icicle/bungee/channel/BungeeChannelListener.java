package net.iceyleagons.icicle.bungee.channel;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.iceyleagons.icicle.bungee.BungeeUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Optional;

/**
 * Handles the incoming raw messages, and runs the appropriate channels.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class BungeeChannelListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);

        String subchannel = in.readUTF();
        Optional<BungeeChannel> bungeeChannelOptional = BungeeUtils.getChannel(subchannel);
        bungeeChannelOptional.ifPresent(bungeeChannel -> bungeeChannel.onBungeeChannelMessage(player, in));
    }
}
