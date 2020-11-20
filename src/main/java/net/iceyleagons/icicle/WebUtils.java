package net.iceyleagons.icicle;

import okhttp3.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Contains basic operations regarding Website connections and requests.
 *
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class WebUtils {

    /**
     * This will open a connection to the given {@link URL} and generates a string with line seperators.
     *
     * @param url the {@link URL}
     * @return the read response, can be null!
     */
    public static String readURL(URL url) {
        try {
            URLConnection urlConnection = url.openConnection();
            try (InputStream inputStream = urlConnection.getInputStream()) {
                try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream,StandardCharsets.UTF_8)) {
                    try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line);
                            stringBuilder.append(System.lineSeparator());
                        }
                        return stringBuilder.toString();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

}
