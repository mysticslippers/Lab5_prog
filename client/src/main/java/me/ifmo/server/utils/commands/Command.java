package me.ifmo.server.utils.commands;

/**
 * An interface for implementing a command template.
 */

public interface Command {
    /**
     * An abstract method for getting the name of a command.
     */

    String getName();

    /**
     * An abstract method for getting a description of a command.
     */

    String getDescription();

    /**
     * An abstract method for executing a command.
     */

    boolean execute();

    /**
     * An abstract method for determining the validity of a command argument.
     */

    boolean hasValidArgument(String argument, Object receivedArgument);
}
