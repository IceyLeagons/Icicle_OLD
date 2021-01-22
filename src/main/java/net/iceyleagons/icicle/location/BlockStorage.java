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

package net.iceyleagons.icicle.location;

import net.iceyleagons.icicle.file.FileZipper;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BlockStorage {
    protected final HashMap<Location, Map<String, Object>> metadatas;
    private final World world;

    public BlockStorage(World world) {
        this.world = world;
        this.metadatas = new HashMap<>();
    }

    public BlockStorage(World world, Map<Map<String, Object>, Map<String, Object>> values) {
        this.world = world;

        HashMap<Location, Map<String, Object>> map = new HashMap<>();
        for (Entry<Map<String, Object>, Map<String, Object>> entr : values.entrySet())
            map.put(Location.deserialize(entr.getKey()), entr.getValue());

        this.metadatas = map;
    }

    public static BlockStorage load(World world) {
        File original = new File(world.getWorldFolder(), "block.map");
        File temp = new File(world.getWorldFolder(), "block.tmp.map");

        try {
            File file;
            if (FileZipper.isZipped(original)) {
                FileZipper.decompress(original, temp);
                file = temp;
            } else {
                file = original;
            }

            try (ObjectInputStream objectOutputStream = new ObjectInputStream(new FileInputStream(file))) {
                Object object = objectOutputStream.readObject();

                if (!temp.delete())
                    throw new IllegalStateException("Could not delete temporary block storage map file!");
                return new BlockStorage(world, (Map<Map<String, Object>, Map<String, Object>>) object);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void handleEvent(BlockBreakEvent event) {
        metadatas.remove(event.getBlock().getLocation());
    }

    public Object getData(Location location, String key) {
        createIfNotExisting(location);
        return metadatas.get(location).get(key);
    }

    public Map<String, Object> getMetadatas(Location location) {
        return metadatas.get(location);
    }

    public void removeData(Location location, String key) {
        createIfNotExisting(location);
        metadatas.get(location).remove(key);
    }

    public void setData(Location location, String key, Object data) {
        createIfNotExisting(location);
        metadatas.get(location).put(key, data);
    }

    public boolean hasData(Location location, String key) {
        createIfNotExisting(location);
        return metadatas.get(location).containsKey(key);
    }

    private void createIfNotExisting(Location location) {
        if (!metadatas.containsKey(location))
            metadatas.put(location, new HashMap<>());
    }

    private Map<Map<String, Object>, Map<String, Object>> serialize() {
        Map<Map<String, Object>, Map<String, Object>> result = new HashMap<>();
        for (Entry<Location, Map<String, Object>> entry : metadatas.entrySet())
            result.put(entry.getKey().serialize(), entry.getValue());

        return result;
    }

    public List<Location> getBlockLocationsForKey(String key) {
        List<Location> list = new ArrayList<>();
        for (Entry<Location, Map<String, Object>> entr : metadatas.entrySet())
            if (entr.getValue().containsKey(key))
                list.add(entr.getKey());

        return list;
    }

    public void save() {
        File tmp = new File(world.getWorldFolder(), "block.tmp.map");
        File file = new File(world.getWorldFolder(), "block.map");

        try {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tmp))) {
                objectOutputStream.writeObject(serialize());
            }

            FileZipper.compress(tmp, file);
            if (!tmp.delete()) throw new IllegalStateException("Could not delete temporary block storage map file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
