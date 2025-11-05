interface Point { x: number, y: number }

/**
 * Find the nearest data point to a goal provided. Distance computed uses 
 * the "Manhattan" distance formula. If there are no data points, this function
 * returns undefined.
 * 
 * This implementation is rather slow, however. It always does a full scan of the 
 * entire dataset to find the closest point. Perhaps there's a better way.
 * 
 * @param goal a Point to find the nearest-neighbor to
 * @param data the set of Points to search
 * @returns the nearest neighbor
 */
export function nearest_neighbor_reference(goal: Point, data: Point[]): Point | undefined {
    let nearest: Point | undefined = undefined
    let nearest_dist: number = Infinity
    for(const pt of data) {
        // Use taxi distance
        const dist = Math.abs(pt.x - goal.x) + Math.abs(pt.y - goal.y)
        if(dist < nearest_dist) {
            nearest = pt
            nearest_dist = dist
        }
    }
    return nearest
}

console.log(nearest_neighbor_reference(
     // Target
     {x: 0, y: 0}, 
     // Data
    [{x: 1, y: 1},{x: 2, y: 2}, {x: 2, y: 1}]))