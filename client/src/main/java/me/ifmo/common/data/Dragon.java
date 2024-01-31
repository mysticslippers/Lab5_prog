package me.ifmo.common.data;

import java.time.LocalDate;

/**
 * The main class for representing collection objects.
 */

public class Dragon{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long age; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonType type; //Поле может быть null
    private DragonCharacter character; //Поле не может быть null
    private DragonCave cave; //Поле не может быть null

    /**
     * Internal class to implement the Builder pattern.
     */

    public static class Builder{
        private long id = 0;
        private String name = null;
        private Coordinates coordinates = new Coordinates(0L, 0);
        private LocalDate creationDate = null;
        private long age = 0L;
        private Color color = null;
        private DragonType type = null;
        private DragonCharacter character = null;
        private DragonCave cave = new DragonCave(0L);

        /**
         * Constructor for the Builder.
         */

        public Builder(){}

        /**
         * Method for setting the new value of field id.
         * @param inputId - The new id value.
         * @return Returns the Builder with the id field set.
         */

        public Builder setId(long inputId){
            this.id = inputId;
            return this;
        }

        /**
         * Method for setting the new value of field name.
         * @param inputName - The new name value.
         * @return Returns the Builder with the name field set.
         */

        public Builder setName(String inputName){
            this.name = inputName;
            return this;
        }

        /**
         * Method for setting the new value of field coordinates.
         * @param inputCoordinates - The new coordinates value.
         * @return Returns the Builder with the coordinates field set.
         */

        public Builder setCoordinates(Coordinates inputCoordinates){
            this.coordinates = inputCoordinates;
            return this;
        }

        /**
         * Method for setting the new value of field creationDate.
         * @param inputLocalDate - The new creationDate value.
         * @return Returns the Builder with the creationDate field set.
         */

        public Builder setCreationDate(LocalDate inputLocalDate){
            this.creationDate = inputLocalDate;
            return this;
        }

        /**
         * Method for setting the new value of field age.
         * @param inputAge - The new age value.
         * @return Returns the Builder with the age field set.
         */

        public Builder setAge(long inputAge){
            this.age = inputAge;
            return this;
        }

        /**
         * Method for setting the new value of field color.
         * @param inputColor - The new color value.
         * @return Returns the Builder with the color field set.
         */

        public Builder setColor(Color inputColor){
            this.color = inputColor;
            return this;
        }

        /**
         * Method for setting the new value of field type.
         * @param inputType - The new type value.
         * @return Returns the Builder with the type field set.
         */

        public Builder setType(DragonType inputType){
            this.type = inputType;
            return this;
        }

        /**
         * Method for setting the new value of field character.
         * @param inputCharacter - The new type character.
         * @return Returns the Builder with the type character set.
         */

        public Builder setCharacter(DragonCharacter inputCharacter){
            this.character = inputCharacter;
            return this;
        }

        /**
         * Method for setting the new value of field cave.
         * @param inputCave - The new type cave.
         * @return Returns the Builder with the type cave set.
         */

        public Builder setCave(DragonCave inputCave){
            this.cave = inputCave;
            return this;
        }

        /**
         * Method for constructing an object of type Dragon with fields set.
         * @return Returns an object of type Dragon.
         */

        public Dragon build(){
            return new Dragon(this);
        }
    }

    /**
     * A constructor for an object of type Dragon using a Builder.
     * @param builder - Builder for the Dragon class.
     */

    private Dragon(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.coordinates = builder.coordinates;
        this.creationDate = builder.creationDate;
        this.age = builder.age;
        this.color = builder.color;
        this.type = builder.type;
        this.character = builder.character;
        this.cave = builder.cave;
    }

    /**
     * Constructor for the Dragon class.
     * @param id - The value for the id field.
     * @param name - The value for the name field.
     * @param coordinates - The value for the coordinates field.
     * @param creationDate - The value for the creationDate field.
     * @param age - The value for the age field.
     * @param color - The value for the color field.
     * @param dragonType - The value for the dragonType field.
     * @param dragonCharacter - The value for the dragonCharacter field.
     * @param cave - The value for the cave field.
     */

    public Dragon(long id, String name, Coordinates coordinates, LocalDate creationDate, long age,
                  Color color, DragonType dragonType, DragonCharacter dragonCharacter, DragonCave cave){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.color = color;
        this.type = dragonType;
        this.character = dragonCharacter;
        this.cave = cave;
    }

    /**
     * Method for setting the new value of field id.
     * @param id - The new id value.
     */

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Method for setting the new value of field name.
     * @param name - The new name value.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for setting the new value of field coordinates.
     * @param coordinates - The new coordinates value.
     */

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    /**
     * Method for setting the new value of field creationDate.
     * @param creationDate - The new creationDate value.
     */

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Method for setting the new value of field age.
     * @param age - The new age value.
     */

    public void setAge(long age) {
        this.age = age;
    }

    /**
     * Method for setting the new value of field color.
     * @param color - The new color value.
     */

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Method for setting the new value of field dragonType.
     * @param type - The new dragonType value.
     */

    public void setType(DragonType type) {
        this.type = type;
    }

    /**
     * Method for setting the new value of field dragonCharacter.
     * @param character - The new dragonCharacter value.
     */

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    /**
     * Method for setting the new value of field dragonCave.
     * @param cave - The new dragonCave value.
     */

    public void setCave(DragonCave cave) {
        this.cave = cave;
    }

    /**
     * Method for getting the value of the id field.
     * @return The value of the id field.
     */

    public long getId() {
        return this.id;
    }

    /**
     * Method for getting the value of the name field.
     * @return The value of the name field.
     */

    public String getName() {
        return this.name;
    }

    /**
     * Method for getting the value of the coordinates field.
     * @return The value of the coordinates field.
     */

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * Method for getting the value of the creationDate field.
     * @return The value of the creationDate field.
     */

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    /**
     * Method for getting the value of the age field.
     * @return The value of the age field.
     */

    public long getAge() {
        return this.age;
    }

    /**
     * Method for getting the value of the color field.
     * @return The value of the color field.
     */

    public Color getColor() {
        return this.color;
    }

    /**
     * Method for getting the value of the dragonType field.
     * @return The value of the dragonType field.
     */

    public DragonType getType() {
        return this.type;
    }

    /**
     * Method for getting the value of the dragonCharacter field.
     * @return The value of the dragonCharacter field.
     */

    public DragonCharacter getCharacter() {
        return this.character;
    }

    /**
     * Method for getting the value of the dragonCave field.
     * @return The value of the dragonCave field.
     */

    public DragonCave getCave() {
        return this.cave;
    }

    /**
     * Overridden toString method for the Dragon class.
     * @return Information about an object of type Dragon.
     */

    @Override
    public String toString(){
        return "Dragon: ID - " + getId() + " || " + "NAME - " + getName() + " || " + getCoordinates().toString() +
                " || " + "DATE - " + getCreationDate() + " || " + "AGE - " + getAge() + " || " + "COLOR - " + getColor() +
                " || " + "TYPE - " + getType() + " || " + "CHARACTER - " + getCharacter() + " || " + getCave();
    }
}