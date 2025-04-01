/*
    Both Java and TypeScript (like most languages, today) are 
    "call by value". This means that before a function or method 
    can be called, its arguments need to be evaluated. 

    There are a few ways this can manifest.
*/

function printTwice(value: number) {
    console.log(value)
    console.log(value)
}
function halfOf(value: number): number { 
    console.log(`halfOf called on ${value}`)
    return value / 2
}
printTwice(halfOf(100)) // 150
/* We only see "halfOf called on 50" _once_, because the value 
   of halfOf(100) must be computed before printTwice can be called. */
console.log('----------------------------------')

///////////////////////////////////////////////////////////

/* Maybe that seems obviously right. In this case I think it is, too. 
   Here's a situation where you might need to do some work, though. */

/**
* Call this function to increment a numeric value. 
* @param value The numeric variable to increment
*/
function incrementValue(value: number) {
    value++
}
let x = 0
incrementValue(x)
console.log(x)
/* What prints? */
