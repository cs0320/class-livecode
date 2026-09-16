
/** We'll run this module in Node's REPL. This requires a bit of 
 * annoyance in the package.json; you aren't expected to remember 
 * how this works. But it might be interesting to see the emitted JS.
*/


export function greaterThan_1(arg1: string, arg2: string): boolean {
    return parseInt(arg1) > parseInt(arg2)
}

interface ConversionFailure {
    error: 'parseInt' | 'parseFloat'
    arg1: string, 
    arg2: string
    example: (hello:number) => number
}

interface ConversionFailure2 {
    error: 'parseInt' | 'parseFloat'
    arg1: string, 
    arg2: string
    example: (hello:number) => number
}


export function greaterThan(arg1: string, arg2: string): boolean | ConversionFailure {
    const num1 = parseInt(arg1)  
    const num2 = parseInt(arg2)
    if(Number.isNaN(num1) || Number.isNaN(num2)) 
        return { error: 'parseInt', arg1: arg1,arg2: arg2, example: (x:number) => 2*x}
    return num1 > num2
}

function foo(x: ConversionFailure2 | boolean) {
    console.log('hi')
}

foo(greaterThan("one", "two"))