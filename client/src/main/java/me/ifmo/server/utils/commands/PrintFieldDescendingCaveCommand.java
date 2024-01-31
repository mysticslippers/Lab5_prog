package me.ifmo.server.utils.commands;

import me.ifmo.common.exceptions.WrongArgumentException;
import me.ifmo.server.utils.CollectionManager;

/**
 * The class that implements the print_field_descending_cave command.
 */

public class PrintFieldDescendingCaveCommand extends BaseCommand{
    CollectionManager collectionManager;

    /**
     * The print_field_descending_cave command constructor.
     * @param collectionManager - Collection manager.
     */

    public PrintFieldDescendingCaveCommand(CollectionManager collectionManager){
        super("print_field_descending_cave", "вывести значения поля cave всех элементов в порядке убывания");
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
            if (!argument.isEmpty() || receivedDragon != null) throw new WrongArgumentException();
        }catch(WrongArgumentException exception){
            System.out.println("----------------------");
            System.out.println("This command does not contain an argument!");
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
     * Method that executes the print_field_descending_cave command.
     * @return Returns true if the command completed successfully.
     */

    @Override
    public boolean execute(){
        System.out.println("----------------------");
        System.out.println("Here are the values of the cave field of all elements in descending order:");
        this.collectionManager.printDescendingCave();
        return true;
    }
}