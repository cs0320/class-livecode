package edu.brown.cs32.serverReview.datasource;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs32.serverReview.datasource.weather.Geolocation;
import edu.brown.cs32.serverReview.datasource.weather.WeatherData;
import edu.brown.cs32.serverReview.main.WeatherHandler;
import edu.brown.cs32.serverReview.datasource.weather.NWSAPIWeatherSource;
import edu.brown.cs32.serverReview.datasource.weather.WeatherDatasource;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWeatherHandler {

    @BeforeAll
    public static void setupOnce() {
        Spark.port(0);
        Logger.getLogger("").setLevel(Level.WARNING); // empty name = root
    }

    private final Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    private JsonAdapter<Map<String, Object>> adapter;
    private JsonAdapter<WeatherData> weatherDataAdapter;

    @BeforeEach
    public void setup() {
        // Re-initialize parser, state, etc. for every test method

        // Real data (cached):
        //WeatherDatasource source = new CachedWeatherSource(new NWSAPIWeatherSource(), 10, 1000);
        // Real data (uncached)
        WeatherDatasource source = new NWSAPIWeatherSource();
        // Mocked data:
        //WeatherDatasource source = new MockedNWSAPISource(new WeatherData(20));

        Spark.get("/weather", new WeatherHandler(source));
        Spark.awaitInitialization(); // don't continue until the server is listening

        // New Moshi adapter for responses (and requests, too; see a few lines below)
        Moshi moshi = new Moshi.Builder().build();
        adapter = moshi.adapter(mapStringObject);
        weatherDataAdapter = moshi.adapter(WeatherData.class);
    }

    @AfterEach
    public void tearDown() {
        // Gracefully stop Spark listening on both endpoints
        Spark.unmap("/weather");
        Spark.awaitStop(); // don't proceed until the server is stopped
    }

    /*
    Recall that the "throws" clause doesn't matter -- JUnit will fail if an
    exception is thrown that hasn't been declared as a parameter to @Test.
     */

    /**
     * Helper to start a connection to a specific API endpoint/params
     * @param apiCall the call string, including endpoint
     *                (TODO: this would be better if it had more structure!)
     * @return the connection for the given URL, just after connecting
     * @throws IOException if the connection fails for some reason
     */
    private HttpURLConnection tryRequest(String apiCall) throws IOException {
        // Configure the connection (but don't actually send a request yet)
        URL requestURL = new URL("http://localhost:"+Spark.port()+"/"+apiCall);
        HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();
        // The request body contains a Json object
        clientConnection.setRequestProperty("Content-Type", "application/json");
        // We're expecting a Json object in the response body
        clientConnection.setRequestProperty("Accept", "application/json");

        clientConnection.connect();
        return clientConnection;
    }

    final Geolocation providence = new Geolocation(41.8240, -71.4128);

    @Test
    public void testWeatherRequestSuccess() throws IOException {
        /////////// LOAD DATASOURCE ///////////
        // Set up the request, make the request
        HttpURLConnection loadConnection = tryRequest("weather?"+providence.toOurServerParams());
        // Get an OK response (the *connection* worked, the *API* provides an error response)
        assertEquals(200, loadConnection.getResponseCode());
        // Get the expected response: a success
        Map<String, Object> body = adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
        showDetailsIfError(body);
        assertEquals("success", body.get("type"));

        // Mocked data: correct temp?
        assertEquals(
                weatherDataAdapter.toJson(new WeatherData(20.0)),
                body.get("temperature"));
        loadConnection.disconnect();
    }

    private void showDetailsIfError(Map<String, Object> body) {
        if(body.containsKey("type") && "error".equals(body.get("type"))) {
            System.out.println(body.toString());
        }
    }

    @Test
    public void fuzzWeather() throws IOException {
        final int numTrials = 2;
        for(int count=0;count<numTrials;count++) {
            // Small region
            double lat = ThreadLocalRandom.current().nextDouble(40, 42);
            double lon = ThreadLocalRandom.current().nextDouble(-72, -70);
            Geolocation loc = new Geolocation(lat, lon);

            HttpURLConnection loadConnection = tryRequest("weather?"+loc.toOurServerParams());
            assertEquals(200, loadConnection.getResponseCode());
            Map<String, Object> body = adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
            showDetailsIfError(body);
            assertEquals("success", body.get("type"));
        }
    }



}
