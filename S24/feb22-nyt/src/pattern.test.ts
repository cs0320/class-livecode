import { pattern } from './pattern'

test('pattern false if all empty', () => {
    const input = ['','','']
    const output = pattern(input)    
    expect(output).toBe(false);
  });

test('pattern true for 1,2,3', () => {
    const input = ['1','2','3']
    const output = pattern(input)    
    expect(output).toBe(true);
  });
