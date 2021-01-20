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

package net.iceyleagons.icicle.wrapped;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Method;

/**
 * @author TOTHTOMI
 */
public class WrappedEnumGamemode {

    public static final Class<? extends Enum<?>> mc_wrappedEnumGamemode;
    private static Method mc_wrappedEnumGamemode_valueOf;

    static {
        mc_wrappedEnumGamemode = (Class<? extends Enum<?>>) Reflections.getNormalNMSClass("EnumGamemode");
        mc_wrappedEnumGamemode_valueOf = Reflections.getMethod(mc_wrappedEnumGamemode, "valueOf", true, String.class);
    }

    public static Object from(GamemodeType gamemodeType) {
        return gamemodeType.getNmsObject();
    }

    /**
     * We need this enum separately because of illegal forward reference.
     */
    @RequiredArgsConstructor
    @Getter
    public enum GamemodeType {
        NOT_SET(Reflections.invoke(mc_wrappedEnumGamemode_valueOf, Object.class, null, "NOT_SET")),
        SURVIVAL(Reflections.invoke(mc_wrappedEnumGamemode_valueOf, Object.class, null, "SURVIVAL")),
        CREATIVE(Reflections.invoke(mc_wrappedEnumGamemode_valueOf, Object.class, null, "CREATIVE")),
        ADVENTURE(Reflections.invoke(mc_wrappedEnumGamemode_valueOf, Object.class, null, "ADVENTURE")),
        SPECTATOR(Reflections.invoke(mc_wrappedEnumGamemode_valueOf, Object.class, null, "SPECTATOR"));

        private final Object nmsObject;
    }

}
