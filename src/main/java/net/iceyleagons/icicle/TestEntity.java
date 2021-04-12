package net.iceyleagons.icicle;

import net.iceyleagons.icicle.storage.annotations.ContainerName;
import net.iceyleagons.icicle.storage.annotations.Entity;
import net.iceyleagons.icicle.storage.annotations.FieldName;
import net.iceyleagons.icicle.storage.annotations.Id;

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
