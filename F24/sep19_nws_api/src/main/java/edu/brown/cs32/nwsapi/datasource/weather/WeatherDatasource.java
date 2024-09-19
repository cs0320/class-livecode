package edu.brown.cs32.nwsapi.datasource.weather;

import edu.brown.cs32.nwsapi.datasource.DatasourceException;

/**
 * A WeatherDatasource can be used to get the current weather
 * at a certain location. It is not specified _where_ the data comes from,
 * how old it might be, etc.
 */
public interface WeatherDatasource {
    /**
     * Retrieve the current weather (in the form of a WeatherData record) at a
     * given geolocation.
     *
     * @param loc the geolocation to retrieve data for
     * @return the weather data obtained
     * @throws DatasourceException if there is an issue retrieving data for this geolocation
     * @throws IllegalArgumentException if the geolocation given is invalid
     */
    WeatherData getCurrentWeather(Geolocation loc) throws DatasourceException, IllegalArgumentException;
}
