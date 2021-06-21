package net.iceyleagons.icicle.commands.test;

import net.iceyleagons.icicle.annotations.commands.Alias;
import net.iceyleagons.icicle.annotations.commands.Command;
import net.iceyleagons.icicle.annotations.commands.SubcommandManager;
import net.iceyleagons.icicle.annotations.commands.checks.PermissionCheck;
import net.iceyleagons.icicle.annotations.commands.checks.PlayerOnly;

@SubcommandManager("test2")
public class ExampleCommand2 {

    @PlayerOnly
    @Command("inside")
    @Alias({"i"})
    @PermissionCheck("icicle.test2")
    public String test(String parameter) {
        return "&9Command successfully executed2 with it's parameter: " + parameter;
    }

}
