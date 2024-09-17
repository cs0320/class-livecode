package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;
import spark.Spark;

/**
 * Simple example of setting up logging for a SparkJava API server.
 *
 * Note: we really should migrate away from SparkJava in 0320, since it appears to not
 * be well-maintained. There are a few good alternatives.
 *
 * SparkJava uses slf4j SimpleLogger by default. This logger appears to be threadsafe:
 *   https://github.com/qos-ch/slf4j/blob/master/slf4j-simple/src/main/java/org/slf4j/simple/SimpleLogger.java
 * so we won't synchronize anything ourselves here.
 */
public class Main {
    /** Path where the log will go. Currently just a file in the current working directory. */
    static final String LOG_PATH = "server_log.log";

    /** Logger instance that can be used to write to the log file. */
    static final Logger logger;

    // This code will be executed at class instantiation. If we wait until object instantiation,
    // it is too late to set the configuration of the logger before the logger is created.
    static {
        // These could also be in a separate configuration file.
        System.setProperty(SimpleLogger.LOG_FILE_KEY, LOG_PATH);
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "yyyy-MM-dd HH:mm:ss:SSS Z");
        logger = LoggerFactory.getLogger(Logger.class);

        // Keep in mind that log entries have levels at differing priorities:
        // ERROR > WARN > INFO > DEBUG > TRACE
        // By default, debug and trace won't be captured. SparkJava produces debug-level
        // messages on its own, which will be suppressed unless we allow them to be logged.
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }
    public static void main(String[] args) {
        // Set the port to use
        Spark.port(13232);

        // Set an exception handler for anything extending Exception.
        // If we had more refined exceptions, we could have specific special cases
        // registered for them, and then use their fields.
        Spark.exception(Exception.class, (exception, request, response) -> {
            logger.warn(exception.getClass()+" "+exception.getMessage());
        });

        // localhost:13232/hello
        Spark.before("/hello", (request,response) -> logger.info("HELLO: "+prettyRequest(request)));
        Spark.get("/hello", (request, response) -> "Hello World!");

        // localhost:13232  (will give a 404 if we don't add a handler for the root)
        Spark.before("/", (request,response) -> logger.info("ROOT: "+prettyRequest(request)));
        Spark.get("/", (request,response) -> "Use the 'hello' endpoint.");

        // localhost:13232/oops
        Spark.before("/oops", (request,response) -> logger.info("OOPS: "+prettyRequest(request)));
        Spark.get("/oops", (request,response) -> { throw new Exception("oh no!");} );
    }

    static String prettyRequest(spark.Request request) {
        StringBuilder queryParamsString = new StringBuilder();
        for(String key : request.queryParams()) {
            queryParamsString.append(key)
                    .append(": ")
                    .append(request.queryParams(key))
                    .append("   ");
        }
        return  request.requestMethod() + " " +
                request.url() + " " +
                queryParamsString + " " +
                request.body();
    }
}