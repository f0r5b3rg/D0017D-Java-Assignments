import java.util.Scanner;

/**
 * This program generates a number of random integers between 0 and 999,
 * according to user input, sorts them and displays them in a certain order.
 *
 * 1. Prompt the user for number of numbers to generate.
 * 2. Genereate numbers, store into an array and count evens and odds.
 * 3. Sort the numbers placing ascending evens firsts and descending odds last.
 * 4. Display numbers in sorted order.
 * 5. Display number of even and odd numbers.
 *
 * @author Erik Forsberg (erkfor-4)
 * @version 1.0
 */

class Main {
    static final String USER_INPUT_PROMPT 
        = "How many random numbers in the range 0 - 999 are desired?";
    static final String RANDOM_NUMBERS_LIST_MESSAGE 
        = "Here are the random numbers:";
    static final String RANDOM_NUMBERS_SORTED_MESSAGE 
        = "Here are the random numbers arranged:";
    static final String NO_ODD_NUMBERS_MESSAGE = "No Odd Numbers";
    static final String NO_EVEN_NUMBERS_MESSAGE = "No Even Numbers";
    static final String INVALID_INPUT_MESSAGE = "Invalid Input";
    // Highest number of random numbers to be generated.
    static final int MAX_RANDOM_NUMBERS = 999;
    // Number to multiply the decimal Math.Random() generates.
    static final int DECIMAL_MULTIPLIER = 1000;

    public static void main(final String[] args) {
        Scanner userInput = new Scanner(System.in);
        int randomNumberCount = 0;
        int[] numberArray = null;
        int numEvens = 0;
        int numOdds = 0;

        System.out.println(USER_INPUT_PROMPT);

        // Asks user for desired amount of random numbers until a valid input
        // is given or the program runs out of memory.
        boolean inputIsValid = false;
        while (!inputIsValid) {
            try {
                randomNumberCount = userInput.nextInt();
                // If the input is not an integer, prompts user for a new input.
            } catch (java.util.InputMismatchException e) {
                userInput.next();
                System.out.println(INVALID_INPUT_MESSAGE);
                continue;
            }
            // Validates the input to be between 1 and 999, prompts user for
            // new input if it isn't.
            if (randomNumberCount > 0
                && randomNumberCount <= MAX_RANDOM_NUMBERS) {
                try {
                    numberArray = new int[randomNumberCount];
                    // If the the input results is too large, prompts user for
                    // a new input.
                } catch (OutOfMemoryError e) {
                    userInput.next();
                    System.out.println(INVALID_INPUT_MESSAGE);
                    continue;
                }
                inputIsValid = true;
            } else {
                System.out.println(INVALID_INPUT_MESSAGE);
            }
        }

        // Generates random numbers, counting the evens and odds.
        for (int i = 0; i < randomNumberCount; i++) {
            // Generates a random number between 0 and 999.
            numberArray[i] = (int) (Math.random() * DECIMAL_MULTIPLIER);
            if (numberArray[i] % 2 == 0) {
                numEvens++;
            } else {
                numOdds++;
            }
        }

        // Displays the random numbers in the order they were generated.
        System.out.println("\n" + RANDOM_NUMBERS_LIST_MESSAGE);
        for (int i = 0; i < randomNumberCount; i++) {
            System.out.print(numberArray[i] + " ");
        }

        // Sorts the numbers using a modified bubble sort algorithm that
        // sorts the even and odd numbers separated.
        boolean swapped = true;
        int tempNum = 0;
        while (swapped) {
            swapped = false;
            for (int i = 1; i < numberArray.length; i++) {
                // If the previous number is odd.
                if (numberArray[i - 1] % 2 != 0) {
                    // If the current number is even.
                    if (numberArray[i] % 2 == 0) {
                        tempNum = numberArray[i - 1];
                        // Swap the two numbers.
                        numberArray[i - 1] = numberArray[i];
                        numberArray[i] = tempNum;
                        swapped = true;
                    // If both numbers are odd.
                    } else {
                        // If the previous number is greater than the current
                        // number.
                        if (numberArray[i - 1] > numberArray[i]) {
                            // Swap the two numbers.
                            tempNum = numberArray[i - 1];
                            numberArray[i - 1] = numberArray[i];
                            numberArray[i] = tempNum;
                            swapped = true;
                        }
                    }
                // If the previous number is even.
                } else {
                    // If the current number is odd.
                    if (numberArray[i] % 2 != 0) {
                        // Do not swap the two numbers.
                        continue;
                    // If both numbers are even.
                    } else {
                        // If the previous number is greater than the current
                        // number.
                        if (numberArray[i - 1] > numberArray[i]) {
                            // Swap the two numbers.
                            tempNum = numberArray[i - 1];
                            numberArray[i - 1] = numberArray[i];
                            numberArray[i] = tempNum;
                            swapped = true;
                        }
                    }
                }
            }
        }

        // Displays all even numbers, starting from the beginning of the
        // sorted array.
        System.out.println("\n\n" + RANDOM_NUMBERS_SORTED_MESSAGE);
        // If no even numbers, prints a message saying so.
        if (numEvens == 0) {
            System.out.print(NO_EVEN_NUMBERS_MESSAGE);
        } else {
            for (int i = 0; i < numEvens; i++) {
                System.out.print(numberArray[i] + " ");
            }
        }

        System.out.print(" - ");
        // Displays all odd numbers, starting from the end of the sorted array
        // going backwards.
        if (numOdds == 0) {
            // If no odd numbers, prints a message saying so.
            System.out.print(NO_ODD_NUMBERS_MESSAGE);
        } else {
            for (int i = 0; i < numOdds; i++) {
                System.out.print(numberArray[numberArray.length - i - 1]
                    + " ");
            }
        }

        // Displays even and odd amounts.
        System.out.println("\n\nOf the above " + randomNumberCount
            + " numbers, " + numEvens + " were even and " + numOdds
            + " were odd.");
        userInput.close();
    }
}