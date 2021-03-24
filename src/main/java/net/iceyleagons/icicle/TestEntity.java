package net.iceyleagons.icicle;

import net.iceyleagons.icicle.annotations.storage.ContainerName;
import net.iceyleagons.icicle.annotations.storage.Entity;
import net.iceyleagons.icicle.annotations.storage.FieldName;
import net.iceyleagons.icicle.annotations.storage.Id;

@ContainerName("entities")
@Entity
public class TestEntity {

    @Id
    public long id;

    @FieldName("username")
    public String name;


    public TestEntity() {

    }

    public TestEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
