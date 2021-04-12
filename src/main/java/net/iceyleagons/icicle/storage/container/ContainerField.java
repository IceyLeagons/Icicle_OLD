package net.iceyleagons.icicle.storage.container;

import lombok.Getter;
import net.iceyleagons.icicle.storage.annotations.FieldName;

import java.lang.reflect.Field;

@Getter
public class ContainerField {

    private final Field field;
    private final String name;
    private final Class<?> type;

    public ContainerField(Field field) {
        this.field = field;
        this.type = field.getType();
        this.name = field.isAnnotationPresent(FieldName.class) ?
                field.getAnnotation(FieldName.class).value() .toLowerCase(): field.getName().toLowerCase();
    }

}
