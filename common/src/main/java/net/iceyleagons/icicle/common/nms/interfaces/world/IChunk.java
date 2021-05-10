package net.iceyleagons.icicle.common.nms.interfaces.world;

import net.iceyleagons.icicle.common.nms.interfaces.biome.IBiomeStorage;
import net.iceyleagons.icicle.common.nms.interfaces.entity.ITileEntity;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.Map;

public interface IChunk {

    IChunk fromBukkit(Chunk bukkitChunk);

    void markDirty();

    IBiomeStorage getBiomeIndex();

    ITileEntity getTileEntity(IBlockPosition location);

    ITileEntity getTileEntity(Location location);

    ITileEntity getTileEntityImmediately(IBlockPosition location);

    ITileEntity getTileEntityImmediately(Location location);

    void setTileEntity(IBlockPosition location, ITileEntity entity);

    void setTileEntity(Location location, ITileEntity entity);

    void setLoaded(boolean loaded);

    boolean isLoaded();

    Map<IBlockPosition, ITileEntity> getTileEntities();

    boolean isNeedsSaving();

    void setNeedsSaving(boolean needsSaving);

    IChunkCoordIntPair getPosition();

    IChunkCoordIntPair getPos();

}
