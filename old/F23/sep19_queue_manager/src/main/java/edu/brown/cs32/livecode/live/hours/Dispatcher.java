package edu.brown.cs32.livecode.live.hours;

import java.util.Map;

/**
 * Every hours dispatcher must at least be able to
 * say how many minutes a TA has left. We'll provide this by
 * returning a map so the caller can look it up for any TA.
 */
public interface Dispatcher {
    Map<TA, Integer> getMinutesLeft();
}
