package me.ifmo.server.utils.commands;

import me.ifmo.common.exceptions.CollectionNotRecognizedException;
import me.ifmo.common.exceptions.WrongArgumentException;
import me.ifmo.common.utils.UserInputManager;
import me.ifmo.server.utils.CollectionManager;

/**
 * A class that implements the remove_by_id {id} command.
 */

public class RemoveByIdCommand extends BaseCommand{
    CollectionManager collectionManager;
    long dragonId;

    /**
     * The remove_by_id command constructor.
     * @param collectionManager - Collection manager.
     */

    public RemoveByIdCommand(CollectionManager collectionManager){
        super("remove_by_id id", "удалить элемент из коллекции по его id");
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
            if(argument.isEmpty() || receivedDragon != null) throw new WrongArgumentException();
            if(this.collectionManager.getCollection().size() == 0) throw new CollectionNotRecognizedException();
            if(!UserInputManager.isDragonIdValid(argument)) valid = false;
            this.dragonId = Long.parseLong(argument);
            if(this.collectionManager.getDragonById(dragonId) == null) throw new NullPointerException();
        }catch(WrongArgumentException exception){
            System.out.println("----------------------");
            System.out.println("This command does contain an argument!");
            valid = false;
        }
        catch(CollectionNotRecognizedException exception){
            System.out.println("----------------------");
            System.out.println("We cannot access the collection object. The collection is empty!");
            valid = false;
        }catch(NullPointerException exception){
            System.out.println("----------------------");
            System.out.println("Object does not exist!");
            valid = false;
        }catch(IllegalArgumentException exception){
            System.out.println("----------------------");
            System.out.println("Please enter a non-empty value!");
            valid = false;
        }
        return valid;
    }

    /**
     * The method that executes the remove_by_id id command.
     * @return Returns true if the command completed successfully.
     */

    @Override
    public boolean execute(){
        System.out.println("----------------------");
        System.out.println("The object has been deleted!");
        this.collectionManager.removeById(this.dragonId);
        return true;
    }
}