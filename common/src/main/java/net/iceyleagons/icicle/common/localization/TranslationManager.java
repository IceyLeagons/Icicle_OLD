package net.iceyleagons.icicle.common.localization;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import lombok.Setter;
import net.iceyleagons.icicle.common.annotations.Service;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Service
public class TranslationManager {

    private final Cache<File, TranslationFile> translationFileCache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    private PerPlayerTranslationProvider perPlayerTranslationProvider = null;
    private TranslationFileProvider translationFileProvider;

    private String defaultLanguageCode = "en";

    public TranslationFile load(File file) {
        TranslationFile translationFile = translationFileCache.getIfPresent(file);
        if (translationFile == null) translationFile = translationFileProvider.readFile(file);

        return translationFile;
    }

    public Translation get(String translationKey) {
        return null;
    }
}
