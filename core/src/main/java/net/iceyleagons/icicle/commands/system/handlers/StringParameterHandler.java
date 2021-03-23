package net.iceyleagons.icicle.commands.system.handlers;

import net.iceyleagons.icicle.api.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.api.commands.CommandParameterHandlerTemplate;
import net.iceyleagons.icicle.api.commands.PluginCommandManager;
import net.iceyleagons.icicle.commands.system.PluginCommandManagerImpl;
import org.bukkit.command.CommandSender;

@CommandParameterHandler(String.class)
public class StringParameterHandler implements CommandParameterHandlerTemplate {

    @Override
    public Object parseFromSting(String input, CommandSender sender, Class<?> requiredType, PluginCommandManager pluginCommandManager) {
        return input; //this is a string yolo
    }
}
