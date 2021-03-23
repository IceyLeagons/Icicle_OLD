package net.iceyleagons.icicle.commands.system.handlers;

import net.iceyleagons.icicle.api.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.api.commands.CommandParameterHandlerTemplate;
import net.iceyleagons.icicle.api.commands.PluginCommandManager;
import net.iceyleagons.icicle.commands.system.PluginCommandManagerImpl;
import org.bukkit.command.CommandSender;

import java.util.UUID;

@CommandParameterHandler(UUID.class)
public class UUIDParameterHandler implements CommandParameterHandlerTemplate {

    @Override
    public Object parseFromSting(String input, CommandSender sender, Class<?> requiredType, PluginCommandManager pluginCommandManager) {
        try {
            return UUID.fromString(input);
        } catch (Exception e) {
            pluginCommandManager.sendMessage(sender, "parameter_uuid_invalid");
            return null;
        }
    }
}
