package net.iceyleagons.icicle.commands.system.handlers;

import net.iceyleagons.icicle.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.commands.system.CommandParameterHandlerTemplate;
import net.iceyleagons.icicle.commands.system.PluginCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameterHandler(Player.class)
public class PlayerParameterHandler implements CommandParameterHandlerTemplate {

    @Override
    public Object parseFromSting(String input, CommandSender sender, Class<?> requiredType, PluginCommandManager pluginCommandManager) {
        Player player = Bukkit.getPlayer(input);

        if (player == null) {
            pluginCommandManager.sendMessage(sender, "parameter_player_not_found");
            return null;
        }

        return player;
    }
}
