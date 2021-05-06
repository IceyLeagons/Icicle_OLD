package net.iceyleagons.icicle.common.wrapped.entity;

import lombok.Getter;
import net.iceyleagons.icicle.common.wrapped.utils.WrappedClass;
import org.bukkit.entity.Entity;

public class WrappedCraftCreature {

    @Getter
    private final Object object;

    public WrappedCraftCreature(Entity entity) {
        this.object = WrappedClass.getCBClass("entity.CraftCreature").cast(entity);
    }

}
