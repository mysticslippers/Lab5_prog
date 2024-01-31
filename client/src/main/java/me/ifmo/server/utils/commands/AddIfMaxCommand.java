package me.ifmo.server.utils.commands;

import me.ifmo.common.data.Dragon;
import me.ifmo.common.exceptions.WrongArgumentException;
import me.ifmo.common.utils.UserInputManager;
import me.ifmo.server.utils.CollectionManager;

/**
 * The class that implements the add_if_max {element} command.
 */

public class AddIfMaxCommand extends BaseCommand{
    CollectionManager collectionManager;
    long numberOfTreasures;
    Dragon receivedDragon;

    /**
     * The add_if_max command constructor.
     * @param collectionManager - Collection manager.
     */

    public AddIfMaxCommand(CollectionManager collectionManager){
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
        try {
            if (argument.isEmpty() || receivedDragon == null) throw new WrongArgumentException();
            if (!UserInputManager.isDragonCaveValid(argument)) valid = false;
            this.numberOfTreasures = Long.parseLong(argument);
            this.receivedDragon = (Dragon) receivedDragon;
        }catch(WrongArgumentException exception){
            System.out.println("----------------------");
            System.out.println("This command does contain an argument!");
            System.out.println("This command does contain an object!");
            valid = false;
        }catch(IllegalArgumentException exception){
            System.out.println("----------------------");
            System.out.println("Please enter a non-empty value!");
            valid = false;
        }
        return valid;
    }

    /**
     * Method that executes the add_if_max {element} command.
     * @return Returns true if the command completed successfully.
     */

    @Override
    public boolean execute(){
        this.collectionManager.addIfMax(this.numberOfTreasures, this.receivedDragon);
        return true;
    }
}