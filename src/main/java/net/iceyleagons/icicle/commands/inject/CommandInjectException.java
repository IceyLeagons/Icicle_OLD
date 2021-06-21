package net.iceyleagons.icicle.commands.inject;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommandInjectException extends Exception {

    /**
     * @param command the command name
     * @param cause   the cause of the exception
     */
    public CommandInjectException(String command, String cause) {
        super("The command named " + command + " can not be injected! Cause: " + cause);
    }

    /**
     * @param command the command
     * @param cause   the cause of the exception
     */
    public CommandInjectException(String command, Throwable cause) {
        super("The command named " + command + " can not be injected! Cause: ", cause);
    }

}
