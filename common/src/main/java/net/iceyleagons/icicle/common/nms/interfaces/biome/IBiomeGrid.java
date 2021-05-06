package net.iceyleagons.icicle.common.nms.interfaces.biome;

import net.iceyleagons.icicle.common.nms.interfaces.IWrappable;
import org.bukkit.generator.ChunkGenerator;

public interface IBiomeGrid extends IWrappable {

    IBiomeStorage getFromBukkit(ChunkGenerator.BiomeGrid biomeGrid);

}
