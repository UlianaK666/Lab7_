package commands;

import application.CollectionManager;
import application.IDGenerator;
import model.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class AddCommand {

    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(Vehicle newVehicle) {
        if (collectionManager.getCurrentUser() != null) {
            Set<Vehicle> collection = this.collectionManager.getCollection();
            int initialSize = collection.size();
            IDGenerator.saveId(newVehicle.getId());
            collection.add(newVehicle);
            int currentSize = collection.size();
            if (currentSize == initialSize + 1) {
                return "Element has been added to collection.\n";
            } else {
                return "Element has not been added because it is already contains in set.\n";
            }
        } else {
            return "You need to login before executing this command.";
        }
    }

}
