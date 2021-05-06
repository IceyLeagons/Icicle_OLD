package net.iceyleagons.icicle.common.storage;

import net.iceyleagons.icicle.common.annotations.Autowired;
import net.iceyleagons.icicle.common.annotations.Service;
import net.iceyleagons.icicle.common.annotations.handlers.impl.storage.EntityAnnotationHandler;
import net.iceyleagons.icicle.common.storage.annotations.ContainerName;
import net.iceyleagons.icicle.common.storage.annotations.Entity;
import net.iceyleagons.icicle.common.storage.annotations.Id;
import net.iceyleagons.icicle.common.storage.container.ContainerField;
import net.iceyleagons.icicle.common.storage.container.StorageContainer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StorageHandlerService {

    @Autowired
    public EntityAnnotationHandler entities;
    public AbstractStorageHandler currentStorage;

    private final Map<Class<?>, StorageContainer> storageContainerMap = new HashMap<>();

    public void cleanup() {
        if (this.currentStorage != null) this.currentStorage.cleanup();;
    }

    public void registerEntityClass(Class<?> clazz) {
        if (storageContainerMap.containsKey(clazz)) return;

        if (clazz.isAnnotationPresent(Entity.class)) {
            String containerName = clazz.isAnnotationPresent(ContainerName.class) ? clazz.getAnnotation(ContainerName.class).value() : clazz.getName();

            Optional<Field> idFieldOpt = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst();

            if (!idFieldOpt.isPresent()) {
                return;
            }

            Field idField = idFieldOpt.get();
            Field[] fields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> !field.isAnnotationPresent(Id.class)).toArray(Field[]::new);

            storageContainerMap.put(clazz, createContainer(containerName, idField, fields));
        }
    }

    public <T> T get(Class<T> entityClass, Object id) {
        Object obj = storageContainerMap.containsKey(entityClass) ? storageContainerMap.get(entityClass).getObject(id, currentStorage) : null;
        if (obj == null) return null;

        return entityClass.isInstance(obj) ? entityClass.cast(obj) : null;
    }

    public void saveObject(Object o) {
        if (storageContainerMap.containsKey(o.getClass())) {
            StorageContainer storageContainer = storageContainerMap.get(o.getClass());
            storageContainer.saveObject(o, currentStorage);
        }
    }

    public void switchStorageTo(AbstractStorageHandler abstractStorageHandler) {
        entities.getEntityClasses().forEach(this::registerEntityClass);
        storageContainerMap.values().forEach(sc -> {
            try {
                abstractStorageHandler.create(sc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        if (this.currentStorage != null) this.currentStorage.cleanup();
        this.currentStorage = abstractStorageHandler;
    }

    public StorageContainer createContainer(String containerName, Field idField, Field[] fields) {
        ContainerField[] containerFields = getContainerFields(fields);
        return new StorageContainer(containerName, new ContainerField(idField), containerFields);
    }

    private ContainerField[] getContainerFields(Field[] fields) {
        ContainerField[] containerFields = new ContainerField[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            containerFields[i] = new ContainerField(field);
        }
        return containerFields;
    }
}
