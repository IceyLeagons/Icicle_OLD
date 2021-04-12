package net.iceyleagons.icicle.commands.system;


import net.iceyleagons.icicle.commands.annotations.CommandParameterHandler;
import org.bukkit.command.CommandSender;

/**
 * Responsible for parsing string inputs into the desired type.
 * Classes implementing this must implement {@link CommandParameterHandler}
 *
 * @version 1.0.0
 * @since 2.0.0
 * @author TOTHTOMI
 */
public interface CommandParameterHandlerTemplate {

    /**
     * Used to convert String inputs from commands to the desired type.
     * (Ex.: type is Player, we'd do Bukkit.getPlayer(input) )
     *
     * @param input the input the user has given
     * @param sender the {@link CommandSender} used to send error messages.
     * @param requiredType is the type you want to supply, usually used if there are multiple types this handler can handle
     * @param pluginCommandManager is the {@link PluginCommandManager}, mainly used for translations (getMessages())
     * @return the parsed object or null if it cannot be parsed
     */
    Object parseFromSting(String input, CommandSender sender, Class<?> requiredType, PluginCommandManager pluginCommandManager);

}
