package edu.brown.cs32.nwsapi.main;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs32.nwsapi.datasource.DatasourceException;
import edu.brown.cs32.nwsapi.datasource.weather.Geolocation;
import edu.brown.cs32.nwsapi.datasource.weather.WeatherData;
import edu.brown.cs32.nwsapi.datasource.weather.WeatherDatasource;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles a request to *OUR* server by making a request to *THEIR* server.
 *
 * Implements Route: Route is the SparkJava interface for request handlers.
 *
 * (1) The job of this class should be handling requests and sending responses,
 *   and NOTHING ELSE. That includes specific details about how the weather
 *   data is obtained. Make a separate datasource class for that, for many
 *   reasons (e.g., you can unit test that datasource much more easily, you
 *   can use the proxy pattern to add caching, etc. etc.)
 *
 * (2) This class shouldn't crash the system on an error. Rather, it should
 *   construct and send an informative error response, instead. It is OK to
 *   log the error, but the API client won't see that. Tell them what happened
 *   in terms they will understand.
 */

public class WeatherHandler implements Route {
    // Internal datasource (note using interface; might be NWS, might be something else)
    private final WeatherDatasource state;

    /**
     * Accepts a weather handler via dependency injection. The handler need not
     * know or care what kind of WeatherDatasource we give it.
     * @param state
     */
    public WeatherHandler(WeatherDatasource state) {
        this.state = state;
    }

    @Override
    public Object handle(Request request, Response response) {
        // Step 1: Prepare to send a reply of some sort
        Moshi moshi = new Moshi.Builder().build();
        // Replies will be Maps from String to Object. This isn't ideal; see reflection...
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
        JsonAdapter<WeatherData> weatherDataAdapter = moshi.adapter(WeatherData.class);
        Map<String, Object> responseMap = new HashMap<>();

        // Get the location that the request is for
        String lat = request.queryParams("lat");
        String lon = request.queryParams("lon");
        if(lat == null || lon == null) {
            // Bad request! Send an error response.
            responseMap.put("query_lon", lon);
            responseMap.put("query_lat", lat);
            responseMap.put("type", "error");
            responseMap.put("error_type", "missing_parameter");
            responseMap.put("error_arg", lat == null ? "lat" : "lon");
            return adapter.toJson(responseMap);
        }

        // Generate the reply
        try {
            double lat_double = Double.parseDouble(lat);
            double lon_double = Double.parseDouble(lon);
            Geolocation loc = new Geolocation(lat_double, lon_double);
            // Low-level NWS API invocation isn't the job of this class!
            // Neither is caching! Just get the data from whatever the source is.
            WeatherData data = state.getCurrentWeather(loc);
            // Building responses *IS* the job of this class:
            responseMap.put("type", "success");

            responseMap.put("temperature", weatherDataAdapter.toJson(data));
            // Decision point; note the difference vs. this
            //responseMap.put("temperature", data);

            return adapter.toJson(responseMap);
        } catch (DatasourceException e) {
            // Issues getting the data. Return an error response.
            responseMap.put("query_lon", lon);
            responseMap.put("query_lat", lat);
            responseMap.put("type", "error");
            responseMap.put("error_type", "datasource");
            responseMap.put("details", e.getMessage());
            return adapter.toJson(responseMap);
        } catch (IllegalArgumentException e) {
            // Invalid geolocation, probably. Return an error response.
            responseMap.put("query_lon", lon);
            responseMap.put("query_lat", lat);
            responseMap.put("type", "error");
            responseMap.put("error_type", "bad_parameter");
            responseMap.put("details", e.getMessage());
            return adapter.toJson(responseMap);
        }
    }
}
