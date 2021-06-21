package net.iceyleagons.icicle.other;

import net.iceyleagons.icicle.thirdparty.jnbt.Tag;

import java.util.Map;

/**
 * Contains some useful things for NBT, which is not in the JNBT NBTUtils.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.7-SNAPSHOT
 */
public final class NBTUtils {

    /**
     * @param items    the values of a Tag
     * @param key      the key to get
     * @param expected the wanted type
     * @param <T>      the wanted type
     * @return if the result can be casted to the wanted type it will return that otherwise null
     */
    public static <T> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) {
        if (!items.containsKey(key)) return null;

        Tag tag = items.get(key);
        if (!expected.isInstance(tag))
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());

        return expected.cast(tag);
    }
}
