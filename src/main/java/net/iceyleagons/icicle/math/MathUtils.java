package net.iceyleagons.icicle.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Contains useful stuff regarding Math and numbers
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class MathUtils {

    /**
     * Creates a vector from the given loactions.
     *
     * @param from the starting point
     * @param to the goal
     * @return a {@link Vector}
     */
    public static Vector getVector(Location from, Location to) {
        double dX = from.getX() - to.getX();
        double dY = from.getY() - to.getY();
        double dZ = from.getZ() - to.getZ();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        double X = Math.sin(pitch) * Math.cos(yaw);
        double Y = Math.sin(pitch) * Math.sin(yaw);
        double Z = Math.cos(pitch);

        return new Vector(X, Z, Y);
    }

    /**
     * @param value the number to check
     * @param min minimum of the range
     * @param max maxmimum of the range
     * @return true if the number is between the minimum and the maximum number
     */
    public static boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Return the appropriate suffix for a number.
     * Ex. 1 item, 2 item<b>s</b>
     *
     * @param value the number
     * @return "s" if it's more than one, otherwise a blank string
     */
    public static String getPronumeral(int value) {
        return value == 1 ? "" : "s";
    }



}
