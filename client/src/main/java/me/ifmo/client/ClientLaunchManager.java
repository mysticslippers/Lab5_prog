package me.ifmo.client;

import me.ifmo.common.command.CommandType;
import me.ifmo.common.data.Dragon;
import me.ifmo.common.exceptions.NoSuchCommandException;
import me.ifmo.common.utils.DragonMaker;
import me.ifmo.common.utils.UserInputManager;
import me.ifmo.server.utils.CollectionManager;
import me.ifmo.server.utils.CommandManager;
import me.ifmo.server.utils.commands.*;

import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class ClientLaunchManager {
    private Scanner userScanner = new Scanner(System.in);

    /**
     * Method for setting a user scanner.
     * @param userScanner - User scanner.
     */

    public void setUserScanner(Scanner userScanner){
        this.userScanner = userScanner;
    }

    /**
     * Method for getting a user scanner.
     * @return Returns user scanner.
     */

    public Scanner getUserScanner() {
        return this.userScanner;
    }

    public void launch(){
        LinkedHashSet<Dragon> collection = new LinkedHashSet<>();
        CollectionManager collectionManager = new CollectionManager(collection);
        CommandManager commandManager = new CommandManager(new AddCommand(collectionManager), new AddIfMaxCommand(collectionManager),
                new AverageOfAgeCommand(collectionManager), new ClearCommand(collectionManager), new ExecuteScriptCommand(),
                new ExitCommand(), new HelpCommand(), new HistoryCommand(), new InfoCommand(collectionManager),
                new PrintFieldDescendingCaveCommand(collectionManager), new RemoveByCharacterCommand(collectionManager),
                new RemoveByIdCommand(collectionManager), new ReorderCommand(collectionManager),
                new SaveCommand(collectionManager), new ShowCommand(collectionManager), new UpdateByIdCommand(collectionManager));

        collectionManager.loadCollectionFromFile();
        String nameOfCommand = "";
        long tmpCounter = 0;
        do{
            String[] userCommand;
            String argumentOfCommand;
            CommandType typeOfCommand;
            Dragon dragon = null;
            try{
                DragonMaker.setConsoleMode(true);
                DragonMaker.setConsoleScanner(getUserScanner());
                System.out.println("----------------------");
                System.out.println("Enter the command: ");
                userCommand = UserInputManager.tokenizeArguments(getUserScanner());
                nameOfCommand = userCommand[0];
                argumentOfCommand = userCommand[1];
                typeOfCommand = UserInputManager.isInputCommandValid(nameOfCommand, argumentOfCommand);
                if(typeOfCommand == CommandType.NOT_VALID) throw new NoSuchCommandException();
                Command inputCommand = commandManager.getCommands().get(nameOfCommand);
                switch (typeOfCommand){
                    case VALID -> {
                        if(inputCommand.hasValidArgument(argumentOfCommand, dragon)){
                            switch (nameOfCommand){
                                case "help" -> {
                                    inputCommand.execute();
                                    commandManager.helpCommand();
                                }
                                case "history" -> {
                                    inputCommand.execute();
                                    commandManager.historyOfCommands();
                                }
                                default -> inputCommand.execute();
                            }
                        }
                    }
                    case SCRIPT -> {
                        inputCommand.execute();
                        commandManager.executeScriptCommand(argumentOfCommand);
                    }
                    case TRANSMITTING -> {
                        tmpCounter += nameOfCommand.hashCode() / 12;
                        dragon = (nameOfCommand.contains("add")) ? DragonMaker.getDragon(tmpCounter) : DragonMaker.updateDragon();
                        if(inputCommand.hasValidArgument(argumentOfCommand, dragon)){
                            inputCommand.execute();
                        }
                    }
                }
                commandManager.addToHistoryOfCommands(nameOfCommand);
                commandManager.saveHistoryOfCommands();
            }catch(NoSuchCommandException exception){
                System.out.println("----------------------");
                System.out.println("There is no such command! \nEnter the help command for a complete list of commands.");
            }catch(IllegalArgumentException exception){
                System.out.println("----------------------");
                System.out.println("Enter at least some command!");
            }catch(NoSuchElementException exception){
                System.out.println("----------------------");
                System.out.println("Goodbye!");
            }
        }while(!nameOfCommand.equals("exit"));
    }
}
