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

package net.iceyleagons.icicle.wrapping.mojang;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Wrapped representation of GameProfile
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedGameProfile {

    public static final Class<?> mojang_gameProfile;
    private static final Constructor<?> constructor;
    private static final Method getId;
    private static final Method getName;
    private static final Method getProperties;

    static {
        mojang_gameProfile = Reflections.getNormalClass("com.mojang.authlib.GameProfile");
        assert mojang_gameProfile != null;
        constructor = Reflections.getConstructor(mojang_gameProfile, true, UUID.class, String.class);
        getId = Reflections.getMethod(mojang_gameProfile, "getId", true);
        getName = Reflections.getMethod(mojang_gameProfile, "getName", true);
        getProperties = Reflections.getMethod(mojang_gameProfile, "getProperties", true);
    }

    @Getter
    private final Object nmsObject;

    @SneakyThrows
    public WrappedGameProfile(UUID uuid, String name) {
        this.nmsObject = constructor.newInstance(uuid, name);
    }

    public WrappedGameProfile(Object root) {
        this.nmsObject = root;
    }

    /**
     * @return the id of this profile.
     */
    public UUID getId() {
        return Reflections.invoke(getId, UUID.class, nmsObject);
    }

    /**
     * @return the properties of this profile. Containing stuff like skin.
     */
    public WrappedPropertyMap getProperties() {
        Object obj = Reflections.invoke(getProperties, Object.class, nmsObject);
        return new WrappedPropertyMap(obj);
    }

    /**
     * @return the name of this profile.
     */
    public String getName() {
        return Reflections.invoke(getName, String.class, nmsObject);
    }
}
