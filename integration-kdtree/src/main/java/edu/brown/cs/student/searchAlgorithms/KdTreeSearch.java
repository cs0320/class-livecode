package edu.brown.cs.student.searchAlgorithms;

import edu.brown.cs.student.coordinates.Coordinate;
import edu.brown.cs.student.coordinates.KeyDistance;
import edu.brown.cs.student.node.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/** Class to perform KdTreeSearch over coordinates of specified type ID.
 @param <T> Any type for the ID of the Coordinate that is specified
 when constructing a KdTreeSearch.
 */
public class KdTreeSearch<T> {

  /** Get the first few IDs of KeyDistances within the passed threshold with the least distances,
   randomizing the order of any with tied distances, by using a Nearest Neighbors Algorithm.
   @param k An int threshold representing the maximum number of IDs that can be
   returned.
   @param targetPoint A Coordinate of type specified when constructing the KdTreeSearch
   representing the point around which all distances will be calculated.
   @param root A node representing the KdTree on which to apply the Nearest Neighbors
   Search algorithm.
   @param excludeTarget A Boolean representing whether the targetPoint should be included
   or excluded within the List of IDs returned
   @return A List of IDs of type specified when constructing the KdTreeSearch.
   */
  public List<Coordinate<T>> getNearestNeighborsResult(int k, Coordinate<T> targetPoint,
                                                       Node<Coordinate<T>> root,
                                                       Boolean excludeTarget) {
    if (excludeTarget) {
      // this is to account for the fact that the neighbors will exclude the target star
      k = k + 1;
    }
    Comparator<KeyDistance<Coordinate<T>>> byDistance
        = Comparator.comparing(KeyDistance::getDistance);

    Comparator<KeyDistance<Coordinate<T>>> byReverseDistance
        = Comparator.comparing(keyDist -> -1 * keyDist.getDistance());
    PriorityQueue<KeyDistance<Coordinate<T>>> kNearestNeighborsQueue
        = new PriorityQueue<>(byDistance);
    // maintaining a reverse ordered PriorityQueue to facilitate ease of peeking maximum
    // known neighbor distance
    PriorityQueue<KeyDistance<Coordinate<T>>> kNearestNeighborsReverse
        = new PriorityQueue<>(byReverseDistance);

    performKdTreeSearchNeighbors(root, k, targetPoint, kNearestNeighborsQueue,
        kNearestNeighborsReverse, 1);

    List<KeyDistance<Coordinate<T>>> kNearestNeighborsList = new ArrayList<>();

    // begin conversion process of PriorityQueue into ListNaiveSearch to handle randomness
    // of tied distance star IDs
    while (!kNearestNeighborsQueue.isEmpty()) {
      KeyDistance<Coordinate<T>> keyDist = kNearestNeighborsQueue.remove();

      // exclude the target star ID if applicable
      if (excludeTarget) {
        if (!(keyDist.getKey().getId().equals(targetPoint.getId()))) {
          kNearestNeighborsList.add(keyDist);
        }
      } else {
        kNearestNeighborsList.add(keyDist);
      }
    }

    ListNaiveSearch<Coordinate<T>> kNN = new ListNaiveSearch<>(kNearestNeighborsList);
    if (excludeTarget) {
      // discount for the previous offset since we still want k IDs at the end
      return kNN.getNaiveNearestNeighbors(k - 1);
    } else {
      return kNN.getNaiveNearestNeighbors(k);
    }
  }

  /** Get all IDs of KeyDistances within the passed threshold by using a Radius Search
   Algorithm.
   @param r A double threshold representing the maximum radius within which IDs of valid points
   may be returned.
   @param targetPoint A Coordinate of type specified when constructing the KdTreeSearch
   representing the point around which all distances will be calculated.
   @param root A node representing the KdTree on which to apply the Radius Search algorithm.
   @param excludeTarget A Boolean representing whether the targetPoint should be included
   or excluded within the List of IDs returned
   @return A List of IDs of type specified when constructing the KdTreeSearch.
   */
  public List<Coordinate<T>> getRadiusSearchResult(Double r, Coordinate<T> targetPoint,
                                                   Node<Coordinate<T>> root,
                                                   Boolean excludeTarget) {
    Comparator<KeyDistance<Coordinate<T>>> byDistance
        = Comparator.comparing(KeyDistance::getDistance);
    PriorityQueue<KeyDistance<Coordinate<T>>> kNearestNeighborsQueue
        = new PriorityQueue<>(byDistance);

    performKdTreeSearchRadius(root, r, targetPoint, kNearestNeighborsQueue, 1);

    List<Coordinate<T>> kNearestNeighborsIndices = new ArrayList<>();

    // convert the PriorityQueue into a List of Indices maintaining the ascending order
    // of distance
    while (!kNearestNeighborsQueue.isEmpty()) {
      KeyDistance<Coordinate<T>> keyDist = kNearestNeighborsQueue.remove();

      if (excludeTarget) {
        // exclude the target star ID if applicable
        if (!(keyDist.getKey().getId().equals(targetPoint.getId()))) {
          kNearestNeighborsIndices.add(keyDist.getKey());
        }
      } else {
        kNearestNeighborsIndices.add(keyDist.getKey());
      }

    }

    return kNearestNeighborsIndices;
  }

  /** Update the PriorityQueue of KeyDistance with valid Neighbors on the path to
   the target in the passed Node tree.
   @param k An int threshold representing the maximum number of IDs upto which Node values
   must be indiscriminately added to the PriorityQueue.
   @param targetPoint A Coordinate of type specified when constructing the KdTreeSearch
   representing the point around which all distances are calculated.
   @param root A Node representing the sub-KdTree on which to recur the Nearest Neighbors
   Search algorithm.
   @param dimension The current cutting dimension in the recursion.
   @param kNearestNeighbors The PriorityQueue to which valid KeyDistances should be added
   in ascending order of their distances.
   @param kNearestNeighborsReverse The PriorityQueue to which valid KeyDistances should be added
   in descending order of their distances to peek the maximum valid neighbor at that level
   of iteration.
   */
  void performKdTreeSearchNeighbors(Node<Coordinate<T>> root,
                           int k, Coordinate<T> targetPoint,
                           PriorityQueue<KeyDistance<Coordinate<T>>> kNearestNeighbors,
                           PriorityQueue<KeyDistance<Coordinate<T>>> kNearestNeighborsReverse,
                           int dimension) {
    if (!(root.getValue() == null || k == 0)) {
      double currentNodeDistanceSquared = 0;
      double currentNodeDistance;

      int index = 1;
      // calculate the distance, which works for coordinates in any dimension
      try {
        while (true) {
          currentNodeDistanceSquared
              += Math.pow(
              root.getValue().getCoordinateVal(index) - targetPoint.getCoordinateVal(index), 2);
          index++;
        }
      } catch (IndexOutOfBoundsException e) {
        currentNodeDistance = Math.sqrt(currentNodeDistanceSquared);
      }

      Coordinate<T> rootId = root.getValue();

      KeyDistance<Coordinate<T>> rootStarDist = new KeyDistance<>(rootId, currentNodeDistance);

      // If not all k neighbors have been filled yet, indiscriminately add to the PriorityQueue
      if (kNearestNeighbors.size() < k
          || currentNodeDistance <= kNearestNeighborsReverse.peek().getDistance()) {
        kNearestNeighbors.add(rootStarDist);
        kNearestNeighborsReverse.add(rootStarDist);
      }

      // get the next dimension
      int nextDimension;
      if (dimension + 1 > targetPoint.getCoordinates().size()) {
        nextDimension = 1;
      } else {
        nextDimension = dimension + 1;
      }

      // If not all k neighbors have been filled yet, recurse in both children
      if (kNearestNeighbors.size() < k) {
        performKdTreeSearchNeighbors(root.getLeft(), k, targetPoint,
            kNearestNeighbors, kNearestNeighborsReverse, nextDimension);
        performKdTreeSearchNeighbors(root.getRight(), k, targetPoint,
            kNearestNeighbors, kNearestNeighborsReverse, nextDimension);
      } else {
        double maxEuclideanDistance = kNearestNeighborsReverse.peek().getDistance();

        double axisDistance = Math.abs(targetPoint.getCoordinateVal(dimension)
            - root.getValue().getCoordinateVal(dimension));

        if (maxEuclideanDistance >= axisDistance) {
          // recur on both children
          performKdTreeSearchNeighbors(root.getLeft(), k, targetPoint, kNearestNeighbors,
              kNearestNeighborsReverse, nextDimension);
          performKdTreeSearchNeighbors(root.getRight(), k, targetPoint, kNearestNeighbors,
              kNearestNeighborsReverse, nextDimension);
        } else {
          if (root.getValue().getCoordinateVal(dimension)
              < targetPoint.getCoordinateVal(dimension)) {
            // If the current node's coordinate on the relevant axis is less than
            // target's coordinate on the relevant axis, recur on the right child.
            performKdTreeSearchNeighbors(root.getRight(), k, targetPoint, kNearestNeighbors,
                kNearestNeighborsReverse, nextDimension);
          } else if (root.getValue().getCoordinateVal(dimension)
              > targetPoint.getCoordinateVal(dimension)) {
            // Else if the current node's coordinate on the relevant axis is greater than
            // the target's coordinate on the relevant axis, recur on the left child.
            performKdTreeSearchNeighbors(root.getLeft(), k, targetPoint, kNearestNeighbors,
                kNearestNeighborsReverse, nextDimension);
          }
        }
      }
    }
  }

  /** Update the PriorityQueue of KeyDistance with valid Neighbors on the path to
   the target in the passed Node tree.
   @param r A double threshold representing the maximum radius within which IDs of
   KeyDistances within that radius can be
   must be indiscriminately added to the PriorityQueue.
   @param targetPoint A Coordinate of type specified when constructing the KdTreeSearch
   representing the point around which all distances are calculated.
   @param root A Node representing the sub-KdTree on which to recur the Nearest Neighbors
   Search algorithm.
   @param dimension The current cutting dimension in the recursion.
   @param kNearestNeighbors The PriorityQueue to which valid KeyDistances should be added
   in ascending order of their distances.
   */
  void performKdTreeSearchRadius(Node<Coordinate<T>> root,
                                        double r,
                                        Coordinate<T> targetPoint,
                                        PriorityQueue<KeyDistance<Coordinate<T>>> kNearestNeighbors,
                                        int dimension) {
    if (root.getValue() != null) {
      double currentNodeDistanceSquared = 0;
      double currentNodeDistance;

      // calculate the distance, which works for coordinates in any dimension
      int index = 1;
      try {
        while (true) {
          currentNodeDistanceSquared
              += Math.pow(
              root.getValue().getCoordinateVal(index) - targetPoint.getCoordinateVal(index), 2);
          index++;
        }
      } catch (IndexOutOfBoundsException e) {
        currentNodeDistance = Math.sqrt(currentNodeDistanceSquared);
      }

      // If the distance is within r, inclusive, the valid is applicable to add
      if (currentNodeDistance <= r) {
        kNearestNeighbors.add(
            new KeyDistance<>(root.getValue(), currentNodeDistance));
      }

      // get next dimension
      int nextDimension;
      if (dimension + 1 > targetPoint.getCoordinates().size()) {
        nextDimension = 1;
      } else {
        nextDimension = dimension + 1;
      }

      double axisDistance = Math.abs(targetPoint.getCoordinateVal(dimension)
          - root.getValue().getCoordinateVal(dimension));

      if (r >= axisDistance) {
        // recur on both children
        performKdTreeSearchRadius(root.getLeft(), r, targetPoint, kNearestNeighbors,
            nextDimension);
        performKdTreeSearchRadius(root.getRight(), r, targetPoint, kNearestNeighbors,
            nextDimension);
      } else {
        if (root.getValue().getCoordinateVal(dimension)
            < targetPoint.getCoordinateVal(dimension)) {
          // If the current node's coordinate on the relevant axis is less than
          // target's coordinate on the relevant axis, recur on the right child.
          performKdTreeSearchRadius(root.getRight(), r, targetPoint, kNearestNeighbors,
              nextDimension);
        } else if (root.getValue().getCoordinateVal(dimension)
            > targetPoint.getCoordinateVal(dimension)) {
          // Else if the current node's coordinate on the relevant axis is greater than
          // the target's coordinate on the relevant axis, recur on the left child.
          performKdTreeSearchRadius(root.getLeft(), r, targetPoint, kNearestNeighbors,
              nextDimension);
        }
      }
    }
  }
}
