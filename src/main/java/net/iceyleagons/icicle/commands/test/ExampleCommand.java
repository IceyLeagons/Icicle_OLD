package net.iceyleagons.icicle.commands.test;

import net.iceyleagons.icicle.annotations.commands.Alias;
import net.iceyleagons.icicle.annotations.commands.Command;
import net.iceyleagons.icicle.annotations.commands.CommandManager;
import net.iceyleagons.icicle.annotations.commands.Subcommand;
import net.iceyleagons.icicle.annotations.commands.checks.PermissionCheck;
import net.iceyleagons.icicle.annotations.commands.checks.PlayerOnly;

@CommandManager("icicle")
public class ExampleCommand {

    @Subcommand
    private ExampleCommand2 exampleCommand2;

    @PlayerOnly
    @Command("test")
    @Alias({"t"})
    @PermissionCheck("icicle.test")
    public String test(String parameter, int param2) {
        return String.format("&9Executed, parameter1: %s, parsed parameter2: %d", parameter, param2);
    }
}
