package edu.brown.cs32.nwsapi.datasource.mocks;

import edu.brown.cs32.nwsapi.datasource.DatasourceException;
import edu.brown.cs32.nwsapi.datasource.weather.Geolocation;
import edu.brown.cs32.nwsapi.datasource.weather.WeatherData;
import edu.brown.cs32.nwsapi.datasource.weather.WeatherDatasource;

/**
 * A datasource that never actually calls the NWS API, but always returns a constant
 * weather-data value. This is very useful in testing, and avoiding the costs of
 * real API invocations. The technique is called "mocking", as in "faking".
 */
public class MockedNWSAPISource implements WeatherDatasource {
    private final WeatherData constantData;

    public MockedNWSAPISource(WeatherData constantData) {
        this.constantData = constantData;
    }

    @Override
    public WeatherData getCurrentWeather(Geolocation loc) throws DatasourceException {
        return constantData;
    }
}
