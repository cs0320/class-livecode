import * as main from './main'

// Jest mocks are CommonJS style. More specifically, the second argument is a 
// factory function for a module. There's poor documentation re: working with
// named imports in ESM, so hat tip to:
//    https://www.emgoto.com/mocking-with-jest/
// Also, for TypeScript, see:
//    https://jestjs.io/docs/mock-function-api#jestfnimplementation
// "Correct mock typings will be inferred, if implementation is passed to jest.fn() "

// Reasons to mock include:
//   - want to isolate specific behaviors without tangling (our goal, here)
//   - want to spy on metadata (e.g., has a function been called?)
//   - want to avoid the cost of invocation (e.g., function under test calls a web API)
//   - want to avoid non-deterministic behavior
//   -   ... etc.

// // This block gets lifted to the start of the module
// jest.mock('./main', () => {
//   return {
//     // Don't change anything other than what we explicitly give below
//     ...jest.requireActual('./main'), 
//     // Needed to allow import/export
//     __esModule: true,    
//     // *Globally* mocks `pattern` to be the false thunk:
//     //pattern: () => jest.fn(() => false)
//     pattern: () => jest.fn(() => false)
//   }
// })

beforeEach(() => {
  // This would just clear metadata
  //jest.clearAllMocks()
  // Instead:
  jest.resetAllMocks()
  main.clearGuesses()
});

// Problem: mocks and ESM seem to still not work well
//   That is, I can mock the impl, but can't get at the mock object itself
//   Manual dependency injection will work, and reinforce a valuable learning goal anyway.

// test('confirm that mocks are working', () => {        
//     // Mock is in place (normally `pattern` would return true for this array)
//     expect(main.pattern(['1', '2', '3'])).toBe(false);
//     // Mutate the mock so it now always returns true (DANGEROUS!)
//     mockedPattern.mockReturnValue(true)
//     expect(main.pattern(['1', '2', '3'])).toBe(true);
    
//     // The mock was called (not strictly necessary, but demo the ability to check)
//     expect(mockedPattern).toHaveBeenCalled() 

//     // Now we've left the mock's implementation in an altered state (returning true)
//     // Fortunately we can add a beforeEach...
//   });

// But this is a good time/place to use dependency injection for mocks

const startHTML =     
`<section class="old-rounds">
</section>
<section class="new-round">
    <div class="guess-round-current">
        <label for="guess-1">Guess 1</label>
        <input type="number" id="guess-1">
        <label for="guess-2">Guess 2</label>
        <input type="number" id="guess-2">
        <label for="guess-3">Guess 3</label>
        <input type="number" id="guess-3">
    </div>
    <div>
        <button id="guess-button" type="button">Guess!</button>
    </div>
</section>`;


test('false pattern: adds incorrect-guess div', () => {
    const falseMock = jest.fn(guess => false)

    // Set up a mock document containing the skeleton that index.html starts with
    document.body.innerHTML = startHTML
    
    // JS template strings, like Python fstrings. Used to help debug this.
    // console.log(`updateGuesses=${main.updateGuesses}`)
    // console.log(`pattern=${main.pattern}`)
  
    // Setup similar to the real module's window.onload()
    const guessButton = document.getElementById("guess-button") as HTMLButtonElement
    guessButton.addEventListener("click", () => main.updateGuesses(falseMock))    
    
    // We have mocked pattern() to always return *false*, so we don't need to populate
    // the text fields at all. Just click the button...
    guessButton.click()

    // ...and we should see an incorrect-guess block
    const correctGuesses = document.getElementsByClassName("correct-guess")
    const incorrectGuesses = document.getElementsByClassName("incorrect-guess")    
    expect(correctGuesses.length).toBe(0)
    expect(incorrectGuesses.length).toBe(1)

    // Demo functionality (not strictly needed here):
    expect(falseMock).toBeCalled()
  })

  test('true pattern: adds incorrect-guess div', () => {
    const trueMock = jest.fn(guess => true)
    document.body.innerHTML = startHTML

    // Note: if we don't reset GUESSES, the prior run will add a 2nd guess to the array
    //   resulting in 2, not 1, correct-guess blocks
    console.log(document.body.innerHTML)
      
    const guessButton = document.getElementById("guess-button") as HTMLButtonElement
    guessButton.addEventListener("click", () => main.updateGuesses(trueMock))
    guessButton.click()
  
    console.log(document.body.innerHTML)
    const correctGuesses = document.getElementsByClassName("correct-guess")
    const incorrectGuesses = document.getElementsByClassName("incorrect-guess")    
    expect(correctGuesses.length).toBe(1)
    expect(incorrectGuesses.length).toBe(0)
  })