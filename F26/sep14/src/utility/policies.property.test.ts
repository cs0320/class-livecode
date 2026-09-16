import fc from "fast-check";
import { describe, expect, it } from "vitest";
import { worldArb } from "../gridworld/arbitraries.js";
import { DELTAS, step } from "../gridworld/engine.js";
import { distanceToNearest, moveToward, rewardPositions } from "./policies.js";
import type { Move, World } from "../gridworld/types.js";
import { parse } from "../gridworld/grid.js";

/** Making our own property test for a specific input. Here's the property. */
function movesTowardReward(world: World, output: Move | undefined): boolean {
  if (output === undefined) return false; // not moving isn't moving closer
  const move = DELTAS[output];
  const next = { x: world.robot.x + move.x, y: world.robot.y + move.y };
  const goals = rewardPositions(world.grid);
  return distanceToNearest(next, goals)
       < distanceToNearest(world.robot, goals);
}

/** Now we write a normal test that invokes the property function. 
 *  Notice that this only works on a SINGLE input, right now. 
 */
describe("moveToward policy: examples", () => {
  it("always move closer (for this particular input!)", () => {
    // Note: the world string isn't indented to avoid splitting on newline/trimming
    const input = `
.$.
...
.R.`;
    const world = parse(input);
    const move = moveToward(world);  // run the implementation
    expect(movesTowardReward(world, move)).toBe(true);  // check the property
  });
});



/** Using fast-check to random-test a property */
describe("moveToward policy: single step", () => {
  it("distance to reward must be monotonically non-increasing", () => {
    // fast-check assertion
    fc.assert(
      // this property should hold, on values produced by this generator:
      fc.property(worldArb, (world) => {
        // Invoke the implementation under test
        const move = moveToward(world);
        if (move === undefined) return; // nothing happened, nothing to check

        // Now validate the output
        const goals = rewardPositions(world.grid);
        const before = distanceToNearest(world.robot, goals);
        const after = distanceToNearest(step(world, move).robot, goals);
        expect(after).toBeLessThanOrEqual(before);
      }),
    );
  });
});

