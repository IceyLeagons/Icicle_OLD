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

import net.iceyleagons.icicle.storage.entities.Container;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.0-SNAPSHOT
 */
public class StorageHandler {

    private Storage storage;
    private boolean active = false;

    /**
     * Migrates a {@link Storage} to an other
     *
     * @param from
     * @param to
     */
    public void migrate(Storage from, Storage to) {
        from.getContainers().forEach(container -> {
            container.reloadFromStorage();
            Container clone = container.clone();
            to.addContainer(clone);
            clone.pushToQueue();
        });
        to.applyChanges();
    }

    /**
     * @return the currently active storage or null
     */
    public Storage getActiveStorage() {
        return active ? storage : null;
    }

    /**
     * Sets the active storage.
     *
     * @param storageToActivate the {@link Storage}
     */
    public void setActiveStorage(Storage storageToActivate) throws StorageException {
        if (storage == null) {
            storage = storageToActivate;
            active = storage.init();
            if (!active)
                storage.getLogger().warning("[Storage] Cannot active the selected Storage. Do the driver exists for it?");
        }
    }
}
