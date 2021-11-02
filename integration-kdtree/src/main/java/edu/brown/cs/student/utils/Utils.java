package edu.brown.cs.student.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Class that contains useful methods that are applicable to non-star projects or classes.
 */
public final class Utils {
  private Utils() {
  }

  /** Shuffle the passed List.
   @param <T> The type of the List passed.
   @param l Any List to be shuffled
   @return A List of the same type as the one passed with all elements in random
   positions.
   */
  public static <T> List<T> shuffleList(List<T> l) {
    List<T> shuffleList = new ArrayList<>();

    while (l.size() > 0) {
      Random rand = new Random();
      double thresh = rand.nextDouble() * l.size();
      // floor function
      int chosen = (int) thresh;
      shuffleList.add(l.get(chosen));
      l.remove(chosen);
    }
    return shuffleList;
  }

  /** Split the passed List at the desired index.
   @param <T> The type of the List passed.
   @param toSplit The List to be split.
   @param separationIndex the index at which to split the List.
   @return A List containing the List of elements before the index and
   List of elements after the index.
   */
  public static <T> List<List<T>> splitList(List<T> toSplit, int separationIndex) {
    List<T> preSplit = new ArrayList<>();
    List<T> postSplit = new ArrayList<>();

    int index = 0;
    for (T el : toSplit) {
      if (index < separationIndex) {
        preSplit.add(el);
      } else if (index > separationIndex) {
        postSplit.add(el);
      }
      index++;
    }
    List<List<T>> preAndPostLists = new ArrayList<>();
    preAndPostLists.add(preSplit);
    preAndPostLists.add(postSplit);
    return preAndPostLists;
  }

  /** Create a function that takes in 3 parameters and returns 1 output of the specified,
   corresponding types.
   @param <T> Type of the first parameter.
   @param <U> Type of the second parameter.
   @param <V> Type of the third parameter.
   @param <W> Return type.
   */
  @FunctionalInterface
  public interface Function3To1<T, U, V, W> {
    /** Converts the given three inputs to a single output.
     @param t The first parameter of type T.
     @param u The second parameter of type U.
     @param v The third parameter of type V.
     @return A result of type W.
     */
    W apply(T t, U u, V v);
  }

  /** Create a function that takes in 4 parameters and returns 1 output of the specified,
   corresponding types.
   @param <S> Type of the first parameter.
   @param <T> Type of the first parameter.
   @param <U> Type of the second parameter.
   @param <V> Type of the third parameter.
   @param <W> Return type.
   */
  @FunctionalInterface
  public interface Function4To1<S, T, U, V, W> {
    /** Converts the given four inputs to a single output.
     @param s The first parameter of type S.
     @param t The first parameter of type T.
     @param u The second parameter of type U.
     @param v The third parameter of type V.
     @return A result of type W.
     */
    W apply(S s, T t, U u, V v);
  }

  /** Create a function that takes in 5 parameters and returns 1 output of the specified,
   corresponding types.
   @param <R> Type of the first parameter.
   @param <S> Type of the second parameter.
   @param <T> Type of the third parameter.
   @param <U> Type of the fourth parameter.
   @param <V> Type of the fifth parameter.
   @param <W> Return type.
   */
  @FunctionalInterface
  public interface Function5To1<R, S, T, U, V, W> {
    /** Converts the given five inputs to a single output.
     @param r The first parameter of type R.
     @param s The first parameter of type S.
     @param t The first parameter of type T.
     @param u The second parameter of type U.
     @param v The third parameter of type V.
     @return A result of type W.
     */
    W apply(R r, S s, T t, U u, V v);
  }
}
