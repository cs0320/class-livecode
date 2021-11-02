package edu.brown.cs.student.coordinates;

import java.util.Objects;

/** Class to create a KeyDistance of specified type ID.
 @param <T> Any type for the ID of the KeyDistance that is specified
 when constructing a KeyDistance.
 */
public class KeyDistance<T> {
  private final T key;
  private final Double distance;

  /** Create a KeyDistance that pairs a unique id to any real number.
   @param key the id of the KeyDistance.
   @param distance the distance of the KeyDistance to some arbitrary target.
   */
  public KeyDistance(T key, Double distance) {
    this.key = key;
    this.distance = distance;
  }

  /** Get the distance from the KeyDistance.
   @return a double number, i.e., a real number.
   */
  public Double getDistance() {
    return distance;
  }

  /** Get the key from the KeyDistance.
   @return a value of the same type when constructing the KeyDistance.
   */
  public T getKey() {
    return key;
  }

  /** Represent the KeyDistance as a String.
   @return a String representation of a KeyDistance.
   */
  @Override
  public String toString() {
    return "KeyDistance{"
        + "id=" + key
        + ", distance=" + distance
        + '}';
  }

  /** Check if this KeyDistance is equal to the passed object.
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
    KeyDistance<?> that = (KeyDistance<?>) o;
    return Objects.equals(key, that.key) && Objects.equals(distance, that.distance);
  }

  /** Get a hashcode for a KeyDistance.
   @return an int representing the hash index.
   */
  @Override
  public int hashCode() {
    return Objects.hash(key, distance);
  }
}
