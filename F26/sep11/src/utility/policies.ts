/**
 * Example policies. 
 */

import { legalMoves } from "../gridworld/engine.js";
import type { Policy } from "../gridworld/types.js";

/** Random walk. */
export const randomWalk: Policy = (world) => {
  const options = legalMoves(world);
  if (options.length === 0) return undefined; // we're stuck!
  return options[Math.floor(Math.random() * options.length)];
};

export const sleeping: Policy = (world) => {
  return undefined
};
