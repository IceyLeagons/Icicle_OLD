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

package net.iceyleagons.icicle.storage;

import lombok.Getter;
import lombok.Setter;
import net.iceyleagons.icicle.storage.entities.Container;
import net.iceyleagons.icicle.storage.entities.ContainerData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author TOTHTOMI
 * @version 1.1.0
 * @since 1.3.0-SNAPSHOT"
 */
public abstract class Storage {

    @Getter
    protected final StorageType type;
    protected final Map<String, Container> containerMap;
    @Getter
    private final Logger logger;
    @Getter
    protected long lastUpdated;
    @Setter
    @Getter
    private boolean loggingEnabled = false;

    /**
     * @param type   the {@link StorageType}
     * @param logger the {@link Logger}
     */
    public Storage(StorageType type, Logger logger) {
        this.type = type;
        this.logger = logger;
        this.containerMap = new HashMap<>();
    }

    /**
     * Prints an error
     *
     * @param msg the message
     */
    public void error(String msg) {
        //We don't check for loggingEnabled since this is vital to know
        logger.severe(type.getPrefix() + " " + msg);
    }

    /**
     * Prints an info
     *
     * @param msg the message
     */
    public void info(String msg) {
        if (loggingEnabled) logger.info(type.getPrefix() + " " + msg);
    }

    /**
     * Prints a warning
     *
     * @param msg the message
     */
    public void warn(String msg) {
        //We don't check for loggingEnabled since this is important to know
        logger.warning(type.getPrefix() + " " + msg);
    }

    /**
     * Initializes the database
     *
     * @return true if successful
     * @throws StorageException if the expected Driver class is not found
     */
    protected abstract boolean init() throws StorageException;

    /**
     * Opens a connection to the database
     *
     * @return true if successful
     * @throws StorageException if an issue happens during opening a connection to the database
     */
    protected abstract boolean openConnection() throws StorageException;

    /**
     * Closes the connection to the database
     *
     * @return true if successful
     */
    protected abstract boolean closeConnection();

    /**
     * @return the list of {@link Container}s this {@link Storage}'s handling
     */
    public Collection<Container> getContainers() {
        return this.containerMap.values();
    }

    /**
     * Adds a new {@link Container} to this database
     *
     * @param container the {@link Container}
     */
    public void addContainer(Container container) {
        if (containerMap.containsKey(container.getContainerName()))
            throw new IllegalArgumentException("Container already registered!");

        container.setStorage(this);
        containerMap.put(container.getContainerName(), container);
    }

    /**
     * Deletes a data where they key is equals to a value in a specified container
     * {@link Container#reloadFromStorage()} should be called after!
     *
     * @param key           the key
     * @param value         the filter
     * @param containerName the containername
     */
    public abstract void deleteData(String key, Object value, String containerName);

    /**
     * @param container the container to get the data from
     * @return all data for a {@link Container}
     */
    public abstract List<ContainerData> getData(Container container);

    /**
     * Applies changes to the database, like creating tables, handling insertion etc.
     * Basically it will go through the queue and handle the changes.
     * Deletion is not handled here!
     */
    public abstract void applyChanges();


}
