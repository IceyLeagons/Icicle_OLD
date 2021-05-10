package net.iceyleagons.icicle.common.localization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Translation {

    @Getter
    private final String key;
    private final String translationRaw;
    private final Map<String, String> parameters = new HashMap<>();

    private boolean parseColors;

    public Translation supplyParameter(String parameterName, String value) {
        parameters.put(parameterName, value);
        return this;
    }

    public String get() {
        return parseColors(parseStringCode());
    }

    //default: true
    public Translation setParseColors(boolean value) {
        this.parseColors = value;
        return this;
    }

    private String parseColors(String input) {
        return parseColors ? ChatColor.translateAlternateColorCodes('&', input) : input;
    }

    private String parseStringCode() {
        return translationRaw; //TODO
    }

}
