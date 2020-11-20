package net.iceyleagons.icicle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains operations regarding {@link Map}s
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class MapUtils {

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
