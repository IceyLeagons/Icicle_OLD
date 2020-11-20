package net.iceyleagons.icicle.protocol;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Contains operations regarding ping.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class PingUtils {

    /**
     * This method is used to get the {@link Player}'s ping
     *
     * @param player the player
     * @return the ping (if -1 then an error happened)
     */
    public static int getPing(Player player) {
        Class<?> playerClazz = player.getClass();
        Method getHandle = Reflections.getMethod(playerClazz,"getHandle",true,Player.class);
        if (getHandle == null) return -1;

        try {
            Object craftPlayer = getHandle.invoke(player);
            Field field = Reflections.getField(craftPlayer.getClass(),"ping",true);

            int ping = field.getInt(craftPlayer);
            return Math.max(ping, 0); //We don't want negative here, because that will indicate an error in our case.
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return -1;
        }

    }

}
