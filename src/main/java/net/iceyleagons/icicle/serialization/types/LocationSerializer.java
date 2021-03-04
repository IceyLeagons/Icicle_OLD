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

package net.iceyleagons.icicle.serialization.types;

import net.iceyleagons.icicle.jnbt.CompoundTag;
import net.iceyleagons.icicle.jnbt.StringTag;
import net.iceyleagons.icicle.location.LocationUtils;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.serialization.NBTType;
import net.iceyleagons.icicle.serialization.Serializer;
import org.bukkit.Location;

import java.lang.reflect.Field;

/**
 * @author TOTHTOMI
 */
public class LocationSerializer implements Serializer<StringTag, Location> {

    @Override
    public StringTag serialize(Field field, Object object, String name) {
        Location location = Reflections.get(field, Location.class, object);
        return new StringTag(name, location == null ? null : LocationUtils.serializeLocation(location, ';'));
    }

    @Override
    public Location deserialize(CompoundTag compoundTag, String name) {

        StringTag stringTag = getChildTag(compoundTag, name, StringTag.class);
        return stringTag == null ? null : LocationUtils.deserializeLocation(stringTag.getValue(), ";");
    }

    @Override
    public void inject(CompoundTag compoundTag, String name, Field field, Object o) {
        try {
            Location location = NBTType.LOCATION.deserialize(compoundTag, name, field, Location.class);
            Reflections.set(field, o, location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
