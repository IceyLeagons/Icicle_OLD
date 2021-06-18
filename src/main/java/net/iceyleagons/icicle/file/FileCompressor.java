package net.iceyleagons.icicle.file;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Contains utility methods regarding File compression.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public final class FileCompressor {

    /**
     * This will compress the file into the given output file. The input file will not be deleted!
     *
     * @param file   the input
     * @param output the output
     * @throws IOException if something happens during this process
     */
    public static void compress(File file, File output) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(output)) {
                try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream)) {
                    final byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fileInputStream.read(buffer)) != -1) {
                        gzipOutputStream.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    /**
     * This will decompress the file into the given output file. The input file will not be deleted!
     *
     * @param file   the input
     * @param output the output
     * @throws IOException if something happens during this process
     */
    public static void decompress(File file, File output) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream)) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(output)) {
                    final byte[] buffer = new byte[1024];
                    int len;

                    while ((len = gzipInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    /**
     * Checks if a file is gzipped.
     *
     * @param f the file to check
     * @return is it gzipped
     */
    public static boolean isGZipped(File f) {
        int magic;

        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            magic = raf.read() & 0xff | ((raf.read() << 8) & 0xff00);
        } catch (IOException ignored) {
            return false;
        }

        return magic == GZIPInputStream.GZIP_MAGIC;
    }
}
