package net.iceyleagons.icicle;

import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.annotations.EventListener;
import net.iceyleagons.icicle.misc.Benchmark;
import net.iceyleagons.icicle.pathing.AStar;
import net.iceyleagons.icicle.pathing.Pathfinder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

@EventListener
public class PathTest implements Listener {

    public Pathfinder pathfinder = new AStar();
    @Autowired
    public Icicle icicle;

    private Location loc1;
    private Location loc2;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.STONE) {
            if (loc1 == null) {
                event.getPlayer().sendMessage("Loc1 placed");
                loc1 = event.getBlock().getLocation().clone().add(0, 1, 0);
            } else if (loc2 == null) {
                event.getPlayer().sendMessage("Loc2 placed");
                loc2 = event.getBlock().getLocation().clone().add(0, 1, 0);

                Benchmark benchmark = new Benchmark();

                benchmark.start();
                Location[] loc = pathfinder.findPath(loc1, loc2, 150);
                benchmark.stop();

                Bukkit.broadcastMessage("Elapsed " + benchmark.getElapsed() + " ms");
                System.out.println(loc.length);

                List<BlockState> states = new ArrayList<>();
                for (Location location : loc) {
                    states.add(location.getBlock().getState());
                    location.getBlock().setType(Material.GOLD_BLOCK);
                }

                Bukkit.getScheduler().runTaskLater(icicle, () -> {
                    states.forEach(s -> s.update(true));
                }, 20L * 6);

                loc1 = null;
                loc2 = null;
            }
        }
    }

}
