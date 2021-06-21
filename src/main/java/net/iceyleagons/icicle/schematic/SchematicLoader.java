package net.iceyleagons.icicle.schematic;

import net.iceyleagons.icicle.other.NBTUtils;
import net.iceyleagons.icicle.thirdparty.jnbt.ByteArrayTag;
import net.iceyleagons.icicle.thirdparty.jnbt.CompoundTag;
import net.iceyleagons.icicle.thirdparty.jnbt.NBTInputStream;
import net.iceyleagons.icicle.thirdparty.jnbt.ShortTag;
import net.iceyleagons.icicle.utils.Asserts;
import net.iceyleagons.icicle.web.WebUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
        String resp = WebUtils.readURL("https://raw.githubusercontent.com/EngineHub/WorldEdit/master/worldedit-core/src/main/resources/com/sk89q/worldedit/world/registry/legacy.json");
        assert resp != null;
        JSONObject legacy = new JSONObject(resp).getJSONObject("blocks");

        Iterator<String> keyIterator = legacy.keys();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            ids.put(key, legacy.getString(key));
        }
    }

    /**
     * Loads in the {@link Schematic} from the given file.
     *
     * @param file the {@link File}
     * @return the Future representing the getting.
     */
    public static CompletableFuture<Schematic> loadSchematic(@NotNull File file) {
        Asserts.notNull(file, "File must not be null!");

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
