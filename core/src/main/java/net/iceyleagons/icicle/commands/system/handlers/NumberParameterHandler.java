package net.iceyleagons.icicle.commands.system.handlers;

import net.iceyleagons.icicle.api.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.api.commands.CommandParameterHandlerTemplate;
import net.iceyleagons.icicle.api.commands.PluginCommandManager;
import net.iceyleagons.icicle.commands.system.PluginCommandManagerImpl;
import org.bukkit.command.CommandSender;

@CommandParameterHandler({
        int.class, Integer.class,
        long.class, Long.class,
        short.class, Short.class,
        float.class, Float.class,
        double.class, Double.class
})
public class NumberParameterHandler implements CommandParameterHandlerTemplate {

    @Override
    public Object parseFromSting(String input, CommandSender sender, Class<?> requiredType, PluginCommandManager pluginCommandManager) {
        try {
            if (requiredType.equals(int.class) || requiredType.equals(Integer.class))
                return Integer.parseInt(input);


            if (requiredType.equals(long.class) || requiredType.equals(Long.class))
                return Long.parseLong(input);


            if (requiredType.equals(short.class) || requiredType.equals(Short.class))
                return Short.parseShort(input);

            if (requiredType.equals(float.class) || requiredType.equals(Float.class))
                return Float.parseFloat(input);

            if (requiredType.equals(double.class) || requiredType.equals(Double.class))
                return Double.parseDouble(input);

        } catch (NumberFormatException e) {
            pluginCommandManager.sendMessage(sender, "parameter_number_invalid");
            return null;
        }
        return null;
    }


}
