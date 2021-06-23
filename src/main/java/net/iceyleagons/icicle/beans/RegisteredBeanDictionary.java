package net.iceyleagons.icicle.beans;

import net.iceyleagons.icicle.utils.Asserts;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This is the object that keeps track of all registered beans of an Icicle based plugin.
 * Basically a cruel "wrapping" of a HashMap :D
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class RegisteredBeanDictionary {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    /**
     * Registers a new bean into the dictionary.
     *
     * @param bean the bean to register
     */
    public void registerBean(Object bean) {
        Asserts.notNull(bean, "Cannot register null!");
        registerBean(bean, bean.getClass());
    }

    /**
     * Registers a new bean into the dictionary.
     *
     * @param bean the bean to register
     */
    public void registerBean(Object bean, Class<?> type) {
        Asserts.notNull(bean, "Cannot register null!");
        Asserts.notNull(type, "Cannot register with null type!");
        beans.put(type, bean);
    }

    /**
     * Used to check whether the dictionary contains a type or not.
     *
     * @param type the wanted bean type
     * @return true if it contains, false otherwise
     */
    public boolean contains(Class<?> type) {
        return this.beans.containsKey(type);
    }

    public Optional<Object> get(Class<?> type) {
        if (!contains(type)) return Optional.empty();

        return Optional.of(beans.get(type));
    }
}
