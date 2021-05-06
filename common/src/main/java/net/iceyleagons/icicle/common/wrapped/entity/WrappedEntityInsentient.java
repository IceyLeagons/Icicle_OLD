package net.iceyleagons.icicle.common.wrapped.entity;

import net.iceyleagons.icicle.common.wrapped.utils.WrappedClass;

public class WrappedEntityInsentient {

    public static final WrappedClass clazz =
            WrappedClass.getNMSClass("EntityInsentient")
                    .lookupMethod("getNavigation", "getNavigation");

    private final Object object;

    public WrappedEntityInsentient(WrappedCraftCreature wrappedCraftCreature) {
        this.object = clazz.cast(wrappedCraftCreature.getObject());
    }

    public WrappedNavigationAbstract getNavigation() {
        return new WrappedNavigationAbstract(clazz.getMethod("getNavigation").invoke(object));
    }

}
