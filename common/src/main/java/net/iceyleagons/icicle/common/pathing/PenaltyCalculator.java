package net.iceyleagons.icicle.common.pathing;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class PenaltyCalculator {

    public static int calculatePenalty(Block block, Location from) {
        Location now = block.getLocation();
        Material type = block.getType();
        Material downType = block.getRelative(BlockFace.DOWN).getType();

        int moveTypePenalty = 1; //we don't want to return 0 because of an internal check

        int nx = now.getBlockX();
        int ny = now.getBlockY();
        int nz = now.getBlockZ();

        int fx = from.getBlockX();
        int fy = from.getBlockY();
        int fz = from.getBlockZ();

        if (ny > fy) moveTypePenalty += 2; //we jumped
        if (nx != fx && nz != fz) moveTypePenalty += 3; //we moved in a non-straight line


        if (type == Material.LAVA) return Integer.MAX_VALUE; //basically instant death, want to avoid as much as possible
        else if (isPressurePlate(type)) return moveTypePenalty + 100; //due to the possibility of traps
        else if (type == Material.CAMPFIRE || type == Material.SOUL_CAMPFIRE || type == Material.WITHER_ROSE) return moveTypePenalty + 50; //due to damage
        else if (downType == Material.CAMPFIRE || downType == Material.SOUL_CAMPFIRE || downType == Material.WITHER_ROSE) return moveTypePenalty + 50; //due to damage
        else if (downType == Material.MAGMA_BLOCK) return moveTypePenalty + 50; //due to damage
        else if (type == Material.SOUL_SAND || type == Material.WATER || type == Material.COBWEB) return moveTypePenalty + 25; //due to slowness
        else if (downType == Material.SOUL_SAND || downType == Material.WATER || downType == Material.COBWEB) return moveTypePenalty + 25; //due to slowness

        return moveTypePenalty;
    }
    private static boolean isPressurePlate(Material material) {
        if (material.isAir() || !material.isSolid()) return false;

        switch (material) {
            case ACACIA_PRESSURE_PLATE:
            case BIRCH_PRESSURE_PLATE:
            case CRIMSON_PRESSURE_PLATE:
            case DARK_OAK_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case JUNGLE_PRESSURE_PLATE:
            case OAK_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case POLISHED_BLACKSTONE_PRESSURE_PLATE:
            case SPRUCE_PRESSURE_PLATE:
            case STONE_PRESSURE_PLATE:
            case WARPED_PRESSURE_PLATE:
                return true;
            default:
                return false;
        }
    }

}
