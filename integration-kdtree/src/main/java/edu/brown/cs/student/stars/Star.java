package edu.brown.cs.student.stars;

import edu.brown.cs.student.coordinates.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Class that represents a Star i.e. each row in a stardata CSV file.
 */
public class Star implements Coordinate<Integer> {
  private final int id;
  private final String name;
  private final List<Double> coordinates;

  /** Create an instance of a 3D Star with the passed arguments.
   @param id An int representing the index of the Star.
   @param name A String representing the name of the Star.
   @param x A double representing the x-coordinate of the Star.
   @param y A double representing the y-coordinate of the Star.
   @param z A double representing the z-coordinate of the Star.
   */
  public Star(int id, String name, double x, double y, double z) {
    this.id = id;
    this.name = name;
    List<Double> coor = new ArrayList<>();
    coor.add(x);
    coor.add(y);
    coor.add(z);
    this.coordinates = coor;
  }

  /** Create an instance of an N-D Star with the passed arguments.
   @param id An int representing the index of the Star.
   @param name A String representing the name of the Star.
   @param coor A List of double representing all coordiantes of the Star.
   */
  public Star(int id, String name, List<Double> coor) {
    this.id = id;
    this.name = name;
    this.coordinates = coor;
  }
  /** Return the name of the Star instantiation.
   @return 1 String.
   */
  public String getName() {
    return name;
  }

  /** Return the x-coordinate of the Star instantiation.
   @return 1 double.
   */
  public double getX() {
    return coordinates.get(0);
  }

  /** Return the y-coordinate of the Star instantiation.
   @return 1 double.
   */
  public double getY() {
    return coordinates.get(1);
  }

  /** Return the z-coordinate of the Star instantiation.
   @return 1 double.
   */
  public double getZ() {
    return coordinates.get(2);
  }

  @Override
  public Double getCoordinateVal(int dim) {
    return coordinates.get(dim - 1);
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public List<Double> getCoordinates() {
    return coordinates;
  }

  @Override
  public String toString() {
    StringBuilder allCoords = new StringBuilder();

    for (Double c : coordinates) {
      allCoords.append(c.toString()).append(", ");
    }

    return "Coordinate{"
        + "id=" + id
        + ", coordinates=[" + allCoords + "]"
        + '}';
  }

  /** Check if this Star is equal to the passed object.
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
    Star star = (Star) o;
    return id == star.id && Objects.equals(name, star.name)
        && Objects.equals(coordinates, star.coordinates);
  }

  /** Get a hashcode for a Star.
   @return an int representing the hash index.
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, name, coordinates);
  }
}
