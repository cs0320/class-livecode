package edu.brown.cs32.serverReview.datasource;

import edu.brown.cs32.serverReview.datasource.weather.CachedWeatherSource;
import edu.brown.cs32.serverReview.datasource.weather.Geolocation;
import edu.brown.cs32.serverReview.datasource.weather.NWSAPIWeatherSource;
import edu.brown.cs32.serverReview.datasource.weather.WeatherData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestWeatherSource {

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testProvidenceWeatherCanLoad_REAL_CACHE() throws DatasourceException {
        // Reference is of real class type because we want to use statistics
        CachedWeatherSource source = new CachedWeatherSource(new NWSAPIWeatherSource(), 10, 1000);
        Geolocation loc = new Geolocation(41.8240,-71.4128);
        WeatherData res = source.getCurrentWeather(loc);

        assertNotNull(res);
        assertEquals(1, source.stats().missCount());
        assertEquals(0, source.stats().hitCount());

        // ... other properties worth checking
    }


}
