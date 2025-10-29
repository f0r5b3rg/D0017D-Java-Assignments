import java.util.Scanner;

/**
 * This program calculates the area and volume of a cone and circle
 * and the fraction of a numerator and denominator, then displays the results.
 *
 * 1. Prompts the user for a radius and height.
 * 2. Calculates the area of a circle and cone, and the volume of a cone.
 * 3. Repeats until the user enters "q" to quit.
 * 4. Prompts the user for a numerator and denominator.
 * 5. Calculates the fraction of the numerator and denominator.
 * 6. Displays the result in the form of a/b.
 * 7. Repeats until the user enters "q" to quit.
 *
 * @author Erik Forsberg (erkfor-4)
 * @version 1.0
 */

public class Main {
    // Creation of scanner object.
    private static Scanner userInputScanner = new Scanner(System.in);

    // Constants
    static final int QUIT = -1;

    /**
     * This method should be used only for unit testing on CodeGrade. Do not
     * change this method! Do not remove this method! Swaps userInputScanner
     * with a custom scanner object bound to a test input stream.
     *
     * @param inputScanner - test scanner object
     */
    public static void injectInput(final Scanner inputScanner) {
        userInputScanner = inputScanner;
    }

    /**
     * Calculates the area of a circle given the radius.
     * 1. The area is calculated: area = π * radius^2
     * 2. Returns the result as a double.
     *
     * @param radius - radius of the circle
     * @return area - area of the circle
     */
    public static double area(final int radius) {
        double area = Math.PI * Math.pow(radius, 2);
        return area;
    }

    /**
     * Calculates the area of a cone given the radius and height.
     * 1. The hypotenuse is calculated using the Pythagorean theorem.
     * 2. The area is calculated: area = π * radius * hypotenuse
     * 3. Returns the result as a double.
     *
     * @param radius - radius of the cone
     * @param height - height of the cone
     * @return area - area of the cone
     */
    public static double area(final int radius, final int height) {
        double hypotenuse = pythagoras(radius, height);
        double area = Math.PI * radius * hypotenuse;
        return area;
    }

    /**
     * Calculates the area of the hypotenuse given a length and height.
     * 1. The hypotenuse is calculated: hypotenuse = sqrt(length^2 + height^2)
     * 2. Returns the result as a double.
     *
     * @param length - the length of the triangle
     * @param height - the height of the triangle
     * @return hypotenuse - the length of the hypotenuse
     */
    public static double pythagoras(final int length, final int height) {
        double hypotenuse = Math.sqrt(Math.pow(length, 2)
                + Math.pow(height, 2));
        return hypotenuse;
    }

    /**
     * Calculates the volume of a cone given a radius and height.
     * 1. The volume is calculated: volume = π * radius^2 * height / 3
     * 2. Returns the result as a double.
     *
     * @param radius - radius of the cone
     * @param height - height of the cone
     * @return volume - volume of the cone
     */
    public static double volume(final int radius, final int height) {
        double volume = Math.PI * Math.pow(radius, 2) * height * (1.0 / 3.0);
        return volume;
    }

    /**
     * Calculates the fraction of a numerator and denominator.
     * 1. Checks if the denominator is 0 and returns null if it is.
     * 2. Checks if the numerator is 0 and returns 0/0/0 if it is.
     * 3. Calculates the whole number part, numerator part, and
     * denominator part.
     * 4. Calculates the GCD of the numerator and denominator.
     * 5. Reduces the fraction by dividing by the GCD.
     * 6. Returns the result as an int array.
     *
     * @param numerator   - the numerator of the fraction
     * @param denominator - the denominator of the fraction
     * @return result - the fraction as an int array
     */
    public static int[] fraction(final int numerator, final int denominator) {
        int[] result = new int[3];
        if (denominator == 0) {
            return null;
        }
        if (numerator == 0) {
            result = new int[] {0, 0, 0 };
        }
        result[0] = numerator / denominator; // whole number part
        result[1] = Math.abs(numerator % denominator); // numerator part
        result[2] = Math.abs(denominator); // denominator part

        int gcd = gcd(result[1], result[2]);
        if (gcd != 0) {
            result[1] /= gcd; // reduce numerator part
            result[2] /= gcd; // reduce denominator part
        }
        return result;
    }

    /**
     * Calculates the GCD of two numbers.
     * 1. If b > a, swap a and b.
     * 2. While b != 0, set temp = a % b, a = b, b = temp.
     * 3. Return a as the GCD.
     *
     * @param a - first number
     * @param b - second number
     * @return a - the GCD of a and b
     */
    public static int gcd(final int a, final int b) {
        int x = a;
        int y = b;
        int temp = 0;
        if (y > x) {
            temp = x;
            x = y;
            y = temp;
        }
        while (y != 0) {
            temp = x % y;
            x = y;
            y = temp;
        }
        return x;
    }

    /**
     * Prints the fraction in the form of a/b.
     * 1. If the fraction is null, print "Error".
     * 2. If the whole number part is not 0, print it.
     * 3. If the numerator part is not 0, print it.
     * 4. If the denominator part is not 0, print it.
     * 5. If the denominator part is 0, print "0".
     *
     * @param fraction - the fraction as an int array
     */
    public static void printFraction(final int[] fraction) {
        if (fraction == null) {
            System.out.println("Error");
            return;
        }

        if (fraction[0] != 0) {
            System.out.print(fraction[0] + " ");
        }
        if (fraction[1] != 0) {
            System.out.print(fraction[1] + "/" + fraction[2]);
        } else if (fraction[2] == 0) {
            System.out.print("0");
        }
        System.out.println();
        return;
    }

    /**
     * This method reads an integer from the user. If the user enters "q", it
     * returns -1.
     * 1. If the input is an integer, return its absolute value.
     * 2. If the input is "q", return -1.
     * 3. If the input is not an integer or "q", do nothing and get a new input.
     *
     * @return int - the absolute value of the input or -1 if "q" is entered
     */
    public static int input() {
        boolean validInput = false;
        while (!validInput) {
            if (userInputScanner.hasNextInt()) {
                return Math.abs(userInputScanner.nextInt());
            } else if (userInputScanner.hasNext()) {
                String input = userInputScanner.next();
                if (input.equalsIgnoreCase("q")) {
                    return QUIT;
                }
            }
        }
        return QUIT; // This line is never reached, required for compilation
    }

    public static void main(final String[] args) {
        int radius = 0;
        int height = 0;
        int numerator = 0;
        int denominator = 0;

        // Print the header of the program for area and volume.
        System.out.println("----------------------------------");
        System.out.println("# Test of area and volume methods");
        System.out.println("----------------------------------");

        // While loop that runs until user enters "q" for area and volume.

        // Sets input delimiter to space and newline
        userInputScanner.useDelimiter("[\\s\\n]");

        while (true) {

            radius = input();
            if (radius == QUIT) {
                break;
            }

            height = input();
            if (height == QUIT) {
                break;
            }

            System.out.println("r = " + radius + ", h = " + height);
            System.out.printf("Circle area: %.2f%n", area(radius));
            System.out.printf("Cone area: %.2f%n", area(radius, height));
            System.out.printf("Cone volume: %.2f%n", volume(radius, height));
        }

        // Print the header of the program for area and volume.
        System.out.println("----------------------------------");
        System.out.println("# Test of the fractional methods");
        System.out.println("----------------------------------");

        // While loop that runs until user enters "q" for the fraction part
        while (true) {

            numerator = input();
            if (numerator == QUIT) {
                break;
            }

            denominator = input();
            if (denominator == QUIT) {
                break;
            }

            System.out.printf("%d/%d = ", numerator, denominator);
            printFraction(fraction(numerator, denominator));
        }
    }
}
