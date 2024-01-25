package edu.brown.cs32.serverReview.datasource;

import edu.brown.cs32.serverReview.datasource.weather.Geolocation;
import edu.brown.cs32.serverReview.datasource.weather.WeatherData;
import edu.brown.cs32.serverReview.datasource.weather.WeatherDatasource;

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
