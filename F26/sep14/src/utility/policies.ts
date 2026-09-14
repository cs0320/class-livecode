/**
 * Example policies.
 */

import { destination, legalMoves } from "../gridworld/engine.js";
import { terrainAt } from "../gridworld/grid.js";
import type { Grid, Policy, Position } from "../gridworld/types.js";

/** Random walk. */
export const randomWalk: Policy = (world) => {
  const options = legalMoves(world);
  if (options.length === 0) return undefined; // we're stuck!
  return options[Math.floor(Math.random() * options.length)];
};

/** Do nothing. */
export const sleeping: Policy = (world) => {
  return undefined
};

/** Manhattan distance (up, down, left, right only) */
export function distance(a: Position, b: Position): number {
  return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
}

/** Helper to locate rewards, since (right now) the world has no nicer reward list. */
export function rewardPositions(grid: Grid): Position[] {
  const found: Position[] = [];
  for (let y = 0; y < grid.height; y++) {
    for (let x = 0; x < grid.width; x++) {
      if (terrainAt(grid, { x, y }) === "reward") found.push({ x, y });
    }
  }
  return found;
}

/** How close is the closest cell in this set? */
export function distanceToNearest(pos: Position, goals: Position[]): number {
  let nearest = Infinity; // no goals at all means "infinitely far"
  for (const goal of goals) {
    nearest = Math.min(nearest, distance(pos, goal));
  }
  return nearest;
}

/**
 * A (slightly) smarter policy: try to head "toward" the nearest reward.
 */
export const moveToward: Policy = (world) => {
  const goals = rewardPositions(world.grid);
  if (goals.length === 0) return undefined // nothing to head toward

  const options = legalMoves(world);
  let best = options[0]
  if(best === undefined) return undefined

  for (const move of options) {
    if (distanceToNearest(destination(world.robot, move), goals) <
        distanceToNearest(destination(world.robot, best), goals)) {
      best = move;
    }
  }
  return best;
};
