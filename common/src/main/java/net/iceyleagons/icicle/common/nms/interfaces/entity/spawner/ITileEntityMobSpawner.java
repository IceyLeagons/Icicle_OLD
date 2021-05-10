package net.iceyleagons.icicle.common.nms.interfaces.entity.spawner;

import net.iceyleagons.icicle.common.nms.interfaces.entity.ITileEntity;

public interface ITileEntityMobSpawner extends ITileEntity {

    IMobSpawnerAbstract getSpawner();

}
