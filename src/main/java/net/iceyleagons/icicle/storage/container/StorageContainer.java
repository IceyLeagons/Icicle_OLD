package net.iceyleagons.icicle.storage.container;

import lombok.Getter;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.storage.AbstractStorageHandler;

@Getter
public class StorageContainer {

    private final String name;
    private final ContainerField idField; //T type of the ID
    private final ContainerField[] fields;

    //private final Map<T, Object> values = new HashMap<>();

    public StorageContainer(String name, ContainerField containerField, ContainerField[] fields) {
        this.name = name;
        this.idField = containerField;
        this.fields = fields;
    }

    @SneakyThrows
    public void saveObject(Object object, AbstractStorageHandler abstractStorageHandler) {
        //TODO generate ID if needed
        abstractStorageHandler.save(name, idField, object, fields);
    }

    public Object getObject(Object id, AbstractStorageHandler abstractStorageHandler) {
        try {
            return abstractStorageHandler.get(id, name, idField, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
