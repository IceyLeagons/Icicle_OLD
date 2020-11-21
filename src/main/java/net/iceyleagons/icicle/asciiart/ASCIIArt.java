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

package net.iceyleagons.icicle.asciiart;

import lombok.SneakyThrows;
import net.iceyleagons.icicle.WebUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Used for generating ASCII style logos.
 * Made possible by @johnnyaboh and his service at https://artii.herokuapp.com/
 * Fonts are available here: https://artii.herokuapp.com/fonts_list
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.1.0
 */
public class ASCIIArt {

    private static final String API_URL = "https://artii.herokuapp.com/make";

    /**
     * Returns a string from the API generated off of the text and font given.
     *
     *
     * @param text the text to generate
     * @param font the font to generate with
     * @return the response from the API appended with System.lineSeperator()
     * @throws MalformedURLException if I've messed up something internally
     */
    private static String getFromAPI(String text, String font) throws MalformedURLException {
        String formatted = text.replace(" ","+");
        String url = API_URL+"?text="+formatted;
        if (font != null) url+="&font="+font;

        return WebUtils.readURL(new URL(url));
    }


    /**
     * Returns a string generated off of the text and font given.
     * This will look for a file, if it does not exists we'll reach out to the API, otherwise read it in from the file.
     *
     * @param text the text to generate
     * @param font the font to generate with
     * @param javaPlugin the {@link JavaPlugin}
     * @return the response from the art appended with System.lineSeperator()
     */
    @SneakyThrows
    public static String get(String text, String font, JavaPlugin javaPlugin) {
        String fileName = text.replace(" ","-")+font;
        File file = new File(javaPlugin.getDataFolder()+File.separator+"icicle-ascii",fileName+".txt");
        if (!file.exists()) {
            boolean result = file.createNewFile();
            String fromAPI = getFromAPI(text,font);

            if (result) {
                try (FileWriter fileWriter = new FileWriter(fileName)) {
                    try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
                        String[] splitted = fromAPI.split(System.lineSeparator());
                        for (String row : splitted) {
                            printWriter.println(row);
                        }
                    }
                }
            }
            return fromAPI;
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

}
