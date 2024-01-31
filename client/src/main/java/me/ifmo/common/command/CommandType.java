package me.ifmo.common.command;

/**
 * An enum representing the command type.
 */

public enum CommandType {
    VALID,
    NOT_VALID,
    TRANSMITTING,
    SCRIPT;

    /**
     * toString method for the Color enum.
     * @return Information about an object of type Color.
     */

    public static String showList(){
        StringBuilder commandTypesList = new StringBuilder("TypesOfCommand: \n");
        for(CommandType commandType : values()){
            commandTypesList.append(commandType.name()).append(",\n");
        }
        return commandTypesList.substring(0, commandTypesList.length() - 2);
    }
}
