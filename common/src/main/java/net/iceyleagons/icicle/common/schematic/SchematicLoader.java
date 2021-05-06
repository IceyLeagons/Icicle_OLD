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

package net.iceyleagons.icicle.common.schematic;


import net.iceyleagons.icicle.common.jnbt.ByteArrayTag;
import net.iceyleagons.icicle.common.jnbt.CompoundTag;
import net.iceyleagons.icicle.common.jnbt.NBTInputStream;
import net.iceyleagons.icicle.common.jnbt.ShortTag;
import net.iceyleagons.icicle.common.misc.NBTUtils;
import net.iceyleagons.icicle.common.web.WebUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Standalone SchematicLoader
 * We use WorldEdit's legacy.json to convert old IDs to the newer BlockState system, that JSON file is the property of WorldEdit!
 * (https://raw.githubusercontent.com/EngineHub/WorldEdit/master/worldedit-core/src/main/resources/com/sk89q/worldedit/world/registry/legacy.json)
 * <p>
 * Built with JNBT
 *
 * @author TOTHTOMI
 * @version 1.1.0
 * @since 1.3.5-SNAPSHOT
 */
public class SchematicLoader {

    public static HashMap<String, String> ids = new HashMap<>();

    static {
        try {
            String resp = WebUtils.readURL(new URL("https://raw.githubusercontent.com/EngineHub/WorldEdit/master/worldedit-core/src/main/resources/com/sk89q/worldedit/world/registry/legacy.json"));
            assert resp != null;
            JSONObject legacy = new JSONObject(resp).getJSONObject("blocks");

            Iterator<String> keyIterator = legacy.keys();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                ids.put(key, legacy.getString(key));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads in the {@link Schematic} from the given file.
     *
     * @param file the {@link File}
     * @return the Future representing the getting.
     */
    public static CompletableFuture<Schematic> loadSchematic(File file) {
        return CompletableFuture.supplyAsync(() -> {
            if (!file.exists()) throw new IllegalArgumentException("Schematic file does not exist!");


            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                try (NBTInputStream nbtInputStream = new NBTInputStream(fileInputStream)) {
                    CompoundTag compoundTag = (CompoundTag) nbtInputStream.readTag();

                    short width = Objects.requireNonNull(NBTUtils.getChildTag(compoundTag.getValue(), "Width", ShortTag.class)).getValue();
                    short height = Objects.requireNonNull(NBTUtils.getChildTag(compoundTag.getValue(), "Height", ShortTag.class)).getValue();
                    short length = Objects.requireNonNull(NBTUtils.getChildTag(compoundTag.getValue(), "Length", ShortTag.class)).getValue();

                    //Map<String, Tag> blockIDs = Objects.requireNonNull(getChildTag(compoundTag.getValue(), "BlockIDs", CompoundTag.class)).getValue();

                    short[] blocks = handleBlocks(compoundTag);
                    byte[] data = Objects.requireNonNull(NBTUtils.getChildTag(compoundTag.getValue(), "Data", ByteArrayTag.class)).getValue();

                    return new Schematic(blocks, data, width, height, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * Will handle the Blocks and AddBlocks merging.
     *
     * @param nbtTagCompound the {@link CompoundTag}
     * @return the resulting short[]
     */
    private static short[] handleBlocks(CompoundTag nbtTagCompound) {
        byte[] blocks = Objects.requireNonNull(NBTUtils.getChildTag(nbtTagCompound.getValue(), "Blocks", ByteArrayTag.class)).getValue();//nbtTagCompound.getByteArray("Blocks");
        ByteArrayTag byteArrayTag = NBTUtils.getChildTag(nbtTagCompound.getValue(), "AddBlocks", ByteArrayTag.class);
        byte[] add = byteArrayTag == null ? new byte[0] : byteArrayTag.getValue();

        short[] shortBlocks = new short[blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            if ((i >> 1) >= add.length) {
                shortBlocks[i] = (short) (blocks[i] & 0xFF);
            } else {
                if ((i & 1) == 0) {
                    shortBlocks[i] = (short) (((add[i >> 1] & 0x0F) << 8) + (blocks[i] & 0xFF));
                } else {
                    shortBlocks[i] = (short) (((add[i >> 1] & 0xF0) << 4) + (blocks[i] & 0xFF));
                }
            }
        }

        return shortBlocks;
    }


}
