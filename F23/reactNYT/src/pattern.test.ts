import { pattern } from './pattern'

test('pattern false if all empty', () => {
    const input = ['','','']
    const output = pattern(input)    
    expect(output).toBe(false);
  });
