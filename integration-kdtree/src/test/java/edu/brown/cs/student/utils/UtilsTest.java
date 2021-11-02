import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

  @Test
  public void testShuffleList_SizeGreaterThan1() {
    List<Integer> l = new ArrayList<>();
    l.add(0);
    l.add(1);
    l.add(2);
    l.add(3);
    l.add(4);
    l.add(5);
    l.add(6);
    l.add(7);
    l.add(8);
    l.add(9);

    List<Integer> lCopy = new ArrayList<>();
    lCopy.add(0);
    lCopy.add(1);
    lCopy.add(2);
    lCopy.add(3);
    lCopy.add(4);
    lCopy.add(5);
    lCopy.add(6);
    lCopy.add(7);
    lCopy.add(8);
    lCopy.add(9);

    List<Integer> shuffled = Utils.shuffleList(lCopy);
    assertEquals(shuffled.size(), 10);
    assertTrue(shuffled.containsAll(l));
    assertTrue(l.containsAll(shuffled));
  }

  @Test
  public void testShuffleList_Size1() {
    List<Integer> l = new ArrayList<>();
    l.add(0);

    List<Integer> lCopy = new ArrayList<>();
    lCopy.add(0);

    List<Integer> shuffled = Utils.shuffleList(lCopy);
    assertEquals(shuffled.size(), 1);
    assertTrue(shuffled.containsAll(l));
    assertTrue(l.containsAll(shuffled));
  }

  @Test
  public void testShuffleList_Size0() {
    List<Integer> l = new ArrayList<>();

    List<Integer> lCopy = new ArrayList<>();

    List<Integer> shuffled = Utils.shuffleList(lCopy);
    assertEquals(shuffled.size(), 0);
    assertTrue(shuffled.containsAll(l));
    assertTrue(l.containsAll(shuffled));
  }

  @Test
  public void testSplitCollection() {
    List<Integer> l = new ArrayList<>();

    l.add(1);
    l.add(2);
    l.add(3);
    l.add(4);
    l.add(5);

    List<List<Integer>> result;

    result = Utils.splitList(l, 3);

    List<Integer> split1 = result.get(0);
    List<Integer> split2 = result.get(1);

    assertEquals(split1.size(), 3);
    assertEquals((int) split1.get(0), 1);
    assertEquals((int) split1.get(1), 2);
    assertEquals((int) split1.get(2), 3);

    assertEquals(split2.size(), 1);
    assertEquals((int) split2.get(0), 5);
  }
}
