package application;

import model.Vehicle;

import java.util.*;

public class CollectionManager {

    private HashSet<Vehicle> collection;
    private Date initTime;
    private String currentUser;

    public CollectionManager(DatabaseManager databaseManager) {
        this.collection = databaseManager.downloadCollection();
        initTime = new Date();
    }

    public Set<Vehicle> getCollection() {
        return Collections.synchronizedSet(collection);
    }

    public void setCollection(HashSet<Vehicle> collection) {
        this.collection = collection;
    }

    public Date getInitTime() {
        return initTime;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void logout() {
        this.currentUser = null;
    }
}
