/*
 * MIT License
 *
 * Copyright (c) 2021 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle.commands.system.arguments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.BooleanUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author TOTHTOMI
 */
@Getter
@AllArgsConstructor
public enum Arguments {

    STRING, INTEGER, ONLINE_PLAYER, BOOLEAN;

    private Object getReturn(Arguments arguments, String fromCommand) throws InvalidArgumentException {
        switch (arguments) {
            default:
                return null;
            case STRING:
                return fromCommand;
            case INTEGER:
                if (!isNumber(fromCommand)) throw new InvalidArgumentException("Passed value is not a valid number!");
                return Integer.parseInt(fromCommand);
            case ONLINE_PLAYER:
                Player p = getOnlinePlayer(fromCommand);
                if (p == null) throw new InvalidArgumentException("Player not found!");
                break;
            case BOOLEAN:
                Boolean bool = isBoolean(fromCommand);
                if (bool == null) throw new InvalidArgumentException("Passed value is not a valid boolean!");
                return bool;
        }
        return null;
    }

    private Player getOnlinePlayer(String fromCommand) {
        return Bukkit.getPlayer(fromCommand);
    }

    private Boolean isBoolean(String input) {
        return BooleanUtils.toBooleanObject(input);
    }

    private boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
