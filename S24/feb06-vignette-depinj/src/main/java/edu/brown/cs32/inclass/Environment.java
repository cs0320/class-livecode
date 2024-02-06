package edu.brown.cs32.inclass;

import java.util.Date;

/**
 * It might be real, or it might be fake. It doesn't matter.
 *
 * In practice, this might be a complex object, but it might
 * also be a basic implementation to allow easier testing, quicker
 * development, etc.
 */
public interface Environment {
    /**
     * Where is the agent?
     *
     * @return a string describing the weather of the
     * environment the agent is currently in.
     */
    String weather();

    /**
     * What is the time?
     *
     * @return a Date object containing the current date and
     * time within the environment.
     */
    Date time();

    /**
     * Speak some words in this environment.
     * @param person the person speaking
     * @param words the words to say
     */
    void speak(Person person, String words);

}
