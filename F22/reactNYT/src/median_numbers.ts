/*
  Additional example re: testing "too much" from Oct 25 2022
*/


/**
 * Returns a sorted version of the input array
 * @param arr the input array to sort
 */
 function sortHelper(arr: number[]): number[] {
    return arr.slice().sort()
}
/**
 * Finds the statistical median of the input set. If the input contains an even 
 * number of elements, this function follows standard convention and returns 
 * the average of the middle two elements, after sorting.
 * 
 * @param sample an array of numbers containing the members of the input set. 
 * This input array is not modified by running median on it.
 */
export function median(sample: number[]): number {
    const sorted: number[] = sortHelper(sample)
    const middle: number = Math.floor(sorted.length / 2)
    if(sorted.length % 2 === 1) 
        return sorted[middle]    
    return (sorted[middle] + sorted[middle-1]) / 2 
}