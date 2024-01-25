import {median} from './median_numbers'
/*
  Demo script for median example
*/

test('median on singleton', () => {
    expect(median([1])).toBe(1)
})
test('median on even-length', () => {
    expect(median([4,2,3,1])).toBe(2.5)
})
test('median on odd-length', () => {
    expect(median([1,10,100])).toBe(10)
})

