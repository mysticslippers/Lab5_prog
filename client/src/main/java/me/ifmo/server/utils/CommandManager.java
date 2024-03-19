package me.ifmo.server.utils;

import me.ifmo.common.command.CommandType;
import me.ifmo.common.data.Dragon;
import me.ifmo.common.exceptions.NoSuchCommandException;
import me.ifmo.common.exceptions.ExecutingScriptException;
import me.ifmo.common.utils.DragonMaker;
import me.ifmo.common.utils.UserInputManager;
import me.ifmo.server.utils.commands.*;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * A class that implements the work of a command manager.
 */

public class CommandManager{
    private final Map<String, Command> commands = new HashMap<>();
    private final ArrayList<String> historyOfCommands = new ArrayList<>();
    private final ArrayList<String> namesOfScripts = new ArrayList<>();
    private final Command addCommand;
    private final Command addIfMaxCommand;
    private final Command averageOfAgeCommand;
    private final Command clearCommand;
    private final Command executeScriptCommand;
    private final Command exitCommand;
    private final Command helpCommand;
    private final Command historyCommand;
    private final Command infoCommand;
    private final Command printFieldDescendingCaveCommand;
    private final Command removeByIdCommand;
    private final Command removeByCharacterCommand;
    private final Command reorderCommand;
    private final Command saveCommand;
    private final Command showCommand;
    private final Command updateByIdCommand;

    /**
     * Constructor linking commands and CommandManager.
     * @param addCommand - The add {element} command.
     * @param addIfMaxCommand - The add_if_max {element} command.
     * @param averageOfAgeCommand - The average_of_age command.
     * @param clearCommand - The clear command.
     * @param executeScriptCommand - The execute_script file_name command.
     * @param exitCommand - The exit command.
     * @param helpCommand - The help command.
     * @param historyCommand - The history command.
     * @param infoCommand - The info command.
     * @param printFieldDescendingCaveCommand - The print_field_descending_cave command.
     * @param removeByIdCommand - The remove_by_id id command.
     * @param removeByCharacterCommand - The remove_all_by_character character command.
     * @param reorderCommand - The reorder command.
     * @param saveCommand - The save command.
     * @param showCommand - The show command.
     * @param updateByIdCommand - The update_by_id id {element}.
     */

    public CommandManager(Command addCommand, Command addIfMaxCommand, Command averageOfAgeCommand,
                          Command clearCommand, Command executeScriptCommand, Command exitCommand, Command helpCommand,
                          Command historyCommand, Command infoCommand, Command printFieldDescendingCaveCommand,
                          Command removeByIdCommand, Command removeByCharacterCommand, Command reorderCommand,
                          Command saveCommand, Command showCommand, Command updateByIdCommand){
        this.addCommand = addCommand;
        this.addIfMaxCommand = addIfMaxCommand;
        this.averageOfAgeCommand = averageOfAgeCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.helpCommand = helpCommand;
        this.historyCommand = historyCommand;
        this.infoCommand = infoCommand;
        this.printFieldDescendingCaveCommand = printFieldDescendingCaveCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.removeByCharacterCommand = removeByCharacterCommand;
        this.reorderCommand = reorderCommand;
        this.saveCommand = saveCommand;
        this.showCommand = showCommand;
        this.updateByIdCommand = updateByIdCommand;

        putCommands();
    }

    /**
     * Method for filling Map with commands and their names.
     */

    public void putCommands(){
        commands.put("add", addCommand);
        commands.put("add_if_max", addIfMaxCommand);
        commands.put("average_of_age", averageOfAgeCommand);
        commands.put("clear", clearCommand);
        commands.put("execute_script", executeScriptCommand);
        commands.put("exit", exitCommand);
        commands.put("help", helpCommand);
        commands.put("history", historyCommand);
        commands.put("info", infoCommand);
        commands.put("print_field_descending_cave", printFieldDescendingCaveCommand);
        commands.put("remove_by_id", removeByIdCommand);
        commands.put("remove_all_by_character", removeByCharacterCommand);
        commands.put("reorder", reorderCommand);
        commands.put("save", saveCommand);
        commands.put("show", showCommand);
        commands.put("update_by_id", updateByIdCommand);
    }

    /**
     * Method for getting a Map with commands.
     * @return Returns dictionary with command names and their meaning.
     */

    public Map<String, Command> getCommands() {
        return this.commands;
    }

    /**
     * Method for getting command history.
     * @return Returns a list of previously used commands.
     */

    public ArrayList<String> getHistoryOfCommands(){
        return this.historyOfCommands;
    }

    /**
     * Method for adding commands to history.
     * @param command - The name of used command.
     */

    public void addToHistoryOfCommands(String command){
        this.historyOfCommands.add(command);
    }

    /**
     * A method for saving a collection of commands to a file before exiting the program.
     */

    public void saveHistoryOfCommands(){
        try(FileWriter fileWriter = new FileWriter(System.getenv("history"))){
            for(String command : getHistoryOfCommands()){
                fileWriter.write(command + "\n");
            }
            fileWriter.flush();
        }catch(IOException exception){
            System.out.println("----------------------");
            System.out.println("Please do not delete the file!");
        }
    }

    /**
     * The method that executes the help command.
     */

    public void helpCommand(){
        for(Command command : this.commands.values()){
            System.out.println(command.getName() + ": " + command.getDescription());
        }
    }

    /**
     * The method that executes the history command.
     */

    public void historyOfCommands(){
        for(String command : getHistoryOfCommands()){
            System.out.println(command);
        }
    }

    /**
     * Method for executing the execute_script command.
     * @param filePath - The path to the file.
     */

    public void executeScriptCommand(String filePath){
        Path pathOfFile = Path.of(filePath);
        String[] inputCommand;
        String nameOfCommand, argumentOfCommand;
        CommandType typeOfCommand;
        Dragon dragon = null;
        int idCounter = 0;
        try(Scanner scriptScanner = new Scanner(new File(pathOfFile.toString()))){
            String fileName = pathOfFile.getFileName().toString();
            if(this.namesOfScripts.contains(fileName)) throw new ExecutingScriptException();
            this.namesOfScripts.add(fileName);
            DragonMaker.setConsoleMode(false);
            DragonMaker.setConsoleScanner(scriptScanner);

            while(scriptScanner.hasNextLine()) {
                try {
                    inputCommand = UserInputManager.tokenizeArguments(scriptScanner);
                    nameOfCommand = inputCommand[0];
                    argumentOfCommand = inputCommand[1];
                    typeOfCommand = UserInputManager.isInputCommandValid(nameOfCommand, argumentOfCommand);

                    if (typeOfCommand == CommandType.NOT_VALID) throw new NoSuchCommandException();
                    Command userCommand = getCommands().get(nameOfCommand);
                    switch (typeOfCommand) {
                        case VALID -> {
                            if (userCommand.hasValidArgument(argumentOfCommand, dragon)) {
                                switch (nameOfCommand) {
                                    case "help" -> {
                                        userCommand.execute();
                                        helpCommand();
                                    }
                                    case "history" -> {
                                        userCommand.execute();
                                        historyOfCommands();
                                    }
                                    default -> userCommand.execute();
                                }
                            }
                        }
                        case TRANSMITTING -> {
                            idCounter += nameOfCommand.hashCode() / 12;
                            dragon = (nameOfCommand.contains("add")) ? DragonMaker.getDragon(idCounter) : DragonMaker.updateDragon();
                            if (userCommand.hasValidArgument(argumentOfCommand, dragon)) {
                                userCommand.execute();
                            }
                        }
                        case SCRIPT -> {
                            userCommand.execute();
                            executeScriptCommand(argumentOfCommand);
                        }
                    }
                    addToHistoryOfCommands(nameOfCommand);
                    saveHistoryOfCommands();
                }catch(NoSuchCommandException exception){
                    System.out.println("----------------------");
                    System.out.println("There is no such command!");
                }
            }
        }catch(ExecutingScriptException exception){
            System.out.println("----------------------");
            System.out.println("The file with the script has already been executed before!");
        }catch(IOException exception){
            System.out.println("----------------------");
            System.out.println("The file cannot be read or is missing!");
        }catch(NoSuchElementException exception){
            System.out.println("----------------------");
            System.out.println("Script file is empty!");
            System.out.println("Maybe you have reached the end of the script execution.");
        }
    }
}