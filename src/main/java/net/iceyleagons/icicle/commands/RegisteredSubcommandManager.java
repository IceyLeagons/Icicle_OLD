package net.iceyleagons.icicle.commands;

import lombok.Getter;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RegisteredSubcommandManager {

    private final RegisteredIciclePlugin registeredIciclePlugin;

    private final String name;
    private final RegisteredCommandManager registeredCommandManager;
    private final Map<String, RegisteredCommand> commands = new HashMap<>();
    private final Object origin;

    public RegisteredSubcommandManager(String name, RegisteredIciclePlugin plugin, RegisteredCommandManager registeredCommandManager, Object origin) {
        this.name = name;
        this.registeredIciclePlugin = plugin;
        this.registeredCommandManager = registeredCommandManager;
        this.origin = origin;

        registeredCommandManager.scanCommands(origin, this.commands);
    }

    public void execute(@NotNull CommandSender commandSender, @NotNull String[] args) throws Exception {
        String cmd = args[0];
        if (!commands.containsKey(cmd)) {
            commandSender.sendMessage(ChatColor.RED + registeredCommandManager.getInvalidCmd());
            return;
        }

        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);

        try {
            commands.get(cmd).execute(commandSender, newArgs);
        } catch (Exception e) {
            commandSender.sendMessage(ChatColor.RED + "An internal exception occured!");
            e.printStackTrace();
        }
    }
}
