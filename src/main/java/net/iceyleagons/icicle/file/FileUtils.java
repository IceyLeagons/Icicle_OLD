package net.iceyleagons.icicle.file;

import java.io.File;

/**
 * Contains useful stuff for {@link File}s
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileUtils {

    /**
     * Will return the file name, but without the extension.
     *
     * @param file the file
     * @return the name of the file
     */
    public static String getFileNameWithoutExtension(File file) {
        return file.getName().replaceFirst("[.][^.]+$", "");
    }

}
