package net.iceyleagons.icicle.bytebin;

import com.google.common.net.MediaType;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.WebUtils;
import okhttp3.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * This class is used to interact with https://github.com/lucko/bytebin
 * <p>
 * If you use the public instance you need to read lucko's conditions!
 * You can use it in your application as long as:
 *<br>
 *   - you're not malicious
 *   - you don't needlessly spam it
 *   - your usage isn't illegal or going to get me into trouble
 *   - you provide a User-Agent header uniquely identifying your application
 *   - if you're planning something likely to be super duper popular or use a lot of data (> 1GB), then please run it past me first
 *
 * </p>
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class Bytebin  {

    private final String userAgent;
    private final String bytebinHost;
    private final OkHttpClient okHttpClient;

    /**
     * Creates a new Bytebin handler.
     *
     * @param javaPlugin the {@link JavaPlugin}
     * @param bytebinHost the host's URL
     */
    public Bytebin(JavaPlugin javaPlugin, String bytebinHost) {
        this.userAgent = javaPlugin.getName()+"-bytebinclient";
        this.bytebinHost = bytebinHost;
        this.okHttpClient = new OkHttpClient();
    }

    /**
     * Will return the content of that Bytebin.
     * <p>Ideally it's a raw JSON</p>
     *
     * @param key the key to the bytebin
     * @return the content
     */
    public String readContent(String key) {
        Request request = new Request.Builder()
                .header("User-Agent",this.userAgent)
                .url(this.bytebinHost+key)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            try (ResponseBody responseBody = response.body()) {
                if (responseBody == null) return null;
                try (InputStream inputStream = responseBody.byteStream()) {
                    try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
                        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line);
                            }
                            return stringBuilder.toString();
                        }
                    }
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Compressed the string into a byte[] using gzip
     *
     * @param data the data to compress
     * @return the compressed data
     */
    private byte[] compress(String data) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }


    /**
     * Posts data to bytebin.
     *
     * @param rawData the data to post
     * @return the key for bytebin reading
     */
    @SneakyThrows
    public String postData(String rawData) {

        byte[] gzipped = compress(rawData);

        RequestBody requestBody = RequestBody.create(WebUtils.JSON_MEDIA_TYPE,gzipped);

        Request.Builder builder = new Request.Builder()
                .url(this.bytebinHost+"post")
                .header("User-Agent", this.userAgent)
                .header("Content-Encoding","gzip");

        Request request = builder.post(requestBody).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                response.close();
                return null;
            }
            return response.header("Location");
        }
    }

}
