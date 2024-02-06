package edu.brown.cs32.inclass;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A "real" world for the agent to react to.
 * In practice, this would get the real weather from an API,
 * but for demo purposes we pick at random. The real world
 * always uses the real time.
 *
 */
public class RealWorld implements Environment {
    final static List<String> weatherOptions = List.of(
            "clear", "cloudy", "rainy", "snowy");
    @Override
    public String weather() {
        int index = ThreadLocalRandom.current()
                .nextInt(weatherOptions.size());
        return weatherOptions.get(index);
    }

    @Override
    public Date time() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    @Override
    public void speak(Person person, String words) {
        // In the real world, everyone can hear...
        System.out.println(person.name()+" said: "+words);
    }
}
