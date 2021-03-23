package net.iceyleagons.icicle.misc;

public interface ParameterSupplier<K, V> {

    V get(K value);

}
