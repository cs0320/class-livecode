/** A random walk through a small room. */

import { run } from "./gridworld/engine.js";
import { parse } from "./gridworld/grid.js";
import { printStep, printWorld } from "./gridworld/render.js";
import { randomWalk, sleeping } from "./utility/policies.js";

const SMALL_ROOM = `
########
#..#..$#
#.$#.#.#
#..H.#.#
#R...#.#
########
`;

const start = parse(SMALL_ROOM);
printWorld(start, "start:");
//run(start, randomWalk, { maxSteps: 3, onStep: printStep });
run(start, sleeping, { maxSteps: 3, onStep: printStep });
//

// In Java, we might do this. But TS lets us bundle arguments with NAMES, which I love
// run(start, sleeping, 3, printStep);
