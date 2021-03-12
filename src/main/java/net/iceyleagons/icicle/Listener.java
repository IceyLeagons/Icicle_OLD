package net.iceyleagons.icicle;


import net.iceyleagons.icicle.annotations.Autowired;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class Listener implements org.bukkit.event.Listener {

    @Autowired
    public TestService testService;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        testService.test();
    }

}
