/** Helpers for building grid states */

import type { Grid, Position, TerrainType, World } from "./types.js";

/** Characters for each type of cell content. */
export const TERRAIN_CHARS: Record<TerrainType, string> = {
  open: ".",
  blocked: "#",
  hazard: "H",
  reward: "$",
};

export const ROBOT_CHAR = "R";

/** Inverse map from string encoding to cell. Since our strings include
 *  a special robot character, we need to map that character to open.
 */
const CHAR_TO_TERRAIN: Record<string, TerrainType> = {
  ".": "open",
  "#": "blocked",
  "H": "hazard",
  "$": "reward",
  // This looks weird, but it's just to say "whatever ROBOT_CHAR is..."
  // since ROBOT_CHAR bare would use that as the field name.
  [ROBOT_CHAR]: "open",
};

/**
 * Turn an ASCII map into a world. Blank lines are ignored.
 */
export function parse(map: string): World {
  // One line per row. Filter out 0-length rows.
  const rows = map.split("\n").filter((row) => row.trim().length > 0);
  // We'll use exceptions sometimes, even if we don't use classes.
  // We could also return an error object, or undefined. 
  if (rows.length === 0) throw new Error("map is empty");

  // Assume same-width rows. This will be checked later.
  const width = rows[0]?.length ?? 0;
  const cells: TerrainType[] = [];
  let robot: Position | undefined = undefined;

  for (let y = 0; y < rows.length; y++) {
    const row = rows[y] ?? "";
    if (row.length !== width) {
      throw new Error(`row ${y} has length ${row.length}, expected ${width}`);
    }
    for (let x = 0; x < width; x++) {
      const char = row[x] ?? "";
      const terrain = CHAR_TO_TERRAIN[char];
      if (terrain === undefined) {
        throw new Error(`unknown map character ${JSON.stringify(char)} at (${x}, ${y})`);
      }
      if (char === ROBOT_CHAR) {
        if (robot !== undefined) throw new Error("map has more than one robot");
        robot = { x, y };
      }
      cells.push(terrain);
    }
  }

  if (robot === undefined) throw new Error(`map has no robot (no '${ROBOT_CHAR}')`);
  return { grid: { width, height: rows.length, cells }, robot };
}

export function inBounds(grid: Grid, pos: Position): boolean {
  return pos.x >= 0 && pos.x < grid.width && pos.y >= 0 && pos.y < grid.height;
}

/** The terrain at `pos`, or `undefined` if `pos` is off the grid. */
export function terrainAt(grid: Grid, pos: Position): TerrainType | undefined {
  if (!inBounds(grid, pos)) return undefined;
  return grid.cells[pos.y * grid.width + pos.x];
}

/** Like `terrainAt`, but for positions we already know are on the grid. */
export function terrainAtOrFail(grid: Grid, pos: Position): TerrainType {
  const terrain = terrainAt(grid, pos);
  if (terrain === undefined) {
    throw new Error(`position (${pos.x}, ${pos.y}) is outside the grid`);
  }
  return terrain;
}

/** The robot may stand anywhere on the grid that isn't blocked. */
export function isPassable(grid: Grid, pos: Position): boolean {
  const terrain = terrainAt(grid, pos);
  return terrain !== undefined && terrain !== "blocked";
}

export function samePosition(a: Position, b: Position): boolean {
  return a.x === b.x && a.y === b.y;
}
