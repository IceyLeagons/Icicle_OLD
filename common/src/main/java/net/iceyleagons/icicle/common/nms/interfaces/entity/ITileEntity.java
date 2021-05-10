package net.iceyleagons.icicle.common.nms.interfaces.entity;

import net.iceyleagons.icicle.common.nms.interfaces.IWrappable;
import net.iceyleagons.icicle.common.nms.interfaces.craft.ICraftWorld;
import net.iceyleagons.icicle.common.nms.interfaces.world.IBlockPosition;
import net.iceyleagons.icicle.common.nms.interfaces.world.IChunk;
import org.bukkit.Location;
import org.bukkit.inventory.InventoryHolder;

public interface ITileEntity extends IWrappable {

    Object getObject();

    ICraftWorld getWorld();

    IChunk getChunk();

    IBlockPosition getPosition();

    Location getLocation();

    void setPosition(IBlockPosition position);

    void setLocation(Location location);

    boolean hasWorld();

    void update();

    void invalidateBlockCache();

    InventoryHolder getOwner();

    InventoryHolder getOwner(boolean snapshot);

}
