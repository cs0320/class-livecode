package edu.brown.cs32.serverReview.datasource.weather;

import edu.brown.cs32.serverReview.datasource.DatasourceException;

/**
 * An object that can be used to get the current weather at a certain location
 */
public interface WeatherDatasource {
    WeatherData getCurrentWeather(Geolocation loc) throws DatasourceException;
}
