package commands;

import application.CollectionManager;
import model.FuelType;
import model.Vehicle;
import model.VehicleType;

import java.util.HashSet;
import java.util.Set;

public class CountLessThanFuelTypeCommand {

    private CollectionManager collectionManager;

    public CountLessThanFuelTypeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(FuelType fuelType) {
        if (collectionManager.getCurrentUser() != null) {
            Set<Vehicle> collection = collectionManager.getCollection();
            int counter = 0;
            for (Vehicle current : collection) {
                if (String.valueOf(current.getFuelType()).length() - String.valueOf(fuelType).length() < 0) {

                    counter++;
                }
            }
            return "Number of items, fuel type of which lower than given, is : " + counter + "\n";
        } else {
            return "You need to login before executing this command.";
        }
    }
}
