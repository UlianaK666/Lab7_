package application;

import commands.*;
import model.FuelType;
import model.VehicleType;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandReader {

    private CollectionManager collectionManager;
    private DatabaseManager databaseManager;

    public CommandReader(CollectionManager collectionManager, DatabaseManager databaseManager) {
        this.collectionManager = collectionManager;
        this.databaseManager = databaseManager;
    }

    public void run() {
        try {
            Scanner reader = new Scanner(System.in);
            final String[] command = new String[1];
            final String[] answer = new String[1];
            final StringBuilder[] stringBuilder = {new StringBuilder()};
            System.out.println("Enter command:");
            UserInputReader userInputReader = new UserInputReader(collectionManager);
            //ExecutorService executorService = Executors.newCachedThreadPool();
            //executorService.execute(() -> {
            while (!(command[0] = reader.nextLine()).equals("exit")) {
                    try {
                        String[] finalUserCommand = command[0].trim().split(" ", 3);
                        switch (finalUserCommand[0]) {
                            case "login":
                                String login = finalUserCommand[1];
                                String password = finalUserCommand[2];
                                if (databaseManager.login(login, password)) {
                                    collectionManager.setCurrentUser(login);
                                    System.out.println("You successfully logged in. Welcome, " + login + "!");
                                } else {
                                    System.out.println("Login or/and password are not correct. Try again.");
                                }
                                break;
                            case "logout":
                                System.out.println("Goodbye, " + collectionManager.getCurrentUser() + "!");
                                collectionManager.logout();
                                break;
                            case "register":
                                List<String> credentials = userInputReader.receiveLoginAndPassword();
                                String login1 = credentials.get(0);
                                String password1 = credentials.get(1);
                                if (databaseManager.register(login1, password1)) {
                                    System.out.println("Account has been created. Now you can login.");
                                } else {
                                    System.out.println("Account is already exists. Try to create another account or login using this.");
                                }
                                break;
                            case "help":
                                answer[0] = new HelpCommand(collectionManager).execute();
                                System.out.println(answer[0]);
                                break;
                            case "info":
                                answer[0] = new InfoCommand(collectionManager).execute();
                                System.out.println(answer[0]);
                                break;
                            case "show":
                                answer[0] = new ShowCommand(collectionManager).execute();
                                System.out.println(answer[0]);
                                break;
                            case "add":
                                answer[0] = new AddCommand(collectionManager).execute(userInputReader.receiveVehicle());
                                System.out.println(answer[0]);
                                break;
                            case "update":
                                answer[0] = new UpdateCommand(collectionManager)
                                        .execute(Long.parseLong(finalUserCommand[1]));
                                System.out.println(answer[0]);
                                break;
                            case "remove_by_id":
                                answer[0] = new RemoveByIdCommand(collectionManager).execute(Long.parseLong(finalUserCommand[1]));
                                System.out.println(answer[0]);
                                break;
                            case "clear":
                                answer[0] = new ClearCommand(collectionManager).execute();
                                System.out.println(answer[0]);
                                break;
                            case "save":
                                answer[0] = new SaveCommand(collectionManager, databaseManager).execute();
                                System.out.println(answer[0]);
                                break;
                            case "execute_script":
                                answer[0] = new ExecuteScriptCommand(collectionManager, databaseManager).execute(finalUserCommand[1]);
                                System.out.println(answer[0]);
                                break;
                            case "exit":
                                stringBuilder[0].append("This script cannot to contain this command.");
                            case "add_if_min":
                                answer[0] = new AddIfMinCommand(collectionManager).execute(userInputReader.receiveVehicle());
                                System.out.println(answer[0]);
                                break;
                            case "add_if_max":
                                answer[0] = new AddIfMaxCommand(collectionManager).execute(userInputReader.receiveVehicle());
                                System.out.println(answer[0]);
                                break;
                            case "remove_lower":
                                answer[0] = new RemoveLowerCommand(collectionManager).execute(userInputReader.receiveVehicle());
                                System.out.println(answer[0]);
                                break;
                            case "remove_all_by_type":
                                answer[0] = new RemoveAllByTypeCommand(collectionManager)
                                        .execute(VehicleType.valueOf(finalUserCommand[1]));
                                System.out.println(answer[0]);
                                break;
                            case "max_by_type":
                                answer[0] = new MaxByTypeCommand(collectionManager).execute();
                                System.out.println(answer[0]);
                                break;
                            case "count_less_than_fuel_type":
                                answer[0] = new CountLessThanFuelTypeCommand(collectionManager).execute(FuelType.valueOf(finalUserCommand[1]));
                                System.out.println(answer[0]);
                                break;
                            default:
                                System.out.println("Unknown command. Write help for getting list of available commands.");
                                break;
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                        System.out.println("Incorrect command entering. Check output of help command and try again.");
                    }
            }
            //});
            //executorService.shutdown();
            //while (!executorService.isTerminated()) {
            //    Thread.sleep(100);
            //}
            reader.close();
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        } //catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
