package net.iceyleagons.icicle.common.storage;

import net.iceyleagons.icicle.common.storage.container.ContainerField;
import net.iceyleagons.icicle.common.storage.container.StorageContainer;

public abstract class AbstractStorageHandler {

    public abstract void init() throws Exception; //mainly for Class.forname aka. checking for driver's existence

    public abstract void cleanup();

    public abstract Object get(Object id, String containerName, ContainerField idField, ContainerField[] fields) throws Exception;

    public abstract void save(String containerName, ContainerField idField, Object toSave, ContainerField[] fields) throws Exception;

    public abstract void create(StorageContainer storageContainer) throws Exception;

}
