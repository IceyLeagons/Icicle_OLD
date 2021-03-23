package net.iceyleagons.icicle.annotations.autowiring;

import net.iceyleagons.icicle.api.annotations.ServiceProvider;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AutowiringHandler;
import net.iceyleagons.icicle.reflect.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.lang.reflect.Field;

@AutowiringHandler(ServiceProvider.class)
public class ServiceProviderAutowiringHandler extends AbstractAutowiringHandler {

    @Override
    public boolean isSupported(Field field) {
        return field.isAnnotationPresent(ServiceProvider.class);
    }

    @Override
    public void inject(Field field, Object object) {
        ServiceProvider serviceProvider = field.getAnnotation(ServiceProvider.class);

        RegisteredServiceProvider<?> registeredServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(serviceProvider.value());
        Reflections.set(field, object, registeredServiceProvider.getService());

    }
}
