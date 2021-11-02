import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class KeyDistanceTest {
  @Test
  public void testGetters() {
    KeyDistance<Integer> keyDist = new KeyDistance<>(1, 10.0);

    assertEquals(10, keyDist.getDistance(), 0.0);
    assertEquals(1, (int) keyDist.getKey());
  }

  @Test
  public void testToString() {
    KeyDistance<Integer> keyDist = new KeyDistance<>(1, 10.0);

    assertEquals("KeyDistance{id=1, distance=10.0}", keyDist.toString());
  }

  @Test
  public void testEquals() {
    KeyDistance<Integer> keyDist1 = new KeyDistance<>(1, 10.0);
    KeyDistance<Integer> keyDist2 = new KeyDistance<>(1, 10.0);
    KeyDistance<Integer> keyDist3 = new KeyDistance<>(1, 10.01);

    assertEquals(keyDist1, keyDist2);
    assertEquals(keyDist1, keyDist1);
    assertNotEquals(keyDist1, keyDist3);
  }
}
