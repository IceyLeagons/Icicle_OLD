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

package net.iceyleagons.icicle.file;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
     * This will create a file "safely".
     * What that means is it will throw a runtime exception and return null if an error happens, it also
     * checks if the file exists or not, and only creates it when it does not.
     * <b>It should only be used for files not directories!</b>
     *
     * @param file the {@link File} to create
     * @return the passed {@link File}
     */
    public static File createFileSafely(@NonNull File file) {
        if (file.exists()) return file;
        try {
            if (!file.createNewFile()) throw new RuntimeException("Could not create file " + file.getName());
            return file;
        } catch (IOException ioException) {
            throw new RuntimeException("Could not create file " + file.getName(), ioException);
        }
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
                fileWriter.write(line + "\n");
        }
    }

}
