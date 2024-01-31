package me.ifmo.server.utils.commands;

import me.ifmo.common.exceptions.WrongArgumentException;

/**
 * A class that implements the execute_script file_name command.
 */

public class ExecuteScriptCommand extends BaseCommand{

    /**
     * The execute_script command constructor.
     */

    public ExecuteScriptCommand(){
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
    }

    /**
     * A method that checks the validity of an argument.
     * @param argument - The command argument.
     * @param receivedDragon - The object passed by the client.
     * @return Returns true if the argument is valid.
     */

    @Override
    public boolean hasValidArgument(String argument, Object receivedDragon){
        boolean valid = true;
        try{
            if(argument.isEmpty() || receivedDragon != null) throw new WrongArgumentException();
        }catch(WrongArgumentException exception){
            System.out.println("----------------------");
            System.out.println("This command does contain an argument!");
            System.out.println("This command does not contain an object!");
            valid = false;
        }
        return valid;
    }

    /**
     * The method that executes the execute_script file_name command.
     * @return Returns true if the command completed successfully.
     */

    @Override
    public boolean execute(){
        System.out.println("Executing script...");
        return true;
    }
}