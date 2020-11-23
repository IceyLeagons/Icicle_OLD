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

package net.iceyleagons.icicle.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * This class contains useful methods for handling items.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0-SNAPSHOT
 */
public class ItemUtils {

    /**
     * This will convert the {@link ItemStack} array into a byte array using {@link BukkitObjectOutputStream}
     *
     * @param items the items to convert
     * @return the generated byte[]
     */
    public static byte[] toByteArray(ItemStack[] items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            try (BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(outputStream)) {
                bukkitObjectOutputStream.writeInt(items.length);
                for (ItemStack item : items) bukkitObjectOutputStream.writeObject(item);
            }
            return outputStream.toByteArray();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * This will convert the byte array into an {@link ItemStack} array using {@link BukkitObjectInputStream}
     *
     * @param array the byte[] to convert
     * @return the generated byte[]
     */
    public static ItemStack[] fromByteArray(byte[] array) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(array)) {
            try (BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(inputStream)) {

                ItemStack[] items = new ItemStack[bukkitObjectInputStream.readInt()];
                for (int i = 0; i < items.length; i++) items[i] = (ItemStack) bukkitObjectInputStream.readObject();

                return items;
            }
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return new ItemStack[0];
    }

    /**
     * This will generate an {@link ItemStack} array from a base64 string
     *
     * @param input the base64 encoded string
     * @return the generated {@link ItemStack} array
     */
    public static ItemStack[] fromBase64(String input) {
        byte[] data = Base64.getDecoder().decode(input);
        return fromByteArray(data);
    }

    /**
     * This will generate a base64 encoded string from the given {@link ItemStack} array
     *
     * @param itemStacks the {@link ItemStack} array
     * @return the generated base64 string
     */
    public static String toBase64(ItemStack[] itemStacks) {
        byte[] data = toByteArray(itemStacks);
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * This will add a "glowing" effect onto an item. (An Enchant & Hide Enchant flag)
     * <b>This will not effect the original item!</b>
     *
     * @param itemStack the {@link ItemStack} to add to
     * @return the generated {@link ItemStack}
     */
    public static ItemStack addGlow(ItemStack itemStack) {
        ItemFactory itemFactory = new ItemFactory(itemStack);
        return itemFactory.makeItGlow().build();
    }

    /**
     * Return an air {@link ItemStack}
     *
     * @return the {@link ItemStack}
     */
    public static ItemStack getAir() {
        return new ItemStack(Material.AIR);
    }

}
