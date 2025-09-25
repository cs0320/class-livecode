import {  weatherReportProvidence } from '../src/inClass'

/* SPOILERS! (Scroll down) */

























describe('Test weather-report function output', () => {
  let consoleLogSpy: jest.SpyInstance;

  // Have Jest "spy" on console.log as each test starts. This is implemented
  // as a pass-through function that is called on the way to the real console.log.
  beforeEach(() => {
    consoleLogSpy = jest.spyOn(console, 'log') //.mockImplementation(() => {});
  });

  // Remember to turn off Jest's spying after each test ends
  afterEach(() => {
    consoleLogSpy.mockRestore();
  });

  test('weather reported, although we cannot know exact temp', async () => {
    // This function is async, so it must be awaited in the test.
    await weatherReportProvidence()    
    
    expect(consoleLogSpy).toHaveBeenCalledWith(
      expect.stringContaining('out in Providence')
    );
  });
});
