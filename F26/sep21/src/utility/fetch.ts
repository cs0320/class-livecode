/**
 * Example of fetching a grid world from a source on the internet. 
 * 
 * We could format this grid data in two ways:
 *    * A string. We already have a parser for this. But if we want 
 *      the source to send more data than the grid, we have to encode
 *      it into strings, too, which seems annoying.
 *    * A structured JSON object. This is a string that serializes 
 *      JS/TS objects. So in theory parsing is trivial, and we can 
 *      keep the data structures we already have. Let's do this. 
 */

import z from 'zod'
import { Grid } from '../gridworld/types.js'


//console.log(response)
//response.then(resp => console.log(resp.json()))

// response // promise to get me web response
//   .then(resp => resp.json()) // promise to get me real data
//   .then(body => console.log(body))

const schema = z.object({
        time: z.number(),
        grid: z.object(
            {width: z.number(),
            height: z.number(),
            cells: z.array(z.union([
                z.literal("open"), 
                z.literal("blocked"),
                z.literal("hazard"),
                z.literal("reward")])) 
            })
    })

async function example() {
    //const response: Promise<Response> = fetch('http://localhost:3232')
    const response2: Response = await fetch('http://localhost:3232')
    // At this point, data has type "any"
    const data = await response2.json()
    // Beware! Don't make data mutable and say data = schema.safeParse... 
    // Instead, create a new identifier:
    const validated = schema.safeParse(data)
    if(validated.success) {
        // Narrowing has happened: TypeScript infers that the data is there. (How? Click through...)
        const theGrid: Grid = data.grid
        return data
    }
}
// type system is fine with this, because any works for number! 
const what: Promise<number> = example()



/////////////////
// Exercise
/////////////////

/**
 * 
 * @returns 
 */
function mockCSV(): string[][] | undefined {
  return [["Tim Nelson", "20", "tim_nelson@brown.edu"], ["Nim Telson", "NOT A NUMBER", "NOT AN EMAIL"]]
}

async function exercise() {
    const data = mockCSV()
    // TASK: make a Zod schema for "array of 3-tuples of [string, string that can be turned into a number, email address]"
    // A tuple is a fixed-length array. Use z.tuple([...])
    // For "can be turned into", see docs at: https://zod.dev/
    // 
}