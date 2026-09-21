/** Drawing a world as text. Render is inverse of parse. */

import { ROBOT_CHAR, TERRAIN_CHARS, samePosition, terrainAtOrFail } from "./grid.js";
import type { Move, World } from "./types.js";

export function render(world: World): string {
  const rows: string[] = [];
  for (let y = 0; y < world.grid.height; y++) {
    let row = "";
    for (let x = 0; x < world.grid.width; x++) {
      const here = { x, y };
      row += samePosition(here, world.robot)
        ? ROBOT_CHAR
        : TERRAIN_CHARS[terrainAtOrFail(world.grid, here)];
    }
    rows.push(row);
  }
  return rows.join("\n");
}

export function printWorld(world: World, label?: string): void {
  if (label !== undefined) console.log(label);
  console.log(render(world));
  console.log();
}

export function printStep(world: World, move: Move, count: number): void {
  console.log(`step ${count}: ${move}`);
  console.log(render(world));
}
