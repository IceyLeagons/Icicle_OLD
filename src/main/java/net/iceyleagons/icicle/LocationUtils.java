package net.iceyleagons.icicle;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Contains operations regarding {@link Location}s
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class LocationUtils {

    /**
     * This will serialize the location into a string, format: world x y z yaw pitch
     * It will join these data together with the given joinChar
     *
     * @param location the {@link Location} to serialize
     * @param joinChar the joinChar
     * @return the serialized Location
     */
    public static String serializeLocation(Location location, char joinChar) {
        //world,x,y,z,yaw,pitch

        return String.format("%s;%f;%f;%f;%f;%f",
                Objects.requireNonNull(location.getWorld()).getUID().toString(),location.getX(),location.getY(),
                location.getZ(),location.getYaw(),location.getPitch()).replace(';',joinChar);
    }

    public static void playSoundAtLocation(@NonNull Location location, @NonNull Sound sound, float volume, float pitch) {
        Objects.requireNonNull(location.getWorld()).playSound(location,sound,volume,pitch);
    }

    /**
     * This will deserialize the location.
     * It will split the string into sections with the given regex. (Write this regex for the joinChar you've specified)
     *
     * @param input the string to deserialize
     * @param regex the regex
     * @return the deserialized Location
     */
    public static Location deserializeLocation(String input, String regex) {
        String[] data = input.split(regex);
        World world = Bukkit.getServer().getWorld(UUID.fromString(data[0]));
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double z = Double.parseDouble(data[3]);
        float yaw = Float.parseFloat(data[4]);
        float pitch = Float.parseFloat(data[5]);

        Location location = new Location(world,x,y,z);
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }
}
