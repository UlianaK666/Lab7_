package commands;

import application.CollectionManager;
import application.IDGenerator;
import model.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class RemoveByIdCommand {

    private CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(Long id) {
        if (collectionManager.getCurrentUser() != null) {
            Set<Vehicle> collection = collectionManager.getCollection();
            boolean wasFound = false;
            for (Vehicle current : collection) {
                if (current.getId().equals(id) && current.getOwner().equals(collectionManager.getCurrentUser())) {
                    wasFound = true;
                    collection.remove(current);
                    IDGenerator.removeId(current.getId());
                    break;
                }
            }
            if (wasFound == true) {
                return "Element has been removed.\n";
            } else {
                return "Element has not been removed because given ID is not exists in the collection or you are not a owner of this element.\n";
            }
        } else {
            return "You need to login before executing this command.";
        }
    }

}
