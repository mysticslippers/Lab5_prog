package me.ifmo.server.utils.commands;

import me.ifmo.common.data.DragonCharacter;
import me.ifmo.common.exceptions.CollectionNotRecognizedException;
import me.ifmo.common.exceptions.WrongArgumentException;
import me.ifmo.common.utils.UserInputManager;
import me.ifmo.server.utils.CollectionManager;


/**
 * A class that implements the remove_all_by_character character command.
 */

public class RemoveByCharacterCommand extends BaseCommand{
    CollectionManager collectionManager;
    DragonCharacter character;

    /**
     * The remove_all_by_character command constructor.
     * @param collectionManager - Collection manager.
     */

    public RemoveByCharacterCommand(CollectionManager collectionManager){
        super("remove_all_by_character character", "удалить из коллекции все элементы, значение поля character которого эквивалентно заданному");
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
            if(collectionManager.getCollection().size() == 0) throw new CollectionNotRecognizedException();
            if(!UserInputManager.isDragonCharacterValid(argument)) valid = false;
            this.character = DragonCharacter.valueOf(argument);
        }catch(WrongArgumentException exception){
            System.out.println("----------------------");
            System.out.println("This command does contain an argument!");
            valid = false;
        }catch(CollectionNotRecognizedException exception){
            System.out.println("----------------------");
            System.out.println("We cannot access the collection object. The collection is empty!");
            valid = false;
        }catch(IllegalArgumentException exception){
            System.out.println("----------------------");
            System.out.println("Please enter a non-empty value!");
            valid = false;
        }
        return valid;
    }

    /**
     * The method that executes the remove_all_by_character character command.
     * @return Returns true if the command completed successfully.
     */

    @Override
    public boolean execute(){
        System.out.println("----------------------");
        System.out.println("The objects have been deleted!");
        this.collectionManager.removeByCharacter(this.character);
        return true;
    }
}