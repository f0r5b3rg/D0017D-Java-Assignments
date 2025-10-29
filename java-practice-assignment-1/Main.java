/**
 * Declare and initialize constant variables.
 * Declar and initialize local variables.
 * Print the battery capacity and value headers.
 * Set the variables for the next data row.
 * Calculate charging power and charging time.
 * Repeat the above step for all data rows.
 */
public class Main {
    static final double BATTERY_CAPACITY = 35800.0;
    static final double W_TO_KW = 1000.0;
    static final double CURRENT_10 = 10.0;
    static final double CURRENT_16 = 16.0;
    static final double CURRENT_32 = 32.0;
    static final double VOLTAGE_230 = 230.0;
    static final double VOLTAGE_400 = 400.0;
    static final double THREE_PHASE_MULTIPLIER = Math.sqrt(3);
    static final int SCALE_TEN = 10;
    static final int NO_OF_DECIMALS = 2;

    public static void main(final String[] args) {
        double chargingPower = 0.0;
        double chargingTime = 0.0;

        // Scaling factor for rounding to 2 decimal places.
        double scale = 0;
        scale = Math.pow(SCALE_TEN, NO_OF_DECIMALS);

        // Print headers.
        System.out.printf("Battery capacity: %.1f (kWh)\n", BATTERY_CAPACITY / W_TO_KW);
        System.out.printf("%-20s %-20s %-20s %-20s\n", "Current(A)", "Voltage(V)", "Charging Power(kW)",
                "Charging Time(h)");

        // Set variables and perform calculations.
        chargingPower = (CURRENT_10 * VOLTAGE_230);
        chargingTime = BATTERY_CAPACITY / chargingPower;
        // Round the values.
        chargingPower = Math.round(chargingPower * scale) / scale;
        chargingTime = Math.round(chargingTime * scale) / scale;
        // Print results.
        System.out.printf("%-20.1f %-20.1f %-20.2f %-20.2f\n", CURRENT_10, VOLTAGE_230,
                chargingPower / W_TO_KW, chargingTime);

        // Repeat four more times, with chargingPower formula changing depending on
        // voltage.
        chargingPower = (CURRENT_16 * VOLTAGE_230);
        chargingTime = BATTERY_CAPACITY / chargingPower;
        chargingPower = Math.round(chargingPower * scale) / scale;
        chargingTime = Math.round(chargingTime * scale) / scale;
        System.out.printf("%-20.1f %-20.1f %-20.2f %-20.2f\n", CURRENT_16, VOLTAGE_230,
                chargingPower / W_TO_KW, chargingTime);

        chargingPower = (CURRENT_10 * VOLTAGE_400 * THREE_PHASE_MULTIPLIER);
        chargingTime = BATTERY_CAPACITY / chargingPower;
        chargingPower = Math.round(chargingPower * scale) / scale;
        chargingTime = Math.round(chargingTime * scale) / scale;
        System.out.printf("%-20.1f %-20.1f %-20.2f %-20.2f\n", CURRENT_10, VOLTAGE_400,
                chargingPower / W_TO_KW, chargingTime);

        chargingPower = (CURRENT_16 * VOLTAGE_400 * THREE_PHASE_MULTIPLIER);
        chargingTime = BATTERY_CAPACITY / chargingPower;
        chargingPower = Math.round(chargingPower * scale) / scale;
        chargingTime = Math.round(chargingTime * scale) / scale;
        System.out.printf("%-20.1f %-20.1f %-20.2f %-20.2f\n", CURRENT_16, VOLTAGE_400,
                chargingPower / W_TO_KW, chargingTime);

        chargingPower = (CURRENT_32 * VOLTAGE_400 * THREE_PHASE_MULTIPLIER);
        chargingTime = BATTERY_CAPACITY / chargingPower;
        chargingPower = Math.round(chargingPower * scale) / scale;
        chargingTime = Math.round(chargingTime * scale) / scale;
        System.out.printf("%-20.1f %-20.1f %-20.2f %-20.2f\n", CURRENT_32, VOLTAGE_400,
                chargingPower / W_TO_KW, chargingTime);
    }
}
