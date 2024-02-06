import edu.brown.cs32.inclass.Person;
import edu.brown.cs32.inclass.Simulation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPerson {

    @Test
    public void testSpeaking() {
        // In a _test_, we use a fake version of the world:
        Simulation env = new Simulation();
        // The person isn't aware it's a Simulation (just an Environment)
        Person nim = new Person("Nim", env);
        nim.react();
        // But the _test_ is aware it's a Simulation:
        assertEquals(env.recentSpeakerName(), "Nim");
        assertTrue(env.recentWords().contains("and the weather is"));
        // This trick is also useful for getting started on your code
        // without waiting on someone else's to be completed.
    }
}
