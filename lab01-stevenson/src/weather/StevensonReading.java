package weather;

import java.util.Objects;

/**
 * The {@link StevensonReading} class represents a weather reading taken from a Stevenson Screen.
 * A Stevenson Screen is a standard shelter that protects very precise meteorological instruments
 * measuring temperature (Celsius), dew point (Celsius), wind speed (mph), rain (mm), etc.
 *
 * The class is immutable and cannot be extended or modified after its creation.
 *
 */
public final class StevensonReading implements WeatherReading {

  // Constants (coefficients) for heat index calculation
  private static final double C1 = -8.78469475556;
  private static final double C2 = 1.61139411;
  private static final double C3 = 2.33854883889;
  private static final double C4 = -0.14611605;
  private static final double C5 = -0.012308094;
  private static final double C6 = -0.0164248277778;
  private static final double C7 = 0.002211732;
  private static final double C8 = 0.00072546;
  private static final double C9 = -0.000003582;

  private final double airTemperature;
  private final double dewPoint;
  private final double windSpeed;
  private final double totalRain;


  /**
   * Constructs a new {@link StevensonReading} object with the specified parameters.
   *
   * @param airTemperature    the air temperature in degrees Celsius
   * @param dewPoint          the dew point temperature in degrees Celsius;
   *                          it cannot be greater than the air temperature
   * @param windSpeed         the wind speed in miles per hour; must be non-negative
   * @param totalRain         the total rain received in the last 24 hours in millimeters;
   *                          it must be non-negative
   * @throws IllegalArgumentException if the dew point is greater than the air temperature,
   *                                  or if the wind speed or rain amount is negative
   */
  public StevensonReading(double airTemperature,
                          double dewPoint, double windSpeed, double totalRain) {
    if (dewPoint > airTemperature) {
      throw new IllegalArgumentException("Dew point cannot be greater than air temperature.");
    }
    if (windSpeed < 0 || totalRain < 0) {
      throw new IllegalArgumentException("Wind speed and rain amount cannot be negative.");
    }
    this.airTemperature = airTemperature;
    this.dewPoint = dewPoint;
    this.windSpeed = windSpeed;
    this.totalRain = totalRain;
  }

  /**
   * Returns the air temperature in degrees Celsius.
   *
   * @return the air temperature in degrees Celsius
   */
  // Implementing the methods from the WeatherReading interface
  @Override
  public int getTemperature() {
    return (int) Math.round(airTemperature);
  }

  /**
   * Returns the dew point temperature in degrees Celsius.
   * Rounded to the nearest integer.
   *
   * @return the dew point temperature in degrees Celsius
   */
  @Override
  public int getDewPoint() {
    return (int) Math.round(dewPoint);
  }

  /**
   * Returns the wind speed in miles per hour.
   * Rounded to the nearest integer.
   *
   * @return the wind speed in miles per hour
   */
  @Override
  public int getWindSpeed() {
    return (int) Math.round(windSpeed);
  }

  /**
   * Returns the total rain received in the last 24 hours in millimeters.
   * Rounded to the nearest integer.
   *
   * @return the total rain received in the last 24 hours in millimeters
   */
  @Override
  public int getTotalRain() {
    return (int) Math.round(totalRain);
  }

  /**
   * Calculates and returns the relative humidity as a percentage.
   * The relative humidity is calculated using the vapor pressure of the dew point
   * and the vapor pressure of the air temperature.
   * The actual vapor (pressure) is calculated from the dew point.
   * The saturated vapor (pressure) is calculated from the actual temperature.
   *
   * Rounded to the nearest integer.
   *
   * @return the relative humidity as a percentage
   *
   * @throws IllegalArgumentException if humidity is outside the 0-100% range
   */
  @Override
  public int getRelativeHumidity() {
    // Calculate relative humidity and round to the nearest integer
    double actualVapor = 6.11 * Math.pow(10, (7.5 * dewPoint) / (237.3 + dewPoint));
    double saturatedVapor = 6.11 * Math.pow(10, (7.5 * airTemperature) / (237.3 + airTemperature));
    double humidity = (actualVapor / saturatedVapor) * 100;
    int roundedHumidity = (int) Math.round(humidity);

    // Throw exception if humidity is outside the 0-100% range
    if (roundedHumidity < 0 || roundedHumidity > 100) {
      throw new IllegalArgumentException("Relative humidity must be between 0% and 100%.");
    }

    // Return the rounded Relative humidity as a percentage
    return roundedHumidity;
  }

  /**
   * Calculates and returns the heat index based on the air temperature and relative humidity.
   * The heat index is a measure of how hot it feels
   * when relative humidity is factored in with the actual temperature.
   *
   * Use the exact the calculated value of the relative humidity without rounding
   * to avoid the discrepancies (that could arise from rounding errors)
   * in the result of the getHeatIndex
   *
   * Rounded to the nearest integer.
   *
   * @return the heat index
   */
  @Override
  public int getHeatIndex() {
    double t = airTemperature;  // Air temperature in Celsius
    double actualVapor = 6.11 * Math.pow(10, (7.5 * dewPoint) / (237.3 + dewPoint));
    double saturatedVapor = 6.11 * Math.pow(10, (7.5 * airTemperature) / (237.3 + airTemperature));
    //the exact calculated value of humidity without rounding
    double humidity = (actualVapor / saturatedVapor) * 100;
    double r = humidity;  // Relative humidity as a percentage

    // Breakdown of each term in the heat index formula
    double term1 = C1;
    double term2 = C2 * t;
    double term3 = C3 * r;
    double term4 = C4 * t * r;
    double term5 = C5 * Math.pow(t, 2);
    double term6 = C6 * Math.pow(r, 2);
    double term7 = C7 * Math.pow(t, 2) * r;
    double term8 = C8 * t * Math.pow(r, 2);
    double term9 = C9 * Math.pow(t, 2) * Math.pow(r, 2);

    // Sum all terms to calculate the heat index
    double heatIndex = term1 + term2 + term3 + term4 + term5 + term6 + term7 + term8 + term9;

    // Return the rounded heat index
    return (int) Math.round(heatIndex);
  }

  /**
   * Calculates and returns the wind chill based on
   * the air temperature in Fahrenheit and the wind speed in miles per hour.
   * Wind chill is related to heat index and is used
   * when the real-feel temperature is lower than the actual temperature.
   *
   * Rounded to the nearest integer.
   *
   * @return the wind chill
   */
  @Override
  public int getWindChill() {
    // Convert Celsius to Fahrenheit
    double airTemperatureFahrenheit = (9.0 / 5) * airTemperature + 32;
    double v = windSpeed;

    double windChill = 35.74 + (0.6215 * airTemperatureFahrenheit)
              - (35.75 * Math.pow(v, 0.16))
              + (0.4275 * airTemperatureFahrenheit * Math.pow(v, 0.16));
    // Convert the result back to Celsius
    return (int) Math.round((windChill - 32) * 5.0 / 9);
  }
  
  /**
   * Returns a string representation of this {@link StevensonReading} object.
   * The string representation has the format:
   * "Reading: T = temperature, D = dewPoint, v = windSpeed, rain = rainInMm".
   *
   * @return a string representation of this object
   */
  @Override
  public String toString() {
    return String.format("Reading: T = %d, D = %d, v = %d, rain = %d",
            getTemperature(), getDewPoint(), getWindSpeed(), getTotalRain());
  }

  /**
   * Compares this {@link StevensonReading} object with the specified object for equality.
   * Two {@link StevensonReading} objects are considered equal if they have the same temperature,
   * dew point, wind speed, and rain values.
   *
   * @param obj the object to be compared for equality with this {@link StevensonReading} object
   * @return {@link true} if the specified object is equal to this object; {@link false} otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    StevensonReading that = (StevensonReading) obj;
    return Double.compare(that.airTemperature, airTemperature) == 0
           && Double.compare(that.dewPoint, dewPoint) == 0
           && Double.compare(that.windSpeed, windSpeed) == 0
           && Double.compare(that.totalRain, totalRain) == 0;
  }

  /**
   * Returns the hash code value for this {@link StevensonReading} object.
   * The hash code is computed based on the temperature, dew point, wind speed, and rain values.
   *
   * @return the hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(airTemperature, dewPoint, windSpeed, totalRain);
  }
}
