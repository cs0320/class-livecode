package edu.brown.cs32.serverReview.datasource.weather;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs32.serverReview.datasource.DatasourceException;
import okio.Buffer;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 *  A datasource for weather forecasts via NWS API.
 */
public class NWSAPIWeatherSource implements WeatherDatasource {

    public NWSAPIWeatherSource() {}

    private static GridResponse resolveGridCoordinates(double lat, double lon) throws DatasourceException {
        try {
            URL requestURL = new URL("https", "api.weather.gov", "/points/"+lat+","+lon);
            HttpURLConnection clientConnection = connect(requestURL);
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<GridResponse> adapter = moshi.adapter(GridResponse.class).nonNull();
            // NOTE: important. New pattern for routing the input stream vs. how Gson worked.
            GridResponse body = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
            clientConnection.disconnect();
            if(body == null || body.properties() == null || body.properties().gridId() == null)
                throw new DatasourceException("Malformed response from NWS");
            return body;
        } catch(IOException e) {
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
     * @throws DatasourceException
     */
    public WeatherData getCurrentWeather(Geolocation loc) throws DatasourceException {
        return getCurrentWeather(loc.lat(), loc.lon());
    }

    private static WeatherData getCurrentWeather(double lat, double lon) throws DatasourceException {
        try {
            // NWS is not robust to high precision; limit to X.XXXX
            lat = Math.floor(lat * 10000.0) / 10000.0;
            lon = Math.floor(lon * 10000.0) / 10000.0;

            GridResponse gridResponse = resolveGridCoordinates(lat, lon);
            String gid = gridResponse.properties().gridId();
            String gx = gridResponse.properties().gridX();
            String gy = gridResponse.properties().gridY();

            URL requestURL = new URL("https", "api.weather.gov", "/gridpoints/"+gid+"/"+gx+","+gy);
            HttpURLConnection clientConnection = connect(requestURL);
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<ForecastResponse> adapter = moshi.adapter(ForecastResponse.class).nonNull();
            // NOTE: important. New pattern for routing the input stream vs. how Gson worked.
            ForecastResponse body = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

            //System.out.println(body); // records are nice for giving auto toString
            clientConnection.disconnect();
            if(body == null || body.properties() == null || body.properties().temperature() == null)
                throw new DatasourceException("Malformed response from NWS");
            assert(body.properties().temperature().values().size() > 0);

            // TODO not well protected, always takes first timestamp of report
            return new WeatherData(body.properties().temperature().values().get(0).value());
        } catch(IOException e) {
            throw new DatasourceException(e.getMessage());
        }
    }

    ////////////////////////
    // NWS API data classes. These must be public.
    ////////////////////////

    public record GridResponse(String id, GridResponseProperties properties) { }
    // Note: case matters! "gridID" will get populated with null, because "gridID" != "gridId"
    public record GridResponseProperties(String gridId, String gridX, String gridY, String timeZone, String radarStation) {}

    public record ForecastResponse(String id, ForecastResponseProperties properties) {}
    public record ForecastResponseProperties(String updateTime, ForecastResponseTemperature temperature) {}
    public record ForecastResponseTemperature(String uom, List<ForecastResponseTempValue> values) {}
    public record ForecastResponseTempValue(String validTime, double value) {}

}
