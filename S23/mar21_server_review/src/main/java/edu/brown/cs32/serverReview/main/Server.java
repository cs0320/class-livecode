package edu.brown.cs32.serverReview.main;

import edu.brown.cs32.serverReview.datasource.weather.CachedWeatherSource;
import edu.brown.cs32.serverReview.datasource.weather.NWSAPIWeatherSource;
import spark.Spark;

public class Server {

    static final int port = 3232;

    /*
      Project state object; useful to allow multiple commands to share state
     */
    static CachedWeatherSource state;

    public static void main(String[] args) {
        state = new CachedWeatherSource(new NWSAPIWeatherSource(), 10, 1000);
        Spark.port(port);
        Spark.get("/weather", new WeatherHandler(state));
    }
}
