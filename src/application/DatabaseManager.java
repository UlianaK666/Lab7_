package application;

import model.Coordinates;
import model.FuelType;
import model.Vehicle;
import model.VehicleType;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager() {
        String URL = "jdbc:postgresql://pg:5432/studs";
        String login = "s371179";
        String password = "bTxkK682UNN1t0NO";
        while (true) {
            try {
                this.connection = DriverManager.getConnection(URL, login, password);
                break;
            } catch (PSQLException psqlException) {
                System.out.println("Cannot connect to database, because SSH tunnel is inactive. Reconnect...");
            } catch (SQLException sqlException) {
                System.out.println("Impossible to connect to database.");
                System.exit(1);
            }
        }
    }

    public HashSet<Vehicle> downloadCollection() {
        int good = 0;
        int bad = 0;
        Set<Long> ids = new LinkedHashSet<>();
        try (ResultSet answer = this.getConnection().createStatement().
                executeQuery("SELECT * FROM vehicle")) {
            HashSet<Vehicle> collection = new HashSet<>();
            while (answer.next()) {
                long id = answer.getInt("id");
                String name = answer.getString("name");
                Coordinates coordinates = new Coordinates(answer.getInt("x"), answer.getFloat("y"));
                String stringDate = answer.getString("creation_date");
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Date creationDate;
                try {
                    creationDate =  dateFormat.parse(answer.getString("creation_date"));
                } catch (ParseException parseException) {
                    creationDate = new Date();
                }
                Float enginePower = answer.getFloat("engine_power");
                VehicleType type = VehicleType.valueOf(answer.getString("type"));
                FuelType fuelType = FuelType.valueOf(answer.getString("fuel_type"));
                String owner = answer.getString("owner");
                Vehicle vehicle = new Vehicle(id, name, coordinates, creationDate, enginePower, type, fuelType, owner);
                if (checkVehicle(vehicle) == true) {
                    collection.add(vehicle);
                    ids.add(vehicle.getId());
                    good++;
                } else {
                    bad++;
                }
            }
            System.out.println("Collection downloaded. Loaded " + good + " elements.");
            System.out.println("But " + bad + " elements were incorrect and not downloaded.");
            IDGenerator.setIDs(ids);
            return collection;
        } catch (SQLException e) {
            System.out.println("Collection can not be loaded now. Please try later.");
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
        if (vehicle.getOwner() == null) return false;
        return true;
    }

    public String saveCollection(Set<Vehicle> vehicles) {
        try {
            String deleteSql = "DELETE FROM vehicle";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.executeUpdate();
            } catch (SQLException e) {
                return "Cannot renew collection content in database.";
            }
            String sql = "INSERT INTO vehicle (name, x, y, creation_date, engine_power, type, fuel_type, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (Vehicle vehicle : vehicles) {
                    preparedStatement.setString(1, vehicle.getName());
                    preparedStatement.setFloat(2, vehicle.getCoordinates().getX());
                    preparedStatement.setFloat(3, vehicle.getCoordinates().getY());
                    preparedStatement.setString(4, vehicle.getCreationDate().toString());
                    preparedStatement.setFloat(5, vehicle.getEnginePower());
                    preparedStatement.setString(6, vehicle.getType().toString());
                    preparedStatement.setString(7, vehicle.getFuelType().toString());
                    preparedStatement.setString(8, vehicle.getOwner());
                    preparedStatement.executeUpdate();
                }
                return "Collection saved.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
           return "Cannot save collection content in database.";
        }
    }

    public boolean login(String username, String password) {
        try {
            Cryptographer cryptographer = new Cryptographer();
            String login = "SELECT COUNT(*) FROM account WHERE login = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(login);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, cryptographer.encrypt(password));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int amount = resultSet.getInt(1);
            if (amount == 1) {
                return true;
            } else if(amount > 1) {
                System.out.println("Database error. Username is not unique. Try later.");
                System.exit(1);
                return false;
            } else throw new SQLException();
        } catch (SQLException sqlException) {
            System.out.println("User not found. You can register this account.");
            return false;
        }
    }

    public boolean register(String username, String password) {
        try {
            Cryptographer cryptographer = new Cryptographer();
            String login = "SELECT COUNT(*) FROM account WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(login);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int amount = resultSet.getInt(1);
            if (amount == 0) {
                String register = "INSERT INTO account (login, password) values (?, ?)";
                PreparedStatement preparedStatement2 = connection.prepareStatement(register);
                preparedStatement2.setString(1, username);
                preparedStatement2.setString(2, cryptographer.encrypt(password));
                preparedStatement2.execute();
                return true;
            } else {
                System.out.println("User is already exist.");
                return false;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("User is already exist.");
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
