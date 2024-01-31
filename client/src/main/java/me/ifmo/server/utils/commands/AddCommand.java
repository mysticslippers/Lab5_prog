package me.ifmo.server.utils.commands;

import me.ifmo.common.data.Dragon;
import me.ifmo.common.exceptions.WrongArgumentException;
import me.ifmo.server.utils.CollectionManager;

/**
 * The class that implements the add {element} command.
 */

public class AddCommand extends BaseCommand{
    CollectionManager collectionManager;
    Dragon receivedDragon;

    /**
     * The add command constructor.
     * @param collectionManager - Collection manager.
     */

    public AddCommand(CollectionManager collectionManager){
        super("add {element}", "добавить новый элемент в коллекцию");
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
            if(!argument.isEmpty() || receivedDragon == null) throw new WrongArgumentException();
            this.receivedDragon = (Dragon) receivedDragon;
        }catch(WrongArgumentException exception){
            System.out.println("----------------------");
            System.out.println("This command does not contain an argument!");
            System.out.println("This command does contain an object!");
            valid = false;
        }
        return valid;
    }

    /**
     * The method that executes the add {element} command.
     * @return Returns true if the command completed successfully.
     */

    @Override
    public boolean execute(){
        System.out.println("----------------------");
        System.out.println("Object added!");
        this.collectionManager.addToCollection(this.receivedDragon);
        return true;
    }
}