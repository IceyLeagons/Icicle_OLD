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

import net.iceyleagons.icicle.item.ItemUtils;
import net.iceyleagons.icicle.jnbt.ByteArrayTag;
import net.iceyleagons.icicle.jnbt.CompoundTag;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.serialization.NBTType;
import net.iceyleagons.icicle.serialization.Serializer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

/**
 * @author TOTHTOMI
 */
public class InventorySerializer implements Serializer<ByteArrayTag, ItemStack[]> {

    @Override
    public ByteArrayTag serialize(Field field, Object object, String name) {
        Inventory inventory = Reflections.get(field, Inventory.class, object);
        return new ByteArrayTag(name, inventory == null ? null : ItemUtils.toByteArray(inventory.getContents()));
    }

    @Override
    public ItemStack[] deserialize(CompoundTag compoundTag, String name) {
        ByteArrayTag byteArrayTag = getChildTag(compoundTag, name, ByteArrayTag.class);
        return byteArrayTag == null ? null : ItemUtils.fromByteArray(byteArrayTag.getValue());
    }

    @Override
    public void inject(CompoundTag compoundTag, String name, Field field, Object o) {
        try {
            ItemStack[] contents = NBTType.deserialize(compoundTag, name, NBTType.INVENTORY, field, ItemStack[].class);
            Inventory inventory = Reflections.get(field, Inventory.class, o);
            if (inventory != null) {
                inventory.setContents(contents);
            }
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

}
