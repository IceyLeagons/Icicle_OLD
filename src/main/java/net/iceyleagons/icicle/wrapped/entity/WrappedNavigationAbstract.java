package net.iceyleagons.icicle.wrapped.entity;

import net.iceyleagons.icicle.wrapped.utils.WrappedClass;

public class WrappedNavigationAbstract {

    public static final WrappedClass clazz = WrappedClass.getNMSClass("NavigationAbstract").lookupMethod(boolean.class,"a", "move",
            double.class, double.class, double.class, double.class);

    private final Object object;

    public WrappedNavigationAbstract(Object from) {
        this.object = from;
    }

    public boolean move(double x, double y, double z, double speed) {
        return (boolean) clazz.getMethod("move").invoke(object, x, y, z, speed);
    }

}
