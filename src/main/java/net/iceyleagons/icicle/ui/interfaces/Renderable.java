package net.iceyleagons.icicle.ui.interfaces;

import org.bukkit.entity.Player;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Renderable {

    /**
     * Renders the UI for the player
     */
    void renderForPlayer();

    /**
     * Sets the player. Ideally used internally.
     *
     * @param player the player
     */
    void setPlayer(Player player);
}
