package commands;

import application.CollectionManager;
import application.DatabaseManager;
import application.FileManager;
import application.UserInputReader;
import model.VehicleType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ExecuteScriptCommand {

    private CollectionManager collectionManager;
    private DatabaseManager databaseManager;
    private static final Set<String> callStack = new LinkedHashSet<>();

    public ExecuteScriptCommand(CollectionManager collectionManager, DatabaseManager databaseManager) {
        this.collectionManager = collectionManager;
        this.databaseManager = databaseManager;
    }

    public String execute(String nameOfFile) {
        if (collectionManager.getCurrentUser() != null) {
            if (!callStack.contains(nameOfFile)) {
                callStack.add(nameOfFile);
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(nameOfFile));
                    String[] finalUserCommand;
                    String command;
                    String answer;
                    StringBuilder stringBuilder = new StringBuilder();
                    UserInputReader userInputReader = new UserInputReader(collectionManager);
                    while ((command = reader.readLine()) != null) {
                        try {
                            finalUserCommand = command.trim().toLowerCase().split(" ", 2);
                            switch (finalUserCommand[0]) {
                                case "help":
                                    answer = new HelpCommand(collectionManager).execute();
                                    stringBuilder.append(answer);
                                    break;
                                case "info":
                                    answer = new InfoCommand(collectionManager).execute();
                                    stringBuilder.append(answer);
                                    break;
                                case "show":
                                    answer = new ShowCommand(collectionManager).execute();
                                    stringBuilder.append(answer);
                                    break;
                                case "add":
                                    answer = new AddCommand(collectionManager).execute(userInputReader.receiveVehicle());
                                    stringBuilder.append(answer);
                                    break;
                                case "update":
                                    answer = new UpdateCommand(collectionManager)
                                            .execute(Long.parseLong(finalUserCommand[1]));
                                    stringBuilder.append(answer);
                                    break;
                                case "remove_by_id":
                                    answer = new RemoveByIdCommand(collectionManager).execute(Long.parseLong(finalUserCommand[1]));
                                    stringBuilder.append(answer);
                                    break;
                                case "clear":
                                    answer = new ClearCommand(collectionManager).execute();
                                    stringBuilder.append(answer);
                                    break;
                                case "save":
                                    answer = new SaveCommand(collectionManager, databaseManager).execute();
                                    stringBuilder.append(answer);
                                    break;
                                case "execute_script":
                                    stringBuilder.append(new ExecuteScriptCommand(collectionManager, databaseManager)
                                            .execute(finalUserCommand[1]));
                                    break;
                                case "exit":
                                    stringBuilder.append("This script cannot to contain this command.");
                                case "add_if_min":
                                    answer = new AddIfMinCommand(collectionManager).execute(userInputReader.receiveVehicle());
                                    stringBuilder.append(answer);
                                    break;
                                case "add_if_max":
                                    answer = new AddIfMaxCommand(collectionManager).execute(userInputReader.receiveVehicle());
                                    stringBuilder.append(answer);
                                    break;
                                case "remove_lower":
                                    answer = new RemoveLowerCommand(collectionManager).execute(userInputReader.receiveVehicle());
                                    stringBuilder.append(answer);
                                    break;
                                case "remove_all_by_type":
                                    answer = new RemoveAllByTypeCommand(collectionManager)
                                            .execute(VehicleType.valueOf(finalUserCommand[1]));
                                    stringBuilder.append(answer);
                                    break;
                                case "max_by_type":
                                    answer = new MaxByTypeCommand(collectionManager).execute();
                                    stringBuilder.append(answer);
                                    break;
                                case "count_less_than_fuel_type":
                                    answer = new AddIfMinCommand(collectionManager).execute(userInputReader.receiveVehicle());
                                    stringBuilder.append(answer);
                                    break;
                                default:
                                    reader.readLine();
                                    break;
                            }
                        } catch (Exception e) {
                            stringBuilder.append("Incorrect command entering. Check output of help command and try again.\n");
                        }
                    }
                    callStack.remove(nameOfFile);
                    reader.close();
                    return stringBuilder.toString();
                } catch (FileNotFoundException fileNotFoundException) {
                    return "File not found. Try again.";
                } catch (IOException ioException) {
                    return "File reading exception. Try again.";
                }
            } else {
                return "Ring recursion detected. Script executing aborted.\n";
            }
        } else {
            return "You need to login before executing this command.";
        }
    }
}
