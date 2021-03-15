package net.iceyleagons.icicle.commands.system.handlers;

import net.iceyleagons.icicle.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.commands.system.CommandParameterHandlerTemplate;
import net.iceyleagons.icicle.math.MathUtils;
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
    public Object parseFromSting(String input, CommandSender sender, Class<?> requiredType) {
        if (!MathUtils.isNumber(input)) {
            sender.sendMessage("§cInvalid number.");
            return null;
        }

        if (requiredType.equals(int.class) || requiredType.equals(Integer.class))
            return requiredType.cast(Integer.parseInt(input)); //this is so if it needs an int for example it will get casted automatically


        if (requiredType.equals(long.class) || requiredType.equals(Long.class))
            return requiredType.cast(Long.parseLong(input)); //this is so if it needs an int for example it will get casted automatically


        if (requiredType.equals(short.class) || requiredType.equals(Short.class))
            return requiredType.cast(Short.parseShort(input)); //this is so if it needs an int for example it will get casted automatically

        if (requiredType.equals(float.class) || requiredType.equals(Float.class))
            return requiredType.cast(Float.parseFloat(input)); //this is so if it needs an int for example it will get casted automatically

        if (requiredType.equals(double.class) || requiredType.equals(Double.class))
            return requiredType.cast(Double.parseDouble(input)); //this is so if it needs an int for example it will get casted automatically

        return null;
    }


}
