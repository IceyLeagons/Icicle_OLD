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

package net.iceyleagons.icicle.serialization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import net.iceyleagons.icicle.jnbt.CompoundTag;
import net.iceyleagons.icicle.jnbt.NBTInputStream;
import net.iceyleagons.icicle.jnbt.NBTOutputStream;
import net.iceyleagons.icicle.jnbt.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TOTHTOMI
 */
public class SerializationHandler {

    public static void serialize(@NonNull Object object, @NonNull File file) throws IOException {
        Map<String, Tag> tags = new HashMap<>();
        List<SerializationData> data = getSerializationFields(object.getClass());

        data.forEach(serData ->
                tags.put(serData.getName(), serData.getNbtType()
                        .getSerializer().serialize(serData.getField(), object, serData.getName())));


        CompoundTag compoundTag = new CompoundTag("data", tags);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try (NBTOutputStream nbtOutputStream = new NBTOutputStream(fileOutputStream)) {
                nbtOutputStream.writeTag(compoundTag);
            }
        }
    }

    /**
     *
     * @param object the object to load the fields into, fields must not be final!
     * @param file the file to load from
     */
    public static void deserialize(@NonNull Object object, @NonNull File file) throws IOException {
        if (!file.exists()) return;

        List<SerializationData> data = getSerializationFields(object.getClass());

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (NBTInputStream nbtInputStream = new NBTInputStream(fileInputStream)) {
                Tag tag = nbtInputStream.readTag();
                if (!tag.getName().equalsIgnoreCase("data")) return;

                CompoundTag compoundTag = (CompoundTag) tag;
                data.forEach(serializationData -> {
                    serializationData.getNbtType().getSerializer().inject(compoundTag,
                            serializationData.getName(), serializationData.getField(), object);
                });
            }
        }
    }

    private static List<SerializationData> getSerializationFields(Class<?> clazz) {
        List<SerializationData> data = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(NBTSerialize.class)) {
                NBTSerialize nbtSerialize = field.getAnnotation(NBTSerialize.class);
                String name = nbtSerialize.name();
                NBTType type = nbtSerialize.type();
                data.add(new SerializationData(name, type, field));
            }
        }

        return data;
    }

    @Getter
    @AllArgsConstructor
    private static class SerializationData {
        private final String name;
        private final NBTType nbtType;
        private final Field field;
    }
}
