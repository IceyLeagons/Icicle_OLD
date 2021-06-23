package net.iceyleagons.icicle.annotations.handlers;

import net.iceyleagons.icicle.RegisteredIciclePlugin;

public interface AutoCreationHandlerListener {

    //type is here due to CGLIB

    void onCreated(Object object, Class<?> type, RegisteredIciclePlugin registeredIciclePlugin);

}
