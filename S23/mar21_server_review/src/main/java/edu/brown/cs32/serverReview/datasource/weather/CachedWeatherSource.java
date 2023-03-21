package edu.brown.cs32.serverReview.datasource.weather;

import com.google.common.cache.*;
import edu.brown.cs32.serverReview.datasource.DatasourceException;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * The original version of this state object used Bloch's Typesafe
 * Heterogeneous Containers (from Effective Java) to use a _single_
 * object to store results, indexed by ID. This was cool, but much
 * more complicated than necessary.
 *
 */
public class CachedWeatherSource implements WeatherDatasource {

    private final LoadingCache<Geolocation, WeatherData> weatherStore;

    public CachedWeatherSource(
            WeatherDatasource source, int minutesExpireAfterWrite, int maxSize) {

        // Instantiate a cache
        this.weatherStore = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(minutesExpireAfterWrite, TimeUnit.MINUTES)
                .recordStats()
                //.removalListener(MY_LISTENER)
                .build(
                        new CacheLoader<>() {
                            @NotNull
                            @Override
                            public WeatherData load(@NotNull Geolocation key) throws Exception {
                                WeatherData data = source.getCurrentWeather(key);
                                return data;
                            }
                        });
    }

    @Override
    public WeatherData getCurrentWeather(Geolocation loc) throws DatasourceException {
        try {
            return weatherStore.get(loc);
        } catch(ExecutionException e) {
            throw new DatasourceException("unable to get weather for " + loc, e);
        }
    }

    public CacheStats stats() {
        return weatherStore.stats();
    }
}
