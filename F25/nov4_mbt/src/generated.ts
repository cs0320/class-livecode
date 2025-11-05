/**
 * You are an expert on TypeScript and algorithms. As an expert,
 * you are always honest when you are unsure, and then ask clarifying
 * questions and provide resources so that you and your partner can work together.
 * 
 * Generate an implementation of a kd-tree backed nearest-neighbor search.
 * The generated search function should conform to the method signature in this file.
 * Points will always be 2-dimensional, and have x and y fields as defined in the Point interface.
 */

interface Point { x: number, y: number }

/**
 * Node in the kd-tree
 */
class KdNode {
  constructor(
    public point: Point,
    public left: KdNode | null = null,
    public right: KdNode | null = null
  ) {}
}

/**
 * Build a kd-tree from an array of points
 * @param points array of points to build tree from
 * @param depth current depth in the tree (determines splitting dimension)
 * @returns root of the kd-tree, or null if points is empty
 */
function buildKdTree(points: Point[], depth: number = 0): KdNode | null {
  if (points.length === 0) {
    return null;
  }

  // Alternate between x and y dimensions
  const dimension = depth % 2; // 0 for x, 1 for y
  const axis = dimension === 0 ? 'x' : 'y';

  // Sort points by the current dimension and find median
  const sorted = [...points].sort((a, b) => a[axis] - b[axis]);
  const medianIndex = Math.floor(sorted.length / 2);

  // Create node with median point
  const node = new KdNode(sorted[medianIndex]);

  // Recursively build left and right subtrees
  node.left = buildKdTree(sorted.slice(0, medianIndex), depth + 1);
  node.right = buildKdTree(sorted.slice(medianIndex + 1), depth + 1);

  return node;
}

/**
 * Calculate Manhattan distance between two points
 */
function manhattanDistance(a: Point, b: Point): number {
  return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
}

/**
 * Search for nearest neighbor in kd-tree
 * @param node current node in the tree
 * @param goal point to find nearest neighbor to
 * @param depth current depth (determines dimension)
 * @param best current best point found
 * @returns the nearest point found
 */
function searchKdTree(
  node: KdNode | null,
  goal: Point,
  depth: number,
  best: { point: Point | null, distance: number }
): void {
  if (node === null) {
    return;
  }

  // Calculate distance to current node
  const distance = manhattanDistance(goal, node.point);

  // Update best if this point is closer
  if (best.point === null || distance < best.distance) {
    best.point = node.point;
    best.distance = distance;
  }

  // Determine which dimension we're splitting on
  const dimension = depth % 2;
  const axis = dimension === 0 ? 'x' : 'y';
  const diff = goal[axis] - node.point[axis];

  // Determine which side to search first
  const nearSide = diff < 0 ? node.left : node.right;
  const farSide = diff < 0 ? node.right : node.left;

  // Search the near side first
  searchKdTree(nearSide, goal, depth + 1, best);

  // Check if we need to search the far side
  // In Manhattan distance, we need to check if the distance to the splitting plane
  // could possibly contain a closer point
  if (Math.abs(diff) < best.distance) {
    searchKdTree(farSide, goal, depth + 1, best);
  }
}

/**
 * Find the nearest data point to a goal provided. Distance computed uses 
 * the "Manhattan" distance formula. If there are no data points, this function
 * returns undefined.
 * @param goal a Point to find the nearest-neighbor to
 * @param data the set of Points to search
 * @returns the nearest neighbor, if there is one.
 */
export function nearest_neighbor(goal: Point, data: Point[]): Point | undefined {
  if (data.length === 0) {
    return undefined;
  }

  // Build the kd-tree
  const tree = buildKdTree(data);

  // Search for nearest neighbor
  const best = { point: null as Point | null, distance: Infinity };
  searchKdTree(tree, goal, 0, best);

  return best.point ?? undefined;
}