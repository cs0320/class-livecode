import fc from "fast-check";
import { describe, expect, it } from "vitest";
import { worldArb } from "../gridworld/arbitraries.js";
import { step } from "../gridworld/engine.js";
import { distanceToNearest, moveToward, rewardPositions } from "./policies.js";

describe("moveToward policy: single step", () => {
  it("distance to reward must be monotonically non-increasing", () => {
    fc.assert(
      fc.property(worldArb, (world) => {
        const move = moveToward(world);
        if (move === undefined) return; // nothing happened, nothing to check

        const goals = rewardPositions(world.grid);
        const before = distanceToNearest(world.robot, goals);
        const after = distanceToNearest(step(world, move).robot, goals);
        expect(after).toBeLessThanOrEqual(before);
      }),
    );
  });
});
