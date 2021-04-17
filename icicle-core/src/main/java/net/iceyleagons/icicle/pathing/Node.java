package net.iceyleagons.icicle.pathing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.iceyleagons.icicle.utils.heap.HeapItem;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Node implements HeapItem<Node> {

    @Getter
    @Setter
    private Node parent;

    @Getter
    private final Location position;

    @Getter
    @Setter
    private int gCost;

    @Getter
    private int penalty = 0;

    @Getter
    @Setter
    private int hCost;

    private int heapIndex;

    public boolean isWalkable() {
        if (position.getBlock().getRelative(BlockFace.DOWN).getType().isAir()) return false;

        return !(position.getBlock().getType().isSolid()) && position.getBlock().getRelative(BlockFace.UP).getType().isAir();
    }

    public void calculatePenalty(Location comingFrom) {
        if (penalty != 0) return;
        penalty = PenaltyCalculator.calculatePenalty(getPosition().getBlock(), comingFrom);
    }

    public int getFCost() {
        return gCost + hCost;
    }

    @Override
    public int getHeapIndex() {
        return this.heapIndex;
    }

    @Override
    public void setHeapIndex(int index) {
        this.heapIndex = index;
    }

    @Override
    public int compareTo(@NotNull Node o) {
        int c = Integer.compare(getFCost(), o.getFCost());
        if (c == 0) {
            c = Integer.compare(getHCost(), o.getHCost());
        }
        return -c;
    }
}
