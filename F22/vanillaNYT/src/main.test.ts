// CommonJS gets only one import, in this style:
//const pattern = require('./main')

// ECM get multiple imports, plus *one* "default" for compat. with CommonJS
// Here we'll just use named imports
import { pattern } from './main'

test('pattern false if all empty', () => {
    const input = ['','','']
    const output = pattern(input)    
    expect(output).toBe(false);
  });

test('pattern false if any empty', () => {
    expect(pattern(['1','',''])).toBe(false);
    expect(pattern(['','1',''])).toBe(false);
    expect(pattern(['','','1'])).toBe(false);
    expect(pattern(['1','2',''])).toBe(false);
    expect(pattern(['','1','2'])).toBe(false);
    expect(pattern(['1','','2'])).toBe(false);
  });

test('pattern false for decreasing', () => {
    expect(pattern(['1','2','1'])).toBe(false);
    expect(pattern(['1','0','1'])).toBe(false);
  });

test('pattern false for nonincreasing', () => {    
    expect(pattern(['1','1','2'])).toBe(false);
  });

test('pattern true for increasing', () => {
    expect(pattern(['1','2','3'])).toBe(true);    
  });

  test('negative inputs handled correctly', () => {
    expect(pattern(['-3','-2','-1'])).toBe(true);    
    expect(pattern(['-1','-2','-3'])).toBe(false);    
  });
