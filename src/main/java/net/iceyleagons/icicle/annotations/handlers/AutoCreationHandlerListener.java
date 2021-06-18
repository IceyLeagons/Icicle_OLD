package net.iceyleagons.icicle.annotations.handlers;

import net.iceyleagons.icicle.RegisteredIciclePlugin;

public interface AutoCreationHandlerListener {

    void onCreated(Object object, RegisteredIciclePlugin registeredIciclePlugin);

}
