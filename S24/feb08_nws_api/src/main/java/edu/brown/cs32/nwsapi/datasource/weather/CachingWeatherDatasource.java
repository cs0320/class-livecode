package edu.brown.cs32.nwsapi.datasource.weather;

import edu.brown.cs32.nwsapi.datasource.DatasourceException;

/**
 * Proxy wrapper for a WeatherDatasource that caches its response
 * for efficiency. NOT FULLY IMPLEMENTED! But this shape should be useful
 * as reference...
 */
public class CachingWeatherDatasource implements WeatherDatasource {
    // The original datasource to wrap and invoke if needed
    private final WeatherDatasource original;

    public CachingWeatherDatasource(WeatherDatasource original) {
        this.original = original;
    }

    @Override
    public WeatherData getCurrentWeather(Geolocation loc) throws DatasourceException, IllegalArgumentException {
        throw new UnsupportedOperationException();

        // If this were implemented, we'd do something similar to the
        // caching livecode from last time.
    }
}
