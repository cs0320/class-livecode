import { nearest_neighbor } from './generated';

interface Point { x: number, y: number }

describe('nearest_neighbor - KD-tree implementation', () => {
  
  test('returns undefined when data array is empty', () => {
    const goal: Point = { x: 0, y: 0 };
    const data: Point[] = [];
    
    const result = nearest_neighbor(goal, data);
    
    expect(result).toBeUndefined();
  });

  test('returns the only point when data has one element', () => {
    const goal: Point = { x: 5, y: 5 };
    const data: Point[] = [{ x: 10, y: 10 }];
    
    const result = nearest_neighbor(goal, data);
    
    expect(result).toEqual({ x: 10, y: 10 });
  });

  test('finds nearest neighbor using Manhattan distance', () => {
    const goal: Point = { x: 0, y: 0 };
    const data: Point[] = [
      { x: 3, y: 4 },   // Manhattan distance: 7
      { x: 1, y: 1 },   // Manhattan distance: 2 (nearest)
      { x: -2, y: -3 }, // Manhattan distance: 5
      { x: 5, y: 0 },   // Manhattan distance: 5
    ];
    
    const result = nearest_neighbor(goal, data);
    
    expect(result).toEqual({ x: 1, y: 1 });
  });

  test('handles multiple points at different positions correctly', () => {
    const goal: Point = { x: 50, y: 50 };
    const data: Point[] = [
      { x: 0, y: 0 },     // Manhattan distance: 100
      { x: 100, y: 100 }, // Manhattan distance: 100
      { x: 48, y: 52 },   // Manhattan distance: 4 (nearest)
      { x: 60, y: 60 },   // Manhattan distance: 20
      { x: 45, y: 45 },   // Manhattan distance: 10
    ];
    
    const result = nearest_neighbor(goal, data);
    
    expect(result).toEqual({ x: 48, y: 52 });
  });

  test('correctly handles negative coordinates', () => {
    const goal: Point = { x: -10, y: -10 };
    const data: Point[] = [
      { x: -8, y: -8 },   // Manhattan distance: 4 (nearest)
      { x: 0, y: 0 },     // Manhattan distance: 20
      { x: -15, y: -15 }, // Manhattan distance: 10
      { x: -5, y: -20 },  // Manhattan distance: 15
    ];
    
    const result = nearest_neighbor(goal, data);
    
    expect(result).toEqual({ x: -8, y: -8 });
  });

  test('returns correct point when goal matches a data point exactly', () => {
    const goal: Point = { x: 7, y: 3 };
    const data: Point[] = [
      { x: 1, y: 1 },
      { x: 7, y: 3 },   // Exact match, Manhattan distance: 0
      { x: 10, y: 10 },
      { x: 5, y: 5 },
    ];
    
    const result = nearest_neighbor(goal, data);
    
    expect(result).toEqual({ x: 7, y: 3 });
  });

  test('handles large dataset efficiently with kd-tree', () => {
    const goal: Point = { x: 500, y: 500 };
    const data: Point[] = []; // Generate a large dataset
    for (let i = 0; i < 1000; i++) {
      data.push({ 
        x: Math.floor(Math.random() * 1000), 
        y: Math.floor(Math.random() * 1000) 
      })}
    // Add a known nearest neighbor
    data.push({ x: 501, y: 501 }); // Manhattan distance: 2    
    const result = nearest_neighbor(goal, data);
    // Calculate Manhattan distance to verify it's a reasonable answer
    expect(result).toBeDefined();
    if (result) {
      const distance = Math.abs(result.x - goal.x) + Math.abs(result.y - goal.y);
      expect(distance).toBeLessThanOrEqual(2);
    }
  });

  test('handles points aligned on same axis', () => {
    const goal: Point = { x: 5, y: 0 };
    const data: Point[] = [
      { x: 0, y: 0 },   // Manhattan distance: 5
      { x: 3, y: 0 },   // Manhattan distance: 2 (nearest)
      { x: 10, y: 0 },  // Manhattan distance: 5
      { x: 5, y: 10 },  // Manhattan distance: 10
    ];
    
    const result = nearest_neighbor(goal, data);
    
    expect(result).toEqual({ x: 3, y: 0 });
  });
});
