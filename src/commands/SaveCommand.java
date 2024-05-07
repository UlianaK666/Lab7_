package commands;

import application.CollectionManager;
import application.DatabaseManager;
import application.FileManager;
import model.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class SaveCommand {

    private CollectionManager collectionManager;
    private DatabaseManager databaseManager;

    public SaveCommand(CollectionManager collectionManager, DatabaseManager databaseManager) {
        this.collectionManager = collectionManager;
        this.databaseManager = databaseManager;
    }

    public String execute() {
        if (collectionManager.getCurrentUser() != null) {
            Set<Vehicle> collection = collectionManager.getCollection();
            String answer = databaseManager.saveCollection(collection);
            return answer;
        } else {
            return "You need to login before executing this command.";
        }
    }

}
