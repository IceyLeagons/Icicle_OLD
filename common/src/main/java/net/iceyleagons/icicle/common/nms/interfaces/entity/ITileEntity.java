package net.iceyleagons.icicle.common.nms.interfaces.entity;

import net.iceyleagons.icicle.common.nms.interfaces.IWrappable;
import org.bukkit.inventory.InventoryHolder;

public interface ITileEntity extends IWrappable {

    Object getObject();

    boolean hasWorld();

    void update();

    void invalidateBlockCache();

    InventoryHolder getOwner();

    InventoryHolder getOwner(boolean snapshot);

}
