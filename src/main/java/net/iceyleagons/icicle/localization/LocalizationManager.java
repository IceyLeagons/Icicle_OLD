package net.iceyleagons.icicle.localization;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Map;

public class LocalizationManager {

    public String getTranslation(String id, Player player, Map<String, String> attributes) {
        return "";
    }

    public String getTranslation(String id, CommandSender commandSender, Map<String, String> attributes) {
        if (commandSender instanceof Player) return getTranslation(id, (Player) commandSender, attributes);

        return "";
    }

    public String getTranslation(String id, Player player) {
        return getTranslation(id, player, Collections.emptyMap());
    }

    public String getTranslation(String id, CommandSender sender) {
        return getTranslation(id, sender, Collections.emptyMap());
    }

}
