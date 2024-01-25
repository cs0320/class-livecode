import * as main from './main'

// We're going to *mock* a pattern function to test functionality more narrowly
// Reasons to mock include:
//   - want to isolate specific behaviors without tangling (our goal, here)
//   - want to spy on metadata (e.g., has a function been called?)
//   - want to avoid the cost of invocation (e.g., function under test calls a web API)
//   - want to avoid non-deterministic behavior
//   -   ... etc.

// Template HTML for test running. Make sure the class names, etc. match
const startHTML =     
`<section class="old-rounds">
</section>
<section class="new-round">
  <div class="guess-round-current">
    <label for="num-1">Guess 1</label>
    <input type="number" id="num-1">
    <label for="num-2">Guess 2</label>
    <input type="number" id="num-2">
    <label for="num-3">Guess 3</label>
    <input type="number" id="num-3">
  </div>
  <div>
    <button id="try-button" type="button">Try it!</button>
  </div>
</section>`;

/////////////////////////////////////////////////////////////

// Setup! This runs /before every test function/
beforeEach(() => {
  // Before each test function, restore the history to empty
  main.clearHistory()

  // Set up a mock document containing the skeleton that index.html starts with
  document.body.innerHTML = startHTML
});

/////////////////////////////////////////////////////////////

// test how the result of pattern function impacts *state*

test('false pattern: adds incorrect-guess div', () => {
    const falseMock = (guess: string[]) => false
    
    // JS template strings, like Python fstrings. Used to help debug this.
    // console.log(`updateGuesses=${main.updateGuesses}`)
    // console.log(`pattern=${main.pattern}`)
  
    // Setup similar to the real module's window.onload()
    // Using typecasting here without too much guilt; an error will fail the test
    // const guessButton = document.getElementById("try-button") as HTMLButtonElement
    
    // Mock the pattern to always return *false*, so we don't need to populate
    // the text fields at all. Just click the button...
    // guessButton.addEventListener("click", () => main.updateHistoryAndRender(falseMock))            
    // guessButton.click()

    // Actually, that's not needed at all. Instead, check behavior of the update function itself:
    main.updateHistoryAndRender(falseMock)
    // We didn't even *NEED* the guessButton!

    // ...and we should see an incorrect-guess block
    const correctTries = document.getElementsByClassName("correct-try")
    const incorrectTries = document.getElementsByClassName("incorrect-try")
    expect(correctTries.length).toBe(0)
    expect(incorrectTries.length).toBe(1)

    // Demo functionality (not strictly needed here)
    // Removed for simplicity; see jest.fn(...) for fancy mocks
    // expect(falseMock).toBeCalled()
  })

  test('true pattern: adds incorrect-guess div', () => {
    const trueMock = jest.fn(guess => true)
    document.body.innerHTML = startHTML

    // Note: if we don't reset GUESSES, the prior test run will add a 2nd guess to the array
    //   resulting in 2, not 1, correct-guess blocks    
      
    main.updateHistoryAndRender(trueMock)    
      
    const correctTries = document.getElementsByClassName("correct-try")
    const incorrectTries = document.getElementsByClassName("incorrect-try")
    expect(correctTries.length).toBe(1)
    expect(incorrectTries.length).toBe(0)
  })

/////////////////////////////////////////////////////////////

// What's missing? 
//   We're not yet testing how a state change impacts the rendering of the DOM

