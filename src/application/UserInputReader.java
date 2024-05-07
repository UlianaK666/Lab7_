package application;

import model.Coordinates;
import model.FuelType;
import model.Vehicle;
import model.VehicleType;

import java.util.*;

public class UserInputReader {

    private CollectionManager collectionManager;

    public UserInputReader(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String receiveName() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("Enter a name of vehicle. It cannot be empty:");
                    String name = scanner.nextLine();
                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty. Try again.");
                    } else {
                        return name;
                    }
                } catch (InputMismatchException inputMismatchException) {
                    System.out.println("Enter must be a string. Try again.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
            return null;
        }
    }

    public Integer receiveX() {
        try {
            while (true) {
                try {
                    System.out.println("Enter x coordinate:");
                    Scanner scanner = new Scanner(System.in);
                    Integer x = scanner.nextInt();
                    return x;
                } catch (InputMismatchException inputMismatchException) {
                    System.out.println("Enter must be an integer number. Try again.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
            return null;
        }
    }

    public Float receiveY() {
        try {
            while (true) {
                try {
                    System.out.println("Enter y coordinate:");
                    Scanner scanner = new Scanner(System.in);
                    Float y = scanner.nextFloat();
                    if (y > 351) {
                        System.out.println("Max value is 351. Try again.");
                        continue;
                    }
                    return y;
                } catch (InputMismatchException inputMismatchException) {
                    System.out.println("Enter must be an integer number. Try again.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
            return null;
        }
    }

    public Coordinates receiveCoordinates() {
        Integer x = receiveX();
        Float y = receiveY();
        Coordinates coordinates = new Coordinates(x, y);
        return coordinates;
    }

    public Date receiveDate() {
        return new Date();
    }

    public Float receiveEnginePower() {
        try {
            while (true) {
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter an engine power:");
                    Float enginePower = scanner.nextFloat();
                    if (enginePower <= 0) {
                        System.out.println("Value must be positive. Try again,");
                        continue;
                    }
                    return enginePower;
                } catch (InputMismatchException inputMismatchException) {
                    System.out.println("Enter must be an integer number. Try again.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
            return null;
        }
    }


    public VehicleType receiveVehicleType() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("Enter vehicle type. List of variants:");
                    System.out.println("1. CAR;");
                    System.out.println("2. BOAT;");
                    System.out.println("3. CHOPPER.");
                    System.out.println("Enter a value from the list or it's number:");
                    String input = scanner.nextLine();
                    if (input.equals("CAR") || input.equals("1")) {
                        return VehicleType.CAR;
                    } else if (input.equals("BOAT") || input.equals("2")) {
                        return VehicleType.BOAT;
                    } else if (input.equals("CHOPPER") || input.equals("3")) {
                        return VehicleType.CHOPPER;
                    } else {
                        System.out.println("Value must be a string from the list or it's number. Try again.");
                    }
                } catch (InputMismatchException inputMismatchException) {
                    System.out.println("Enter must be an integer number. Try again.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
            return null;
        }
    }

    public FuelType receiveFuelType() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("Enter fuel type. List of variants:");
                    System.out.println("1. DIESEL;");
                    System.out.println("2. ALCOHOL;");
                    System.out.println("3. MANPOWER;");
                    System.out.println("4. NUCLEAR.");
                    System.out.println("Enter a value from the list or it's number:");
                    String input = scanner.nextLine();
                    if (input.equals("DIESEL") || input.equals("1")) {
                        return FuelType.DIESEL;
                    } else if (input.equals("ALCOHOL") || input.equals("2")) {
                        return FuelType.ALCOHOL;
                    } else if (input.equals("MANPOWER") || input.equals("3")) {
                        return FuelType.MANPOWER;
                    } else if (input.equals("NUCLEAR") || input.equals("4")) {
                        return FuelType.NUCLEAR;
                    } else {
                        System.out.println("Value must be a string from the list. or it's number Try again.");
                    }
                } catch (InputMismatchException inputMismatchException) {
                    System.out.println("Enter must be an integer number. Try again.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
            return null;
        }
    }

    public Vehicle receiveVehicle() {
        Long id = IDGenerator.generateID();
        String name = receiveName();
        System.out.println("here");
        Coordinates coordinates = receiveCoordinates();
        Date creationDate = receiveDate();
        Float enginePower = receiveEnginePower();
        VehicleType type = receiveVehicleType();
        FuelType fuelType = receiveFuelType();
        return new Vehicle(id, name, coordinates, creationDate, enginePower, type, fuelType, collectionManager.getCurrentUser());
    }

    public List<String> receiveLoginAndPassword() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Account creating...");
            while (true) {
                try {
                    System.out.println("Enter your login:");
                    String login = scanner.nextLine();
                    System.out.println("Enter your password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter your password again:");
                    String password2 = scanner.nextLine();
                    if (password2.equals(password)) {
                        return Arrays.asList(login, password);
                    } else {
                        System.out.println("Passwords are not the same. Try again.");
                    }
                } catch (InputMismatchException inputMismatchException) {
                    System.out.println("Incorrect enter. Try again.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
            return null;
        }
    }
}
