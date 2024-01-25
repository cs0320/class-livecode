
/**
 *  Private helper function, not intended to be exported
 */
function sortHelper(lst: number[]): number[] {
    return lst.slice().sort()
}
/**
 * Finds the statistical median of the input set. If the input contains an even 
 * number of elements, this function follows standard convention and returns 
 * the average of the middle two elements, after sorting.
 * 
 * This function returns `undefined` if given an empty list.
 * 
 * @param sample an array of numbers containing the members of the input set. 
 * This input array is not modified by running median on it.
 */
export function median(sample: number[]): number | undefined {
    if(sample.length < 1) return undefined
    const sorted: number[] = sortHelper(sample)
    const middle: number = Math.floor(sorted.length / 2)
    if(sorted.length % 2 === 1) 
        return sorted[middle]    
    return (sorted[middle] + sorted[middle-1]) / 2 
}