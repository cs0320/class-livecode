import {nearest_neighbor_reference} from './index'


interface Point {
  x: number,
  y: number
}

class KdNode {
  point: Point;
  left: KdNode | null;
  right: KdNode | null;
  
  constructor(point: Point) {
    this.point = point;
    this.left = null;
    this.right = null;
  }
}

class KdTree {
  root: KdNode | null;
  
  constructor() {
    this.root = null;
  }
  
  /**
   * Build a balanced kd-tree from an array of points
   * @param points Array of points to build the tree from
   */
  build(points: Point[]): void {
    this.root = this.buildRecursive(points, 0);
  }
  
  /**
   * Recursively build a balanced kd-tree
   * @param points Array of points to build from
   * @param depth Current depth in the tree
   * @returns Root node of the subtree
   */
  private buildRecursive(points: Point[], depth: number): KdNode | null {
    if (points.length === 0) return null;
    
    // Alternate between x and y axis based on depth
    const axis = depth % 2;
    
    // Sort points along the current axis
    points.sort((a, b) => (axis === 0 ? a.x - b.x : a.y - b.y));
    
    // Find the median point
    const medianIdx = Math.floor(points.length / 2);
    
    // Create node with median point
    const node = new KdNode(points[medianIdx]);
    
    // Build left and right subtrees
    node.left = this.buildRecursive(points.slice(0, medianIdx), depth + 1);
    node.right = this.buildRecursive(points.slice(medianIdx + 1), depth + 1);
    
    return node;
  }
  
  /**
   * Find the nearest neighbor to a goal point
   * @param goal Point to find the nearest neighbor for
   * @returns The nearest neighbor or undefined if the tree is empty
   */
  nearest(goal: Point): Point | undefined {
    if (!this.root) return undefined;
    
    let bestPoint: Point = this.root.point;
    let bestDistance = this.manhattanDistance(goal, bestPoint);
    
    this.nearestRecursive(this.root, goal, 0, bestPoint, bestDistance, 
      (point, distance) => {
        bestPoint = point;
        bestDistance = distance;
      }
    );
    
    return bestPoint;
  }
  
  /**
   * Recursively search for the nearest neighbor
   * @param node Current node
   * @param goal Goal point
   * @param depth Current depth in the tree
   * @param bestPoint Current best point
   * @param bestDistance Current best distance
   * @param updateBest Callback to update the best point and distance
   */
  private nearestRecursive(
    node: KdNode | null, 
    goal: Point, 
    depth: number, 
    bestPoint: Point, 
    bestDistance: number,
    updateBest: (point: Point, distance: number) => void
  ): void {
    if (!node) return;
    
    // Calculate distance to current node
    const distance = this.manhattanDistance(goal, node.point);
    
    // Update best if current is better
    if (distance < bestDistance) {
      updateBest(node.point, distance);
      bestDistance = distance;
    }
    
    // Determine which subtree to search first
    const axis = depth % 2;
    const value = axis === 0 ? goal.x : goal.y;
    const nodeValue = axis === 0 ? node.point.x : node.point.y;
    
    // Determine primary and secondary subtrees
    const primarySubtree = value < nodeValue ? node.left : node.right;
    const secondarySubtree = value < nodeValue ? node.right : node.left;
    
    // Search the primary subtree first
    this.nearestRecursive(primarySubtree, goal, depth + 1, bestPoint, bestDistance, updateBest);
    
    // Only search the secondary subtree if it might contain a closer point
    // For Manhattan distance, we need to check if the distance to the splitting plane
    // is less than the current best distance
    const axisDistance = Math.abs(value - nodeValue);
    if (axisDistance < bestDistance) {
      this.nearestRecursive(secondarySubtree, goal, depth + 1, bestPoint, bestDistance, updateBest);
    }
  }
  
  /**
   * Calculate Manhattan distance between two points
   * @param a First point
   * @param b Second point
   * @returns Manhattan distance
   */
  private manhattanDistance(a: Point, b: Point): number {
    return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
  }
}

/**
 * Find the nearest data point to a goal provided. Distance computed uses
 * the "Manhattan" distance formula. If there are no datapoints, this function
 * returns undefined.
 * @param goal a Point to find the nearest-neighbor to
 * @param data the set of Points to search
 * @returns the nearest neighbor
 */
function nearest_neighbor(goal: Point, data: Point[]): Point | undefined {
  if (data.length === 0) return undefined;
  
  // For a single point, just return it
  if (data.length === 1) return data[0];
  
  // Build KD-tree with the data points
  const kdTree = new KdTree();
  kdTree.build(data);
  
  // Find the nearest neighbor
  return kdTree.nearest(goal);
}

// Example usage:
// const points: Point[] = [
//   { x: 2, y: 3 },
//   { x: 5, y: 4 },
//   { x: 9, y: 6 },
//   { x: 4, y: 7 },
//   { x: 8, y: 1 },
//   { x: 7, y: 2 }
// ];
// 


//////////////////////////////////////////////////////////////////////////////


// const goal: Point = { x: 6, y: 5 };
// const nearest = nearest_neighbor(goal, points);
// console.log(nearest); // Should output the closest point to (6, 5)

// console.log(nearest_neighbor({x: 0, y: 0}, [{x: 1, y: 1},{x: 2, y: 2}, {x: 2, y: 1}]))

function rand_point(min: number, max: number): Point { 
    return {x: (Math.random() * (max - min + 1)) + min,
            y: (Math.random() * (max - min + 1)) + min}
}

// console.log(rand_point(-100, 100))

function rand_point_list(min: number, max: number, max_len: number): Point[] {
    const len = Math.random() * max_len
    return Array.from({length: len}, () => rand_point(min, max))
}

// console.log(rand_point_list(-100, 100, 10))

// In the interest of time (sorry...)
let cGood = 0
let cUndef = 0
let cInput = 0
let cNearest = 0


function run_trial(count: number) { 
    const goal = rand_point(0, 5)
    const data = rand_point_list(0, 5, 100)
    // What if we can't use a reference oracle?
    //const expected = nearest_neighbor_reference(goal, data)

    const actual = nearest_neighbor(goal, data)
  
    
    //if(expected != actual) {
    const outcome = isValid(goal, data, actual)
    if(outcome !== OUTCOME.Good) {
      if(outcome === OUTCOME.Bad_Undefined) cUndef++
      if(outcome === OUTCOME.Not_In_Input) cInput++
      if(outcome === OUTCOME.Not_Nearest) cNearest++
        //console.log(`Failed on trial ${count}:`)
        //console.log(`goal: ${JSON.stringify(goal)}`)
        //console.log(`data: ${JSON.stringify(data)}`)
        //console.log(`expected: ${JSON.stringify(expected)}`)
        //console.log(`actual: ${JSON.stringify(actual)}`)

    } else {
      cGood++
    }
    // :-( we could define a custom type and pass one of these 
    //return {good: cGood, input: cInput, undef: cUndef, nearest:cNearest}
}

enum OUTCOME {
  Good, 
  Bad_Undefined, 
  Not_In_Input,
  Not_Nearest
}

function isValid(goal: Point, data: Point[], output: Point | undefined): OUTCOME {
  if(output === undefined ) {
    // The output is undefined if-and-only-if data is empty
    if(data.length !== 0) return OUTCOME.Bad_Undefined
  } else {
    // Property: the output must be an element of the data
    if(!data.includes(output)) return OUTCOME.Not_In_Input
    // Property: distance from output is smallest across all data
    for(const pt of data) {
        // Use taxi distance
        const dist = Math.abs(pt.x - goal.x) + Math.abs(pt.y - goal.y)
        const output_dist = Math.abs(output.x - goal.x) + Math.abs(output.y - goal.y)
        if(dist < output_dist) 
            return OUTCOME.Not_Nearest
    }
  }
  return OUTCOME.Good
}

for(let count=0;count<100000;count++) {
    run_trial(count)
}
console.log({good: cGood, input: cInput, undef: cUndef, nearest:cNearest})