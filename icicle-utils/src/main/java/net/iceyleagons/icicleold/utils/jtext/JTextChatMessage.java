/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicleold.utils.jtext;

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
        if (formats != null) formats.forEach(format -> jsonObject.put(format.getFormat(), true));

    }

    public JTextChatMessage(@NonNull String text) {
        this(text, null, null);
    }

    public JTextChatMessage(@NonNull String text, JTextChatColor color) {
        this(text, color, null);
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
