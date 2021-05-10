package net.iceyleagons.icicle.common.localization;

import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public interface PerPlayerTranslationProvider {

    File getFileForPlayer(Player player);
    File getFileForPlayer(UUID player);

}
