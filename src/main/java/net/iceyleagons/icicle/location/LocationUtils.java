package net.iceyleagons.icicle.location;

import lombok.NonNull;
import net.iceyleagons.icicle.utils.Asserts;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Contains operations regarding {@link Location}s
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public final class LocationUtils {

    /**
     * This will serialize the location into a string, format: world x y z yaw pitch
     * It will join these data together with the given joinChar
     *
     * @param location the {@link Location} to serialize
     * @param joinChar the joinChar
     * @return the serialized Location
     */
    public static String serializeLocation(@NotNull Location location, char joinChar) {
        Asserts.notNull(location, "Location must not be null!");
        //world,x,y,z,yaw,pitch

        return String.format("%s;%f;%f;%f;%f;%f",
                Objects.requireNonNull(location.getWorld()).getUID().toString(), location.getX(), location.getY(),
                location.getZ(), location.getYaw(), location.getPitch()).replaceAll(";", String.valueOf(joinChar)).replaceAll(",", ".");
    }

    public static void playSoundAtLocation(@NotNull Location location, @NotNull Sound sound, float volume, float pitch) {
        Asserts.notNull(location, "Location must not be null!");
        Asserts.notNull(sound, "Sound must not be null!");

        Objects.requireNonNull(location.getWorld()).playSound(location, sound, volume, pitch);
    }

    /**
     * This will deserialize the location.
     * It will split the string into sections with the given regex. (Write this regex for the joinChar you've specified)
     *
     * @param input the string to deserialize
     * @param regex the regex
     * @return the deserialized Location
     */
    @Nullable
    public static Location deserializeLocation(String input, String regex) {
        if (input == null || input.isEmpty() || regex == null || regex.isEmpty()) return null;

        String[] data = input.split(regex);
        World world = Bukkit.getServer().getWorld(UUID.fromString(data[0]));
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double z = Double.parseDouble(data[3]);
        float yaw = Float.parseFloat(data[4]);
        float pitch = Float.parseFloat(data[5]);

        Location location = new Location(world, x, y, z);
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }
}
