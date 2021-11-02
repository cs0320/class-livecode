import Coordinate;
import KdTree;
import KeyDistance;
import Node;
import Star;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class KdTreeSearchTest {

  List<Double> data = new ArrayList<>();
  List<Double> data2 = new ArrayList<>();
  List<Double> data3 = new ArrayList<>();
  List<Double> data4 = new ArrayList<>();
  List<Double> data5 = new ArrayList<>();
  List<Double> data6 = new ArrayList<>();
  List<Double> data7 = new ArrayList<>();
  List<Double> data8 = new ArrayList<>();
  List<Double> data9 = new ArrayList<>();
  List<Double> data10 = new ArrayList<>();
  List<Double> data11 = new ArrayList<>();

  Coordinate<Integer> c1;
  Coordinate<Integer> c2;
  Coordinate<Integer> c3;
  Coordinate<Integer> c4;
  Coordinate<Integer> c5;
  Coordinate<Integer> c6;
  Coordinate<Integer> c7;
  Coordinate<Integer> c8;
  Coordinate<Integer> c9;
  Coordinate<Integer> c10;
  Coordinate<Integer> c11;

  List<Coordinate<Integer>> allCoords = new ArrayList<>();

  @Before
  public void setUp() {
    data.add(0.0);
    data.add(10.0);
    c1 = new Star(1, "star1", data);

    data2.add(1.0);
    data2.add(9.0);
    c2 = new Star(2, "star2", data2);

    data3.add(2.0);
    data3.add(-1.0);
    c3 = new Star(3, "star3", data3);

    data4.add(3.0);
    data4.add(7.0);
    c4 = new Star(4, "star4", data4);

    data5.add(4.0);
    data5.add(-5.0);
    c5 = new Star(5, "star5", data5);

    data6.add(5.0);
    data6.add(5.0);
    c6 = new Star(6, "star6", data6);

    data7.add(6.0);
    data7.add(4.0);
    c7 = new Star(7, "star7", data7);

    data8.add(7.0);
    data8.add(-3.0);
    c8 = new Star(8, "star8", data8);

    data9.add(8.0);
    data9.add(2.0);
    c9 = new Star(9, "star9", data9);

    data10.add(9.0);
    data10.add(-1.0);
    c10 = new Star(10, "star10", data10);

    data11.add(10.0);
    data11.add(0.0);
    c11 = new Star(11, "star11", data11);

    allCoords.add(c1);
    allCoords.add(c2);
    allCoords.add(c3);
    allCoords.add(c4);
    allCoords.add(c5);
    allCoords.add(c6);
    allCoords.add(c7);
    allCoords.add(c8);
    allCoords.add(c9);
    allCoords.add(c10);
    allCoords.add(c11);
  }

  @Test
  public void testGetNearestNeighborsResult_StarName() {
    // load tree
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    int k = 5;
    Coordinate<Integer> targetPoint = c8;

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    // map over nearest stars to get nearest indices
    List<Integer> kNearestNeighborIndices
        = search.getNearestNeighborsResult(k, targetPoint, root, true).stream().map(Coordinate::getId).collect(Collectors.toList());

    List<Integer> expectedIndices = new ArrayList<>();
    expectedIndices.add(10);
    expectedIndices.add(5);
    expectedIndices.add(11);
    expectedIndices.add(9);
    expectedIndices.add(3);

    assertEquals(kNearestNeighborIndices, expectedIndices);
  }

  @Test
  public void testGetNearestNeighborsResult_NoStarName() {
    // load tree
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    int k = 5;
    Coordinate<Integer> targetPoint = c8;

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    // map over nearest stars to get nearest indices
    List<Integer> kNearestNeighborsIndices
        = search.getNearestNeighborsResult(k, targetPoint, root, false).stream().map(Coordinate::getId).collect(Collectors.toList());

    List<Integer> expectedIndices = new ArrayList<>();
    expectedIndices.add(8);
    expectedIndices.add(10);
    expectedIndices.add(5);
    expectedIndices.add(11);
    expectedIndices.add(9);

    assertEquals(kNearestNeighborsIndices, expectedIndices);
  }

  @Test
  public void testGetRadiusSearchResult_StarName() {
    // load tree
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    double r = 3.423;
    Coordinate<Integer> targetPoint = c8;

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    // map over nearest stars to get nearest indices
    List<Integer> kNearestNeighborsIndices
        = search.getRadiusSearchResult(r, targetPoint, root, true).stream().map(Coordinate::getId).collect(Collectors.toList());


    List<Integer> expectedIndices = new ArrayList<>();
    expectedIndices.add(10);

    assertEquals(kNearestNeighborsIndices, expectedIndices);
  }

  @Test
  public void testGetRadiusSearchResult_NoStarName() {
    // load tree
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    double r = 3.484;
    Coordinate<Integer> targetPoint = c8;

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    // map over nearest stars to get nearest indices
    List<Integer> kNearestNeighborsIndices
        = search.getRadiusSearchResult(r, targetPoint, root, false).stream().map(Coordinate::getId).collect(Collectors.toList());


    List<Integer> expectedIndices = new ArrayList<>();
    expectedIndices.add(8);
    expectedIndices.add(10);

    assertEquals(kNearestNeighborsIndices, expectedIndices);
  }

  @Test
  public void testPerformKdTreeSearchNeighbors_kGreaterThanSize() {
    // load tree
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    int k = 200;
    Coordinate<Integer> targetPoint = c8;

    // create comparators and priority queues
    Comparator<KeyDistance<Coordinate<Integer>>> byDistance
        = Comparator.comparing(KeyDistance::getDistance);
    Comparator<KeyDistance<Coordinate<Integer>>> byReverseDistance
        = Comparator.comparing(keyDist -> -1 * keyDist.getDistance());

    PriorityQueue<KeyDistance<Coordinate<Integer>>> kNearestNeighborsQueue
        = new PriorityQueue<>(byDistance);
    PriorityQueue<KeyDistance<Coordinate<Integer>>> kNearestNeighborsReverse
        = new PriorityQueue<>(byReverseDistance);

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    search.performKdTreeSearchNeighbors(root, k, targetPoint,
        kNearestNeighborsQueue, kNearestNeighborsReverse, 1);

    // convert resultant priority queue to list for comparison against known
    // correct list of indices
    List<Integer> kNearestNeighborsIndices = new ArrayList<>();
    while (!kNearestNeighborsQueue.isEmpty()) {
      KeyDistance<Coordinate<Integer>> keyDist = kNearestNeighborsQueue.remove();
      kNearestNeighborsIndices.add(keyDist.getKey().getId());
    }

    List<Integer> expectedIndices = new ArrayList<>();
    expectedIndices.add(8);
    expectedIndices.add(10);
    expectedIndices.add(5);
    expectedIndices.add(11);
    expectedIndices.add(9);
    expectedIndices.add(3);
    expectedIndices.add(7);
    expectedIndices.add(6);
    expectedIndices.add(4);
    expectedIndices.add(2);
    expectedIndices.add(1);

    assertEquals(kNearestNeighborsIndices, expectedIndices);
  }

  @Test
  public void testPerformKdTreeSearchNeighbors_kLessThanSize() {
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    int k = 3;
    Coordinate<Integer> targetPoint = c8;

    // create comparators and priority queues
    Comparator<KeyDistance<Coordinate<Integer>>> byDistance
        = Comparator.comparing(KeyDistance::getDistance);
    Comparator<KeyDistance<Coordinate<Integer>>> byReverseDistance
        = Comparator.comparing(keyDist -> -1 * keyDist.getDistance());

    PriorityQueue<KeyDistance<Coordinate<Integer>>> kNearestNeighborsQueue
        = new PriorityQueue<>(byDistance);
    PriorityQueue<KeyDistance<Coordinate<Integer>>> kNearestNeighborsReverse
        = new PriorityQueue<>(byReverseDistance);

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    search.performKdTreeSearchNeighbors(root, k, targetPoint,
        kNearestNeighborsQueue, kNearestNeighborsReverse, 1);

    // convert resultant priority queue to list for comparison against known
    // correct list of indices
    List<Integer> kNearestNeighborsIndices = new ArrayList<>();
    while (!kNearestNeighborsQueue.isEmpty()) {
      KeyDistance<Coordinate<Integer>> keyDist = kNearestNeighborsQueue.remove();
      kNearestNeighborsIndices.add(keyDist.getKey().getId());
    }

    // avoiding one of the subtrees
    List<Integer> expectedIndices = new ArrayList<>();
    expectedIndices.add(8);
    expectedIndices.add(10);
    expectedIndices.add(5);
    expectedIndices.add(11);
    expectedIndices.add(9);
    expectedIndices.add(3);
    expectedIndices.add(7);
    expectedIndices.add(6);
    expectedIndices.add(4);

    assertEquals(kNearestNeighborsIndices, expectedIndices);
  }

  @Test
  public void testPerformKdTreeSearchNeighbors_0K() {
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    int k = 0;
    Coordinate<Integer> targetPoint = c8;

    // create comparators and priority queues
    Comparator<KeyDistance<Coordinate<Integer>>> byDistance
        = Comparator.comparing(KeyDistance::getDistance);
    Comparator<KeyDistance<Coordinate<Integer>>> byReverseDistance
        = Comparator.comparing(keyDist -> -1 * keyDist.getDistance());

    PriorityQueue<KeyDistance<Coordinate<Integer>>> kNearestNeighborsQueue
        = new PriorityQueue<>(byDistance);
    PriorityQueue<KeyDistance<Coordinate<Integer>>> kNearestNeighborsReverse
        = new PriorityQueue<>(byReverseDistance);

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    search.performKdTreeSearchNeighbors(root, k, targetPoint,
        kNearestNeighborsQueue, kNearestNeighborsReverse, 1);

    // convert resultant priority queue to list for comparison against known
    // correct list of indices
    List<Integer> kNearestNeighborsIndices = new ArrayList<>();
    while (!kNearestNeighborsQueue.isEmpty()) {
      KeyDistance<Coordinate<Integer>> keyDist = kNearestNeighborsQueue.remove();
      kNearestNeighborsIndices.add(keyDist.getKey().getId());
    }

    List<Integer> expectedIndices = new ArrayList<>();

    assertEquals(kNearestNeighborsIndices, expectedIndices);
  }

  @Test
  public void testPerformKdTreeSearchRadius_allStarsWithinR() {
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    double r = 32.39393939393939485945495849584958495;
    Coordinate<Integer> targetPoint = c8;

    // create comparators and priority queues
    Comparator<KeyDistance<Coordinate<Integer>>> byDistance
        = Comparator.comparing(KeyDistance::getDistance);

    PriorityQueue<KeyDistance<Coordinate<Integer>>> kNearestNeighborsQueue
        = new PriorityQueue<>(byDistance);

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    search.performKdTreeSearchRadius(root, r, targetPoint,
        kNearestNeighborsQueue, 1);

    // convert resultant priority queue to list for comparison against known
    // correct list of indices
    List<Integer> kNearestNeighborsIndices = new ArrayList<>();
    while (!kNearestNeighborsQueue.isEmpty()) {
      KeyDistance<Coordinate<Integer>> keyDist = kNearestNeighborsQueue.remove();
      kNearestNeighborsIndices.add(keyDist.getKey().getId());
    }

    List<Integer> expectedIndices = new ArrayList<>();
    expectedIndices.add(8);
    expectedIndices.add(10);
    expectedIndices.add(5);
    expectedIndices.add(11);
    expectedIndices.add(9);
    expectedIndices.add(3);
    expectedIndices.add(7);
    expectedIndices.add(6);
    expectedIndices.add(4);
    expectedIndices.add(2);
    expectedIndices.add(1);

    assertEquals(kNearestNeighborsIndices, expectedIndices);
  }

  @Test
  public void testPerformKdTreeSearchRadius_someStarsWithinR() {
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    double r = 4;
    Coordinate<Integer> targetPoint = c8;

    // create comparators and priority queues
    Comparator<KeyDistance<Coordinate<Integer>>> byDistance
        = Comparator.comparing(KeyDistance::getDistance);

    PriorityQueue<KeyDistance<Coordinate<Integer>>> kNearestNeighborsQueue
        = new PriorityQueue<>(byDistance);

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    search.performKdTreeSearchRadius(root, r, targetPoint,
        kNearestNeighborsQueue, 1);

    // convert resultant priority queue to list for comparison against known
    // correct list of indices
    List<Integer> kNearestNeighborsIndices = new ArrayList<>();
    while (!kNearestNeighborsQueue.isEmpty()) {
      KeyDistance<Coordinate<Integer>> keyDist = kNearestNeighborsQueue.remove();
      kNearestNeighborsIndices.add(keyDist.getKey().getId());
    }

    List<Integer> expectedIndices = new ArrayList<>();
    expectedIndices.add(8);
    expectedIndices.add(10);
    expectedIndices.add(5);

    assertEquals(kNearestNeighborsIndices, expectedIndices);
  }

  @Test
  public void testPerformKdTreeSearchRadius_0R() {
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    double r = 0;
    Coordinate<Integer> targetPoint = c8;

    // create comparators and priority queues
    Comparator<KeyDistance<Coordinate<Integer>>> byDistance
        = Comparator.comparing(KeyDistance::getDistance);

    PriorityQueue<KeyDistance<Coordinate<Integer>>> kNearestNeighborsQueue
        = new PriorityQueue<>(byDistance);

    KdTreeSearch<Integer> search = new KdTreeSearch<>();

    search.performKdTreeSearchRadius(root, r, targetPoint,
        kNearestNeighborsQueue, 1);

    // convert resultant priority queue to list for comparison against known
    // correct list of indices
    List<Integer> kNearestNeighborsIndices = new ArrayList<>();
    while (!kNearestNeighborsQueue.isEmpty()) {
      KeyDistance<Coordinate<Integer>> keyDist = kNearestNeighborsQueue.remove();
      kNearestNeighborsIndices.add(keyDist.getKey().getId());
    }

    List<Integer> expectedIndices = new ArrayList<>();
    expectedIndices.add(8);

    assertEquals(kNearestNeighborsIndices, expectedIndices);
  }
}
