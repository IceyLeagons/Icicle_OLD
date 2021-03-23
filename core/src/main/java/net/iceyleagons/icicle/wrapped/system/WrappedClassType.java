package net.iceyleagons.icicle.wrapped.system;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.misc.ParameterSupplier;
import net.iceyleagons.icicle.reflect.Reflections;

@RequiredArgsConstructor
public enum WrappedClassType {

    NORMAL_NMS(Reflections::getNormalNMSClass),
    NORMAL_CB(Reflections::getNormalCBClass),
    NORMAL_CLASS(Reflections::getNormalClass);

    @Getter
    private final ParameterSupplier<String, Class<?>> classSupplier;

}
