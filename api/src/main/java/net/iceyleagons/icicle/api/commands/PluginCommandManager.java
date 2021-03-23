package net.iceyleagons.icicle.api.commands;

import org.bukkit.command.CommandSender;

import java.util.Map;

public interface PluginCommandManager {

    void registerCommandContainer(Object o);
    void sendMessage(CommandSender sender, String key);
    Map<String, RegisteredCommand> getCommands();
    Map<Class<?>, CommandParameterHandlerTemplate> getParameterHandlers();

}
