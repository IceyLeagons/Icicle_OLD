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

package net.iceyleagons.icicle.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.file.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Updater for Spigot plugin.
 * API provided by spiget.org
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
@RequiredArgsConstructor
public class Updater {

    private static final String API = "https://api.spiget.org/v2/resources/%resourceId%/versions/latest";
    private static final String DOWNLOAD = "https://api.spiget.org/v2/resources/%resourceId%/versions/latest/download";

    /**
     * The spigot resource id
     */
    private final String resourceId;
    /**
     * The java plugin
     */
    private final JavaPlugin javaPlugin;

    private String getVersion() {
        return javaPlugin.getDescription().getVersion();
    }

    /**
     * Used for checking whether the plugin is up-to-date or not
     *
     * @return the {@link UpdaterResponse}
     */
    @SneakyThrows
    public UpdaterResponse checkForUpdates() {
        JSONObject response = getAPIResponse();
        if (response == null || response.has("name"))
            return new UpdaterResponse(ResponseType.ERROR, "API fetching error.");

        String version = response.getString("name");
        int result = compareVersions(version, getVersion());
        if (result == 1) { //v1 bigger than v2
            return new UpdaterResponse(ResponseType.NOT_UP_TO_DATE, "Plugin is not up to date! Newest version: " + version);
        } else if (result == -1) { //v1 smaller than v2
            return new UpdaterResponse(ResponseType.NON_EXISTING_VERSION, "Plugin's version is bigger than the ones found online!");
        }

        return new UpdaterResponse(ResponseType.UP_TO_DATE, "Plugin up to date!");
    }

    /**
     * Attempts to download the update and replace old jar file.
     * Will require a reload afterwards!
     *
     * @return true only if it was successful, false if any error happens
     */
    public boolean downloadUpdate() {
        String url = DOWNLOAD.toLowerCase().replace("%resourceId%", resourceId);
        try {
            File pluginFile = new File(javaPlugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            return FileUtils.downloadFile(url,pluginFile);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    private JSONObject getAPIResponse() throws MalformedURLException {
        String url = API.toLowerCase().replace("%resourceId%", resourceId);
        URL url1 = new URL(url);
        String fromWeb = WebUtils.readURL(url1);
        return fromWeb == null ? null : new JSONObject(fromWeb);
    }

    //From
    public int compareVersions(String version1, String version2) {
        String[] string1Vals = version1.split("\\.");
        String[] string2Vals = version2.split("\\.");

        int length = Math.max(string1Vals.length, string2Vals.length);

        for (int i = 0; i < length; i++) {
            int v1 = (i < string1Vals.length) ? Integer.parseInt(string1Vals[i]) : 0;
            int v2 = (i < string2Vals.length) ? Integer.parseInt(string2Vals[i]) : 0;

            if (v1 > v2) {
                return 1;
            } else if (v1 < v2) {
                return -1;
            }
        }
        return 0;
    }

    @Getter
    @AllArgsConstructor
    public static class UpdaterResponse {
        private final ResponseType type;
        private final String message;
    }

    @Getter
    @AllArgsConstructor
    public enum ResponseType {
        UP_TO_DATE(0), NOT_UP_TO_DATE(1), NON_EXISTING_VERSION(2), ERROR(3);
        private final int id;
    }
}
