package edu.brown.cs.student.searchAlgorithms;

import edu.brown.cs.student.coordinates.KeyDistance;
import edu.brown.cs.student.coordinates.KeyDistanceList;
import edu.brown.cs.student.utils.Utils;

import java.util.ArrayList;
import java.util.List;

// Wrapper class - uses composition in place of inheritance
/** Class to perform NaiveSearch over coordinates of specified type ID.
 @param <T> Any type for the ID of the Coordinate that is specified
 when constructing a ListNaiveSearch.
 */
public class ListNaiveSearch<T> extends KeyDistanceList<T> {
  /** Construct a ListNaiveSearch using the passed values by storing in a List
   of KeyDistance.
   @param kNearestNeighbors A List of KeyDistance of the type specified by the KeyDistance ID.
   */
  public ListNaiveSearch(List<KeyDistance<T>> kNearestNeighbors) {
    super(new ArrayList<>(kNearestNeighbors));
  }

  /** Get the first few stars within the passed threshold with the least distances,
   randomizing the order of any with tied distances.
   @param k An int threshold representing the maximum number of stars that may be
   printed to console in the event of a successful command execution.
   @return A List of specified type of ID, of size threshold k.
   */
  public List<T> getNaiveNearestNeighbors(int k) {
    List<KeyDistance<T>> keysDist = super.getL();
    List<T> nearestStars = new ArrayList<>();

    List<KeyDistance<T>> commonDistStars = new ArrayList<>();
    double prevDist = 0;
    int index = 0;
    // filledPos represents how many filled positions out of k
    int filledPos = 0;
    try {
      while (filledPos < k) {
        // any common dist stars get added to commonDistStars
        if (keysDist.get(index).getDistance() == prevDist) {
          commonDistStars.add(keysDist.get(index));
        } else {
          // if the star does not have common dist, print out everything from
          // commonDistStars after shuffling, then add this star to commonDistStars
          if (commonDistStars.size() == 1) {
            nearestStars.add(commonDistStars.get(0).getKey());
            filledPos++;
          } else {
            filledPos = addCommonDistStars(k, commonDistStars, filledPos, nearestStars);
          }
          commonDistStars = new ArrayList<>();
          commonDistStars.add(keysDist.get(index));
          prevDist = keysDist.get(index).getDistance();
        }
        index++;
        if (index == keysDist.size() && commonDistStars.size() > 0) {
          filledPos = addCommonDistStars(k, commonDistStars, filledPos, nearestStars);
        }
      }
      return nearestStars;
    } catch (IndexOutOfBoundsException e) {
      // occurs when k > no.of stars
      return nearestStars;
    }
  }

  /** Add all star IDs with common distances to the passed array of stars to be
   consoled after calling a method that shuffled aforementioned stars.
   @param k An int threshold representing the maximum number of stars that may be
   printed to console in the event of a successful command execution.
   @param commonDistStars An ArrayList of StarDistance with the same distance,
   of which some IDs will be printed depending on the threshold and filled spots.
   @param filledPos An int representing the number of stars that have been
   added to the passed ArrayList for printing to console.
   @param nearestStarIndices An ArrayList of String containing all star IDs
   that are to be printed to console, in the exact order in which they were added.
   @return An int, the updated number of slots filled for stars within the
   threshold to be printed to console.
   */
  int addCommonDistStars(int k, List<KeyDistance<T>> commonDistStars, int filledPos,
                                List<T> nearestStarIndices) {
    List<KeyDistance<T>> shuffledDistStars = Utils.shuffleList(commonDistStars);
    int shuffleIndex = 0;
    while (shuffleIndex < shuffledDistStars.size() && filledPos < k) {
      nearestStarIndices.add(shuffledDistStars.get(shuffleIndex).getKey());
      filledPos++;

      shuffleIndex++;
    }
    return filledPos;
  }

  /** Get all possible stars close lying within or on the passed threshold.
   @param r An N threshold representing the maximum radial distance, inclusive,
   of stars that may be printed to console.
   @return A List of specified type of ID, of size threshold k.
   */
  public List<T> getNaiveRadiusSearchResult(double r) {
    List<KeyDistance<T>> keysDist = super.getL();
    List<T> nearestStars = new ArrayList<>();

    int index = 0;
    while (index < keysDist.size()) {
      if (keysDist.get(index).getDistance() <= r) {
        nearestStars.add(keysDist.get(index).getKey());
        index++;
      } else {
        break;
      }
    }
    return nearestStars;
  }
}
