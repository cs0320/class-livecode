package edu.brown.cs32.serverReview.main;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs32.serverReview.datasource.DatasourceException;
import edu.brown.cs32.serverReview.datasource.weather.Geolocation;
import edu.brown.cs32.serverReview.datasource.weather.WeatherData;
import edu.brown.cs32.serverReview.datasource.weather.WeatherDatasource;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class WeatherHandler implements Route {
    private final WeatherDatasource state;

    public WeatherHandler(WeatherDatasource state) {
        this.state = state;
    }

    @Override
    public Object handle(Request request, Response response) {
        // Get the location that the request is for
        String lat = request.queryParams("lat");
        String lon = request.queryParams("lon");

        // Prepare to send a reply
        Moshi moshi = new Moshi.Builder().build();
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
        JsonAdapter<WeatherData> weatherDataAdapter = moshi.adapter(WeatherData.class);
        Map<String, Object> responseMap = new HashMap<>();

        // Generate the reply
        try {
            double lat_double = Double.parseDouble(lat);
            double lon_double = Double.parseDouble(lon);
            Geolocation loc = new Geolocation(lat_double, lon_double);
            // Low-level NWS API invocation isn't the job of this class!
            // Neither is caching!
            WeatherData data = state.getCurrentWeather(loc);
            // Building responses is the job of this class:
            responseMap.put("type", "success");

            // Decision point; note the difference here
            responseMap.put("temperature", weatherDataAdapter.toJson(data));
            //responseMap.put("temperature", data);

            return adapter.toJson(responseMap);
        } catch (DatasourceException e) {
            responseMap.put("type", "error");
            responseMap.put("error_type", "datasource");
            responseMap.put("details", e.getMessage());
            return adapter.toJson(responseMap);
        }
    }
}
