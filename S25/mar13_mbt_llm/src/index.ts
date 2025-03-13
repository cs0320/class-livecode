interface Point {
    x: number,
    y: number
}

/**
 * Find the nearest data point to a goal provided. Distance computed uses 
 * the "Manhattan" distance formula. If there are no datapoints, this function
 * returns undefined.
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

console.log(nearest_neighbor_reference({x: 0, y: 0}, [{x: 1, y: 1},{x: 2, y: 2}, {x: 2, y: 1}]))