package net.iceyleagons.icicle.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.config.ConfigComment;
import net.iceyleagons.icicle.annotations.config.ConfigField;
import net.iceyleagons.icicle.beans.AutowiringUtils;
import org.simpleyaml.configuration.file.YamlConfiguration;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractConfiguration {

    @Setter
    private String pathToFile;

    @Setter
    private Object origin;

    @Setter
    private Class<?> originType;

    @Setter
    private String header = null;

    @Setter
    private RegisteredIciclePlugin plugin;

    private YamlFile file;

    public void init() {
        if (!plugin.getJavaPlugin().getDataFolder().exists()) {
            if (!plugin.getJavaPlugin().getDataFolder().mkdirs())
                throw new IllegalStateException("Could not create plugin data folder");
        }

        this.file = new YamlFile(new File(plugin.getJavaPlugin().getDataFolder(), pathToFile));
        if (!file.exists()) {
            try {
                file.createNewFile(true);
                this.file.loadWithComments();
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

        loadDefaultValues();
    }

    private List<Field> getFields() {
        return Arrays.stream(originType.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(ConfigField.class)).collect(Collectors.toList());
    }

    private Map<String, Object> getValues(List<Field> fields) {
        Map<String, Object> values = new HashMap<>();

        for (Field field : fields) {
            ConfigField configPath = field.getAnnotation(ConfigField.class);
            try {
                values.put(configPath.value(), AutowiringUtils.getValue(field, origin, Object.class));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    @SneakyThrows
    private void loadDefaultValues() {
        List<Field> fields = getFields();
        Map<String, Object> values = getValues(fields);

        if (header != null) file.options().header(header);

        values.forEach((path, value) -> {
            if (!file.contains(path)) {
                System.out.println("Setting \"" + path + "\" to " + value);
                file.set(path, value);
            }
        });

        fields.stream().filter(f -> f.isAnnotationPresent(ConfigComment.class)).forEach(f -> {
            String path = f.getAnnotation(ConfigField.class).value();
            ConfigComment comment = f.getAnnotation(ConfigComment.class);

            file.setComment(path, comment.value(), comment.type());
        });

        file.save();
        reloadFromConfig();
    }

    @SneakyThrows
    public void saveToConfig() {
        List<Field> fields = getFields();
        Map<String, Object> values = getValues(fields);

        values.forEach((path, value) -> file.set(path, value));
        file.save();
    }

    @SneakyThrows
    public void reloadFromConfig() {
        this.file.loadWithComments();
        Map<String, Field> values = new HashMap<>();

        for (Field field : getFields()) {
            ConfigField configPath = field.getAnnotation(ConfigField.class);
            values.put(configPath.value(), field);
        }

        values.forEach((path, field) -> {
            if (file.get(path) != null) {
                try {
                    AutowiringUtils.injectField(field, origin, file.get(path));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
