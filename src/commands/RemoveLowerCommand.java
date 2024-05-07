package commands;

import application.CollectionManager;
import application.IDGenerator;
import model.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class RemoveLowerCommand {

    private CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(Vehicle vehicle) {
        if (collectionManager.getCurrentUser() != null) {
            Set<Vehicle> collection = collectionManager.getCollection();
            HashSet<Vehicle> copy = new HashSet<>();
            for (Vehicle current : collection) {
                if (current.compareTo(vehicle) >= 0 || !current.getOwner().equals(collectionManager.getCurrentUser())) {
                    copy.add(current);
                } else {
                    IDGenerator.removeId(current.getId());
                }
            }
            collectionManager.setCollection(copy);
            return "All items, belonging to you, which lower than given has been removed.\n";
        } else {
            return "You need to login before executing this command.";
        }
    }

}