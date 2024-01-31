package me.ifmo.server.utils.commands;

import me.ifmo.common.exceptions.CollectionNotRecognizedException;
import me.ifmo.common.exceptions.WrongArgumentException;
import me.ifmo.server.utils.CollectionManager;

/**
 * The class that implements the show command.
 */

public class ShowCommand extends BaseCommand{
    CollectionManager collectionManager;

    /**
     * The show command constructor.
     * @param collectionManager - Collection manager.
     */

    public ShowCommand(CollectionManager collectionManager){
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
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
            if(this.collectionManager.getCollection().size() == 0) throw new CollectionNotRecognizedException();
        }catch(WrongArgumentException exception){
            System.out.println("----------------------");
            System.out.println("This command does not contain an argument!");
            valid = false;
        }catch(CollectionNotRecognizedException exception){
            System.out.println("----------------------");
            System.out.println("The collection is empty!");
            valid = false;
        }
        return valid;
    }

    /**
     * The method that executes the show command.
     * @return Returns true if the command completed successfully.
     */

    @Override
    public boolean execute(){
        System.out.println("----------------------");
        System.out.println("Here are the elements of the collection: ");
        this.collectionManager.show();
        return true;
    }
}