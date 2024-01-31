package me.ifmo.common.data;

/**
 * Class to represent the DragonCave field.
 */

public class DragonCave{
    private Long numberOfTreasures; //Поле не может быть null, Значение поля должно быть больше 0

    /**
     * Constructor for the DragonCave class.
     * @param numberOfTreasures - The value for the numberOfTreasures field.
     */

    public DragonCave(Long numberOfTreasures){
        this.numberOfTreasures = numberOfTreasures;
    }

    /**
     * Method for setting the new value of field numberOfTreasures.
     * @param numberOfTreasures - The new numberOfTreasures value.
     */

    public void setNumberOfTreasures(Long numberOfTreasures){
        this.numberOfTreasures = numberOfTreasures;
    }

    /**
     * Method for getting the value of the numberOfTreasures field.
     * @return The value of the numberOfTreasures field.
     */

    public Long getNumberOfTreasures(){
        return this.numberOfTreasures;
    }

    /**
     * Overridden toString method for the DragonCave class.
     * @return Information about an object of type DragonCave.
     */

    @Override
    public String toString(){
        return "DragonCave: numberOfTreasures - " + getNumberOfTreasures();
    }
}