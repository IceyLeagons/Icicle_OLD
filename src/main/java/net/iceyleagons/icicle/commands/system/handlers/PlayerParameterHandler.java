package net.iceyleagons.icicle.commands.system.handlers;

import net.iceyleagons.icicle.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.commands.system.CommandParameterHandlerTemplate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameterHandler(Player.class)
public class PlayerParameterHandler implements CommandParameterHandlerTemplate {

    @Override
    public Object parseFromSting(String input, CommandSender sender, Class<?> requiredType) {
        Player player = Bukkit.getPlayer(input);

        if (player == null) {
            sender.sendMessage("§cPlayer named " + input + " is offline!");
            return null;
        }

        return player;
    }
}
