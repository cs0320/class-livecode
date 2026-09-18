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
    const response: Promise<Response> = fetch('http://localhost:3232')
    const response2: Response = await fetch('http://localhost:3232')
    const data = await response2.json()
    //console.log(data)
    const huh = schema.safeParse(data)
    if(huh.success) {
        const huh2 = huh.data
        const theGrid: Grid = huh.data.grid
    }
    console.log(huh)
    return data
}
// type system is fine with this, because any works for number! 
const what: Promise<number> = example()