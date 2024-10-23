import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import weather.StevensonReading;

/**
 * Class for testing the StevensonReading class {@link StevensonReading}.
 */

public class StevensonReadingTest {
  /**
   * Tests the constructor with valid values.
   * Verifies that the object is created with the correct values
   * and the methods return the expected results.
   */
  @Test
  public void testConstructorValidValues() {
    StevensonReading reading = new StevensonReading(20.0, 10.0, 5.0, 2.0);
    assertEquals(20, reading.getTemperature());
    assertEquals(10, reading.getDewPoint());
    assertEquals(5, reading.getWindSpeed());
    assertEquals(2, reading.getTotalRain());
  }

  /**
   * Tests the constructor with an invalid dew point (greater than air temperature).
   * Expects an {@link IllegalArgumentException} to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidDewPoint() {
    new StevensonReading(10.0, 15.0, 5.0, 2.0);
  }

  /**
   * Tests the constructor with a negative wind speed.
   * Expects an {@link IllegalArgumentException} to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegativeWindSpeed() {
    new StevensonReading(10.0, 5.0, -1.0, 2.0);
  }

  /**
   * Tests the constructor with a negative rain value.
   * Expects an {@link IllegalArgumentException} to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegativeRain() {
    new StevensonReading(10.0, 5.0, 3.0, -0.1);
  }

  /**
   * Tests the {@link StevensonReading#getRelativeHumidity()} method.
   * Verifies that the relative humidity is calculated correctly
   * and falls within the expected range of 0% to 100%.
   */
  @Test
  public void testGetRelativeHumidity() {
    StevensonReading reading = new StevensonReading(30.0, 25.0, 10.0, 0.0);
    int humidity = reading.getRelativeHumidity();
    assertTrue("Humidity should be between 0% and 100%", humidity >= 0 && humidity <= 100);
    assertEquals(75, humidity);
  }

  /**
   * Tests the {@link StevensonReading#getHeatIndex()} method.
   * Verifies that the heat index is calculated correctly based on temperature
   * and relative humidity.
   */
  @Test
  public void testGetHeatIndex() {
    StevensonReading reading = new StevensonReading(95.590494, 86.909615, 14.734458, 24);
    int heatIndex = reading.getHeatIndex();
    assertNotNull("Heat index should be calculable", heatIndex);
    assertEquals(755, heatIndex);
  }

  /**
   * Tests the {@link StevensonReading#getWindChill} method to ensure it correctly calculates
   * T = airTemperature = 0 °C = 32°F
   * v = windSpeed = 10 mph
   * windChill = 35.74 + (0.6215 * 32)
   *            - (35.75 * Math.pow(10, 0.16))
   *            + (0.4275 * 32 * Math.pow(10, 0.16))
   * windChill = 23.727
   * Rounded to the nearest integer: windChill = 24 mph
   */
  @Test
  public void testGetWindChill() {
    StevensonReading reading = new StevensonReading(0.0, -5.0, 10.0, 2.0);

    assertEquals(24, reading.getWindChill());
  }

  /**
   * Tests the {@link StevensonReading#getWindChill} method to ensure that it handles the conversion
   * from Celsius to Fahrenheit correctly and applies the wind chill formula accurately.
   */
  @Test
  public void testGetWindChillConversionAndFormula() {
    // 10°C is 50°F, wind speed 20 mph
    StevensonReading reading = new StevensonReading(10.0, 5.0, 20.0, 2.0);
    assertEquals(44, reading.getWindChill());
  }

  /**
   * Tests the {@link StevensonReading#toString()} method.
   * Verifies that the string representation of the object matches the expected format.
   */
  @Test
  public void testToString() {
    StevensonReading reading = new StevensonReading(15.0, 10.0, 5.0, 1.0);
    String expected = "Reading: T = 15, D = 10, v = 5, rain = 1";
    assertEquals(expected, reading.toString());
  }

  /**
   * Tests the {@link StevensonReading#equals(Object)}
   * and {@link StevensonReading#hashCode()} methods.
   * Verifies that objects with the same state are considered equal and have the same hash code,
   * while objects with different states are not equal.
   */
  @Test
  public void testEqualsAndHashCode() {
    StevensonReading reading1 = new StevensonReading(20.0, 15.0, 10.0, 2.0);
    StevensonReading reading2 = new StevensonReading(20.0, 15.0, 10.0, 2.0);
    StevensonReading reading3 = new StevensonReading(20.0, 15.0, 5.0, 2.0);

    assertEquals(reading1, reading2);
    assertEquals(reading1.hashCode(), reading2.hashCode());
    assertNotEquals(reading1, reading3);
  }
}
