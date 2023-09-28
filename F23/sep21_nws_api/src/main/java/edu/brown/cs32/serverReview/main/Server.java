package edu.brown.cs32.serverReview.main;

import edu.brown.cs32.serverReview.datasource.weather.NWSAPIWeatherSource;
import edu.brown.cs32.serverReview.datasource.weather.WeatherDatasource;
import spark.Spark;

import static spark.Spark.after;

/**
 * Notice that the name of the main class doesn't need to be Main.
 *
 * I've updated this to include CORS headers -- they do clutter the code, but it's not so bad.
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

        /*
            Setting CORS headers to allow cross-origin requests from the client; this is necessary for the client to
            be able to make requests to the server.

            By setting the Access-Control-Allow-Origin header to "*", we allow requests from any origin.
            This is not a good idea in real-world applications, since it opens up your server to cross-origin requests
            from any website. Instead, you should set this header to the origin of your client, or a list of origins
            that you trust.

            By setting the Access-Control-Allow-Methods header to "*", we allow requests with any HTTP method.
            Again, it's generally better to be more specific here and only allow the methods you need, but for
            this demo we'll allow all methods.

            We recommend you learn more about CORS with these resources:
                - https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
                - https://portswigger.net/web-security/cors
         */
        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "*");
        });


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
