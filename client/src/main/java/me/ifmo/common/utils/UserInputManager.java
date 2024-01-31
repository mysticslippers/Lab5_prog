package me.ifmo.common.utils;

import me.ifmo.common.command.CommandType;
import me.ifmo.common.data.Color;
import me.ifmo.common.data.DragonCharacter;
import me.ifmo.common.data.DragonType;
import me.ifmo.common.exceptions.FieldEmptyException;
import me.ifmo.common.exceptions.LocalDateParseException;
import me.ifmo.common.exceptions.NumberOutOfRangeException;
import me.ifmo.common.exceptions.StringContainCsvDelimiterException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for handling user input.
 */

public class UserInputManager {

    /**
     * A method that implements the separation of the input arguments.
     * @param scanner - User scanner.
     * @return Returns tokenized arguments.
     */

    public static String[] tokenizeArguments(Scanner scanner){
        String argument1 = "";
        String argument2 = "";
        try {
            String[] tokens;

            tokens = scanner.nextLine().trim().replaceAll("( )+", " ").split(" ");
            switch (tokens.length) {
                case 2 -> {
                    argument1 = tokens[0];
                    argument2 = tokens[1];
                }
                case 1 -> {
                    argument1 = tokens[0];
                    argument2 = "";
                }
                default -> {
                    argument1 = "";
                    argument2 = "";
                }
            }
        }catch(NoSuchElementException exception){
            System.out.println("----------------------");
            System.out.println("Problems with keyboard input!");
        }
        return new String[]{argument1, argument2};
    }

    /**
     * Validator for a custom command.
     * @param nameOfCommand - The name of the custom command.
     * @param argumentOfCommand - Custom command argument.
     * @return Returns the type of the custom command.
     */

    public static CommandType isInputCommandValid(String nameOfCommand, String argumentOfCommand){
        switch (nameOfCommand){
            case "add" -> {
                if(!argumentOfCommand.isEmpty()) return CommandType.NOT_VALID;
                return CommandType.TRANSMITTING;
            }
            case "add_if_max", "update_by_id" -> {
                if(argumentOfCommand.isEmpty()) return CommandType.NOT_VALID;
                return CommandType.TRANSMITTING;
            }
            case "average_of_age", "clear", "exit", "help", "history", "info", "print_field_descending_cave", "show", "reorder", "save" -> {
                if(!argumentOfCommand.isEmpty()) return CommandType.NOT_VALID;
            }
            case "remove_by_id", "remove_all_by_character" -> {
                if(argumentOfCommand.isEmpty()) return CommandType.NOT_VALID;
            }
            case "execute_script" -> {
                if(argumentOfCommand.isEmpty()) return CommandType.NOT_VALID;
                return CommandType.SCRIPT;
            }
            case "" -> {
                return CommandType.NOT_VALID;
            }
            default -> {
                System.out.println("----------------------");
                System.out.println("Wrong command input!");
                return CommandType.NOT_VALID;
            }
        }
        return CommandType.VALID;
    }

    /**
     * Validator for the id field of the Dragon class.
     * @param inputId - The id entered by the user.
     * @return Returns true if id is a valid value.
     */

    public static boolean isDragonIdValid(String inputId){
        boolean exist = true;
        long id;
        try{
            if(inputId.isEmpty()) throw new FieldEmptyException();
            id = Long.parseLong(inputId);
            if(id <= 0) throw new NumberOutOfRangeException();
        }catch(FieldEmptyException exception){
            System.out.println("----------------------");
            System.out.println("The id field cannot be empty!");
            exist = false;
        }catch(NumberFormatException exception){
            System.out.println("----------------------");
            System.out.println("A number must be entered in the id field!");
            exist = false;
        }catch(NumberOutOfRangeException exception){
            System.out.println("----------------------");
            System.out.println("The value of the id field must be greater than 0.");
            exist = false;
        }
        return exist;
    }

    /**
     * Validator for the name field of the Dragon class.
     * @param inputName - The name entered by the user.
     * @return Returns true if name is a valid value.
     */

    public static boolean isDragonNameValid(String inputName){
        boolean exist = true;
        try{
            if(inputName.isEmpty()) throw new FieldEmptyException();
            if(inputName.contains(",")) throw new StringContainCsvDelimiterException();
        }catch(FieldEmptyException exception){
            System.out.println("----------------------");
            System.out.println("The name field cannot be empty!");
            exist = false;
        }catch(StringContainCsvDelimiterException exception){
            System.out.println("----------------------");
            System.out.println("Enter venue's name without ',' !");
            exist = false;
        }
        return exist;
    }

    /**
     * Validator for the coordinates field of the Dragon class.
     * @param inputX - The coordinate X entered by the user.
     * @param inputY - The coordinate X entered by the user.
     * @return Returns true if coordinates X and Y are a valid value.
     */

    public static boolean isDragonCoordinatesValid(String inputX, String inputY){
        boolean exist = true;
        long x;
        float y;
        try{
            if(inputX.isEmpty() || inputY.isEmpty()) throw new FieldEmptyException();
            x = Long.parseLong(inputX);
            y = Float.parseFloat(inputY);
            if(x <= -489 || x == Long.MAX_VALUE) throw new NumberOutOfRangeException();
            if(y < 0 || y >= Float.MAX_VALUE) throw new NumberOutOfRangeException();
        }catch(FieldEmptyException exception){
            System.out.println("----------------------");
            System.out.println("The X and Y coordinate fields must not be empty!");
            exist = false;
        }catch(NumberFormatException exception){
            System.out.println("----------------------");
            System.out.println("Numbers entered incorrectly!");
            exist = false;
        }catch(NumberOutOfRangeException exception){
            System.out.println("----------------------");
            System.out.println("X coordinate must be greater than -489!");
            System.out.println("Enter number valid for the range: [" + (-488) + "..." + (Long.MAX_VALUE) + "]");
            System.out.println("Y coordinate must be greater than 0!");
            System.out.println("Enter number valid for the range: [" + (0) + "..." + (Float.MAX_VALUE) + "]");
            exist = false;
        }
        return exist;
    }

    /**
     * Validator for the LocalDate field of the Dragon class.
     * @param inputLocalDate - The LocalDate entered by the user.
     * @return Returns true if LocalDate is a valid value.
     */

    public static boolean isDragonLocalDateValid(String inputLocalDate){
        boolean exist = true;
        Pattern pattern = Pattern.compile("\\b(?:1[0-9]{3}|0[0-9]{3}|20[0-2][0-9])-(?:0[0-9]|1[0-2])-(?:[0-2][0-9]|3[01])\\b");
        Matcher matcher = pattern.matcher(inputLocalDate);
        try{
            if(inputLocalDate.isEmpty()) throw new FieldEmptyException();
            if(!matcher.matches()) throw new LocalDateParseException();
        }catch(FieldEmptyException exception){
            System.out.println("----------------------");
            System.out.println("The field with the local date must not be empty!");
            exist = false;
        }catch(LocalDateParseException exception){
            System.out.println("----------------------");
            System.out.println("String does not match date pattern!");
            exist = false;
        }
        return exist;
    }

    /**
     * Validator for the age field of the Dragon class.
     * @param inputAge - The age entered by the user.
     * @return Returns true if age is a valid value.
     */

    public static boolean isDragonAgeValid(String inputAge){
        boolean exist = true;
        long age;
        try{
            if(inputAge.isEmpty()) throw new FieldEmptyException();
            age = Long.parseLong(inputAge);
            if(age <= 0 || age == Long.MAX_VALUE) throw new NumberOutOfRangeException();
        }catch(FieldEmptyException exception){
            System.out.println("----------------------");
            System.out.println("The field with the age must not be empty!");
            exist = false;
        }catch(NumberFormatException exception){
            System.out.println("----------------------");
            System.out.println("Number entered incorrectly!");
            exist = false;
        }catch(NumberOutOfRangeException exception){
            System.out.println("----------------------");
            System.out.println("Age must be greater than 0!");
            System.out.println("Enter number valid for the range: [" + (0) + "..." + (Long.MAX_VALUE) + "]");
            exist = false;
        }
        return exist;
    }

    /**
     * Validator for the color field of the Dragon class.
     * @param inputColor - The color entered by the user.
     * @return Returns true if color is a valid value.
     */

    public static boolean isDragonColorValid(String inputColor){
        boolean exist = true;
        try{
            if(inputColor.isEmpty() || inputColor.equals("null")) throw new FieldEmptyException();
            Color.valueOf(inputColor);
        }catch(FieldEmptyException exception){
            System.out.println("----------------------");
            System.out.println("The field with the color must not be empty!");
            exist = false;
        }catch(IllegalArgumentException exception){
            System.out.println("----------------------");
            System.out.println("The Color value must be from a list!");
            exist = false;
        }
        return exist;
    }

    /**
     * Validator for the dragonType field of the Dragon class.
     * @param inputType - The dragonType entered by the user.
     * @return Returns true if dragonType is a valid value.
     */

    public static boolean isDragonTypeValid(String inputType){
        boolean exist = true;
        try{
            if(!inputType.isEmpty() && !inputType.equals("null")){
                DragonType.valueOf(inputType);
            }
        }catch(IllegalArgumentException exception){
            System.out.println("----------------------");
            System.out.println("The DragonType value must be from a list or empty!");
            exist = false;
        }
        return exist;
    }

    /**
     * Validator for the dragonCharacter field of the Dragon class.
     * @param inputCharacter - The dragonCharacter entered by the user.
     * @return Returns true if dragonCharacter is a valid value.
     */

    public static boolean isDragonCharacterValid(String inputCharacter){
        boolean exist = true;
        try{
            if(inputCharacter.isEmpty() || inputCharacter.equals("null")) throw new FieldEmptyException();
            DragonCharacter.valueOf(inputCharacter);
        }catch(FieldEmptyException exception){
            System.out.println("----------------------");
            System.out.println("The field with the color must not be empty!");
            exist = false;
        }catch(IllegalArgumentException exception){
            System.out.println("----------------------");
            System.out.println("The Color value must be from a list!");
            exist = false;
        }
        return exist;
    }

    /**
     * Validator for the numberOfTreasures field of the Dragon class.
     * @param inputNumberOfTreasures - The numberOfTreasures entered by the user.
     * @return Returns true if numberOfTreasures is a valid value.
     */

    public static boolean isDragonCaveValid(String inputNumberOfTreasures){
        boolean exist = true;
        long numberOfTreasures;
        try{
            if(inputNumberOfTreasures.isEmpty()) throw new FieldEmptyException();
            numberOfTreasures = Long.parseLong(inputNumberOfTreasures);
            if(numberOfTreasures <= 0 || numberOfTreasures == Long.MAX_VALUE) throw new NumberOutOfRangeException();
        }catch(FieldEmptyException exception){
            System.out.println("----------------------");
            System.out.println("The field with the number of treasures must not be empty!");
            exist = false;
        }catch(NumberFormatException exception){
            System.out.println("----------------------");
            System.out.println("Number entered incorrectly!");
            exist = false;
        }catch(NumberOutOfRangeException exception){
            System.out.println("----------------------");
            System.out.println("Age must be greater than 0!");
            System.out.println("Enter number valid for the range: [" + (1) + "..." + (Long.MAX_VALUE) + "]");
            exist = false;
        }
        return exist;
    }

    /**
     * A validator for determining the source file.
     * @return Returns true if the path to the environment variable named CollectionXML and history is correct.
     */

    public static boolean isFileNotNull(){
        boolean exist = true;
        try{
            if((System.getenv("1CSV") == null) && (System.getenv("2XML") == null) && (System.getenv("3JSON") == null)){
                System.out.println("----------------------");
                System.out.println("Collection file not found!");
                throw new FileNotFoundException();
            }
            if(System.getenv("history") == null){
                System.out.println("----------------------");
                System.out.println("Command history file not found!");
                throw new FileNotFoundException();
            }
        }catch(FileNotFoundException exception){
            exist = false;
        }
        return exist;
    }

    /**
     * A method for checking all information about a dragon.
     * @param dragonInfo - Input dragon's data.
     * @return Returns true if all entered information is valid.
     */

    public static boolean isDragonDataValid(ArrayList<String> dragonInfo){
        return isDragonIdValid(dragonInfo.get(0)) && isDragonNameValid(dragonInfo.get(1)) && isDragonCoordinatesValid(dragonInfo.get(2), dragonInfo.get(3)) && isDragonLocalDateValid(dragonInfo.get(4)) &&
                isDragonAgeValid(dragonInfo.get(5)) && isDragonColorValid(dragonInfo.get(6)) && isDragonTypeValid(dragonInfo.get(7)) && isDragonCharacterValid(dragonInfo.get(8)) && isDragonCaveValid(dragonInfo.get(9));
    }
}
