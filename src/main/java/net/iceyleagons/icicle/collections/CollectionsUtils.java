package net.iceyleagons.icicle.collections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Useful things regarding Lists
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.4.0-SNAPSHOT
 */
public final class CollectionsUtils {

    @SafeVarargs
    public static <T> Collection<T> mergeCollections(Collection<T>... collections) {
        return Arrays.stream(collections).flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * This wil sort the given map by it's values.
     *
     * @param map the map
     * @param <K> the key
     * @param <V> the value
     * @return a new {@link LinkedHashMap} with the sorted order.
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        return new LinkedHashMap<>(list.parallelStream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

}
