package net.iceyleagons.icicle.commands.system.handlers;

import net.iceyleagons.icicle.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.commands.system.CommandParameterHandlerTemplate;
import org.bukkit.command.CommandSender;

@CommandParameterHandler(String.class)
public class StringParameterHandler implements CommandParameterHandlerTemplate {

    @Override
    public Object parseFromSting(String input, CommandSender sender, Class<?> requiredType) {
        return input; //this is a string yolo
    }
}
