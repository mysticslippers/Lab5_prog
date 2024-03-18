package me.ifmo.server.utils;

import me.ifmo.common.data.Dragon;
import me.ifmo.common.data.DragonCharacter;
import me.ifmo.common.interfaces.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * A class for linking the collection and the commands to be executed.
 */

public class CollectionManager{
    private LinkedHashSet<Dragon> collection;
    private LocalDateTime timeOfInitialization;
    private LocalDateTime timeOfConservation;
    private static long avgAge = 0;
    private final ComparatorDescendingOrder comparatorDescending = new ComparatorDescendingOrder();
    private final ComparatorAscendingOrder comparatorAscending = new ComparatorAscendingOrder();

    /**
     * Constructor for working with a specific collection.
     * @param collection - Current collection.
     */

    public CollectionManager(LinkedHashSet<Dragon> collection){
        this.collection = collection;
    }

    /**
     * Method to access our collection.
     * @return Returns our collection.
     */

    public LinkedHashSet<Dragon> getCollection(){
        return this.collection;
    }

    /**
     * @return Returns the collection initialization time.
     */

    public LocalDateTime getTimeOfInitialization(){
        return this.timeOfInitialization;
    }

    /**
     * @return Returns the time the collection was saved.
     */

    public LocalDateTime getTimeOfConservation(){
        return this.timeOfConservation;
    }

    /**
     * Method for adding a new element to the collection.
     * @param dragon - The new item in the collection.
     */

    public void addToCollection(Dragon dragon){
        try{
            this.collection.add(dragon);
            sortByCave();
        }catch(NullPointerException exception){
            System.out.println("----------------------");
            System.out.println("Object not recognized!");
        }
    }

    /**
     * Method for adding a new element if the specified value is greater than the element's maximum value.
     * @param numberOfTreasures - The value by which we will compare the elements.
     * @param dragon - The object to add if the comparison is successful.
     */

    public void addIfMax(long numberOfTreasures, Dragon dragon){
        AddIFMax execute = (numberOfTreasuresInput, numberOfTreasuresMax) -> numberOfTreasuresInput > numberOfTreasuresMax;
        Dragon maxDragon = this.collection.stream().max(comparatorDescending).orElse(null);
        long numberOfTreasuresMaxDragon = (maxDragon != null) ? maxDragon.getCave().getNumberOfTreasures() : 0;
        if(execute.addIfMax(numberOfTreasures, numberOfTreasuresMaxDragon)){
            System.out.println("----------------------");
            System.out.println("Object added!");
            addToCollection(dragon);
        } else {
            System.out.println("----------------------");
            System.out.println("Object didn't added!");
        }
    }

    /**
     * Method for calculating the average value of the age field for all elements of the collection.
     */

    public void averageOfAge(){
        AverageOfAge execute = (summa, dragonAge) -> {
            summa += dragonAge;
            return summa;
        };
        this.collection.stream().forEach(dragon -> avgAge = execute.sumAge(avgAge, dragon.getAge()));
        System.out.println(avgAge / this.collection.stream().count());
    }

    /**
     * Method for clearing the collection.
     */

    public void clearCollection(){
        this.collection.clear();
    }

    /**
     * Method for finding a collection element by the value of its id field.
     * @param id - The id value for the searched element.
     * @return Returns the element of the collection by the id field.
     */

    public Dragon getDragonById(long id){
        return this.collection.stream().filter(dragon -> dragon.getId() == id).findFirst().orElse(null);
    }

    /**
     * Method for displaying information about the collection.
     */

    public void info(){
        System.out.println("Collection type: " + Dragon.class.getName());
        System.out.println("Time of initialization: " + getTimeOfInitialization());
        System.out.println("Size of collection: " + (this.collection.stream().count()));
        System.out.println("Time of conservation: " + getTimeOfConservation());
    }

    /**
     * Method for initializing a collection (loading data from a file into a collection).
     */

    public void loadCollectionFromFile(){
        this.collection = FileManager.readFileCSVByCSVFormat(System.getenv("1CSV"));
        this.timeOfInitialization = LocalDateTime.now();
        sortByCave();
    }

    /**
     * Method for displaying all elements of the collection in descending order by the cave field.
     */

    public void printDescendingCave(){
        sortByCave();
        show();
    }

    /**
     * Method for deleting an object by the value of the entered id.
     * @param id - Entered id.
     */

    public void removeById(long id){
        this.collection.remove(getDragonById(id));
    }

    /**
     * Method for removing all elements of the collection whose character field value is equal to the given one.
     * @param character - The value by which we will compare the elements.
     */

    public void removeByCharacter(DragonCharacter character){
        RemoveAllByCharacter execute = (characterInput, dragonCharacter) -> dragonCharacter == characterInput;
        this.collection.stream().forEach(dragon -> {
            if(execute.containsCharacter(character, dragon.getCharacter())){
                this.collection.remove(dragon);
            }
        });
    }

    /**
     * Method for sorting the collection by the cave field of the Dragon class in ascending order.
     */

    public void resortByCave(){
        this.collection = this.collection.stream().sorted(this.comparatorAscending).collect(Collectors.toCollection(LinkedHashSet<Dragon> :: new));
    }

    /**
     * Method for saving the collection (writing data to a file).
     */

    public void saveCollectionToFile(){
        FileManager.writeFileCSVByPattern(this.collection, System.getenv("1CSV"));
        this.timeOfConservation = LocalDateTime.now();
    }

    /**
     * Method for displaying all elements of the collection.
     */

    public void show(){
        System.out.println(this.collection.stream().reduce("", (dragon1, dragon2) -> dragon1 + (dragon2 + "\n\n"), (current, next) -> current + next).trim());
    }

    /**
     * Method for sorting the collection by the cave field of the Dragon class in descending order.
     */

    public void sortByCave(){
        this.collection = this.collection.stream().sorted(this.comparatorDescending).collect(Collectors.toCollection(LinkedHashSet<Dragon> :: new));
    }

    /**
     * Method for updating data about an object.
     * @param id - Entered id.
     */

    public void updateById(long id, Dragon dragon){
        removeById(id);
        addToCollection(dragon);
    }
}