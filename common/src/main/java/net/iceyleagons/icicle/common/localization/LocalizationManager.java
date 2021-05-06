/*
 * MIT License
 *
 * Copyright (c) 2021 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle.common.localization;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * This class should be used to set the language at startup from your plugin's main config.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class LocalizationManager {
    public static YamlConfiguration languageFile;
    private static File localeFolder;

    /**
     * Sets the locale folder.
     * The {@link #loadLanguageFile(String)} will search for <langKey>.yml files here
     *
     * @param folder the folder
     */
    public static void setLocaleFolder(File folder) {
        if (folder.exists()) {
            if (!folder.isDirectory()) throw new IllegalArgumentException("Folder is not folder!");

            localeFolder = folder;
        } else {
            if (!folder.mkdirs()) throw new IllegalStateException("Could not create folder for locales!");
        }
    }

    /**
     * Will attempt to load in a language yml file if it exist.
     *
     * @param langKey the language file name (do not include .yml)
     */
    public static void loadLanguageFile(String langKey) {
        File file = new File(localeFolder, langKey + ".yml");
        if (!file.exists()) languageFile = null;
        else {
            languageFile = YamlConfiguration.loadConfiguration(file);
        }
    }

}
