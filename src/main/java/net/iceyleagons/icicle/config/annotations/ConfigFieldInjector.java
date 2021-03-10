package net.iceyleagons.icicle.config.annotations;

import net.iceyleagons.icicle.config.Config;
import net.iceyleagons.icicle.injection.AbstractInjectionHandler;
import net.iceyleagons.icicle.injection.annotations.InjectionHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@InjectionHandler(ConfigField.class)
public class ConfigFieldInjector extends AbstractInjectionHandler {

    private final Map<String, Configuration> configurationMap = new HashMap<>();

    @Override
    public void inject(Object o) {
        Arrays.stream(o.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ConfigField.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    ConfigField annotation = field.getAnnotation(ConfigField.class);

                    String pathToValue = annotation.value();
                    String[] data = pathToValue.split("\\$");

                    String name = data[0];
                    String key = data[1];

                    Class<?> wantedType = field.getClass();

                    YamlConfiguration config = getConfig(name);
                    if (config == null) throw new IllegalStateException("Invalid config with name: " + name);

                    Object value = config.get(key);
                    if (wantedType.isInstance(value)) {
                        try {
                            field.set(o, wantedType.cast(value));
                        } catch (Exception e) {
                            //how the fuck did we get here if there's a fucking if statement above this
                            e.printStackTrace();
                        }
                    } else {
                        throw new IllegalStateException("Required field type does not match type in config specified by key: " + key + " in config: " + name);
                    }
                });
    }

    private YamlConfiguration getConfig(String name) {
        return getConfiguration(name).getYamlConfiguration();
    }

    public Configuration getConfiguration(String fileName) {
        if (!configurationMap.containsKey(fileName)) {
            configurationMap.put(fileName, new Configuration(getJavaPlugin(), fileName));
        }
        return configurationMap.get(fileName);
    }

    public Configuration getConfiguration(File folder, String fileName) {
        if (!configurationMap.containsKey(fileName)) {
            configurationMap.put(fileName, new Configuration(getJavaPlugin(), folder, fileName));
        }
        return configurationMap.get(fileName);
    }

    private static class Configuration extends Config {
        public Configuration(JavaPlugin javaPlugin, File file) {
            super(javaPlugin, file);
        }

        public Configuration(JavaPlugin javaPlugin, String fileName) {
            super(javaPlugin, fileName);
        }

        public Configuration(JavaPlugin javaPlugin, File folder, String fileName) {
            super(javaPlugin, folder, fileName);
        }
    }
}
