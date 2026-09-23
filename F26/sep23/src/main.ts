
/**
 * Demo
 * 
 * Please follow along! **NOTE**: if you want to exit the REPL, do any of
 *   ctrl-D
 *   ctrl-C twice
 *   .exit
 * 
 * 
 * Return a function that can be repeatedly called to get gradually-increasing
 * numbers. Specifically, each call yields 1 more than the last. 
 * 
 * In Java, we might write this as a class that extends Iterator<Number>,
 * but we don't need to use a class for this at all. Let's just use a function.
 * 
 * @param start First value to yield.
 */
export function newNumberGenerator(start: number): () => number {
    
}

/**
 * Your exercise: using only functions (not classes), build something 
 * that functions like a class. How about a basic linked list with only 
 * "addFirst", "getFirst", and "getRest"?
 * 
 * We'll pause between the two steps below.
 * 
 * Step 1: What's the interface for our "class"? The generic variable is exactly 
 * like what you've seen in Java: "T" is whatever type values in the list have.
 */

export interface LinkedList<T> {

}

/**
 * Step 2: How do we implement the "class"?
 */

export function newList<T>(): LinkedList<T> {
    return () => {} // placeholder to avoid type errors
}
