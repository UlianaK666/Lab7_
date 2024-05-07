package commands;

import application.CollectionManager;
import model.Vehicle;
import model.VehicleType;

import java.util.HashSet;
import java.util.Set;

public class MaxByTypeCommand {

    private CollectionManager collectionManager;

    public MaxByTypeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute() {
        if (collectionManager.getCurrentUser() != null) {
            Set<Vehicle> collection = collectionManager.getCollection();
            Vehicle maxVehicle = new Vehicle();
            boolean isFirst = true;
            for (Vehicle current : collection) {
                if (isFirst) {
                    maxVehicle = current;
                    isFirst = false;
                } else {
                    if (String.valueOf(current.getType()).length() - String.valueOf(maxVehicle.getType()).length() > 0) {

                        maxVehicle = current;
                    }
                }
            }
            if (isFirst) {
                return "Collection is empty.\n";
            } else {
                return "One of max vehicles by type is: " + maxVehicle + "\n";
            }
        } else {
            return "You need to login before executing this command.";
        }
    }

}
