package weather;

public class Main {

    public static void main(String[] args) {
        // Create StevensonReading instances with different parameters
        try {
            StevensonReading reading1 = new StevensonReading(67.468448, 66.326441, 49.811793, 6);
            StevensonReading reading2 = new StevensonReading(8.136959, -2.053103, 19.060520, 81);
            StevensonReading reading3 = new StevensonReading(72.500384, 53.767550, 46.958175, 18);
            StevensonReading reading4 = new StevensonReading(95.912468, 84.870745, 11.962080, 49);
            StevensonReading reading5 = new StevensonReading(94.997928, 85.746917, 35.210145, 71);
            StevensonReading reading6 = new StevensonReading(88.425323, 72.907685, 27.497372, 95);
            StevensonReading reading7 = new StevensonReading(75.696371, 72.185936, 5.817415, 82);
            StevensonReading reading8 = new StevensonReading(95.590494, 86.909615, 14.734458, 24);
            StevensonReading reading9 = new StevensonReading(68.257267, 54.912307, 12.821509, 34);

            // Display the readings and their calculated values
            System.out.println("Reading 1:");
            displayReadingInfo(reading1);
            System.out.println("expected Heat Index = 433");// expected Heat Index = 433
            System.out.println();

            // Display the readings and their calculated values
            System.out.println("Reading 2:");
            displayReadingInfo(reading2);
            System.out.println("expected Heat Index = 41");// expected Heat Index = expected 41
            System.out.println();

            // Display the readings and their calculated values
            System.out.println("Reading 3:");
            displayReadingInfo(reading3);
            System.out.println("expected Heat Index = 219");// expected Heat Index = expected 219
            System.out.println();

            // Display the readings and their calculated values
            System.out.println("Reading 4:");
            displayReadingInfo(reading4);
            System.out.println("expected Heat Index = 688");// expected Heat Index = expected 688
            System.out.println();

            // Display the readings and their calculated values
            System.out.println("Reading 5:");
            displayReadingInfo(reading5);
            System.out.println("expected Heat Index = 724");// expected Heat Index = expected 724
            System.out.println();

            // Display the readings and their calculated values
            System.out.println("Reading 6:");
            displayReadingInfo(reading6);
            System.out.println("expected Heat Index = 451");// expected Heat Index = expected 451
            System.out.println();

            // Display the readings and their calculated values
            System.out.println("Reading 7:");
            displayReadingInfo(reading7);
            System.out.println("expected Heat Index = 517");// expected Heat Index = expected 517
            System.out.println();

            // Display the readings and their calculated values
            System.out.println("Reading 8:");
            displayReadingInfo(reading8);
            System.out.println("expected Heat Index = 755");// expected Heat Index = expected 755
            System.out.println();

            // Display the readings and their calculated values
            System.out.println("Reading 9:");
            displayReadingInfo(reading9);
            System.out.println("expected Heat Index = 237");// expected Heat Index = expected 755
            System.out.println();

            // Demonstrating handling of invalid input
            System.out.println("\nAttempting to create an invalid reading:");
            StevensonReading invalidReading = new StevensonReading(15.0, 20.0, -5.0, 3.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Helper method to display information about a StevensonReading instance.
     *
     * @param reading the StevensonReading instance to display
     */
    private static void displayReadingInfo(StevensonReading reading) {
        System.out.println(reading);
        System.out.println("Temperature: " + reading.getTemperature() + "°C");
        System.out.println("Dew Point: " + reading.getDewPoint() + "°C");
        System.out.println("Wind Speed: " + reading.getWindSpeed() + " mph");
        System.out.println("Total Rain: " + reading.getTotalRain() + " mm");
        System.out.println("Relative Humidity: " + reading.getRelativeHumidity() + "%");
        System.out.println("Heat Index: " + reading.getHeatIndex());
        System.out.println("Wind Chill: " + reading.getWindChill());
    }
}
