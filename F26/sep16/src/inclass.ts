
/** We'll run this module in Node's REPL. This requires a bit of 
 * annoyance in the package.json; you aren't expected to remember 
 * how this works. But it might be interesting to see the emitted JS.
*/


export function greaterThan(arg1: string, arg2: string): boolean {
    return parseInt(arg1) > parseInt(arg2)
}


