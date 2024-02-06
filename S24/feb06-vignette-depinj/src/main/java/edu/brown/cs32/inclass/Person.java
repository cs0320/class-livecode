package edu.brown.cs32.inclass;

public class Person {
    private final Environment env;
    private final String name;

    /**
     * When the person wakes up in the morning, they are in an environment.
     * Is it real? Is it fake? THEY DON'T KNOW AND IT DOESN'T MATTER!
     * @param env the environment the person to operate in
     */
    public Person(String name, Environment env) {
        this.name = name;
        this.env = env;
    }

    /**
     * Getter for name
     * @return this agent's name
     */
    public String name() {
        return this.name;
    }

    /**
     * React to the current environment
     */
    public void react() {
       env.speak(this, "I look at my watch and it's "+env.time());
       env.speak(this, "I look outside and the weather is "+env.weather());
    }
}
