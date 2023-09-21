package edu.brown.cs32.serverReview.datasource;

import edu.brown.cs32.serverReview.datasource.weather.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UNIT tests for the NWS API datasource class.
 * Notice the difference between this and the TestWeatherHandler class.
 * Here we are testing the specific datasources. In the handler tester,
 * we are testing the integration of those datasources and the handler code.
 */
public class TestWeatherSource {

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * This method tests the _real_ API datasource. It's good to have one such
     * test, but we strongly suggest mocking all others when possible. That way,
     * you aren't spamming the NWS with API requests whenever your tests run.
     * @throws DatasourceException
     */
    @Test
    public void testProvidenceWeatherCanLoad_REAL() throws DatasourceException {
        WeatherDatasource source = new NWSAPIWeatherSource();
        Geolocation loc = new Geolocation(41.8240,-71.4128);
        WeatherData res = source.getCurrentWeather(loc);

        assertNotNull(res);

        // ... surely, there are other properties worth checking...
    }


}
