package net.iceyleagons.icicle.jtext;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;

public class JTextChatExtra {

    private final JSONObject extra;

    public JTextChatExtra(String text, JTextChatColor color, List<JTextChatFormat> formats) {
        extra = new JSONObject();
        extra.put("text", text);
        extra.put("color", color.getColor());
        formats.forEach(f -> extra.put(f.getFormat(),true));
    }

    public void setClickEvent(Player player, Consumer<Player> playerConsumer) {
        String code = RandomStringUtils.randomAlphabetic(10);
        setRunCommand(JText.COMMAND_FORMAT.replace("<code>",code));
        JText.clickConsumers.put(code,playerConsumer);
    }

    public void setRunCommand(String command) {
        JSONObject clickEvent = new JSONObject();
        clickEvent.put("action", JTextChatClickEventType.RUN_COMMAND.getType());
        clickEvent.put("value", command);
        extra.put("clickEvent", clickEvent);
    }

    public void setHoverEvent(JTextChatHoverEventType action, String value) {
        JSONObject hoverEvent = new JSONObject();
        hoverEvent.put("action", action.getType());
        hoverEvent.put("value", value);
        extra.put("hoverEvent", hoverEvent);
    }

    public JSONObject toJSON() {
        return extra;
    }
}
