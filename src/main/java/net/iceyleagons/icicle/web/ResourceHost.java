package net.iceyleagons.icicle.web;

import net.iceyleagons.icicle.file.FileUtils;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * This is a wrapper for our ResourceHost api.
 *
 * @author Gábe
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
public class ResourceHost {

    /**
     * The access url to the root page of the resourcehost api.
     */
    public static final String fileHostUrl = "https://resourcehost.iceyleagons.net/";

    /**
     * Uploads a file and returns the direct download link for it.
     *
     * @param file the file we wish to upload.
     * @return the url of the download link for what we just uploaded. May be null if there was an exception somewhere.
     */
    @Nullable
    public static String upload(@NotNull File file) {
        try {
            return String.format("%sapi/upload/%s", fileHostUrl, new JSONObject(new OkHttpClient.Builder()
                    .build()
                    .newCall(new Request.Builder()
                            .url(fileHostUrl)
                            .post(new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("application/zip")))
                                    .build())
                            .build())
                    .execute()
                    .body().string()).getString("id"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (JSONException jsonException) {
            throw new RuntimeException("Quota reached! Try again in an hour!");
        }

        return null;
    }

    /**
     * Checks whether or not the resource-pack with the provided ID exists on our server.
     *
     * @param what the id of the resource-pack. If the download link is https://resourcehost.iceyleagons.net/get/abcd then the ID is abcd.
     * @return whether or not this resource-pack exists on the remote server.
     */
    public static boolean exists(@NotNull String what) {
        try {
            return new JSONObject(new OkHttpClient.Builder()
                    .build()
                    .newCall(new Request.Builder()
                            .url(String.format("%sapi/exists/%s", fileHostUrl, what))
                            .get()
                            .build())
                    .execute()
                    .body().string()).getBoolean("exists");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (JSONException jsonException) {
            throw new RuntimeException("Wrong json return! Something went wrong on the remote server.");
        }

        return false;
    }

    /**
     * Downloads the resourcepack with the provided ID.
     *
     * @param where in what file should we save the resourcepack.
     * @param what  the id of the resource-pack. If the download link is https://resourcehost.iceyleagons.net/get/abcd then the ID is abcd.
     * @return the file we just downloaded. May be null if unsuccessful.
     */
    @Nullable
    public static File download(@NotNull File where, @NotNull String what) {
        if (FileUtils.downloadFile(String.format("%sget/%s", fileHostUrl, what), where))
            return where;
        return null;
    }

    /**
     * Same as {@link #download(File, String)}
     * <p>
     * Downloads the resourcepack with the provided ID.
     *
     * @param where in what file should we save the resourcepack.
     * @param what  the id of the resource-pack. If the download link is https://resourcehost.iceyleagons.net/get/abcd then the ID is abcd.
     * @return the file we just downloaded. May be null if unsuccessful.
     */
    @Nullable
    public static File get(@NotNull File where, @NotNull String what) {
        return download(where, what);
    }

}
