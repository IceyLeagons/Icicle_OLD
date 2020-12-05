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

package net.iceyleagons.icicle.web;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.UUID;

/**
 * This class contains operations to communicate with Mojang's API
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mojang {

    /**
     * This will return a string array with the name history of the user, where the last
     * element is the current name.
     * <b>Caching is not implemented!</b>
     *
     * @param uuid the {@link UUID} of the user to check for
     * @return a String[] array with the name history. Can be null if an error happens!
     */
    public static String[] getNameHistory(UUID uuid) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/" + uuid.toString() + "/names");
            String response = WebUtils.readURL(url);
            if (response == null) return null;

            JSONArray jsonArray = new JSONArray(response);
            String[] names = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                names[i] = jsonArray.getJSONObject(i).getString("name");
            }
            return names;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This will get the UUID for the username on Mojang's API.
     * Note that this will only return a UUID, if the player's account exists in the Mojang database aka. they bought the game.
     * <b>Caching is not implemented!</b>
     *
     * @param username the username of the player
     * @return the UUID of such player (can be null!)
     */
    public static UUID getUUID(String username) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            String response = WebUtils.readURL(url);
            if (response == null) return null;

            JSONObject jsonObject = new JSONObject(response);
            String uuid = jsonObject.getString("id");

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i <= 31; i++) {
                stringBuilder.append(uuid.charAt(i));
                if (i == 7 || i == 11 || i == 15 || i == 19) {
                    stringBuilder.append('-');
                }
            }

            return UUID.fromString(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}