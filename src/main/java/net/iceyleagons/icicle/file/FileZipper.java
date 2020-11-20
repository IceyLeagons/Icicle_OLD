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

import java.io.*;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Used for compressing, decompressing and checking files for compression.
 * <b>Based around GZIP</b>
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileZipper {

    /**
     * This will compress the file into the given output file. The input file will not be deleted!
     *
     * @param file the input
     * @param output the output
     * @throws IOException if something happens during this process
     */
    public static void compress(File file, File output) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(output)) {
                try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while((len=fileInputStream.read(buffer)) != -1) {
                        gzipOutputStream.write(buffer,0,len);
                    }
                }
            }
        }
    }

    /**
     * This will decompress the file into the given output file. The input file will not be deleted!
     *
     * @param file the input
     * @param output the output
     * @throws IOException if something happens during this process
     */
    public static void decompress(File file, File output) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream)) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(output)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while((len=fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer,0,len);
                    }
                }
            }
        }
    }


    /**
     * Used for checking whether a file is Gzipped or not.
     *
     * @param file the {@link File} to check
     * @return true if it's gzipped otherwise false
     */
    public static boolean isZipped(File file) {
        int magic = 0;

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            magic = raf.read() & 0xff | ((raf.read() << 8) & 0xff00);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return magic == GZIPInputStream.GZIP_MAGIC;
    }

}
