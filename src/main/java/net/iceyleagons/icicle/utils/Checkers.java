package net.iceyleagons.icicle.utils;

import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Contains useful checker methods mainly for user inputs.
 * We suggest combining it with {@link Asserts#isTrue(boolean, String)}.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Checkers {

    /**
     * Checks whether the input is a proper {@link URL}.
     * Attempts to create a new {@link URL} from the input.
     * Providing null input will result in false as a return.
     *
     * @param input the input
     * @return true if valid, false otherwise.
     */
    public static boolean isURL(String input) {
        if (input == null) return false;

        try {
            new URL(input);
            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    /**
     * Checks whether the input is a proper {@link Integer}.
     * Uses {@link Integer#parseInt(String)}.
     * Providing null input will result in false as a return.
     *
     * @param input the input
     * @return true if valid, false otherwise.
     */
    public static boolean isInteger(@Nullable String input) {
        if (input == null) return false;

        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    /**
     * Checks whether the input is a proper {@link Double}.
     * Uses {@link Double#parseDouble(String)}.
     * Providing null input will result in false as a return.
     *
     * @param input the input
     * @return true if valid, false otherwise.
     */
    public static boolean isDouble(@Nullable String input) {
        if (input == null) return false;

        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    /**
     * Checks whether the input is a proper {@link Float}.
     * Uses {@link Float#parseFloat(String)}.
     * Providing null input will result in false as a return.
     *
     * @param input the input
     * @return true if valid, false otherwise.
     */
    public static boolean isFloat(@Nullable String input) {
        if (input == null) return false;

        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    /**
     * Checks whether the input is a proper {@link Short}.
     * Uses {@link Short#parseShort(String)}.
     * Providing null input will result in false as a return.
     *
     * @param input the input
     * @return true if valid, false otherwise.
     */
    public static boolean isShort(@Nullable String input) {
        if (input == null) return false;

        try {
            Short.parseShort(input);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    /**
     * Checks whether the input is a proper {@link Long}.
     * Uses {@link Long#parseLong(String)}.
     * Providing null input will result in false as a return.
     *
     * @param input the input
     * @return true if valid, false otherwise.
     */
    public static boolean isLong(@Nullable String input) {
        if (input == null) return false;

        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
