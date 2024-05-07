package commands;

import application.CollectionManager;
import model.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class InfoCommand {

    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute() {
        if (collectionManager.getCurrentUser() != null) {
            StringBuilder answer = new StringBuilder();
            Set<Vehicle> collection = collectionManager.getCollection();
            answer.append("Type of collection: ").append(collection.getClass()).append("\n");
            answer.append("Time of initialization: ").append(collectionManager.getInitTime()).append("\n");
            answer.append("Number of items: ").append(collection.size()).append("\n");
            return answer.toString();
        } else {
            return "You need to login before executing this command.";
        }
    }

}
