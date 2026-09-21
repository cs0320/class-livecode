import { describe, expect, it } from "vitest";
import { parse } from "../gridworld/grid.js";
import { moveToward } from "./policies.js";

describe("moveToward policy: examples", () => {
  it("moves up if reward is up 2 squares and no other factors", () => {
    // Note: the world string isn't indented to avoid splitting on newline/trimming
    const input = `
.$.
...
.R.`;
    const world = parse(input);
    expect(moveToward(world)).toBe("up");
  });
});
