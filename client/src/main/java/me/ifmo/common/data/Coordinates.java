package me.ifmo.common.data;

/**
 * Class to represent the Coordinates field.
 */

public class Coordinates{
    private Long x; //Значение поля должно быть больше -489, Поле не может быть null
    private float y; //Значение поля должно быть больше 0, Поле не может быть null

    /**
     * Constructor for the DragonCave class.
     * @param x - The value for the x field.
     * @param y - The value for the y field.
     */

    public Coordinates(Long x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Method for setting the new value of field x.
     * @param x - The new x value.
     */

    public void setX(Long x){
        this.x = x;
    }

    /**
     * Method for setting the new value of field y.
     * @param y - The new y value.
     */

    public void setY(float y){
        this.y = y;
    }

    /**
     * Method for getting the value of the x field.
     * @return The value of the x field.
     */

    public Long getX(){
        return this.x;
    }

    /**
     * Method for getting the value of the y field.
     * @return The value of the y field.
     */

    public float getY(){
        return this.y;
    }

    /**
     * Overridden toString method for the Coordinates class.
     * @return Information about an object of type Coordinates.
     */

    @Override
    public String toString(){
        return "Coordinates: X - " + getX() + ", Y - " + getY();
    }
}