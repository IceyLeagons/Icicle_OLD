package net.iceyleagons.icicle.annotations.handlers;

import net.iceyleagons.icicle.RegisteredIciclePlugin;

public interface CustomAnnotationHandlerListener {

    void postRegistered(Object object, Class<?> type, RegisteredIciclePlugin registeredIciclePlugin);

}
