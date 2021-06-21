package net.iceyleagons.icicle.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.config.ConfigField;
import net.iceyleagons.icicle.beans.AutowiringUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConfiguration {

    @Setter
    private String pathToFile;

    @Getter
    @Setter
    private Object origin;

    @Getter
    @Setter
    private String header = null;

    @Setter
    private RegisteredIciclePlugin plugin;

    private File file;
    private YamlConfiguration yamlConfiguration;

    public void init() {
        this.file = new File(plugin.getJavaPlugin().getDataFolder(), pathToFile);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IllegalStateException("Could not create config file!");
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
            if (field.isAnnotationPresent(ConfigField.class)) {
                ConfigField configPath = field.getAnnotation(ConfigField.class);

                try {
                    values.put(configPath.value(), AutowiringUtils.getValue(field, origin, Object.class));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
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
            if (field.isAnnotationPresent(ConfigField.class)) {
                ConfigField configPath = field.getAnnotation(ConfigField.class);
                values.put(configPath.value(), field);
            }
        }

        values.forEach((path, field) -> {
            if (yamlConfiguration.get(path) != null) {
                try {
                    AutowiringUtils.injectField(field, origin, yamlConfiguration.get(path));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
