/**
 * The vocabulary of grid worlds in our program
 */

/** Each cell is exactly one of these. */
export type TerrainType = "open" | "blocked" | "hazard" | "reward";

/** A location in the grid. (0,0) is the top-left. */
export type Position = {
  readonly x: number;
  readonly y: number;
};

/** No diagonal movement. Only these moves are _well-typed_ */
export type Move = "up" | "down" | "left" | "right";
/** Runtime accessible set of moves. Consider why separate from above. */
export const MOVES: readonly Move[] = ["up", "down", "left", "right"];

/**
 * The world-state, excluding the robot(s). 
 * The cell at (x, y) is `cells[y * width + x]`.
 */
export type Grid = {
  readonly width: number;
  readonly height: number;
  readonly cells: readonly TerrainType[];
};

/** Everything there is to know about the world at one moment. */
export type World = {
  readonly grid: Grid;
  readonly robot: Position;
};

/**
 * What should the robot do, at any given time?
 */
export type Policy = (world: World) => Move | undefined;
