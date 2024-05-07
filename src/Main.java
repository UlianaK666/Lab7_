import model.Coordinates;
import model.FuelType;
import model.Vehicle;
import model.VehicleType;

import java.util.*;

/*
.java - .class - 1010101
 */
public class Main {
    public static void main(String[] args) {
       Vehicle vehicle = new Vehicle(1L, "abc", new Coordinates(1, 1.5F), new Date(), 0.5F,
               VehicleType.CAR, FuelType.ALCOHOL, null);
        System.out.println(vehicle);
    }
}