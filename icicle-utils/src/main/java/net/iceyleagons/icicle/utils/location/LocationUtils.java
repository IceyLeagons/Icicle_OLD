/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicle.utils.location;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;

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
                Objects.requireNonNull(location.getWorld()).getUID().toString(), location.getX(), location.getY(),
                location.getZ(), location.getYaw(), location.getPitch()).replaceAll(";", String.valueOf(joinChar)).replaceAll(",", ".");
    }

    public static void playSoundAtLocation(@NonNull Location location, @NonNull Sound sound, float volume, float pitch) {
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
    public static Location deserializeLocation(String input, String regex) {
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
