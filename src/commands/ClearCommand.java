package commands;

import application.CollectionManager;
import model.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class ClearCommand {

    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute() {
        if (collectionManager.getCurrentUser() != null) {
            Set<Vehicle> collection = collectionManager.getCollection();
            HashSet<Vehicle> copy = new HashSet<>();
            for (Vehicle vehicle : collection) {
                if (!vehicle.getOwner().equals(collectionManager.getCurrentUser())) {
                    copy.add(vehicle);
                }
            }
            collectionManager.setCollection(copy);
            return "Collection has been cleared. All your items has been deleted.\n";
        } else {
            return "You need to login before executing this command.";
        }
    }
}
