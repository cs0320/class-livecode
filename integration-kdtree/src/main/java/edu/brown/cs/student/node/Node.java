package edu.brown.cs.student.node;

import java.util.Objects;

/** Class to create a Node of specified type value.
 @param <T> Any type for the value of the Node that is specified
 when constructing a Node.
 */
public class Node<T> {
  private final T value;
  private final Node<T> left, right;

  /** Create an instance of a Node of specified type with the passed arguments.
   @param value Represents the value of the Node of the type specified.
   @param left A Node of the same type as the value representing the left child of
   this Node instantiation.
   @param right A Node of the same type as the value representing the right child of
   this Node instantiation.
   */
  public Node(T value, Node<T> left, Node<T> right) {
    this.value = value;
    this.left = left;
    this.right = right;
  }

  /** Get the value of the Node.
   @return Returns the value of the Node of type specified when originally constructing the Node.
   */
  public T getValue() {
    return value;
  }

  /** Get the left Node.
   @return Returns the left Node child of this Node of the same type.
   */
  public Node<T> getLeft() {
    return left;
  }

  /** Get the right Node.
   @return Returns the right Node child of this Node of the same type.
   */
  public Node<T> getRight() {
    return right;
  }

  /** Represent the Node as a String.
   @return a String representation of a Node.
   */
  @Override
  public String toString() {
    if (this.getLeft() == null && this.getRight() == null) {
      return "Node{"
          + "value=" + value
          + ", left=NULL"
          + ", right=NULL"
          + '}';
    } else if (this.getLeft() == null) {
      return "Node{"
          + "value=" + value
          + ", left=NULL"
          + ", right=" + right.toString()
          + '}';
    } else if (this.getRight() == null) {
      return "Node{"
          + "value=" + value
          + ", left=" + left.toString()
          + ", right=NULL"
          + '}';
    } else {
      return "Node{"
          + "value=" + value
          + ", left=" + left.toString()
          + ", right=" + right.toString()
          + '}';
    }
  }

  /** Check if this Node is equal to the passed object.
   @param o Another object
   @return a Boolean ture/false if the objects are equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node<?> node = (Node<?>) o;
    return Objects.equals(value, node.value) && Objects.equals(left, node.left)
        && Objects.equals(right, node.right);
  }

  /** Get a hashcode for a Node.
   @return an int representing the hash index.
   */
  @Override
  public int hashCode() {
    return Objects.hash(value, left, right);
  }
}
