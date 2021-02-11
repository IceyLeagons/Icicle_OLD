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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.jnbt.CompoundTag;
import net.iceyleagons.icicle.jnbt.Tag;
import net.iceyleagons.icicle.serialization.types.BlockFaceSerializer;
import net.iceyleagons.icicle.serialization.types.IntSerializer;
import net.iceyleagons.icicle.serialization.types.InventorySerializer;
import net.iceyleagons.icicle.serialization.types.LocationSerializer;
import net.iceyleagons.icicle.serialization.types.PlayerSerializer;
import net.iceyleagons.icicle.serialization.types.StringSerializer;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

/**
 * @author TOTHTOMI
 */
@RequiredArgsConstructor
@Getter
public enum NBTType {

    STRING(new StringSerializer(), String.class, String.class),
    INTEGER(new IntSerializer(), Integer.class, Integer.class),
    BLOCK_FACE(new BlockFaceSerializer(), BlockFace.class, BlockFace.class),
    INVENTORY(new InventorySerializer(), Inventory.class, ItemStack[].class),
    LOCATION(new LocationSerializer(), Location.class, Location.class),
    PLAYER(new PlayerSerializer(), Player.class, OfflinePlayer.class)
    ;

    private final Serializer<? extends Tag,?> serializer;
    private final Class<?> requiredType;
    private final Class<?> returnType;

    public static Tag serialize(NBTType nbtType, Field field, Object object, String name) throws IllegalArgumentException{
        if (!nbtType.getRequiredType().equals(field.getType())) throw new IllegalArgumentException("Field is not type of requiredType!");
        return nbtType.getSerializer().serialize(field, object, name);
    }

    public static <T> T deserialize(CompoundTag tag, String name, NBTType nbtType, Field field, Class<T> wantedType) throws IllegalArgumentException, ClassCastException {
        if (!nbtType.getReturnType().isAssignableFrom(field.getType())) throw new IllegalArgumentException("Field is not type of returnType!");
        if (!nbtType.getReturnType().isAssignableFrom(wantedType)) throw new IllegalArgumentException("WantedType is not type of returnType!");

        Object object = nbtType.getSerializer().deserialize(tag, name);
        return wantedType.cast(object);
    }
}
