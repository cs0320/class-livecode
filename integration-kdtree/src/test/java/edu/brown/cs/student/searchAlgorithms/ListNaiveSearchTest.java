import KeyDistance;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class ListNaiveSearchTest {
  @Test
  public void testGetNaiveNearestNeighbor_NoDuplicateDistances_0K() {
    List<KeyDistance<Integer>> keysNIndices = new ArrayList<>();
    keysNIndices.add(new KeyDistance<>(100, 0.0));
    keysNIndices.add(new KeyDistance<>(20, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(1, 0.0111));
    keysNIndices.add(new KeyDistance<>(10, 1.0));
    keysNIndices.add(new KeyDistance<>(120, 23.30));
    keysNIndices.add(new KeyDistance<>(1340, 23.50));

    ListNaiveSearch<Integer> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Integer> nearestNeighbors = new ArrayList<>();

    assertEquals(naiveSearch.getNaiveNearestNeighbors(0), nearestNeighbors);
  }

  @Test
  public void testGetNaiveNearestNeighbor_NoDuplicateDistances_KLessThanSize() {
    List<KeyDistance<Integer>> keysNIndices = new ArrayList<>();
    keysNIndices.add(new KeyDistance<>(100, 0.0));
    keysNIndices.add(new KeyDistance<>(20, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(1, 0.0111));
    keysNIndices.add(new KeyDistance<>(10, 1.0));
    keysNIndices.add(new KeyDistance<>(120, 23.30));
    keysNIndices.add(new KeyDistance<>(1340, 23.50));

    ListNaiveSearch<Integer> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Integer> nearestNeighbors = new ArrayList<>();
    nearestNeighbors.add(100);
    nearestNeighbors.add(20);
    nearestNeighbors.add(1);

    assertEquals(naiveSearch.getNaiveNearestNeighbors(3), nearestNeighbors);
  }

  @Test
  public void testGetNaiveNearestNeighbor_NoDuplicateDistances_KGreaterThanSize() {
    List<KeyDistance<Integer>> keysNIndices = new ArrayList<>();
    keysNIndices.add(new KeyDistance<>(100, 0.0));
    keysNIndices.add(new KeyDistance<>(20, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(1, 0.0111));
    keysNIndices.add(new KeyDistance<>(10, 1.0));
    keysNIndices.add(new KeyDistance<>(120, 23.30));
    keysNIndices.add(new KeyDistance<>(1340, 23.50));

    ListNaiveSearch<Integer> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Integer> nearestNeighbors = new ArrayList<>();
    nearestNeighbors.add(100);
    nearestNeighbors.add(20);
    nearestNeighbors.add(1);
    nearestNeighbors.add(10);
    nearestNeighbors.add(120);
    nearestNeighbors.add(1340);

    assertEquals(naiveSearch.getNaiveNearestNeighbors(6), nearestNeighbors);
  }

  @Test
  public void testGetNaiveNearestNeighbor_DuplicateDistances_0K() {
    List<KeyDistance<Integer>> keysNIndices = new ArrayList<>();
    keysNIndices.add(new KeyDistance<>(100, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(20, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(1, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(10, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(120, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(1340, 23.50));

    ListNaiveSearch<Integer> naiveSearch = new ListNaiveSearch<>(keysNIndices);
    assertEquals(0, naiveSearch.getNaiveNearestNeighbors(0).size());
  }

  @Test
  public void testGetNaiveNearestNeighbor_DuplicateDistances_SomeDuplicatesShown() {
    List<KeyDistance<Integer>> keysNIndices = new ArrayList<>();
    keysNIndices.add(new KeyDistance<>(100, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(20, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(1, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(1340, 23.50));


    ListNaiveSearch<Integer> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Integer> indices = naiveSearch.getNaiveNearestNeighbors(2);

    // check against all possible randomly generated solutions
    List<Integer> someIndices1 = new ArrayList<>();
    someIndices1.add(100);
    someIndices1.add(20);

    List<Integer> someIndices2 = new ArrayList<>();
    someIndices2.add(20);
    someIndices2.add(1);

    List<Integer> someIndices3 = new ArrayList<>();
    someIndices3.add(1);
    someIndices3.add(100);

    assertTrue(indices.containsAll(someIndices1)
        || indices.containsAll(someIndices2)
        || indices.containsAll(someIndices3));
    assertTrue(someIndices1.containsAll(indices)
        || someIndices2.containsAll(indices)
        || someIndices3.containsAll(indices));
    assertEquals(indices.size(), 2);
  }

  @Test
  public void testGetNaiveNearestNeighbor_DuplicateDistances_AllDuplicatesShown() {
    List<KeyDistance<Integer>> keysNIndices = new ArrayList<>();
    keysNIndices.add(new KeyDistance<>(100, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(20, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(1, 0.0000000000000001));
    keysNIndices.add(new KeyDistance<>(1340, 23.50));


    ListNaiveSearch<Integer> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Integer> indices = naiveSearch.getNaiveNearestNeighbors(3);

    List<Integer> someIndices = new ArrayList<>();
    someIndices.add(1);
    someIndices.add(20);
    someIndices.add(100);

    assertTrue(indices.containsAll(someIndices));
    assertTrue(someIndices.containsAll(indices));
    assertEquals(indices.size(), 3);
  }

  @Test
  public void testAddCommonDistStars_AllIDsCanBeAdded() {
    List<KeyDistance<Integer>> keysNIndices = new ArrayList<>();
    keysNIndices.add(new KeyDistance<>(1, 11.5));
    keysNIndices.add(new KeyDistance<>(2, 11.5));
    keysNIndices.add(new KeyDistance<>(3, 11.5));

    ListNaiveSearch<Integer> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Integer> indices = new ArrayList<>();

    assertEquals(naiveSearch.addCommonDistStars(10, keysNIndices, 5, indices),
        8);

    List<Integer> allIndices = new ArrayList<>();
    allIndices.add(1);
    allIndices.add(2);
    allIndices.add(3);

    assertTrue(indices.containsAll(allIndices));
    assertTrue(allIndices.containsAll(indices));
  }

  @Test
  public void testAddCommonDistStars_SomeIDsCanBeAdded() {
    List<KeyDistance<Integer>> keysNIndices = new ArrayList<>();
    keysNIndices.add(new KeyDistance<>(1, 11.5));
    keysNIndices.add(new KeyDistance<>(2, 11.5));
    keysNIndices.add(new KeyDistance<>(3, 11.5));

    ListNaiveSearch<Integer> naiveSearch = new ListNaiveSearch<>(keysNIndices);


    List<Integer> indices = new ArrayList<>();
    assertEquals(naiveSearch.addCommonDistStars(10, keysNIndices, 8, indices),
        10);

    // check against all possible randomly generated solutions
    List<Integer> someIndices1 = new ArrayList<>();
    someIndices1.add(1);
    someIndices1.add(2);

    List<Integer> someIndices2 = new ArrayList<>();
    someIndices2.add(2);
    someIndices2.add(3);

    List<Integer> someIndices3 = new ArrayList<>();
    someIndices3.add(1);
    someIndices3.add(3);

    assertTrue(indices.containsAll(someIndices1)
        || indices.containsAll(someIndices2)
        || indices.containsAll(someIndices3));
    assertTrue(someIndices1.containsAll(indices)
        || someIndices2.containsAll(indices)
        || someIndices3.containsAll(indices));
    assertEquals(indices.size(), 2);
  }
}
