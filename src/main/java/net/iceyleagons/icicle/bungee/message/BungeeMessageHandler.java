package net.iceyleagons.icicle.bungee.message;

import org.bukkit.entity.Player;

/**
 * Handles a specific {@link BungeeMessage} (parses it, and understands it) coming from the proper {@link net.iceyleagons.icicle.bungee.channel.BungeeChannel}
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BungeeMessageHandler {

    /**
     * This method is ran when the {@link net.iceyleagons.icicle.bungee.channel.BungeeChannel} gets a message, that is applicable for this handler
     *
     * @param message the incoming message <p>Ideally a raw JSON</p>
     * @param player the {@link Player}
     */
    void handle(String message, Player player);

    /**
     * @return the message ID, this can understand
     */
    String getMessageId();

}
