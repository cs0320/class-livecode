
/**
 *  0320 Koan: "It's the same picture."
 * 
 *  Part 1: Reminder about generators in TypeScript
 *  Part 2: Building generators ourselves
 *  Part 3: Customizing our home-built generators
 *  Part 4: Building our own classes: OOP with only functions
 */

/*
  We've seen that TypeScript's "function*" keyword gives us a function that produces 
  a generator object. This is a built-in language feature.
*/

function* oneToTen() {
    let count = 1 
    while(count <= 10) {
        yield count  // "return" the value of count, but continue from here if called again
        count++
    }
    return null // the generator is finished, no more values will be produced
}

const generator1 = oneToTen()
console.log(generator1.next().value) // 1
console.log(generator1.next().value) // 2
const generator2 = oneToTen() // _an entirely new generator_
console.log(generator2.next().value) // 1
console.log(generator2.next().value) // 2
console.log(generator1.next().value) // 3

for(const n of generator1) { // 4, ..., 10
    console.log(n)
}

console.log(`-------------------------------------`)

/*
  Do we need TypeScript to do this for us, or is it something we could do ourselves? 
  Here's a version that we build ourselves. It's missing some features (like the yield 
  vs. return distinction), but it works. Notice that it relies on:
  
    (1) The 'count' variable is accessible for as long we're calling the returned function. 
    (2) Each time we call buildOneToTen(), it's a different call and so a different variable
        that's shared by all calls to the function it returns.
    
    In FP terminology, we say that the inner function "closes over" the non-local variable. 
    This is why you might hear anonymous functions referred to as "closures".
*/

function buildOneToTen() {
    let count = 1
    return function() {
        if(count <= 10) {
            return count++
        } 
        return null
    }
}

const gen1 = buildOneToTen()
const gen2 = buildOneToTen()

console.log(gen1())
console.log(gen1())
console.log(gen2())
console.log(gen2())

console.log(`-------------------------------------`)

/** That's a little restrictive, though. What if we wanted a generator that starts 
 *  and ends in a different place? We'd need to give some more arguments to the builder 
 *  function, and have it use them in constructing the generator.  */

function buildOneToTenCustom(startAt: number, endAt: number) {
    let count = startAt
    return function() {
        if(count <= endAt) {
            return count++
        } 
        return null
    }
}

const gen3 = buildOneToTenCustom(5,6)
const gen4 = buildOneToTenCustom(4, 10)

console.log(gen3())
console.log(gen3())
console.log(gen4())
console.log(gen4())
console.log(`-------------------------------------`)

/** Think about what you know of OO in Java. Objects have constructors, and often have 
 *  internal state. There's a strong parallel between creating objects in Java and what 
 *  we're doing here with functions. 
 * 
 *  TypeScript _does_ have a class system, but we're not using it for now to avoid 
 *  confusion when we get to front-end programming with React. Ordinary TypeScript 
 *  "objects" are just key-value stores like HashMaps: dictionaries like 
 *  {x: 100, y: 200} and so on.
 * 
 *  Could we re-invent OO-style objects if we only had functions and dictionaries 
 *  to work with? Well... If you took 0200, you might remember building a functional
 *  linked-list class. */

/** Empty lists. We could have used "undefined" instead of making our own type, but 
  * I wanted to hint at how branded types could be built internally in Zod. This symbol
  * will always be different from others, and always have its own unique type (which 
  * we use below).
  * 
  * For more information:  https://www.typescriptlang.org/docs/handbook/symbols.html */
const EmptyList: unique symbol = Symbol();

/** Methods provided by a list node. */
interface ListNode<T> {
    getValue: () => T
    getNext: () => ListNode<T> | typeof EmptyList
}

/** A "constructor" for immutable list nodes */
function newListNode<T>(value: T, next: ListNode<T> | typeof EmptyList) {
    let internalValue = value // Internal, "private" field
    let internalNext = next   // Internal, "private" field
    const getValue = () => internalValue // Defining a getter "method"
    const getNext = () => internalNext   // Defining a getter "method"
    return {getValue: getValue, getNext: getNext} // Dictionary of "methods" 
}

/** A "constructor" for the wrapper class */
function newLinkedList<T>() {
    // I needed to give TypeScript an explicit type declaration here:
    let start: (ListNode<T> | typeof EmptyList) = EmptyList

    const addFirst = (value: T) => {
        const newNode = newListNode<T>(value, start)
        start = newNode
    }

    const getHelper = (idx: number, place: (ListNode<T> | typeof EmptyList)): 
      (T | undefined) => {
        if(place == EmptyList) return undefined
        if(idx <= 0) return place.getValue()
        return getHelper(idx-1, place.getNext())
    }
    const get = (idx: number) => {
        return getHelper(idx, start)
    }

    return {addFirst: addFirst, get: get}
}

const ll = newLinkedList()
console.log(ll.get(0)) // we'll get undefined because there's nothing in the list yet
ll.addFirst(100)
ll.addFirst(200)
ll.addFirst(300)
console.log(ll.get(0)) // 300
console.log(ll.get(1)) // 200
console.log(ll.get(2)) // 100
console.log(ll.get(3)) // undefined

/** We can use functions alone to build the sort of programming constructs that
 *  classes provide! 
 * 
 *  Language runtimes do a lot of optimization work that we haven't, however. So
 *   in practice, we'd use TypeScript's class system if we really needed OOP.
 * 
 *  Every semester, I say that taking Programming Languages (CSCI 1730) will help you
 *  level up as an engineer. This is an example of why, although it might seem like a 
 *  silly example at first. Understanding different essential shapes of computation, 
 *  rather than only specific language features, is powerful. Understanding gives you
 *  the ability to improvise, explore, and debug in new settings. 
 */

