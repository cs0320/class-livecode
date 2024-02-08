package edu.brown.cs32.nwsapi.datasource.weather;

/**
 * A record representing a weather-report datum, or rather the
 * information we want to retain from a weather report and provide
 * to our client(s).
 * @param temp_C the temperature in degrees Celsius
 */
public record WeatherData(double temp_C) {}
