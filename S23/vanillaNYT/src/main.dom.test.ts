// Import all available from the program under test
// (If you change it, remember to `tsc`)
import * as main from './main'

// Lets us use DTL's query library
import {screen} from '@testing-library/dom'
// Lets us send user events (like typing and clicking)
import userEvent from '@testing-library/user-event';

// Template HTML for test running. 
// Notice that we don't need to include everything in the page. 
// Also, notice that we've preserved the metadata...
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

let tryButton: HTMLElement
// Don't neglect to give the type for _every_ identifier.
let input1: HTMLElement, input2: HTMLElement, input3: HTMLElement

// Setup! This runs /before every test function/
beforeEach(() => {
  // (1) Restore the program's history to empty
  main.clearHistory()

  // (2) Set up a mock document containing the skeleton that 
  // index.html starts with. This is refreshed for every test.
  document.body.innerHTML = startHTML

  // (3) Find the elements that should be present at the beginning
  // Using "getBy..." will throw an error if this element doesn't exist.
  tryButton = screen.getByText("Try it!")
  input1 = screen.getByLabelText("Guess 1")
  input2 = screen.getByLabelText("Guess 2")
  input3 = screen.getByLabelText("Guess 3")
});

/////////////////////////////////////////////////////////////
// Some example tests
/////////////////////////////////////////////////////////////

test('false pattern: adds incorrect-guess div (ignore actual entry)', () => {
    // Create a pattern function that always returns false
    const falseMock = (guess: string[]) => false
    // tell `main` to use that mock function when rendering
    tryButton.addEventListener("click", () => main.updateHistoryAndRender(falseMock));
  
    // Alternatively, we could check behavior of the update function itself directly:
    // main.updateHistoryAndRender(falseMock)    

    // We _could_ also simulate typing in the input fields via userEvent.type(...)
    // But this test is meant to be independent of the exact guess.

    userEvent.click(tryButton)
    // Now, did we get an incorrect-try block?
    
    // Could do this, but is the class name something a user (or screenreader) sees?
    // const incorrectTries = document.getElementsByClassName("incorrect-try")    
    // We should prefer this instead:
    const incorrectTries = screen.getAllByText("Guess 1 was incorrect") 
    expect(incorrectTries.length).toBe(1)
  })

  test('entering data updates internal state', () => {
    const falseMock = (guess: string[]) => false

    userEvent.type(input1, "1")
    userEvent.type(input2, "2")
    userEvent.type(input3, "3")
    main.updateHistoryAndRender(falseMock)
    expect(main.getHistory()).toContain(["1","2","3"])
  })

/////////////////////////////////////////////////////////////

// Yes, you can combine the ideas in these two tests. If you want, you can
// do everything in a test via the UI---type values, click, look at the HTML result.

// Exercise: What's missing? 

