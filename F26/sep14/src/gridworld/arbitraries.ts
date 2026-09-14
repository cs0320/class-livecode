/** fast-check generators over our gridworlds */

import fc from "fast-check";
import type { Grid, TerrainType, World } from "./types.js";

const TERRAIN_TYPES: readonly TerrainType[] = ["open", "blocked", "hazard", "reward"];

/**
 * Random rectangular gridworld with dimensions between 1 and 8 inclusive. 
 */
export const worldArb: fc.Arbitrary<World> = fc
  // STEP 1: Generate a width and a height. These define the shape of this world.
  .record({ width: fc.integer({ min: 1, max: 8 }), height: fc.integer({ min: 1, max: 8 }) })
  // STEP 2: We need to do something with that width and height now, so pass it on...
  .chain(({ width, height }) =>
    // ...to a function that returns a generator of grids (worlds without a robot)
    fc // <---- working with generators, not concrete grids
      .array(fc.constantFrom(...TERRAIN_TYPES), { minLength: width * height, maxLength: width * height })
      .map((cells): Grid => ({ width, height, cells })),
  )
  // STEP 3: Now pass that robot-less world into a function...
  .chain((grid) => {
    // ...that returns a generator of worlds with a robot in them, or 
    // undefined (failed generation) if there is nowhere to put a robot here.
    const passableIndices = grid.cells
      .map((terrain, index) => (terrain === "blocked" ? -1 : index))
      .filter((index) => index >= 0);
    if (passableIndices.length === 0) return fc.constant(undefined);
    return fc.constantFrom(...passableIndices).map(
      (index): World => ({
        grid,
        robot: { x: index % grid.width, y: Math.floor(index / grid.width) },
      }),
    );
  })
  // This is an assertion: we're _telling_ typescript that a defined result is a World. 
  // EXERCISE: could I rewrite this generator so that it never produced undefined results?
  // HINT: think about order of operations.
  .filter((world): world is World => world !== undefined);
