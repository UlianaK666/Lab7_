package commands;

import application.CollectionManager;
import application.UserInputReader;
import model.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class UpdateCommand {

    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(Long id) {
        if (collectionManager.getCurrentUser() != null) {
            Set<Vehicle> collection = collectionManager.getCollection();
            boolean wasFound = false;
            Vehicle forRemove = new Vehicle();
            for (Vehicle current : collection) {
                if (current.getId().equals(id)) {
                    wasFound = true;
                    forRemove = current;
                    break;
                }
            }
            if (wasFound == true) {
                if (forRemove.getOwner().equals(collectionManager.getCurrentUser())) {
                    UserInputReader reader = new UserInputReader(collectionManager);
                    Vehicle vehicle = reader.receiveVehicle();
                    collection.remove(forRemove);
                    vehicle.setId(id);
                    collection.add(vehicle);
                    return "Element has been updated.\n";
                } else {
                    return "You are not owner of this item. Cannot update.\n";
                }
            } else {
                return "Element has not been updated because given ID is not exists in the collection\n";
            }
        } else {
            return "You need to login before executing this command.";
        }
    }
}
