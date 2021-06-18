package net.iceyleagons.icicle.file;

import lombok.SneakyThrows;
import net.iceyleagons.icicle.utils.Asserts;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.*;

/**
 * Contains utility methods regarding File writing (Output) and File reading (Input).
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public final class FileIO {

    /**
     * Will return the file name, but without the extension.
     *
     * @param file the file
     * @return the name of the file
     */
    @NotNull
    public static String getFileNameWithoutExtension(@NotNull File file) {
        Asserts.notNull(file, "File cannot be null!");

        return file.getName().replaceFirst("[.][^.]+$", "");
    }

    /**
     * Writes the specified content to the file. Where every new element in the array is a new line.
     *
     * @param file    the {@link File} to write to
     * @param content the lines
     */
    @SneakyThrows
    public static void writeFile(@NotNull File file, @NotNull String... content) {
        Asserts.notNull(file, "File cannot be null!");
        Asserts.notNull(content, "Content cannot be null!");

        try (FileWriter fileWriter = new FileWriter(file)) {
            for (final String line : content) {
                fileWriter.write(line + System.lineSeparator());
            }
        }
    }

    /**
     * Writes the specified {@link JSONObject} to the file, in a pretty format. Where every new element in the array is a new line.
     *
     * @param file    the {@link File} to write to
     * @param jsonObject the JSON to write
     */
    @SneakyThrows
    public static void writeJsonToFile(@NotNull File file, @NotNull JSONObject jsonObject) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            try (Writer writer = jsonObject.write(fileWriter, 1, 2)) {
                writer.flush();
            }
        }
    }

    /**
     * Reads the file and joins every row together with {@link System#lineSeparator()}
     *
     * @param file the {@link File} to read
     * @return the content
     */
    @NotNull
    public static String readFile(@NotNull File file) {
        Asserts.notNull(file, "File cannot be null!");

        final StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append(System.lineSeparator());
            }
        } catch (IOException ignored) {
            return "";
        }

        return contentBuilder.toString();
    }
}
