package net.iceyleagons.icicle.jtext;

import lombok.NonNull;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class JTextChatMessage {

    private final JSONObject jsonObject;

	public JTextChatMessage(@NonNull String text, JTextChatColor color, List<JTextChatFormat> formats) {
        jsonObject = new JSONObject();
        jsonObject.put("text", text);

        if (color != null) jsonObject.put("color", color.getColor());
        if (formats != null) formats.forEach(format -> jsonObject.put(format.getFormat(),true));

    }

    public JTextChatMessage(@NonNull String text) {
        this(text,null,null);
    }

    public JTextChatMessage(@NonNull String text, JTextChatColor color) {
        this(text,color,null);
    }

    public void addExtra(JTextChatExtra extraObject) {
        if (jsonObject.get("extra") == null) jsonObject.put("extra", new JSONArray());

        JSONArray extra = (JSONArray) jsonObject.get("extra");
        extra.put(extraObject.toJSON());
        jsonObject.put("extra", extra);
    }

    public void sendToPlayer(Player player) {
        player.spigot().sendMessage(ComponentSerializer.parse(jsonObject.toString()));
    }

    public void broadcast() {
        Bukkit.getServer().spigot().broadcast(ComponentSerializer.parse(jsonObject.toString()));
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }
}
