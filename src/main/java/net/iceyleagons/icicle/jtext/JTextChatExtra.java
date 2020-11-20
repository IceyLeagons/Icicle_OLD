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
