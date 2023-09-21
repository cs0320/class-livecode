package edu.brown.cs32.serverReview.datasource;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs32.serverReview.datasource.mocks.MockedNWSAPISource;
import edu.brown.cs32.serverReview.datasource.weather.Geolocation;
import edu.brown.cs32.serverReview.datasource.weather.WeatherData;
import edu.brown.cs32.serverReview.main.WeatherHandler;
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

/**
 * Integration tests: send real web requests to our server as it is
 * running. Note that for these, we prefer to avoid sending many
 * real API requests to the NWS, and use "mocking" to avoid it.
 * (There are many other reasons to use mock data here. What are they?)
 *
 * In short, there are two new techniques demonstrated here:
 * integration testing; and
 * testing with mock data / mock objects.
 */
public class TestWeatherHandler {

    @BeforeAll
    public static void setupOnce() {
        // Pick an arbitrary free port
        Spark.port(0);
        // Eliminate logger spam in console for test suite
        Logger.getLogger("").setLevel(Level.WARNING); // empty name = root
    }

    // Helping Moshi serialize Json responses; see the gearup for more info.
    private final Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    private JsonAdapter<Map<String, Object>> adapter;
    private JsonAdapter<WeatherData> weatherDataAdapter;

    @BeforeEach
    public void setup() {
        // Re-initialize parser, state, etc. for every test method

        // Use *MOCKED* data when in this test environment.
        // Notice that the WeatherHandler code doesn't need to care whether it has
        // "real" data or "fake" data. Good separation of concerns enables better testing.
        WeatherDatasource mockedSource = new MockedNWSAPISource(new WeatherData(20));
        Spark.get("/weather", new WeatherHandler(mockedSource));
        Spark.awaitInitialization(); // don't continue until the server is listening

        // New Moshi adapter for responses (and requests, too; see a few lines below)
        //   For more on this, see the Server gearup.
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
     *                (Note: this would be better if it had more structure!)
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

        // Mocked data: correct temp? We know what it is, because we mocked.
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


}
