/*
 * MIT License
 *
 * Copyright (c) 2021 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicle.commands.system;

import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.commands.CommandInjectException;
import net.iceyleagons.icicle.commands.CommandUtils;
import net.iceyleagons.icicle.commands.system.arguments.CommandArgs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TOTHTOMI
 */
@RequiredArgsConstructor
public class CommandManager implements CommandExecutor, TabCompleter {

    private final Map<String, RegisteredCommand> commands = new HashMap<>();
    private final CommandUtils commandUtils;

    public void registerClass(Object commandHolder) {
        Map<String, RegisteredCommand> subcommands = new HashMap<>();

        for (Method method : commandHolder.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(net.iceyleagons.icicle.commands.system.Command.class)) {
                net.iceyleagons.icicle.commands.system.Command annotation = method.getAnnotation(net.iceyleagons.icicle.commands.system.Command.class);

                RegisteredCommand registeredCommand = new RegisteredCommand(annotation.command().toLowerCase(), annotation, commandHolder, method);
                if (annotation.subcommand())
                    subcommands.put(annotation.command().toLowerCase(), registeredCommand);
                else {
                    commands.put(annotation.command().toLowerCase(), registeredCommand);
                }
            }
        }

        subcommands.values().forEach(subcommand -> {
            if (subcommand.getAnnotation().subcommands().length != 0) {
                for (String sc : subcommand.getAnnotation().subcommands()) {
                    subcommand.addSubcommand(subcommands.get(sc));
                }
            }
        });

        commands.values().forEach(command -> {
            if (command.getAnnotation().subcommands().length != 0) {
                for (String sc : command.getAnnotation().subcommands()) {
                    command.addSubcommand(subcommands.get(sc));
                }
            }
            try {
                commandUtils.injectCommand(command.getName(), this, this);
            } catch (CommandInjectException e) {
                e.printStackTrace();
            }
        });
    }

    private CommandFinish onCommand(RegisteredCommand cmd, CommandSender sender, String[] args) {
        net.iceyleagons.icicle.commands.system.Command annotation = cmd.getAnnotation();
        if (annotation.subcommands().length != 0) {
            if (args.length == 0) {
                return invoke(cmd, new CommandArgs(sender, args));
            } else {
                RegisteredCommand registeredCommand = cmd.getSubCommands().get(args[0].toLowerCase());
                return onCommand(registeredCommand, sender, getArgs(1, args));
            }
        } else {
            return invoke(cmd, new CommandArgs(sender, args));
        }
    }

    private String[] getArgs(int remove, String[] args) {
        if (args.length - remove <= 0) return new String[0];
        String[] newArgs = new String[args.length - remove];
        if (args.length - remove >= 0) System.arraycopy(args, remove, newArgs, remove, args.length - remove);
        return newArgs;
    }


    private CommandFinish onCommand(CommandSender sender, Command command, String[] args) {
        String commandName = command.getName();
        if (commands.containsKey(commandName)) {
            return onCommand(commands.get(commandName), sender, args);
        }
        return CommandFinish.NOT_FOUND;
    }

    private CommandFinish invoke(RegisteredCommand registeredCommand, CommandArgs commandArgs) {
        try {
            return (CommandFinish) registeredCommand.getMethod().invoke(registeredCommand.getDeclaringObject(), commandArgs);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return CommandFinish.INTERNAL_ERROR;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (onCommand(sender, command, args)) {
            case SUCCESSFUL:
                return true;
            case NO_PERMISSION:
                sender.sendMessage("You do not meet the permission requirements for this command!");
                return true;
            case PLAYER_ONLY:
                sender.sendMessage("We're sorry, but this command is only for players!");
                return true;
            case INTERNAL_ERROR:
                return false;
            case NOT_FOUND:
                sender.sendMessage("Command not found!");
                return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        String token = (args.length == 0 ? "" : args[args.length - 1]);
//TODO proper: suggest all subcommands as well!
        StringUtil.copyPartialMatches(token, commands.keySet(), completions);

        Collections.sort(completions);
        return completions;
    }

}
