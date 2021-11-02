import Node;
import Star;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class KdTreeTest {
  @Test
  public void testCreateKdTree() {
    // creating coordinates
    List<Double> data = new ArrayList<>();
    data.add(0.0);
    data.add(10.0);
    Coordinate<Integer> c1 = new Star(1, "s1", data);

    List<Double> data2 = new ArrayList<>();
    data2.add(1.0);
    data2.add(9.0);
    Coordinate<Integer> c2 = new Star(2, "s2", data2);

    List<Double> data3 = new ArrayList<>();
    data3.add(2.0);
    data3.add(-1.0);
    Coordinate<Integer> c3 = new Star(3, "s3", data3);

    List<Double> data4 = new ArrayList<>();
    data4.add(3.0);
    data4.add(7.0);
    Coordinate<Integer> c4 = new Star(4, "s4", data4);

    List<Double> data5 = new ArrayList<>();
    data5.add(4.0);
    data5.add(-5.0);
    Coordinate<Integer> c5 = new Star(5, "s5", data5);

    List<Double> data6 = new ArrayList<>();
    data6.add(5.0);
    data6.add(5.0);
    Coordinate<Integer> c6 = new Star(6, "s6", data6);

    List<Double> data7 = new ArrayList<>();
    data7.add(6.0);
    data7.add(4.0);
    Coordinate<Integer> c7 = new Star(7, "s7", data7);

    List<Double> data8 = new ArrayList<>();
    data8.add(7.0);
    data8.add(-3.0);
    Coordinate<Integer> c8 = new Star(8, "s8", data8);

    List<Double> data9 = new ArrayList<>();
    data9.add(8.0);
    data9.add(2.0);
    Coordinate<Integer> c9 = new Star(9, "s9", data9);

    List<Double> data10 = new ArrayList<>();
    data10.add(9.0);
    data10.add(-1.0);
    Coordinate<Integer> c10 = new Star(10, "s10", data10);

    List<Double> data11 = new ArrayList<>();
    data11.add(10.0);
    data11.add(0.0);
    Coordinate<Integer> c11 = new Star(11, "s11", data11);

    // creating list of coordinates
    List<Coordinate<Integer>> allCoords = new ArrayList<>();
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

    // loading tree
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    Node<Coordinate<Integer>> root = tree.getRoot();

    // checking tree
    assertEquals(root.getValue().getId(), 6, 0);

    assertEquals(root.getLeft().getValue().getId(), 4, 0);
    assertEquals(root.getRight().getValue().getId(), 11, 0);

    assertEquals(root.getLeft().getLeft().getValue().getId(), 5, 0);
    assertEquals(root.getLeft().getRight().getValue().getId(), 2, 0);

    assertEquals(root.getLeft().getLeft().getLeft().getValue().getId(), 3, 0);
    assertNull(root.getLeft().getLeft().getRight().getValue());

    assertEquals(root.getLeft().getRight().getLeft().getValue().getId(), 1, 0);
    assertNull(root.getLeft().getRight().getRight().getValue());

    assertEquals(root.getRight().getLeft().getValue().getId(), 10, 0);
    assertEquals(root.getRight().getRight().getValue().getId(), 9, 0);

    assertEquals(root.getRight().getLeft().getLeft().getValue().getId(), 8, 0);
    assertNull(root.getRight().getLeft().getRight().getValue());

    assertEquals(root.getRight().getRight().getLeft().getValue().getId(), 7, 0);
    assertNull(root.getRight().getRight().getRight().getValue());
  }

  @Test
  public void testToString() {
    // creating coordinates
    List<Double> data = new ArrayList<>();
    data.add(0.0);
    data.add(10.0);
    Coordinate<Integer> c1 = new Star(1, "s1", data);

    List<Double> data2 = new ArrayList<>();
    data2.add(1.0);
    data2.add(9.0);
    Coordinate<Integer> c2 = new Star(2, "s2", data2);

    List<Double> data3 = new ArrayList<>();
    data3.add(2.0);
    data3.add(-1.0);
    Coordinate<Integer> c3 = new Star(3, "s3", data3);

    List<Double> data4 = new ArrayList<>();
    data4.add(3.0);
    data4.add(7.0);
    Coordinate<Integer> c4 = new Star(4, "s4", data4);

    // creating list of coordinates
    List<Coordinate<Integer>> allCoords = new ArrayList<>();
    allCoords.add(c1);
    allCoords.add(c2);
    allCoords.add(c3);
    allCoords.add(c4);

    // loading tree
    KdTree<Integer> tree = new KdTree<>(2, allCoords);

    assertEquals(tree.toString(), "KdTree{dimensions=2, " +
        "tree=Node{" +
        "value=Coordinate{id=3, coordinates=[2.0, -1.0, ]}, " +
        "left=Node{value=Coordinate{id=1, coordinates=[0.0, 10.0, ]}, " +
        "left=Node{value=Coordinate{id=2, coordinates=[1.0, 9.0, ]}, " +
        "left=Node{value=null, left=NULL, right=NULL}, " +
        "right=Node{value=null, left=NULL, right=NULL}}, " +
        "right=Node{value=null, left=NULL, right=NULL}}, " +
        "right=Node{value=Coordinate{id=4, coordinates=[3.0, 7.0, ]}, " +
        "left=Node{value=null, left=NULL, right=NULL}, " +
        "right=Node{value=null, left=NULL, right=NULL}}}}");
  }

  @Test
  public void testEquals() {
    // creating coordinates
    List<Double> data = new ArrayList<>();
    data.add(0.0);
    data.add(10.0);
    Coordinate<Integer> c1 = new Star(1, "s1", data);

    List<Double> data2 = new ArrayList<>();
    data2.add(1.0);
    data2.add(9.0);
    Coordinate<Integer> c2 = new Star(2, "s2", data2);

    List<Double> data3 = new ArrayList<>();
    data3.add(2.0);
    data3.add(-1.0);
    Coordinate<Integer> c3 = new Star(3, "s3", data3);

    List<Double> data4 = new ArrayList<>();
    data4.add(3.0);
    data4.add(7.0);
    Coordinate<Integer> c4 = new Star(4, "s4", data4);

    // creating list of coordinates
    List<Coordinate<Integer>> allCoords = new ArrayList<>();
    allCoords.add(c1);
    allCoords.add(c2);
    allCoords.add(c3);
    allCoords.add(c4);

    // loading tree
    KdTree<Integer> tree1 = new KdTree<>(2, allCoords);
    KdTree<Integer> tree2 = new KdTree<>(2, allCoords);
    KdTree<Integer> tree3 = new KdTree<>(1, allCoords);

    assertEquals(tree1, tree2);
    assertEquals(tree1, tree1);
    assertNotEquals(tree1, tree3);
  }
}
