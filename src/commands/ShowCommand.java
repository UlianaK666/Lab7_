package commands;

import application.CollectionManager;
import application.VehicleComparator;
import model.Vehicle;

import java.util.*;

public class ShowCommand {

    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute() {
        if (collectionManager.getCurrentUser() != null) {
            StringBuilder answer = new StringBuilder();
            Set<Vehicle> collection = collectionManager.getCollection();
            List<Vehicle> vehicleList = new ArrayList<>(collection);
            Collections.sort(vehicleList, new VehicleComparator());
            if (vehicleList.isEmpty()) {
                answer.append("Collection is empty.\n");
            } else {
                for (Vehicle currentVehicle : vehicleList) {
                    answer.append(currentVehicle.toString());
                    answer.append("\n");
                }
            }
            return answer.toString();
        } else {
            return "You need to login before executing this command.";
        }
    }

}
