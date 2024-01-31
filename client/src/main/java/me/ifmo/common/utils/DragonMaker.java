package me.ifmo.common.utils;

import me.ifmo.common.data.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Class for creating a dragon and updating fields.
 */

public class DragonMaker{
    private static boolean consoleMode = true;
    private static Scanner consoleScanner;

    /**
     * Method that sets the mode of the console.
     * @param consoleMode - Custom mode.
     */

    public static void setConsoleMode(boolean consoleMode) {
        DragonMaker.consoleMode = consoleMode;
    }

    /**
     * A method that sets a custom console.
     * @param consoleScanner - User console.
     */

    public static void setConsoleScanner(Scanner consoleScanner) {
        DragonMaker.consoleScanner = consoleScanner;
    }

    /**
     * Method for getting the operating mode of the console.
     * @return Returns the operating mode of the console.
     */

    public static boolean isConsoleMode() {
        return consoleMode;
    }

    /**
     * Method for getting a custom console.
     * @return Returns the user console.
     */

    public static Scanner getConsoleScanner() {
        return consoleScanner;
    }

    /**
     * Method for creating an object from the console.
     * @return Returns the newly created object from the console.
     */

    public static Dragon getDragon(long counterId){
        ArrayList<String> dragonInfo = new ArrayList<>();
        Set<Boolean> scriptDataValid = new HashSet<>();
        String info;

        try{
            if(isConsoleMode()){
                System.out.println("Let's name the object:");
                do {
                    info = getConsoleScanner().nextLine().trim();
                    System.out.println("----------------------");
                    System.out.println(info);
                } while (!UserInputManager.isDragonNameValid(info));
                System.out.println("----------------------");
                System.out.println("Great! Let's give it the coordinates.");
                System.out.println("Enter values with SPACE between them:");
            }else{
                info = getConsoleScanner().nextLine().trim();
                scriptDataValid.add(UserInputManager.isDragonNameValid(info));
            }
            dragonInfo.add(info);

            String[] tokens;
            if(isConsoleMode()){
                do {
                    tokens = UserInputManager.tokenizeArguments(getConsoleScanner());
                    System.out.println("----------------------");
                    System.out.println(tokens[0] + " " + tokens[1]);
                } while (!UserInputManager.isDragonCoordinatesValid(tokens[0], tokens[1]));
                System.out.println("----------------------");
                System.out.println("Excellent! Let's give it an age:");
            }else{
                tokens = UserInputManager.tokenizeArguments(getConsoleScanner());
                scriptDataValid.add(UserInputManager.isDragonCoordinatesValid(tokens[0], tokens[1]));
            }
            dragonInfo.add(tokens[0]);
            dragonInfo.add(tokens[1]);

            if(isConsoleMode()){
                do {
                    info = getConsoleScanner().nextLine().trim();
                    System.out.println("----------------------");
                    System.out.println(info);
                } while (!UserInputManager.isDragonAgeValid(info));
                System.out.println("----------------------");
                System.out.println("Nice! Let's give it a color.");
            }else{
                info = getConsoleScanner().nextLine().trim();
                scriptDataValid.add(UserInputManager.isDragonAgeValid(info));
            }
            dragonInfo.add(info);

            if(isConsoleMode()){
                do {
                    System.out.println(Color.showList());
                    info = getConsoleScanner().nextLine().trim();
                    System.out.println("----------------------");
                    System.out.println(info);
                } while (!UserInputManager.isDragonColorValid(info));
                System.out.println("----------------------");
                System.out.println("Cool! Let's give it type.");
            }else{
                info = getConsoleScanner().nextLine().trim();
                scriptDataValid.add(UserInputManager.isDragonColorValid(info));
            }
            dragonInfo.add(info);

            if(isConsoleMode()){
                do {
                    System.out.println(DragonType.showList());
                    info = getConsoleScanner().nextLine().trim();
                    System.out.println("----------------------");
                    System.out.println(info);
                } while (!UserInputManager.isDragonTypeValid(info));
                System.out.println("----------------------");
                System.out.println("Great! Let's give it character.");
            }else{
                info = getConsoleScanner().nextLine().trim();
                scriptDataValid.add(UserInputManager.isDragonTypeValid(info));
            }
            if(info.equals("")) info = null;
            dragonInfo.add(info);

            if(isConsoleMode()){
                do {
                    System.out.println(DragonCharacter.showList());
                    info = getConsoleScanner().nextLine().trim();
                    System.out.println("----------------------");
                    System.out.println(info);
                } while (!UserInputManager.isDragonCharacterValid(info));
                System.out.println("----------------------");
                System.out.println("Excellent! Let's give it cave:");
            }else{
                info = getConsoleScanner().nextLine().trim();
                scriptDataValid.add(UserInputManager.isDragonCharacterValid(info));
            }
            dragonInfo.add(info);

            if(isConsoleMode()){
                do {
                    info = getConsoleScanner().nextLine().trim();
                    System.out.println("----------------------");
                    System.out.println(info);
                } while (!UserInputManager.isDragonCaveValid(info));
                System.out.println("----------------------");
            }else{
                info = getConsoleScanner().nextLine().trim();
                scriptDataValid.add(UserInputManager.isDragonCaveValid(info));
            }
            dragonInfo.add(info);

            Color color;
            DragonType dragonType = null;
            DragonCharacter dragonCharacter;
            color = Color.valueOf(dragonInfo.get(4));
            if(dragonInfo.get(5) != null) dragonType = DragonType.valueOf(dragonInfo.get(5));
            dragonCharacter = DragonCharacter.valueOf(dragonInfo.get(6));

            if(isConsoleMode() ^ (!isConsoleMode() && !scriptDataValid.contains(false) && dragonInfo.size() == 8)){
                System.out.println("----------------------");
                System.out.println("Object created!");
                return new Dragon(counterId, dragonInfo.get(0), new Coordinates(Long.parseLong(dragonInfo.get(1)), Float.parseFloat(dragonInfo.get(2))),
                        LocalDate.now(), Long.parseLong(dragonInfo.get(3)), color, dragonType, dragonCharacter,
                        new DragonCave(Long.parseLong(dragonInfo.get(7))));
            }
        }catch(NoSuchElementException exception){
            System.out.println("----------------------");
            System.out.println("Something went wrong!");
        }catch(IllegalArgumentException exception){
            System.out.println("----------------------");
            System.out.println("Not valid data!");
        }
        return null;
    }

    /**
     * The method that implements the update_by_id command.
     * @return Returns an updated dragon.
     */

    public static Dragon updateDragon(){
        String field;
        String info;
        try {
            if(consoleMode){
                do {
                    System.out.println("----------------------");
                    System.out.println("Which field do you want to change? Available: ");
                    System.out.println(" name \n coordinates \n age \n color \n type \n character \n cave");
                    System.out.println("----------------------");
                    field = consoleScanner.nextLine().trim();
                }while(field.isEmpty());
            }else{
                field = consoleScanner.nextLine().trim();
            }
            switch (field) {

                case "name" -> {
                    if (isConsoleMode()) {
                        System.out.println("----------------------");
                        System.out.println("Let's name the object:");
                        do {
                            info = consoleScanner.nextLine().trim();
                            System.out.println("----------------------");
                            System.out.println(info);
                        } while (!UserInputManager.isDragonNameValid(info));
                        System.out.println("----------------------");
                    } else {
                        info = consoleScanner.nextLine().trim();
                        if (!UserInputManager.isDragonNameValid(info)) return null;
                    }
                    return new Dragon.Builder().setName(info).build();
                }

                case "coordinates" -> {
                    System.out.println("----------------------");
                    System.out.println("Enter values with SPACE between them: ");
                    String[] tokens;
                    if (isConsoleMode()) {
                        do {
                            tokens = UserInputManager.tokenizeArguments(getConsoleScanner());
                            System.out.println("----------------------");
                            System.out.println(tokens[0] + " " + tokens[1]);
                        } while (!UserInputManager.isDragonCoordinatesValid(tokens[0], tokens[1]));
                        System.out.println("----------------------");
                    } else {
                        tokens = UserInputManager.tokenizeArguments(getConsoleScanner());
                        if (!UserInputManager.isDragonCoordinatesValid(tokens[0], tokens[1])) return null;
                    }
                    return new Dragon.Builder().setCoordinates(new Coordinates(Long.parseLong(tokens[0]), Float.parseFloat(tokens[1]))).build();
                }

                case "age" -> {
                    System.out.println("----------------------");
                    System.out.println("Enter value: ");
                    if (isConsoleMode()) {
                        do {
                            info = consoleScanner.nextLine().trim();
                            System.out.println("----------------------");
                            System.out.println(info);
                        } while (!UserInputManager.isDragonAgeValid(info));
                        System.out.println("----------------------");
                    } else {
                        info = consoleScanner.nextLine().trim();
                        if (!UserInputManager.isDragonAgeValid(info)) return null;
                    }
                    return new Dragon.Builder().setAge(Long.parseLong(info)).build();
                }

                case "color" -> {
                    Color color;
                    System.out.println("----------------------");
                    System.out.println("Enter value: ");
                    if (isConsoleMode()) {
                        do {
                            System.out.println(Color.showList());
                            info = consoleScanner.nextLine().trim();
                            System.out.println("----------------------");
                            System.out.println(info);
                        } while (!UserInputManager.isDragonColorValid(info));
                        System.out.println("----------------------");
                    } else {
                        info = consoleScanner.nextLine().trim();
                        if (!UserInputManager.isDragonColorValid(info)) return null;
                    }
                    color = Color.valueOf(info);
                    return new Dragon.Builder().setColor(color).build();
                }

                case "type" -> {
                    DragonType type = null;
                    System.out.println("----------------------");
                    System.out.println("Enter value: ");
                    if(isConsoleMode()){
                        do {
                            System.out.println(DragonType.showList());
                            info = consoleScanner.nextLine().trim();
                            System.out.println("----------------------");
                            System.out.println(info);
                        } while (!UserInputManager.isDragonTypeValid(info));
                        System.out.println("----------------------");
                    }else{
                        info = consoleScanner.nextLine().trim();
                        if (!UserInputManager.isDragonTypeValid(info)) return null;
                    }
                    if(!info.isEmpty()) type = DragonType.valueOf(info);
                    return new Dragon.Builder().setType(type).build();
                }

                case "character" -> {
                    DragonCharacter character;
                    System.out.println("----------------------");
                    System.out.println("Enter value: ");
                    if(isConsoleMode()){
                        do {
                            System.out.println(DragonCharacter.showList());
                            info = consoleScanner.nextLine().trim();
                            System.out.println("----------------------");
                            System.out.println(info);
                        } while (!UserInputManager.isDragonCharacterValid(info));
                        System.out.println("----------------------");
                    }else{
                        info = consoleScanner.nextLine().trim();
                        if (!UserInputManager.isDragonCharacterValid(info)) return null;
                    }
                    character = DragonCharacter.valueOf(info);
                    return new Dragon.Builder().setCharacter(character).build();
                }

                case "cave" -> {
                    System.out.println("----------------------");
                    System.out.println("Enter value: ");
                    if(isConsoleMode()){
                        do {
                            info = getConsoleScanner().nextLine().trim();
                            System.out.println("----------------------");
                            System.out.println(info);
                        }while(!UserInputManager.isDragonCaveValid(info));
                        System.out.println("----------------------");
                    }else{
                        info = getConsoleScanner().nextLine().trim();
                        if (!UserInputManager.isDragonCaveValid(info)) return null;
                    }
                    return new Dragon.Builder().setCave(new DragonCave(Long.parseLong(info))).build();
                }
            }
            return null;
        }catch(NoSuchElementException exception){
            System.out.println("----------------------");
            System.out.println("Something went wrong!");
        }catch(IllegalArgumentException exception){
            System.out.println("----------------------");
            System.out.println("Not valid data!");
        }
        return null;
    }
}