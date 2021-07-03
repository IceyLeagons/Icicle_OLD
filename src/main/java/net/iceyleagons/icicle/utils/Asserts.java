package net.iceyleagons.icicle.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The following class, contains useful assertions to use within our code.
 * When the required conditions are not met, these methods will throw exceptions, which are mainly: {@link IllegalStateException} or {@link IllegalArgumentException}
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Asserts {

    public static void hasPermission(@Nullable Player player, String permission, String message) {
        if (player == null || !(player.hasPermission(permission) || player.isOp())) {
            throw new IllegalStateException(message);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isSize(int size, @Nullable List<?> array, String message) {
        if (array == null || array.size() != size) {
            throw new IllegalStateException(message);
        }
    }

    public static void isSize(int size, @Nullable Object[] array, String message) {
        if (array == null || array.length != size) {
            throw new IllegalStateException(message);
        }
    }

    public static void notEmpty(@Nullable List<?> list, String message) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(@Nullable Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(@Nullable String value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void isInstanceOf(Class<?> type, @Nullable Object object, String message) {
        notNull(type, "Type to check against must not be null!");

        if (!type.isInstance(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, String message) {
        notNull(superType, "Super-type to check against must not be null!");


        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message);
        }
    }
}
