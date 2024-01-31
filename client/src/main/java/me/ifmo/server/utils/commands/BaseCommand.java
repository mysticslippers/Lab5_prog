package me.ifmo.server.utils.commands;

/**
 * An abstract class to represent a base command with an implementation of the Command interface.
 */

public abstract class BaseCommand implements Command{
    private final String name;
    private final String description;

    /**
     * The base command constructor, consisting of a name and a description.
     * @param name - The name of the executing command.
     * @param description - The description of the executing command.
     */

    public BaseCommand(String name, String description){
        this.name = name;
        this.description = description;
    }

    /**
     * Method for getting command name.
     * @return Returns the command name.
     */

    @Override
    public String getName(){
        return this.name;
    }

    /**
     * Method for getting command description.
     * @return Returns the description of the command.
     */

    @Override
    public String getDescription(){
        return this.description;
    }

    /**
     * An abstract method for executing a command.
     * @return Returns the status of the executed command.
     */

    @Override
    public abstract boolean execute();

    /**
     * An abstract method for determining the validity of a command argument.
     * @param argument - Argument for executing command.
     * @param receivedArgument - Transmitted object for executing command.
     * @return Returns true if the command should have an argument.
     */

    @Override
    public abstract boolean hasValidArgument(String argument, Object receivedArgument);
}