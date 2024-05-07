package commands;

import application.CollectionManager;
import application.IDGenerator;
import model.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class AddIfMaxCommand {

    private CollectionManager collectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(Vehicle vehicle) {
        if (collectionManager.getCurrentUser() != null) {
            System.out.println("Vehicle: " + vehicle);
            Set<Vehicle> collection = collectionManager.getCollection();
            Vehicle maxVehicle = new Vehicle();
            boolean isFirstVehicle = true;
            for (Vehicle current : collection) {
                if (isFirstVehicle == true) {
                    maxVehicle = current;
                    isFirstVehicle = false;
                    continue;
                }
                if (current.compareTo(maxVehicle) > 0) {
                    maxVehicle = current;
                }
            }
            if (vehicle.compareTo(maxVehicle) > 0) {
                IDGenerator.saveId(vehicle.getId());
                collection.add(vehicle);
                return "Element has been added.\n";
            } else {
                return "Element has not been added, because it is not greater than maximal.\n";
            }
        } else {
            return "You need to login before executing this command.";
        }
    }

}