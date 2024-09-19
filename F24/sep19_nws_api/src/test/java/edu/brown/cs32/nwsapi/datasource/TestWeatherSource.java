package edu.brown.cs32.nwsapi.datasource;

import edu.brown.cs32.nwsapi.datasource.weather.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UNIT tests for the NWS API datasource class.
 * Notice the difference between this and the TestWeatherHandler class.
 * Here we are testing the specific datasources. In the handler tester,
 * we are testing the integration of those datasources and the handler code.
 * These are _different_!
 *
 * In particular, here we are very concerned about how the real fetching of data functions.
 */
public class TestWeatherSource {

    @BeforeEach
    public void setup() {
        // No setup needed
    }

    @AfterEach
    public void tearDown() {
        // No teardown needed
    }

    /**
     * This method tests the _real_ API datasource. It's good to have at least one such
     * test, but we strongly suggest mocking when possible in *integration* testing.
     * That way, you aren't spamming the NWS with API requests whenever your tests run.
     */
    @Test
    public void testProvidenceWeatherCanLoad_REAL() throws DatasourceException {
        WeatherDatasource source = new NWSAPIWeatherSource();
        Geolocation loc = new Geolocation(41.8240,-71.4128); // Providence
        WeatherData res = source.getCurrentWeather(loc);

        assertNotNull(res);

        // For demo purposes
        System.out.println(res.temp_C());

        // This is _live data_! So we can't check the temperature is an exact value.
        // We should at least check the temperature is in a reasonable range. But be
        // tolerant of variation... (Better would be to put these into a "validTemp" method.)

        // Absolute zero is −273.15 C
        assertTrue(res.temp_C() > -273.16);
        // Average surface temperature of Venus is around 460 C, IIRC
        assertTrue(res.temp_C() < 460);
    }


}
