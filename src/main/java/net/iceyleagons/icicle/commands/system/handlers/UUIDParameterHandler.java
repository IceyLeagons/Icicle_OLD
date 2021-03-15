package net.iceyleagons.icicle.commands.system.handlers;

import net.iceyleagons.icicle.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.commands.system.CommandParameterHandlerTemplate;
import org.bukkit.command.CommandSender;

import java.util.UUID;

@CommandParameterHandler(UUID.class)
public class UUIDParameterHandler implements CommandParameterHandlerTemplate {

    @Override
    public Object parseFromSting(String input, CommandSender sender, Class<?> requiredType) {
        try {
            return UUID.fromString(input);
        } catch (Exception e) {
            sender.sendMessage("§cInvalid UUID format!");
            return null;
        }
    }
}
