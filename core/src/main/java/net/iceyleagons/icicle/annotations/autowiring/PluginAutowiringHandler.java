package net.iceyleagons.icicle.annotations.autowiring;

import net.iceyleagons.icicle.api.annotations.Plugin;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AutowiringHandler;
import net.iceyleagons.icicle.reflect.Reflections;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;

@AutowiringHandler(Plugin.class)
public class PluginAutowiringHandler extends AbstractAutowiringHandler {

    @Override
    public boolean isSupported(Field field) {
        return field.isAnnotationPresent(Plugin.class);
    }

    @Override
    public void inject(Field field, Object object) {
        Plugin plugin = field.getAnnotation(Plugin.class);

        org.bukkit.plugin.Plugin bukkitPlugin = Bukkit.getPluginManager().getPlugin(plugin.name());
        if (bukkitPlugin == null) return;
        if (plugin.main().isInstance(bukkitPlugin)) {
            Reflections.set(field, object, bukkitPlugin);
        }
    }
}
