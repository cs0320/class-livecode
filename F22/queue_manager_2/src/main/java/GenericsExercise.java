import java.util.Collection;
import java.util.HashSet;

public class GenericsExercise {

    static Number sumAll(Collection<Number> numbers) {
        int sum = 0;
        for(Number num : numbers) {
            sum += num.intValue(); // "may involve rounding or truncation"
        }
        return sum;
    }
    public static void main(String[] args) {
        ////////////////////////////////////////////////////////////
        Collection<Number> someNums = new HashSet<>(); // YES
        Collection<Integer> someInts = new HashSet<>(); // YES
        Collection<Object> someObjects = new HashSet<>(); // NO

//        sumAll(someNums);     // ?
//        sumAll(someInts);     // ?  MYSTERY NUMBER 1 -- WHY????
//        sumAll(someObjects);  // ?

//        Collection<Number> someNums = new HashSet<>();
//        Number aNumber = sumAll(someNums);    // ? // YES
//        Integer anInteger = sumAll(someNums); // ? // NO
//        Object anObject = sumAll(someNums);   // ? // YES...?









        ////////////////////////////////////////////////////////////

        // Problematic:

        //someNums = someInts; // if Coll<Int> subt of Coll<Num>
        someNums.add(Math.PI); // adding a Number to a collection of Numbers
        for(int i : someInts) {
            System.out.println(Integer.numberOfLeadingZeros(i));
        }


        ////////////////////////////////////////////////////////////





        // Last example, very important:
        
        someNums.add(1);    // setup (works)
        someNums.add(1.5);  // setup (works)


        Collection< ? extends Number> someNums2 = new HashSet<>();  // ?
        //someNums2.add(1);       // ?
        //someNums2.add(1.5);     // ?
        //someNums2.add(null);    // ?
        //someNums2 = someNums;   // ?
    }

}
