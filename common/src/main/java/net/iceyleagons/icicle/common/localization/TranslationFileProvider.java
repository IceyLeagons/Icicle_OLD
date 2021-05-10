package net.iceyleagons.icicle.common.localization;

import java.io.File;

public interface TranslationFileProvider {

    TranslationFile readFile(File file);

}
