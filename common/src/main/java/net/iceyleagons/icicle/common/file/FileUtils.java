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

package net.iceyleagons.icicle.common.file;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

/**
 * Contains useful stuff for {@link File}s
 *
 * @author TOTHTOMI
 * @version 2.1.0
 * @since 1.1.4-SNAPSHOT
 */
public class FileUtils {

    /**
     * Will return the file name, but without the extension.
     *
     * @param file the file
     * @return the name of the file
     */
    public static String getFileNameWithoutExtension(@NonNull File file) {
        return file.getName().replaceFirst("[.][^.]+$", "");
    }

    /**
     * Downloads a file from a URL.
     *
     * @param url the url
     * @param to  the file to download to (MUST EXIST!)
     * @return true only if it was successful, false otherwise
     */
    public static boolean downloadFile(String url, File to) {
        try {
            URL url1 = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "IcicleUpdater");
            try (ReadableByteChannel readableByteChannel = Channels.newChannel(httpURLConnection.getInputStream())) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(to)) {
                    fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, httpURLConnection.getContentLengthLong());
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * This will create a file "safely".
     * What that means is it will throw a runtime exception and return null if an error happens, it also
     * checks if the file exists or not, and only creates it when it does not.
     * <b>It should only be used for files not directories!</b>
     *
     * @param file the {@link File} to create
     * @return the passed {@link File}
     */
    @Nullable
    public static File createFileSafely(@NonNull File file) {
        if (file.exists()) return file;
        try {
            if (!file.createNewFile()) return null;
            return file;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    /**
     * Writes the specified content to the file. Where every new element in the array is a new line.
     *
     * @param file    the {@link File} to write to
     * @param content the lines
     */
    @SneakyThrows
    public static void writeToFile(@NonNull File file, @NonNull String... content) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (String line : content)
                fileWriter.write(line + System.lineSeparator());
        }
    }

    /**
     * Reads the file and joins every row together with {@link System#lineSeparator()}
     *
     * @param file the {@link File} to read
     * @return the content
     */
    @Nullable
    public static String readFile(@NonNull File file) {

        try (InputStream inputStream = new FileInputStream(file)) {
            try (Scanner scanner = new Scanner(inputStream)) {
                StringBuilder stringBuilder = new StringBuilder();

                while (scanner.hasNextLine()) {
                    stringBuilder.append(scanner.hasNextLine()).append("\n");
                }

                return stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
