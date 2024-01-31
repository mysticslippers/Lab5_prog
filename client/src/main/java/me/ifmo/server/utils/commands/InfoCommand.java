package me.ifmo.server.utils.commands;

import me.ifmo.common.exceptions.WrongArgumentException;
import me.ifmo.server.utils.CollectionManager;

/**
 * The class that implements the info command.
 */

public class InfoCommand extends BaseCommand{
    CollectionManager collectionManager;

    /**
     * The info command constructor.
     * @param collectionManager - Collection manager.
     */

    public InfoCommand(CollectionManager collectionManager){
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
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
            if(!argument.isEmpty() || receivedDragon != null) throw new WrongArgumentException();
        }catch(WrongArgumentException exception){
            System.out.println("----------------------");
            System.out.println("This command does not contain an argument!");
            System.out.println("This command does not contain an object!");
            valid = false;
        }
        return valid;
    }

    /**
     * The method that executes the info command.
     * @return Returns true if the command completed successfully.
     */

    @Override
    public boolean execute(){
        System.out.println("----------------------");
        System.out.println("Collection information: ");
        this.collectionManager.info();
        return true;
    }
}