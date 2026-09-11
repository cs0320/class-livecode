/** Moving the robot with the provided policies */

import { isPassable } from "./grid.js";
import { MOVES } from "./types.js";
import type { Move, Policy, Position, World } from "./types.js";

export const DELTAS: Record<Move, Position> = {
  up: { x: 0, y: -1 },
  down: { x: 0, y: 1 },
  left: { x: -1, y: 0 },
  right: { x: 1, y: 0 },
};

export function destination(pos: Position, move: Move): Position {
  const delta = DELTAS[move];
  return { x: pos.x + delta.x, y: pos.y + delta.y };
}

export function legalMoves(world: World): Move[] {
  const moves: Move[] = [];
  for (const move of MOVES) {
    if (isPassable(world.grid, destination(world.robot, move))) moves.push(move);
  }
  return moves;
}

export function step(world: World, move: Move): World {
  const target = destination(world.robot, move);
  if (!isPassable(world.grid, target)) return world;
  return { grid: world.grid, robot: target };
}

export type RunOptions = {
  /** How many steps to allow, maximum */
  readonly maxSteps: number;
  /** Invoked after each step. Lets the caller say what to do 
   *  (e.g., by providing a printer function) */
  readonly onStep?: (world: World, move: Move, count: number) => void;
};

/** Entry point for a simulation. 
 *  @param start the starting grid and robot position
 *  @param policy the function saying what the robot should do at every state
 *  @param options configuration options like how to print
*/
export function run(
  start: World,
  policy: Policy,
  options: RunOptions,
): World {
  const { maxSteps, onStep } = options; 
  
  let world = start;
  for (let count = 1; count <= maxSteps; count++) {
    const move = policy(world);           // what to do next?
    if (move === undefined) return world; // nothing changed
    world = step(world, move);            // update the world
    onStep?.(world, move, count);         // let the caller print etc.
  }

  return world; // return the final world state
}
