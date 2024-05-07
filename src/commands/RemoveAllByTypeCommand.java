package commands;

import application.CollectionManager;
import application.IDGenerator;
import model.FuelType;
import model.Vehicle;
import model.VehicleType;

import java.util.HashSet;
import java.util.Set;

public class RemoveAllByTypeCommand {

    private CollectionManager collectionManager;

    public RemoveAllByTypeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(VehicleType fuelType) {
        if (collectionManager.getCurrentUser() != null) {
            Set<Vehicle> collection = collectionManager.getCollection();
            HashSet<Vehicle> copy = new HashSet<>();
            for (Vehicle current : collection) {
                if (!current.getType().equals(fuelType) || !current.getOwner().equals(collectionManager.getCurrentUser())) {
                    copy.add(current);
                } else {
                    IDGenerator.removeId(current.getId());
                }
            }
            collectionManager.setCollection(copy);
            return "All items, belonging to you, type of which is equal for a given, has been removed.\n";
        } else {
            return "You need to login before executing this command.";
        }
    }

}
