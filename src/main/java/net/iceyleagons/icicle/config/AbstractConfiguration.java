package net.iceyleagons.icicle.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
<<<<<<< HEAD:core/src/main/java/net/iceyleagons/icicle/config/AbstractConfiguration.java
import net.iceyleagons.icicle.api.annotations.config.ConfigPath;
import net.iceyleagons.icicle.api.plugin.RegisteredPlugin;
=======
import net.iceyleagons.icicle.annotations.config.ConfigPath;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/config/AbstractConfiguration.java
import net.iceyleagons.icicle.reflect.Reflections;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class AbstractConfiguration {

    private final Logger logger = Logger.getLogger(AbstractConfiguration.class.getName());

    @Setter
    private String pathToFile;

    @Getter
    @Setter
    private Object origin;

    @Getter
    @Setter
    private String header = null;

    @Setter
    private RegisteredPlugin registeredPlugin;

    private File file;
    private YamlConfiguration yamlConfiguration;

    public void init() {
        this.file = new File(registeredPlugin.getJavaPlugin().getDataFolder(), pathToFile);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    logger.warning("Cannot create config file.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        loadDefaultValues();
    }

    private Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();

        for (Field field : origin.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigPath.class)) {
                ConfigPath configPath = field.getAnnotation(ConfigPath.class);
                values.put(configPath.value(), Reflections.get(field, Object.class, origin));
            }
        }
        return values;
    }

    @SneakyThrows
    public void loadDefaultValues() {
        Map<String, Object> values = getValues();

        if (header != null) yamlConfiguration.options().header(header);

        values.forEach((path, value) -> {
            if (!yamlConfiguration.contains(path)) {
                yamlConfiguration.set(path, value);
            }
        });

        yamlConfiguration.save(file);
        reloadFromConfig();
    }

    @SneakyThrows
    public void saveToConfig() {
        Map<String, Object> values = getValues();

        values.forEach((path, value) -> yamlConfiguration.set(path, value));
        yamlConfiguration.save(file);
    }

    public void reloadFromConfig() {
        Map<String, Field> values = new HashMap<>();

        for (Field field : origin.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigPath.class)) {
                ConfigPath configPath = field.getAnnotation(ConfigPath.class);
                values.put(configPath.value(), field);
            }
        }

        values.forEach((path, field) -> {
            if (yamlConfiguration.get(path) != null) {
                Reflections.set(field, origin, yamlConfiguration.get(path));
                //yamlConfiguration.set(path, value);
            }
        });
    }
}
