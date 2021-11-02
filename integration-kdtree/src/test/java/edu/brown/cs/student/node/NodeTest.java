import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NodeTest {
  @Test
  public void testGetters() {
    Node<String> left = new Node<>("left", null, null);
    Node<String> right = new Node<>("right", null, null);
    Node<String> n = new Node<>("root", left, right);

    assertEquals(n.getValue(), "root");
    assertEquals(n.getLeft(), left);
    assertEquals(n.getRight(), right);
  }

  @Test
  public void testToString_RightAndLeftNull() {
    Node<String> left = new Node<>("left", null, null);
    Node<String> right = new Node<>("right", null, null);
    Node<String> n = new Node<>("root", left, right);

    assertEquals(n.toString(), "Node{value=root, " +
        "left=Node{value=left, left=NULL, right=NULL}, " +
        "right=Node{value=right, left=NULL, right=NULL}}");
  }

  @Test
  public void testToString_RightNull() {
    Node<String> rightRight = new Node<>("right-right", null, null);
    Node<String> left = new Node<>("left", null, null);
    Node<String> right = new Node<>("right", null, rightRight);
    Node<String> n = new Node<>("root", left, right);

    assertEquals(n.toString(), "Node{value=root, " +
        "left=Node{value=left, left=NULL, right=NULL}, " +
        "right=Node{value=right, left=NULL, right=Node{value=right-right, left=NULL, right=NULL}}}");
  }

  @Test
  public void testToString_LeftNull() {
    Node<String> leftLeft = new Node<>("left-left", null, null);
    Node<String> left = new Node<>("left", leftLeft, null);
    Node<String> right = new Node<>("right", null, null);
    Node<String> n = new Node<>("root", left, right);

    assertEquals(n.toString(), "Node{value=root, " +
        "left=Node{value=left, left=Node{value=left-left, left=NULL, right=NULL}, right=NULL}, " +
        "right=Node{value=right, left=NULL, right=NULL}}");
  }

  @Test
  public void testEquals() {
    Node<String> left = new Node<>("left", null, null);
    Node<String> right = new Node<>("right", null, null);
    Node<String> n1 = new Node<>("root", left, right);
    Node<String> n2 = new Node<>("root", left, right);
    Node<String> n3 = new Node<>("toor", left, right);

    assertEquals(n1, n2);
    assertEquals(n1, n1);
    assertNotEquals(n1, n3);
  }
}
