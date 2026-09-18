/**
 * Mock server for grids. You aren't expected to understand this yet; 
 * I made it so we could play with fetching and validating data.
 */

import express, { type Request, type Response } from "express";
import type { Grid } from "../src/gridworld/types.js";

const PORT: number = 3232;
const HOST: string = "127.0.0.1"; // localhost only

const GRID: Grid = {
  width: 5,
  height: 5,
  cells: [
    "open", "open", "blocked", "open", "open",
    "open", "hazard", "blocked", "open", "open",
    "open", "open", "open", "open", "reward",
    "blocked", "open", "hazard", "open", "open",
    "open", "open", "open", "open", "open",
  ],
};

const RESPONSE_DATA = {
  grid: GRID,
  time: Date.now() // let's add some data
}

const app = express();

app.use((req: Request, res: Response) => {
  res.json(RESPONSE_DATA);
});

app.listen(PORT, HOST, () => {
  console.log(`Grid server listening on http://${HOST}:${PORT}`);
});
