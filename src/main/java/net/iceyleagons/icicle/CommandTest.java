package net.iceyleagons.icicle;

import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.annotations.commands.Command;
import net.iceyleagons.icicle.annotations.commands.CommandContainer;
import org.bukkit.command.CommandSender;

@CommandContainer
public class CommandTest {

    @Autowired
    public TestConfig testConfig;

    @Command("test")
    public String run(String arg1, String arg2, CommandSender commandSender) {
        commandSender.sendMessage("Arg1: " + arg1 + " Arg2: " + arg2);
        commandSender.sendMessage(testConfig.hi);

        return null;
    }

}
