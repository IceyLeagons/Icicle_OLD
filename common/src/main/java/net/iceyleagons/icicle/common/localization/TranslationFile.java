package net.iceyleagons.icicle.common.localization;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class TranslationFile {

    @Getter
    private final Map<String, Translation> translations = new HashMap<>();

    public void addTranslation(Translation translation) {
        translations.put(translation.getKey(), translation);
    }

    public Translation getTranslation(String key) {
        return translations.get(key);
    }
}
