package net.iceyleagons.icicle.bungee.message;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.AllArgsConstructor;
import net.iceyleagons.icicle.bungee.BungeeUtils;
import net.iceyleagons.icicle.bungee.channel.BungeeChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Contains a {@link BungeeMessage} and a {@link BungeeChannel}, basically an object that links these two together.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
@AllArgsConstructor
public class RegisteredBungeeMessage {

    private final BungeeMessage bungeeMessage;
    private final BungeeChannel bungeeChannel;

    /**
     * Send the message to BungeeCord main channel with the first online player found in {@link Bukkit#getOnlinePlayers()}.
     */
    public void send() {
        send(Iterables.getFirst(Bukkit.getOnlinePlayers(), null));
    }

    /**
     * Send the message to BungeeCord main channel with a specific {@link Player}
     *
     * @param player the sender
     */
    public void send(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(bungeeMessage.getId());
        out.writeUTF(bungeeMessage.getMessage());
        BungeeUtils.send(out,bungeeChannel.getChannelName(),player);
    }

}
