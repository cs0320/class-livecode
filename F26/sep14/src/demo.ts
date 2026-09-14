/** A random walk through a small room. */

import { run } from "./gridworld/engine.js";
import { parse } from "./gridworld/grid.js";
import { printStep, printWorld } from "./gridworld/render.js";
import { moveToward, randomWalk, sleeping } from "./utility/policies.js";
import type { Policy } from "./gridworld/types.js";

const SMALL_ROOM = `
########
#..#..$#
#.$#.#.#
#..H.#.#
#R...#.#
########
`;

const POLICIES: Record<string, Policy> = {
  randomwalk: randomWalk,
  sleeping: sleeping,
  toward: moveToward,
};

const name = process.argv[2] ?? "sleeping";
const policy = POLICIES[name];
if (policy === undefined) {
  console.error(`unknown policy ${JSON.stringify(name)}; choose one of: ${Object.keys(POLICIES).join(", ")}`);
  process.exit(1);
}

const start = parse(SMALL_ROOM);
printWorld(start, "start:");
run(start, policy, { maxSteps: 3, onStep: printStep });
