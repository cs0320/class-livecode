package edu.brown.cs.student.coordinates;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Reusable forwarding class
/** Class to create a KeyDistanceList of specified type ID.
 @param <E> Any type for the ID of each KeyDistance that is specified
 when constructing a KeyDistanceList.
 */
public class KeyDistanceList<E> implements List<KeyDistance<E>> {
  private final List<KeyDistance<E>> l;

  /** Get the list of KeyDistance implementing List.
   @return a list of KeyDistance.
   */
  public List<KeyDistance<E>> getL() {
    return l;
  }

  /** Construct a KeyDistanceList from a List of KeyDistance.
   * @param l a list of KeyDistance to be converted to KeyDistanceList.
   */
  public KeyDistanceList(List<KeyDistance<E>> l) {
    this.l = l;
  }

  /** Call List.clear() as implemented for List.
   */
  public void clear() {
    l.clear();
  }

  /** Call List.contains() as implemented for List.
   @param o Object to be passed in .contains().
   @return true or false if the object is contained.
   */
  public boolean contains(Object o) {
    return l.contains(o);
  }

  /** Call List.isEmpty() as implemented for List.
   @return true or false if the List is empty.
   */
  public boolean isEmpty() {
    return l.isEmpty();
  }

  /** Call List.size() as implemented for List.
   @return int representing the List size.
   */
  public int size() {
    return l.size();
  }

  /** Call List.iterator() as implemented for List.
   @return Iterator of KeyDistance of some id type.
   */
  public Iterator<KeyDistance<E>> iterator() {
    return l.iterator();
  }

  /** Call List.toArray() as implemented for List.
   @return KeyDistance List as array of Objects.
   */
  public Object[] toArray() {
    return l.toArray();
  }

  /** Call List.toArray() as implemented for List.
   @param <T> specified type to which List must be converted to Array.
   @param a reference array of specified type
   @return KeyDistance List as array of specified type.
   */
  public <T> T[] toArray(T[] a) {
    return l.toArray(a);
  }

  /** Call List.add() as implemented for List.
   @param e KeyDistance of some id type to be added to the List.
   @return true or false.
   */
  public boolean add(KeyDistance<E> e) {
    return l.add(e);
  }

  /** Call List.remove() as implemented for List.
   @param o Object to be removed from the List.
   @return true or false.
   */
  public boolean remove(Object o) {
    return l.remove(o);
  }

  /** Call List.containsAll() as implemented for List.
   @param c a Collection.
   @return true or false.
   */
  public boolean containsAll(Collection<?> c) {
    return l.containsAll(c);
  }

  /** Call List.addAll() as implemented for List.
   @param c Collection to be passed in .addAll().
   @return true or false as specified in the List implementation.
   */
  public boolean addAll(Collection<? extends KeyDistance<E>> c) {
    return l.addAll(c);
  }

  /** Call List.addAll() as implemented for List.
   @param index index at which to add the passed collection.
   @param c Collection to be passed in .addAll().
   @return true or false as specified in the List implementation.
   */
  public boolean addAll(int index, Collection<? extends KeyDistance<E>> c) {
    return l.addAll(index, c);
  }

  /** Call List.removeAll() as implemented for List.
   @param c Collection to be passed in .removeAll().
   @return true or false as specified in the List implementation.
   */
  public boolean removeAll(Collection<?> c) {
    return l.removeAll(c);
  }

  /** Call List.toArray() as implemented for List.
   @param c Collection to be passed to the method.
   @return true or false as specified in the List implementation.
   */
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  /** Call List.equals() as implemented for List.
   @param o Object to be passed in .equals().
   @return true or false as specified in the List implementation
   */
  @Override
  public boolean equals(Object o) {
    return l.equals(o);
  }

  /** Call List.hashCode() as implemented for List.
   @return hashcode integer reference.
   */
  @Override
  public int hashCode() {
    return l.hashCode();
  }

  /** Call List.get() as implemented for List.
   @param index int to be passed in .get().
   @return KeyDistance of specified id type at that index.
   */
  public KeyDistance<E> get(int index) {
    return l.get(index);
  }

  @Override
  public KeyDistance<E> set(int index, KeyDistance<E> element) {
    return null;
  }

  /** Call List.add() as implemented for List.
   @param index index at which element must be retrieved.
   @param element actual KeyDistance to add.
   */
  public void add(int index, KeyDistance<E> element) {
    l.add(index, element);
  }

  /** Call List.remove() as implemented for List.
   @param index index of element to remove from the List.
   @return KeyDistance of specified id type which has been removed.
   */
  public KeyDistance<E> remove(int index) {
    return l.remove(index);
  }

  /** Call List.indexOf() as implemented for List.
   @param o Object whose first occurrence index must be found.
   @return index of passed Object.
   */
  public int indexOf(Object o) {
    return l.indexOf(o);
  }

  /** Call List.lastIndexOf() as implemented for List.
   @param o Object whose last occurrence index must be found.
   @return index of passed Object.
   */
  public int lastIndexOf(Object o) {
    return l.lastIndexOf(o);
  }

  /** Call List.listIterator() as implemented for List.
   @return ListIterator of KeyDistance with specified type.
   */
  public ListIterator<KeyDistance<E>> listIterator() {
    return l.listIterator();
  }

  /** Call List.listIterator() as implemented for List.
   @param index reference index for iteration.
   @return ListIterator of KeyDistance with specified type.
   */
  public ListIterator<KeyDistance<E>> listIterator(int index) {
    return l.listIterator(index);
  }

  /** Call List.subList() as implemented for List.
   @param fromIndex start index of the sublist.
   @param toIndex end index of the sublist.
   @return List of KeyDistance with specified type representing the sublist.
   */
  public List<KeyDistance<E>> subList(int fromIndex, int toIndex) {
    return l.subList(fromIndex, toIndex);
  }

  /** Call List.toString() as implemented for List.
   @return String representation of the List.
   */
  @Override
  public String toString() {
    return l.toString();
  }
}
