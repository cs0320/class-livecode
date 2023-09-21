package edu.brown.cs32.serverReview.main;

import edu.brown.cs32.serverReview.datasource.weather.NWSAPIWeatherSource;
import edu.brown.cs32.serverReview.datasource.weather.WeatherDatasource;
import spark.Spark;

/**
 * Notice that the name of the main class doesn't need to be Main.
 *
 * I haven't included the CORS header lines in this example; they clutter the
 * flow of the code. See the gearup code for the CORS lines.
 */
public class Server {

    static final int port = 3232;

    /**
      Project state object; useful to allow multiple commands to share state.
      Notice that we aren't using a specific class here, just something that
      implements the WeatherDatasource interface.

      Since this example has only one endpoint/command, it's not strictly needed.
      But this is a useful thing to do if you have multiple endpoints...
     */
    private final WeatherDatasource state;

    public Server(WeatherDatasource toUse) {
        // Use whatever was dependency-injected into this constructor
        state = toUse;
        // Set up our SparkJava server:
        Spark.port(port);
        // Listen on the weather endpoint, and route requests to a new
        // WeatherHandler object that references our global data source.
        Spark.get("/weather", new WeatherHandler(state));
        // Wait until the server has started.
        Spark.awaitInitialization();
    }

    public static void main(String[] args) {
        // At time of creation, we decide on a specific datasource class:
        Server server = new Server(new NWSAPIWeatherSource());
        // Notice that this runs, but the program continues executing. Why
        // do you think that is? (We'll address this in a couple of weeks.)
        System.out.println("Server started; exiting main...");
    }
}
