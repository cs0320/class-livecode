/** A random walk through a small room. */

import { run } from "./gridworld/engine.js";
import { parse } from "./gridworld/grid.js";
import { printStep, printWorld } from "./gridworld/render.js";
import { randomWalk } from "./utility/policies.js";

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
run(start, randomWalk, { maxSteps: 3, onStep: printStep });
