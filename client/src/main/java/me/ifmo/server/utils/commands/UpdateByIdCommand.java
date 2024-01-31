package me.ifmo.server.utils.commands;

import me.ifmo.common.data.Dragon;
import me.ifmo.common.exceptions.CollectionNotRecognizedException;
import me.ifmo.common.exceptions.WrongArgumentException;
import me.ifmo.common.utils.UserInputManager;
import me.ifmo.server.utils.CollectionManager;

/**
 * The class that implements the update_by_id id {element} command.
 */

public class UpdateByIdCommand extends BaseCommand{
    CollectionManager collectionManager;
    long dragonId;
    Dragon dragon;

    /**
     * The update_by_id command constructor.
     * @param collectionManager - Collection manager.
     */

    public UpdateByIdCommand(CollectionManager collectionManager){
        super("update_by_id {element}", "обновить значение элемента коллекции, id которого равен заданному");
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
            if(argument.isEmpty() || receivedDragon == null) throw new WrongArgumentException();
            if(this.collectionManager.getCollection().size() == 0) throw new CollectionNotRecognizedException();
            if(!UserInputManager.isDragonIdValid(argument)) valid = false;
            this.dragonId = Long.parseLong(argument);
            if(this.collectionManager.getDragonById(this.dragonId) == null) throw new NullPointerException();
            this.dragon = (Dragon) receivedDragon;
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
     * The method that executes the update_by_id {element} command.
     * @return Returns true if the command completed successfully.
     */

    @Override
    public boolean execute(){
        Dragon dragonOld = this.collectionManager.getDragonById(this.dragonId);
        this.dragon.setId(dragonOld.getId());
        this.dragon.setCreationDate(dragonOld.getCreationDate());
        if(this.dragon.getName() == null) this.dragon.setName(dragonOld.getName());
        if(this.dragon.getCoordinates() == null || (this.dragon.getCoordinates().getX() == 0 && this.dragon.getCoordinates().getY() == 0)) this.dragon.setCoordinates(dragonOld.getCoordinates());
        if(this.dragon.getAge() == 0) this.dragon.setAge(dragonOld.getAge());
        if(this.dragon.getColor() == null) this.dragon.setColor(dragonOld.getColor());
        if(this.dragon.getType() == null) this.dragon.setType(dragonOld.getType());
        if(this.dragon.getCharacter() == null) this.dragon.setCharacter(dragonOld.getCharacter());
        if(this.dragon.getCave().getNumberOfTreasures() == 0) this.dragon.setCave(dragonOld.getCave());
        System.out.println("----------------------");
        System.out.println("Object updated!");
        this.collectionManager.updateById(this.dragonId, this.dragon);
        return true;
    }
}