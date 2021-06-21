package net.iceyleagons.icicle.thirdparty.jnbt;

//@formatter:off

/*
 * JNBT License
 *
 * Copyright (c) 2010 Graham Edgecombe
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of the JNBT team nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

//@formatter:on

/**
 * A class which contains NBT-related utility methods.
 *
 * @author Graham Edgecombe
 */
public final class NBTUtils {

    /**
     * Default private constructor.
     */
    private NBTUtils() {

    }

    /**
     * Gets the type name of a tag.
     *
     * @param clazz The tag class.
     * @return The type name.
     */
    public static String getTypeName(final Class<? extends Tag> clazz) {

        if (clazz.equals(ByteArrayTag.class)) {
            return "TAG_Byte_Array";
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.ByteTag.class)) {
            return "TAG_Byte";
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.CompoundTag.class)) {
            return "TAG_Compound";
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.DoubleTag.class)) {
            return "TAG_Double";
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.EndTag.class)) {
            return "TAG_End";
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.FloatTag.class)) {
            return "TAG_Float";
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.IntArrayTag.class)) {
            return "TAG_Int_Array";
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.IntTag.class)) {
            return "TAG_Int";
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.ListTag.class)) {
            return "TAG_List";
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.LongTag.class)) {
            return "TAG_Long";
        } else if (clazz.equals(ShortTag.class)) {
            return "TAG_Short";
        } else if (clazz.equals(StringTag.class)) {
            return "TAG_String";
        } else {
            throw new IllegalArgumentException("[JNBT] Invalid tag classs ("
                    + clazz.getName() + ").");
        }
    }

    /**
     * Gets the type code of a tag class.
     *
     * @param clazz The tag class.
     * @return The type code.
     * @throws IllegalArgumentException if the tag class is invalid.
     */
    public static int getTypeCode(final Class<? extends Tag> clazz) {

        if (clazz.equals(ByteArrayTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_BYTE_ARRAY;
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.ByteTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_BYTE;
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.CompoundTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_COMPOUND;
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.DoubleTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_DOUBLE;
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.EndTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_END;
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.FloatTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_FLOAT;
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.IntArrayTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_INT_ARRAY;
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.IntTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_INT;
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.ListTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_LIST;
        } else if (clazz.equals(net.iceyleagons.icicle.thirdparty.jnbt.LongTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_LONG;
        } else if (clazz.equals(ShortTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_SHORT;
        } else if (clazz.equals(StringTag.class)) {
            return net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_STRING;
        } else {
            throw new IllegalArgumentException("[JNBT] Invalid tag classs ("
                    + clazz.getName() + ").");
        }
    }

    /**
     * Gets the class of a type of tag.
     *
     * @param type The type.
     * @return The class.
     * @throws IllegalArgumentException if the tag type is invalid.
     */
    public static Class<? extends Tag> getTypeClass(final int type) {

        switch (type) {
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_END:
                return net.iceyleagons.icicle.thirdparty.jnbt.EndTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_BYTE:
                return net.iceyleagons.icicle.thirdparty.jnbt.ByteTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_SHORT:
                return ShortTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_INT:
                return net.iceyleagons.icicle.thirdparty.jnbt.IntTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_LONG:
                return net.iceyleagons.icicle.thirdparty.jnbt.LongTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_FLOAT:
                return net.iceyleagons.icicle.thirdparty.jnbt.FloatTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_DOUBLE:
                return net.iceyleagons.icicle.thirdparty.jnbt.DoubleTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_BYTE_ARRAY:
                return ByteArrayTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_STRING:
                return StringTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_LIST:
                return net.iceyleagons.icicle.thirdparty.jnbt.ListTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_COMPOUND:
                return net.iceyleagons.icicle.thirdparty.jnbt.CompoundTag.class;
            case net.iceyleagons.icicle.thirdparty.jnbt.NBTConstants.TYPE_INT_ARRAY:
                return net.iceyleagons.icicle.thirdparty.jnbt.IntArrayTag.class;
            default:
                throw new IllegalArgumentException(
                        "[JNBT] Invalid tag type : " + type + ".");
        }
    }
}
