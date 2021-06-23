package net.iceyleagons.icicle.annotations.handlers.impl.autocreation;

import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.bukkit.EventListener;
import net.iceyleagons.icicle.annotations.handlers.annotations.AutoCreationHandler;
import net.iceyleagons.icicle.annotations.handlers.AutoCreationHandlerListener;
import net.iceyleagons.icicle.utils.Asserts;
import org.bukkit.event.Listener;

@AutoCreationHandler(EventListener.class)
public class EventListenerAutoCreationHandler implements AutoCreationHandlerListener {

    @Override
    public void onCreated(Object object, Class<?> type, RegisteredIciclePlugin registeredIciclePlugin) {
        Asserts.isInstanceOf(Listener.class, object, "Could not register class marked with @EventListener since it does not implement org.bukkit.event.Listener!");
        final Listener listener = (Listener) object;

        registeredIciclePlugin.getJavaPlugin().getServer().getPluginManager().registerEvents(listener, registeredIciclePlugin.getJavaPlugin());
    }
}
