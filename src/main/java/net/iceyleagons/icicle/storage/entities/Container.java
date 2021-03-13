/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle.storage.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.iceyleagons.icicle.storage.Storage;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Container is what stores the data in case of SQL it's a table in mongo it's a collection etc.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.0-SNAPSHOT"
 */
@RequiredArgsConstructor
@Getter
public class Container implements Cloneable {

    private final String containerName;
    private final DataType[] dataKeyTypes;
    private final String[] dataKeyNames;
    private final List<ContainerData> queue = new ArrayList<>();
    private final List<ContainerData> data = new ArrayList<>();
    @Setter
    private Storage storage = null;

    /**
     * @param customid customId if null the ID will be generated
     * @param values   the values (must match with the size of keys and datatypes)
     * @return the generated {@link ContainerData}
     */
    public ContainerData putData(@Nullable Long customid, @NonNull Object... values) {
        for (int i = 0; i < getDataKeyTypes().length; i++) {
            Class<?> clazz = getDataKeyTypes()[i].getJavaRepresentation();
            Object value = values[i];
            if (!value.getClass().equals(clazz)) {
                throw new IllegalArgumentException("Not proper value supplied for dataType " + clazz.toGenericString() + " Got: " + value.getClass().toGenericString());
            }
        }

        if (getDataKeyTypes().length != getDataKeyNames().length || getDataKeyTypes().length != values.length) {
            throw new IllegalArgumentException("DataType, key or values array does not match in size!");
        }
        long id = (customid != null) ? customid : data.size() + 1;
        ContainerData containerData = new ContainerData(dataKeyTypes, dataKeyNames, values, id);
        data.add(containerData);

        return containerData;
    }


    /**
     * Returns a specific {@link ContainerData} with that ID
     *
     * @param id the ID
     * @return the {@link ContainerData}, can be null
     */
    public ContainerData getData(long id) {
        for (ContainerData containerData : data) {
            if (containerData.getId() == id) return containerData;
        }
        return null;
    }

    /**
     * Puts the current data list to the queue
     */
    public void pushToQueue() {
        queue.addAll(data);
    }

    /**
     * Runs {@link #pushToQueue()}
     * and applies changes with {@link Storage#applyChanges()}
     */
    public void pushToStorage() {
        pushToQueue();
        storage.applyChanges();
    }

    /**
     * Reads data from the storage
     * All current data will be deleted!
     */
    public void reloadFromStorage() {
        if (storage == null)
            throw new IllegalStateException("Container has not been added to a storage");
        data.clear();

        List<ContainerData> containerData = storage.getData(this);
        //containerData.forEach(data -> System.out.println("Fetched: "+data.getId()));
        if (!containerData.isEmpty()) {
            containerData.forEach(System.out::println);
            data.addAll(containerData);
        }
    }

    @Override
    public Container clone() {
        Container container = new Container(this.getContainerName(), this.getDataKeyTypes(), this.getDataKeyNames());
        this.getData().forEach(data -> container.putData(data.getId(), data.getValues()));
        return container;
    }

}
