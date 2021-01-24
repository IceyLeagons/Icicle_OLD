/*
 * MIT License
 *
 * Copyright (c) 2021 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle.misc.geo;

import lombok.SneakyThrows;
import net.iceyleagons.icicle.web.WebUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.net.URL;
import java.util.Objects;

/**
 * API service provided as is by codetabs.com
 * Information: https://codetabs.com/ip-geolocation/ip-geolocation.html
 * Their service includes IP2Location LITE data available from https://lite.ip2location.com.
 * <p>
 * The database used by them may not be the most accurate one, but it should be plenty for automatic language select purposes.
 *
 * <b>This is just a library. It's your responsibility and or liability to do this legally!<br>
 * We shall not be responsible or liable for any illegal activities related to this library</b>
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class Geolocation {

    private static final String URL_FORMAT = "https://api.codetabs.com/v1/geolocation/json?q=%ip%";

    @SneakyThrows
    @Nullable
    public static GeoData locate(String toLocate) {
        String url = URL_FORMAT.replaceAll("%ip%", toLocate).toLowerCase();

        String response = WebUtils.readURL(new URL(url));
        if (response == null) return null;

        JSONObject jsonObject = new JSONObject(response);

        String ip = jsonObject.getString("ip");
        String countryCode = jsonObject.getString("country_code");
        String countryName = jsonObject.getString("country_name");
        String regionName = jsonObject.getString("region_name");
        String city = jsonObject.getString("city");
        String zipCode = jsonObject.getString("zip_code");
        String timeZone = jsonObject.getString("time_zone"); //In format of -07:00 PARSE IT!
        float latitude = jsonObject.getFloat("latitude");
        float longitude = jsonObject.getFloat("longitude");

        return new GeoData(ip, countryCode, countryName, regionName,
                city, zipCode, timeZone, latitude, longitude);

    }

    public static GeoData locate(Player player) {
        return locate(Objects.requireNonNull(player.getAddress()).getHostName());
    }

}
