package application;

import model.Coordinates;
import model.FuelType;
import model.Vehicle;
import model.VehicleType;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FileManager {

    private String filepath;

    public FileManager(String filepath) {
        this.filepath = filepath;
    }

    public String saveCollection(HashSet<Vehicle> collection) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            StringBuilder stringBuilder = new StringBuilder();
            for (Vehicle current : collection) {
                stringBuilder
                        .append(current.getId()).append(",")
                        .append(current.getName()).append(",")
                        .append(current.getCoordinates().getX()).append(",")
                        .append(current.getCoordinates().getY()).append(",")
                        .append(current.getCreationDate().toString()).append(",")
                        .append(current.getEnginePower()).append(",")
                        .append(current.getType().toString()).append(",")
                        .append(current.getFuelType().toString()).append("\n");
            }
            String data = stringBuilder.toString();
            writer.write(data);
            writer.close();
            return "Collection has been saved.\n";
        } catch (IOException ioException) {
            return "Cannot save collection.\n";
        }
    }

    public HashSet<Vehicle> downloadCollection() {
        System.out.println(filepath);
        HashSet<Vehicle> collection = new HashSet<>();
        BufferedReader reader;
        int good = 0;
        int bad = 0;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                Long id = IDGenerator.generateID();
                String name = parts[1];
                Coordinates coordinates = new Coordinates();
                Integer x = Integer.parseInt(parts[2]);
                Float y = Float.parseFloat(parts[3]);
                coordinates.setX(x);
                coordinates.setY(y);
                Date creationDate = new Date();
                Float enginePower = Float.parseFloat(parts[5]);
                VehicleType type = VehicleType.valueOf(parts[6]);
                FuelType fuelType = FuelType.valueOf(parts[7]);
//                Vehicle vehicle = new Vehicle(id, name, coordinates, creationDate, enginePower, type, fuelType);
//                if (checkVehicle(vehicle) == true) {
//                    IDGenerator.saveId(vehicle.getId());
//                    collection.add(vehicle);
//                    good++;
//                } else {
//                    bad++;
//                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
            System.out.println("Collection downloaded. Read: " + (good + bad) + " elements.");
            System.out.println("Correct items number, which was downloaded: " + good);
            System.out.println("Incorrect items number, which wasn't downloaded: " + bad);
            return collection;
        } catch (IOException exception) {
            System.out.println("Downloaded empty collection.");
            return new HashSet<>();
        }
    }
    private boolean checkVehicle(Vehicle vehicle) {
        if (vehicle.getId() == null) return false;
        if (vehicle.getId() <= 0) return false;
        if (!IDGenerator.checkIfIDUnique(vehicle.getId())) return false;
        if (vehicle.getName() == null) return false;
        if (vehicle.getName().isEmpty()) return false;
        if (vehicle.getCoordinates() == null) return false;
        if (vehicle.getCoordinates().getX() == null) return false;
        if (vehicle.getCoordinates().getY() == null) return false;
        if (vehicle.getCoordinates().getY() > 351) return false;
        if (vehicle.getCreationDate() == null) return false;
        if (vehicle.getEnginePower() == null) return false;
        if (vehicle.getEnginePower() <= 0) return false;
        if (vehicle.getFuelType() == null) return false;
        return true;
    }
}
