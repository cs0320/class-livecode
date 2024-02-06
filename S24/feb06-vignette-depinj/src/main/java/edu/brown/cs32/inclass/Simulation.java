package edu.brown.cs32.inclass;

import java.util.Calendar;
import java.util.Date;

/**
 * A "fake" world for the agent to react to.
 * It's always 10AM on January 24th. It's always clear.
 *
 */
public class Simulation implements Environment {

    @Override
    public String weather() {
        return "clear";
    }

    @Override
    public Date time() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 24);
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }

    private String recentWords = "";
    private Person recentSpeaker = null;


    @Override
    public void speak(Person person, String words) {
        // In the simulation, perhaps the speaking system isn't
        // implemented yet. But we still allow the same call, so
        // that the person can't tell the difference.

        // Or, perhaps, we record what they said, because we want to
        // test their reactions in a unit test...
        this.recentSpeaker = person;
        this.recentWords = words;
    }

    public String recentWords() { return this.recentWords; }
    public String recentSpeakerName() { return this.recentSpeaker.name(); }
}
