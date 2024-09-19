package edu.brown.cs32.nwsapi.datasource.weather;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs32.nwsapi.datasource.DatasourceException;
import okio.Buffer;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 *  A datasource for weather forecasts via NWS API. This class uses the _real_
 *  API to return results. It has no caching in itself, and is focused on working
 *  with the real API.
 *
 *  The gearup code will give additional help with serializing/deserializing.
 */
public class NWSAPIWeatherSource implements WeatherDatasource {

    private static GridResponse resolveGridCoordinates(double lat, double lon) throws DatasourceException {
        try {
            URL requestURL = new URL("https", "api.weather.gov", "/points/"+lat+","+lon);
            HttpURLConnection clientConnection = connect(requestURL);
            Moshi moshi = new Moshi.Builder().build();

            // NOTE WELL: THE TYPES GIVEN HERE WOULD VARY ANYTIME THE RESPONSE TYPE VARIES
            // You need to give two things, here:
            // (1) the compiler-time generic type for JsonAdapter, in angle brackets
            // (2) the run-time type object that the adapter can follow when parsing.
            JsonAdapter<GridResponse> adapter = moshi.adapter(GridResponse.class).nonNull();

            // We could probably improve this from a defensive-programming perspective.
            GridResponse body = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
            clientConnection.disconnect();
            if(body == null || body.properties() == null || body.properties().gridId() == null)
                throw new DatasourceException("Malformed response from NWS");

            return body;
        } catch(IOException e) {
            // Why are we catching and re-throwing here? Because the caller won't necessarily be
            // aware that this is the NWS API source. So they may not be expecting an IOException.
            throw new DatasourceException(e.getMessage());
        }
    }

    /**
     * Private helper method; throws IOException so different callers
     * can handle differently if needed.
     */
    private static HttpURLConnection connect(URL requestURL) throws DatasourceException, IOException {
        URLConnection urlConnection = requestURL.openConnection();
        if(! (urlConnection instanceof HttpURLConnection))
            throw new DatasourceException("unexpected: result of connection wasn't HTTP");
        HttpURLConnection clientConnection = (HttpURLConnection) urlConnection;
        clientConnection.connect(); // GET
        if(clientConnection.getResponseCode() != 200)
            throw new DatasourceException("unexpected: API connection not success status "+clientConnection.getResponseMessage());
        return clientConnection;
    }

    /**
     * Given a geolocation, find the current weather at that location by
     * invoking the NWS API. This method will make real web requests.
     * @param loc the location to find weather at
     * @return the current weather at the given location
     * @throws DatasourceException if there is an issue obtaining the data from the API
     */
    @Override
    public WeatherData getCurrentWeather(Geolocation loc) throws DatasourceException, IllegalArgumentException {
        return getCurrentWeather(loc.lat(), loc.lon());
    }

    private static WeatherData getCurrentWeather(double lat, double lon) throws DatasourceException, IllegalArgumentException {
        try {
            // Double-check that the coordinates are valid. Yes, this has already
            // been checked, in principle, when the caller gave a Geolocation object.
            // But this is very cheap, and protects against mistakes I might make later
            // (such as making this method public, which would bypass the first check).
            if(!Geolocation.isValidGeolocation(lat, lon)) {
                throw new IllegalArgumentException("Invalid geolocation");
            }

            // NWS is not robust to high precision; limit to X.XXXX
            lat = Math.floor(lat * 10000.0) / 10000.0;
            lon = Math.floor(lon * 10000.0) / 10000.0;

            System.out.println("Debug: getCurrentWeather: "+lat+";"+lon);

            GridResponse gridResponse = resolveGridCoordinates(lat, lon);
            String gid = gridResponse.properties().gridId();
            String gx = gridResponse.properties().gridX();
            String gy = gridResponse.properties().gridY();

            System.out.println("Debug: gridResponse: "+gid+";"+gx+";"+gy);

            URL requestURL = new URL("https", "api.weather.gov", "/gridpoints/"+gid+"/"+gx+","+gy);
            HttpURLConnection clientConnection = connect(requestURL);
            Moshi moshi = new Moshi.Builder().build();

            // NOTE WELL: THE TYPES GIVEN HERE WOULD VARY ANYTIME THE RESPONSE TYPE VARIES
            JsonAdapter<ForecastResponse> adapter = moshi.adapter(ForecastResponse.class).nonNull();

            ForecastResponse body = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

            System.out.println(body); // records are nice for giving auto toString

            clientConnection.disconnect();

            // Validity checks for response
            if(body == null || body.properties() == null || body.properties().temperature() == null)
                throw new DatasourceException("Malformed response from NWS");
            if(body.properties().temperature().values().isEmpty())
                throw new DatasourceException("Could not obtain temperature data from NWS");

            // TODO not well protected, always takes first timestamp of report
            return new WeatherData(body.properties().temperature().values().get(0).value());
        } catch(IOException e) {
            throw new DatasourceException(e.getMessage(), e);
        }
    }

    ////////////////////////////////////////////////////////////////
    // There are multiple ways to structure data classes for Moshi. This is one I like
    // when I know I am dealing with nested Json OBJECTS (which group with squiggle-braces "{ ... }")
    //
    // NWS API data classes. These must be public for Moshi.
    // They are "inner classes"; refer to them as NWSAPIDataSource.GridResponse, etc.
    // The key is that the nesting of fields matches the expected nesting of the response string.
    // E.g., a GridResponse contains properties, which contain ... etc.
    //
    // When you are working with the Census in 2.2, instead of the NWS, note that they may be sending
    // back Json ARRAYS (which group with square-brackets "[ ... ]"). Refer to the gearup for that;
    // use the nested record approach for Json objects only.
    ////////////////////////////////////////////////////////////////

    public record GridResponse(String id, GridResponseProperties properties) { }
    // Note: case matters! "gridID" will get populated with null, because "gridID" != "gridId"
    public record GridResponseProperties(String gridId, String gridX, String gridY, String timeZone, String radarStation) {}

    public record ForecastResponse(String id, ForecastResponseProperties properties) {}
    public record ForecastResponseProperties(String updateTime, ForecastResponseTemperature temperature) {}
    public record ForecastResponseTemperature(String uom, List<ForecastResponseTempValue> values) {}
    public record ForecastResponseTempValue(String validTime, double value) {}

}
