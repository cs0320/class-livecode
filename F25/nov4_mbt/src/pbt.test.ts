import { nearest_neighbor } from './generated';
import { nearest_neighbor_reference } from './index';

interface Point { x: number, y: number }

describe('Property-Based Testing: KD-tree vs Reference Implementation', () => {
  
  test('kd-tree implementation matches reference implementation on random large datasets', () => {
    const numTrials = 1000; // Number of random test cases to run
    const datasetSize = 10; // Size of each random dataset
    
    for (let trial = 0; trial < numTrials; trial++) {
      // Generate random goal point
      const goal: Point = {
        x: Math.floor(Math.random() * 2000) - 1000, // Range: -1000 to 1000
        y: Math.floor(Math.random() * 2000) - 1000
      };
      
      // Generate random dataset
      const data: Point[] = [];
      for (let i = 0; i < datasetSize; i++) {
        data.push({
          x: Math.floor(Math.random() * 2000) - 1000,
          y: Math.floor(Math.random() * 2000) - 1000
        });
      }
      
      // Call both implementations
      const kdTreeResult = nearest_neighbor(goal, data);
      const referenceResult = nearest_neighbor_reference(goal, data);
      
      // Both should return the same point (or both undefined)
      if(kdTreeResult != referenceResult && kdTreeResult && referenceResult) { //
        console.log(`${JSON.stringify(data)}\n ${JSON.stringify(kdTreeResult)} \n ${JSON.stringify(referenceResult)}`)
        for(const pt of data) {
            const kdTreeDistance = Math.abs(kdTreeResult.x - goal.x) + Math.abs(kdTreeResult.y - goal.y);
            const referenceDistance = Math.abs(referenceResult.x - goal.x) + Math.abs(referenceResult.y - goal.y);
            const ptDistance = Math.abs(pt.x - goal.x) + Math.abs(pt.y - goal.y);
            console.log(`${JSON.stringify(pt)}: ${ptDistance} : ${kdTreeDistance} : ${referenceDistance}`)
        }
      }
      expect(kdTreeResult).toEqual(referenceResult);

      // Additional verification: if both found a point, verify distances match
      if (kdTreeResult && referenceResult) {
        const kdTreeDistance = Math.abs(kdTreeResult.x - goal.x) + Math.abs(kdTreeResult.y - goal.y);
        const referenceDistance = Math.abs(referenceResult.x - goal.x) + Math.abs(referenceResult.y - goal.y);
        
        expect(kdTreeDistance).toBe(referenceDistance);
      }
    }
  });
});
