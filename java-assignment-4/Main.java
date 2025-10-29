import java.util.Scanner;
import java.util.Date;

/**
 *
 * 1. Initialize the items, sales, and saleDates arrays and set the last
 * item number.
 * 2. Print a welcoming message.
 * 3. Start the main loop that handles the menu options.
 * 4. Use a switch statement depending on user input.
 * 6. Exit the loop if the user chooses to quit.
 *
 *
 * @author Erik Forsberg (erkfor-4)
 */
public class Main {

    // Constants for the item array
    public static final int ITEM_ID = 0;
    public static final int ITEM_COUNT = 1;
    public static final int ITEM_PRICE = 2;
    public static final int ITEM_COLUMN_SIZE = 3;
    public static final int INITIAL_ITEM_SIZE = 10;

    // Constants for the sales array
    public static final int SALE_ITEM_ID = 0;
    public static final int SALE_ITEM_COUNT = 1;
    public static final int SALE_ITEM_PRICE = 2;
    public static final int SALE_COLUMN_SIZE = 3;
    public static final int MAX_SALES = 1000;

    // Other constants
    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_ITEM_4 = 4;
    public static final int MENU_ITEM_5 = 5;
    public static final int MENU_ITEM_6 = 6;
    public static final int MENU_ITEM_Q = -1;

    public static final int INITIAL_ITEM_NUMBER = 999;

    public static final int TEN_MULTIPLIER = 10;
    public static final int THOUSAND_MULTIPLIER = 1000;

    private static Scanner userInputScanner = new Scanner(System.in);

    /**
     * This method should be used only for unit testing on CodeGrade. Do not
     * change this method! Swaps userInputScanner with a custom scanner object
     * bound to a test input stream
     *
     * @param inputScanner
     *            test scanner object
     */
    public static void injectInput(final Scanner inputScanner) {
        userInputScanner = inputScanner;
    }

    /**
     * Display the menu and return the user's choice.
     *
     * 1. Display the menu options
     * 2. Read the user's input
     * 3. Return the user's choice as an integer
     *
     * @return user's choice as an integer
     */
    public static int menu() {
        System.out.println("1. Insert items");
        System.out.println("2. Remove an item");
        System.out.println("3. Display a list of items");
        System.out.println("4. Register a sale");
        System.out.println("5. Display sales history");
        System.out.println("6. Sort and display sales history table");
        System.out.println("q. Quit");
        System.out.println("Your selection:");

        int choice = input();
        return choice;
    }

    /**
     * Reads an integer from the user. If the user enters "q", it returns -1.
     * 1. If the input is an integer, return its absolute value.
     * 2. If the input is "q", return -1.
     * 3. If the input is not an integer or "q", print an error message and get
     * a new input.
     *
     * @return the absolute value of the input or -1 if "q" is entered
     */
    public static int input() {
        // Flag to check if a valid input has been entered
        boolean validInput = false;
        while (!validInput) {
            if (userInputScanner.hasNextInt()) {
                return Math.abs(userInputScanner.nextInt());
            } else if (userInputScanner.hasNext()) {
                String input = userInputScanner.next();
                if (input.equalsIgnoreCase("q")) {
                    return MENU_ITEM_Q;
                }
            }
            System.out.println("Invalid");
        }
        // This line is never reached, required for compilation
        return MENU_ITEM_Q;
    }

    /**
     * Checks if the items array can hold a specified number of items. It checks
     * if the number of free slots in the items array is less
     * than the specified number of items.
     *
     * 1. Loop through the items array and increment the free slots counter for
     * each
     * item with ID 0.
     * 2. Return true if there are less free slots than the amount of items to
     * be added.
     *
     * @param items
     * @param noOfItems
     * @return true if the items array can hold the specified number of items,
     *         false otherwise.
     */
    public static boolean checkFull(final int[][] items, final int noOfItems) {
        int freeslots = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == 0) {
                freeslots++;
            }
        }
        return freeslots < noOfItems; // Return true if there are not enough
                                      // free slots
    }

    /**
     * Extends the items array by a specified number of items. It creates a new
     * array with the new size and copies the old items to the
     * new array.
     *
     * 1. Create a new array the size of the old array plus the number of items
     * to be added.
     * 2. Copy all the old items to the new array.
     * 3. Return the new array.
     *
     * @param items
     * @param noOfItems
     * @return the new items array
     */
    public static int[][] extendArray(final int[][] items,
            final int noOfItems) {
        int[][] newItems = new int[items.length + noOfItems][ITEM_COLUMN_SIZE];
        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }
        return newItems;
    }

    /**
     * Finds the the index of an item in the items array based on its ID.
     *
     * 1. Loop through the items array until the item ID is found.
     * 2. Return the index of the item if found, else return -1.
     *
     * @param items
     * @param itemId
     * @return the index of the item in the items array or -1 if not found
     */
    public static int findItemIndexInItems(final int[][] items,
            final int itemId) {
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == itemId) {
                return i;
            }
        }
        return -1; // Return -1 if the item is not found
    }

    /**
     * Inserts items into the items array and extends it if necessary.
     *
     * 1. Check if the items array is full and extend it if necessary.
     * 2. Loop through the new items to be added and assign random values for
     * item ID, count, and price.
     * 3. Return the new items array.
     *
     * @param items
     * @param lastItemId
     * @param noOfItems
     * @return the new items array
     */
    public static int[][] insertItems(final int[][] items, final int lastItemId,
            final int noOfItems) {
        int[][] newItems = items;
        if (checkFull(items,
                ((lastItemId - INITIAL_ITEM_NUMBER) + noOfItems))) {
            newItems = extendArray(newItems, noOfItems);
        }
        int itemId = lastItemId;
        // Loop through all the new items to be added.
        // i is the index of the new item in the new array, calculated by
        // finding the index of the last item ID in the new array and adding 1
        // to start from where the next item ID should be added.
        for (int i = findItemIndexInItems(newItems, lastItemId)
                + 1; i < findItemIndexInItems(newItems, lastItemId) + 1
                        + noOfItems; i++) {
            // Starts by incrementing the last item ID to get the next item ID
            // to add
            itemId++;
            newItems[i][ITEM_ID] = itemId;
            // Assign random count between 0 and 10
            newItems[i][ITEM_COUNT] = (int) (Math.random() * TEN_MULTIPLIER);
            // Assign random price between 0 and 1000
            newItems[i][ITEM_PRICE] = (int) (Math.random()
                    * THOUSAND_MULTIPLIER);
        }
        return newItems;
    }

    /**
     * Removes an item from the items array by setting its fields to 0.
     *
     * 1. Find the index of the item in the items array.
     * 2. If the item ID is valid, set the item ID, count, and price to 0.
     * 3. Return 0 to indicate success or -1 if the item ID is invalid.
     *
     * @param items
     * @param itemId
     * @return 0 if successful -1 if invalid item ID
     */
    public static int removeItem(final int[][] items, final int itemId) {
        // Check if the item ID is valid
        int itemIndex = findItemIndexInItems(items, itemId);
        if (itemIndex == -1) {
            return -1; // Return -1 if the item ID is invalid
        }
        items[itemIndex][ITEM_ID] = 0;
        items[itemIndex][ITEM_COUNT] = 0;
        items[itemIndex][ITEM_PRICE] = 0;
        return 0; // Return 0 to indicate success
    }

    /**
     * Sorts the items array based on the item ID in ascending order.
     *
     * 1. Loop through the items array and compare adjacent items.
     * 2. If the item ID of the first item is greater than the second item,
     * swap them.
     * 3. Repeat until the array is sorted.
     *
     * @param items
     */
    public static void sortItems(final int[][] items) {
        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items.length - i - 1; j++) {
                if (items[j][ITEM_ID] > items[j + 1][ITEM_ID]) {
                    int[] tempItem = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = tempItem;
                }
            }
        }
    }

    /**
     * Prints the items in the items array.
     *
     * 1. Print the header for the items table.
     * 2. Sort the items array.
     * 3. Loop through the items array and print the item ID, count, and
     * price.
     *
     * @param items
     */
    public static void printItems(final int[][] items) {
        System.out.printf("%12s %12s %12s", "Item Number", "Item Count",
                "Item Price\n");
        sortItems(items);
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] != 0) { // Check if the item ID is not 0
                // Print item details
                System.out.printf("%12d %12d %12d\n", items[i][ITEM_ID],
                        items[i][ITEM_COUNT], items[i][ITEM_PRICE]);
            }
        }
    }

    /**
     * Returns the index of the first free space in the sales array.
     *
     * 1. Loop through the sales array and check if the item ID is 0.
     * 2. If found, return the index of the free space, otherwise return -1.
     *
     * @param sales
     * @return the index of the free space or -1 if not found
     */
    public static int findFreeSpaceInSales(final int[][] sales) {
        for (int i = 0; i < sales.length; i++) {
            if (sales[i][SALE_ITEM_ID] == 0) {
                return i; // Return the index of the free space
            }
        }
        return -1; // Return -1 if no free space is found
    }

    /**
     * Sells an item by updating the sales array and the items array.
     *
     * 1. Find the index of the item in the items array, if not found return -1.
     * 2. Find the index of the free space in the sales array.
     * 3. Check if there is enough of an item left to sell, if not return the
     * amount of stock available.
     * 4. Decrease the item count by the amount sold.
     * 5. Add the item ID, sold amount, item price and date to the sales array.
     *
     * @param sales
     * @param salesDate
     * @param items
     * @param itemIdToSell
     * @param amountToSell
     * @return 0 if successful, -1 if item ID is invalid, or the amount of an
     *         item is left if not enough are left to sell.
     */
    public static int sellItem(final int[][] sales, final Date[] salesDate,
            final int[][] items, final int itemIdToSell,
            final int amountToSell) {
        // Find the index of the item in the items array
        int itemIndex = findItemIndexInItems(items, itemIdToSell);
        if (itemIndex == -1) { // Check if the item ID is valid
            return -1; // Return -1 if the item ID is invalid
        }
        // Find the index of the free space in the sales array
        int salesIndex = findFreeSpaceInSales(sales);

        // Check if thre is free space in the sales array
        if (items[itemIndex][ITEM_COUNT] < amountToSell) {
            // Return the amount of stock available
            return items[itemIndex][ITEM_COUNT];
        }

        // Decrease the item count y the amount sold
        items[itemIndex][ITEM_COUNT] -= amountToSell;
        // Assign the item ID to the sales array
        sales[salesIndex][SALE_ITEM_ID] = itemIdToSell;
        // Assign the amount sold to the sales array
        sales[salesIndex][SALE_ITEM_COUNT] = amountToSell;
        // Assign the item price to the sales array
        sales[salesIndex][SALE_ITEM_PRICE] = amountToSell
                * items[itemIndex][ITEM_PRICE];
        // Assign the current date to the sales array
        salesDate[salesIndex] = new Date();

        return 0; // Return 0 to indicate success
    }

    /**
     * Prints the sales history.
     *
     * 1. Print the header for the sales table.
     * 2. Loop through the sales array and print the item ID, count, price,
     * and date. If the item ID is 0, skip the item.
     *
     * @param sales
     * @param salesDate
     */
    public static void printSales(final int[][] sales, final Date[] salesDate) {
        System.out.printf("%12s %12s %12s %12s\n", "Item number", "Item Count",
                "Item Price", "Sale Date");
        for (int i = 0; i < sales.length; i++) {
            if (sales[i][SALE_ITEM_ID] != 0) { // Check if the item ID is not 0
                // Print sale details
                System.out.printf("%12d %12d %12d %12tF\n",
                        sales[i][SALE_ITEM_ID],
                        sales[i][SALE_ITEM_COUNT], sales[i][SALE_ITEM_PRICE],
                        salesDate[i]);
            }
        }
    }

    /**
     * Sorts the sales array based on the item ID.
     *
     * 1. Create a temporary sales array to sort.
     * 2. Create a temporary sales date array to sort.
     * 3. Sort the sales array based on the item ID.
     * 4. Print the sorted sales array.
     *
     * @param sales
     * @param salesDate
     */
    public static void sortedTable(final int[][] sales,
            final Date[] salesDate) {
        // Create a temporary sales array to sort
        int[][] tempSales = sales;
        // Create a temporary sales date array to sort
        Date[] tempSalesDate = salesDate;

        // Sort the sales array based on the item id in descending order
        for (int i = 0; i < tempSales.length; i++) {
            for (int j = 0; j < tempSales.length - 1; j++) {
                // Compare the item IDs
                if (tempSales[j][SALE_ITEM_ID] > sales[j + 1][SALE_ITEM_ID]) {
                    // Swap the sales array elements
                    int[] tempSale = tempSales[j];
                    Date tempDate = tempSalesDate[j];

                    tempSales[j] = tempSales[j + 1];
                    tempSalesDate[j] = tempSalesDate[j + 1];

                    tempSales[j + 1] = tempSale;
                    tempSalesDate[j + 1] = tempDate;
                }
            }
            // Print the sorted sales array
        }
        printSales(tempSales, tempSalesDate);
    }

    /**
     * Main method containing the main loop.
     *
     * @param args
     */
    public static void main(final String[] args) {
        // Data stucture to store items
        int[][] items = new int[INITIAL_ITEM_SIZE][ITEM_COLUMN_SIZE];
        // Data structure to store sales
        int[][] sales = new int[MAX_SALES][SALE_COLUMN_SIZE];
        // Data structure to store sale dates
        Date[] saleDates = new Date[MAX_SALES];
        // Keep track of last added item number
        int lastItemNumber = INITIAL_ITEM_NUMBER;

        System.out.println("This is Marked Assignment 4");

        boolean running = true; // Flag to control the main loop
        while (running) {
            switch (menu()) {
                case MENU_ITEM_1: // Insert items
                    System.out.println("How many items do you want to add?");
                    int noOfItems = input();
                    if (noOfItems > 0) {
                        // Instert items into the array
                        items = insertItems(items, lastItemNumber, noOfItems);
                        // Update the last item number
                        lastItemNumber += noOfItems;
                        System.out.println(noOfItems + " items added!");
                    } else {
                        System.out.println(
                                "Invalid number of items. Please try again.");
                    }
                    break;
                case MENU_ITEM_2: // Remove an item
                    System.out.println("Specify an item ID to remove:");
                    // Read the item ID to remove
                    int itemIdToRemove = input();
                    // Remove the item from the array
                    if (removeItem(items, itemIdToRemove) == -1) {
                        System.out.println("Could not find");
                    } else {
                        System.out.println(
                                "Successfully removed item " + itemIdToRemove);
                    }
                    break;
                case MENU_ITEM_3: // Display a list of items
                    printItems(items); // Print the items in the array
                    break;
                case MENU_ITEM_4: // Register a sale
                    System.out.println("Specify an item ID to sell:");
                    int itemIdToSell = input(); // Read the item ID to sell
                    System.out.println("Specify the amount to sell:");
                    int amountToSell = input(); // Read the amount to sell
                    int salesResult = sellItem(sales, saleDates, items,
                            itemIdToSell, amountToSell);
                    if (salesResult == -1) { // Sell the item
                        System.out.println(
                                "Could not find");
                    } else if (salesResult > 0) {
                        System.out.println("Failed to sell specified amount.");
                    } else {
                        System.out.println("Successfully sold " + amountToSell
                                + " of item " + itemIdToSell);
                    }
                    break;
                case MENU_ITEM_5: // Display sales history
                    printSales(sales, saleDates); // Print the sales history
                    break;
                case MENU_ITEM_6: // Sort and display sales history table
                    // Sort and print the sales history table
                    sortedTable(sales, saleDates);
                    break;
                case MENU_ITEM_Q: // Quit the program
                    // Set the running flag to false to exit the loop
                    running = false;
                    break;
                default: // Invalid input
                    System.out.println("Invalid");
                    break;
            }
        }
    }
}
