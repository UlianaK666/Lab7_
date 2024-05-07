package application;

import model.Vehicle;

import java.util.Comparator;

public class VehicleComparator implements Comparator<Vehicle> {


    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return (int) (o1.getId() - o2.getId());
    }

}
