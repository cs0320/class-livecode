import fc from "fast-check";
import { describe, expect, it } from "vitest";
import { worldArb } from "../gridworld/arbitraries.js";
import { step } from "../gridworld/engine.js";
import { distanceToNearest, moveToward, rewardPositions } from "./policies.js";

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
