import {  weatherReportProvidence } from '../src/inClass'

describe('Test weather-report function output', () => {
  test('weather reported, although we cannot know exact temp', async () => {
    // This function is async, so it must be awaited in the test.
    await weatherReportProvidence()    
    
    // ???
  });
});
