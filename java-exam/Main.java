import java.util.Scanner;

/**
 * LTU Rent-a-car program
 * This program is a car rental system where users can add cars to a fleet and
 * then rent them out. The program has functionality for adding, renting,
 * returning
 * and printing overviews for the current fleet and rentals.
 *
 * @author Erik Forsberg (erkfor-4)
 **/

public class Main {

    // Constants for car fleet and rentals
    public static final int MAX_CARS = 100;
    public static final int MAX_RENTALS = 100;
    public static final int FLEET_SIZE = 3;
    public static final int RENTAL_SIZE = 5;

    public static final int REGISTRATION_NUMBER = 0;

    // Constants for car fleet
    public static final int MAKE_AND_MODEL = 1;
    public static final int IS_RENTED = 2;

    // Constants for rentals
    public static final int TIME_OF_RENTING = 1;
    public static final int TIME_OF_RETURNING = 2;
    public static final int RENTER_NAME = 3;
    public static final int COST = 4;

    // Constants for rental status
    public static final String RENTED = "Rented";

    public static final String NOT_RENTED = "Available";

    // Constants for error messages
    public static final String ERROR_INVALID_MENU_ITEM = "Invalid menu item";
    public static final String ERROR_INVALID_REGISTRATION_NUMBER = "Invalid "
            + "registration number";
    public static final String ERROR_INVALID_MAKE_AND_MODEL = "Invalid make "
            + "and model";
    public static final String ERROR_INVALID_NAME = "Invalid name";
    public static final String ERROR_INVALID_TIME = "Invalid time format";

    // Other constants
    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_ITEM_4 = 4;
    public static final int MENU_ITEM_5 = 5;
    public static final int MENU_ITEM_Q = -1;

    public static final int REGISTRATION_NUMBER_LENGTH = 6;

    public static final int INVALID_RESULT = -1;

    public static final int HOURS_IN_DAY = 24;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int TIME_FORMAT_LENGTH = 5;

    public static final int MINUTE_FEE = 2;

    private static Scanner userInputScanner = new Scanner(System.in);

    public static void main(final String[] args) {
        userInputScanner.useDelimiter(System.lineSeparator());

        // Initialize car fleet and rental lists.
        String[][] carFleet = new String[MAX_CARS][FLEET_SIZE];
        String[][] rentalList = new String[MAX_RENTALS][RENTAL_SIZE];

        System.out.println("--------------------------------");
        System.out.println("# LTU Rent-a-car");
        System.out.println("--------------------------------");

        // Menu system
        boolean running = true;
        while (running) {
            switch (menu()) {
                case MENU_ITEM_1:
                    // Add a car to the fleet
                    addCar(carFleet);
                    break;
                case MENU_ITEM_2:
                    // Rent a car from the fleet
                    rentCar(carFleet, rentalList);
                    break;
                case MENU_ITEM_3:
                    // Return a rented car
                    returnCar(carFleet, rentalList);
                    break;
                case MENU_ITEM_4:
                    // Print the current car fleet
                    printCarFleet(carFleet);
                    break;
                case MENU_ITEM_5:
                    // Print a summary of rentals and revenue
                    printRentalSummary(rentalList);
                    break;
                case MENU_ITEM_Q:
                    // Exit the program
                    running = false;
                    break;
                default:
                    System.out.println(ERROR_INVALID_MENU_ITEM);
                    break;
            }
        }
    }

    /**
     * Displays a menu and returns user choice.
     *
     * @return User's choice as an integer.
     */
    public static int menu() {
        System.out.println("1. Add car to fleet");
        System.out.println("2. Rent a car");
        System.out.println("3. Return a car");
        System.out.println("4. Print car fleet");
        System.out.println("5. Print rental summary");
        System.out.println("q. End program");
        System.out.print("> Enter your option: ");

        int choice = menuInput();
        return choice;
    }

    /**
     * Reads user input for menu selection.
     * Validates the input ensuring an integer or q was entered.
     *
     * @return User's choice as an integer.
     */
    public static int menuInput() {
        // Flag to check if a valid input has been entered
        while (true) {
            if (userInputScanner.hasNextInt()) {
                // Valid integer input
                return Math.abs(userInputScanner.nextInt());
            } else if (userInputScanner.hasNext()) {
                String input = userInputScanner.next();
                if (input.equalsIgnoreCase("q")) {
                    // Valid quit input
                    return MENU_ITEM_Q;
                }
            }
            System.out.println(ERROR_INVALID_MENU_ITEM);
        }
    }

    /**
     * Adds a car to the fleet.
     *
     * @param carFleet
     */
    public static void addCar(final String[][] carFleet) {
        // Set registration number
        System.out.print("Enter registration number: ");
        String regNumber = registrationNumberInput();
        // Check if car is already in fleet
        for (int i = 0; i < carFleet.length; i++) {
            if (carFleet[i][REGISTRATION_NUMBER] != null) {
                if (carFleet[i][REGISTRATION_NUMBER].equals(regNumber)) {
                    System.out.println(
                            regNumber + " already exists in the fleet");
                    return;
                }
            }
        }
        // Set make and model
        System.out.print("Enter make and model: ");
        String makeAndModel = makeAndModelInput();

        // Check if there is free space in the fleet
        int freeSpace = findFreeSpace(carFleet);
        if (freeSpace != -1) {
            // Add car to fleet
            carFleet[freeSpace][REGISTRATION_NUMBER] = regNumber;
            carFleet[freeSpace][MAKE_AND_MODEL] = makeAndModel;
            carFleet[freeSpace][IS_RENTED] = NOT_RENTED;
            System.out.println(makeAndModel + " with registration number "
                    + regNumber + " was added to car fleet.");
        } else {
            System.out.println("No free space in car fleet");
        }
    }

    /**
     * Reads and validates user input for registration number.
     *
     * @return User's registration number as a string.
     */
    public static String registrationNumberInput() {
        String regNumber = "";
        boolean validInput = false;
        while (!validInput) {
            // Sets valid input flag to true, any invalid input will
            // set it back to false.
            validInput = true;
            regNumber = userInputScanner.next();
            // Check if the input is the correct length
            if (regNumber.length() == REGISTRATION_NUMBER_LENGTH) {
                // Check if the first three characters are letters
                for (int i = 0; i < 3; i++) {
                    if (!Character.isLetter(regNumber.charAt(i))) {
                        validInput = false;
                        System.out.println(ERROR_INVALID_REGISTRATION_NUMBER);
                        break;
                    }
                }
                // If the previous check was successful, check if the last
                // three characters are digits
                if (validInput) {
                    for (int i = 3; i < REGISTRATION_NUMBER_LENGTH; i++) {
                        if (!Character.isDigit(regNumber.charAt(i))) {
                            validInput = false;
                            System.out
                                    .println(ERROR_INVALID_REGISTRATION_NUMBER);
                            break;
                        }
                    }
                }
            } else {
                validInput = false;
                System.out.println(ERROR_INVALID_REGISTRATION_NUMBER);
            }
        }
        return regNumber;
    }

    /**
     * Reads and validates user input for make and model.
     *
     * @return User's make and model as a string.
     */
    public static String makeAndModelInput() {
        String makeAndModel = "";
        while (true) {
            makeAndModel = userInputScanner.next();
            // Check if the input is empty
            if (makeAndModel.isEmpty()) {
                System.out.println(ERROR_INVALID_MAKE_AND_MODEL);
            } else {
                return makeAndModel;
            }
        }
    }

    /**
     * Reads and validates user input for time.
     *
     * @return User's time as a string.
     */
    public static String timeInput() {
        String time = "";
        while (true) {
            time = userInputScanner.next();
            // Initial test to see if the input is the correct length
            // and if the third character is a colon
            if (time.length() == TIME_FORMAT_LENGTH && time.charAt(2) == ':') {
                // The time variable is split into two parts, hour and minute
                // Source for splitting time:
                // https://stackoverflow.com/questions/26502637/
                // separating-hours-from-minutes-in-a-given-time-value
                String[] splitTime = time.split(":");
                String hour = splitTime[0];
                String minute = splitTime[1];
                // Check if the hour and minute parts are valid numbers
                // and if they are within the valid range
                if (isNumber(hour) && isNumber(minute)) {
                    if (Integer.parseInt(hour) < HOURS_IN_DAY
                            && Integer.parseInt(hour) >= 0
                            && Integer.parseInt(minute) < SECONDS_IN_MINUTE
                            && Integer.parseInt(minute) >= 0) {
                        return time;
                    }
                }
            }
            System.out.println(ERROR_INVALID_TIME);
        }
    }

    /**
     * Checks if a string is a number.
     *
     * @param stringToCheck
     * @return True if the string is a number otherwise false.
     */
    public static Boolean isNumber(final String stringToCheck) {
        try {
            // Try to parse the string as an integer
            // If it succeeds, return true
            Integer.parseInt(stringToCheck);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Reads and validates user input for renter's name.
     *
     * @return User's renter's name as a string.
     */
    public static String nameInput() {
        String name = "";
        while (true) {
            name = userInputScanner.next();
            // Check if the input is empty
            if (name.isEmpty()) {
                System.out.println(ERROR_INVALID_NAME);
            } else {
                return name;
            }
        }
    }

    /**
     * Finds the first free space in the array.
     *
     * @param array
     * @return The index of the first free space, or -1 if no free space is
     *         found.
     */
    public static int findFreeSpace(final String[][] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i][0] == null) {
                return i; // Return the index of the free space
            }
        }
        return -1; // Return -1 if no free space is found
    }

    /**
     * Finds a car in the array by its registration number.
     *
     * @param array
     * @param regNumber
     * @return The index of the car, or -1 if not found.
     */
    public static int findCarinFleet(final String[][] array,
            final String regNumber) {
        for (int i = 0; i < array.length; i++) {
            if (array[i][REGISTRATION_NUMBER] != null) {
                if (array[i][REGISTRATION_NUMBER].equals(regNumber)) {
                    return i; // Return the index of the car
                }
            }
        }
        return -1; // Return -1 if car not found
    }

    /**
     * Finds a car in the rental list by its registration number.
     *
     * @param rentalList
     * @param regNumber
     * @return The index of the car, or -1 if not found.
     */
    public static int findCarInRentalList(final String[][] rentalList,
            final String regNumber) {
        for (int i = 0; i < rentalList.length; i++) {
            if (rentalList[i][REGISTRATION_NUMBER] != null) {
                if (rentalList[i][REGISTRATION_NUMBER].equals(regNumber)) {
                    if (rentalList[i][TIME_OF_RETURNING].isEmpty()) {
                        return i; // Return the index of the car
                    }
                }
            }
        }
        return -1; // Return -1 if car not found
    }

    /**
     * Rents a car from the fleet.
     *
     * @param carFleet
     * @param rentalList
     */
    public static void rentCar(final String[][] carFleet,
            final String[][] rentalList) {
        // Set registration number
        System.out.print("Enter registration number: ");
        String regNumber = registrationNumberInput();
        // Check if car is in fleet
        int carIndex = findCarinFleet(carFleet, regNumber);
        if (carIndex != -1) {
            // Check if car is available
            if (carFleet[carIndex][IS_RENTED].equals(NOT_RENTED)) {
                // Set time of pickup and renter's name
                System.out.print("Enter time of pickup: ");
                String timeOfPickup = timeInput();
                System.out.print("Enter renter's name: ");
                String renterName = nameInput();

                // Check if there is free space in the rental list
                int freeSpace = findFreeSpace(rentalList);
                if (freeSpace != -1) {
                    // Add car to rental list
                    rentalList[freeSpace][REGISTRATION_NUMBER] = regNumber;
                    rentalList[freeSpace][TIME_OF_RENTING] = timeOfPickup;
                    rentalList[freeSpace][TIME_OF_RETURNING] = "";
                    rentalList[freeSpace][RENTER_NAME] = renterName;
                    rentalList[freeSpace][COST] = "";
                    carFleet[carIndex][IS_RENTED] = RENTED;
                    System.out.println("Car with registration number "
                            + regNumber + " was rented by "
                            + renterName + " at " + timeOfPickup);
                    return;
                } else {
                    System.out.println("No free space in rental list");
                }
            } else {
                System.out.println("Car " + regNumber + " not available");
            }
            return;
        } else {
            System.out.println("Car " + regNumber + " not found");
        }
    }

    /**
     * Calculates the time difference between two times.
     *
     * @param timeOfPickup
     * @param timeOfReturn
     * @return The time difference in minutes, or -1 if the return time is
     *         before the pickup time.
     */
    public static int calculateTimeDifference(final String timeOfPickup,
            final String timeOfReturn) {
        // Split the time strings into hours and minutes
        // and convert them to integers
        String[] pickupTime = timeOfPickup.split(":");
        String[] returnTime = timeOfReturn.split(":");
        int pickupHour = Integer.parseInt(pickupTime[0]);
        int pickupMinute = Integer.parseInt(pickupTime[1]);
        int returnHour = Integer.parseInt(returnTime[0]);
        int returnMinute = Integer.parseInt(returnTime[1]);
        // Check if the return time is before the pickup time
        // If it is, return -1, otherwise return the time difference in minutes
        if ((pickupHour > returnHour)
                || (pickupHour == returnHour && pickupMinute > returnMinute)) {
            return INVALID_RESULT;
        }
        return ((returnHour - pickupHour) * MINUTES_IN_HOUR
                + (returnMinute - pickupMinute));
    }

    /**
     * Returns a rented car to the fleet.
     *
     * @param carFleet
     * @param rentalList
     */
    public static void returnCar(final String[][] carFleet,
            final String[][] rentalList) {
        String timeOfReturn = "";
        int minutesRented = 0;
        // Set registration number
        System.out.print("Enter registration number: ");
        String regNumber = registrationNumberInput();
        int fleetIndex = findCarinFleet(carFleet, regNumber);
        // Check if car is in fleet
        if (fleetIndex != -1) {
            // Check if car is rented
            if (carFleet[fleetIndex][IS_RENTED].equals(RENTED)) {
                // Find the car in the rental list
                int rentalIndex = findCarInRentalList(rentalList, regNumber);
                if (rentalIndex != -1) {
                    // Set time of return
                    System.out.print("Enter time of return: ");
                    boolean validInput = false;
                    // Check if valid time is entered and loop until it is
                    while (!validInput) {
                        timeOfReturn = timeInput();
                        minutesRented = calculateTimeDifference(
                                rentalList[rentalIndex][TIME_OF_RENTING],
                                timeOfReturn);
                        if (minutesRented != -1) {
                            validInput = true;
                        } else {
                            System.out.println("Invalid time of return");
                        }
                    }

                    // Update the rental list with the return time and cost
                    rentalList[rentalIndex][TIME_OF_RETURNING] = timeOfReturn;
                    rentalList[rentalIndex][COST] = String
                            .valueOf(minutesRented * MINUTE_FEE);
                    carFleet[fleetIndex][IS_RENTED] = NOT_RENTED;
                    // Print the single rental summary
                    System.out.println("==========================");
                    System.out.println("LTU Rent-a-car");
                    System.out.println("==========================");
                    double hours = minutesRented / (float) MINUTES_IN_HOUR;
                    System.out.printf(
                            "Name: %s\nCar: %s (%s)\nTime: %s-%s (%.1f hours)\n"
                                    + "Total Cost: %s SEK\n",
                            rentalList[rentalIndex][RENTER_NAME],
                            carFleet[fleetIndex][MAKE_AND_MODEL],
                            carFleet[fleetIndex][REGISTRATION_NUMBER],
                            rentalList[rentalIndex][TIME_OF_RENTING],
                            timeOfReturn,
                            hours,
                            rentalList[rentalIndex][COST]);
                } else {
                    System.out.println("Car not found in rental list");
                }
            } else {
                System.out.println("Car " + regNumber + " not rented");
            }
        } else {
            System.out.println("Car " + regNumber + " not found");
        }
    }

    /**
     * Prints the current car fleet.
     *
     * @param carFleet
     */
    public static void printCarFleet(final String[][] carFleet) {
        int totalNumberOfCars = 0;
        int totalNumberOfAvailableCars = 0;

        System.out.println("LTU Rent-a-car car fleet:\n");
        System.out.printf("%-25s %-25s %-25s\n", "Model", "Numberplate",
                "Status");
        for (int i = 0; i < carFleet.length; i++) {
            if (carFleet[i][REGISTRATION_NUMBER] != null) {
                System.out.printf("%-25s %-25s %-25s\n",
                        carFleet[i][MAKE_AND_MODEL],
                        carFleet[i][REGISTRATION_NUMBER],
                        carFleet[i][IS_RENTED]);
                totalNumberOfCars++;
                if (carFleet[i][IS_RENTED].equals(NOT_RENTED)) {
                    totalNumberOfAvailableCars++;
                }
            }
        }
        System.out.println("Total number of cars: " + totalNumberOfCars);
        System.out.println("Total number of available cars: "
                + totalNumberOfAvailableCars);
    }

    /**
     * Prints a summary of rentals and revenue.
     *
     * @param rentalList
     */
    public static void printRentalSummary(final String[][] rentalList) {
        int totalNumberOfRentals = 0;
        int totalRevenue = 0;

        System.out.println("LTU Rent-a-car rental summary:\n");
        System.out.printf("%-25s %-25s %-25s %-25s %-25s\n", "Name",
                "Numberplate", "Pickup", "Return", "Cost");
        for (int i = 0; i < rentalList.length; i++) {
            if (rentalList[i][REGISTRATION_NUMBER] != null) {
                System.out.printf("%-25s %-25s %-25s %-25s %-25s\n",
                        rentalList[i][RENTER_NAME],
                        rentalList[i][REGISTRATION_NUMBER],
                        rentalList[i][TIME_OF_RENTING],
                        rentalList[i][TIME_OF_RETURNING], rentalList[i][COST]);
                totalNumberOfRentals++;
                if (!rentalList[i][COST].isEmpty()) {
                    totalRevenue += Integer.parseInt(rentalList[i][COST]);
                }
            }
        }
        System.out
                .println("\nTotal number of rentals: " + totalNumberOfRentals);
        System.out.println("Total revenue: " + totalRevenue + " SEK");
    }
}
