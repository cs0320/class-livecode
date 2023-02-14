/*
  Vanilla TypeScript (non React) clone of the basic NYT puzzle functionality.
  There's no Firebase backend in this version, so we can focus on TypeScript.

  Remember to run `npx tsc -w` to compile this into JavaScript, otherwise index.html will
  load but won't actually do anything when the button is clicked.
*/

///////////////////////////////////////////////////////////////////////////////
// First, make this code generic over different pattern-testing functions
// This is useful both for extensibility *and* testing.
///////////////////////////////////////////////////////////////////////////////

// TypeScript does something unusual here: the type contains an argument name
// However, this turns out not to pattern (the function doesn't need to use the
//   same argument name as the type does, so long as the types correspond.)
type PatternFunction = (a_guess_argument: string[]) => boolean;
// Now we can use `PatternFunction` everywhere we need to work with pattern-checker functions.

///////////////////////////////////////////////////////////////////////////////
// State management for the app
///////////////////////////////////////////////////////////////////////////////

// Each pattern is stored as an array of strings.
// The `history` stores all previous patterns tried.
let history: Array<Array<string>> = [];
// A global reference to the guess button, which we'll set below
let tryButton: HTMLButtonElement;

///////////////////////////////////////////////////////////////////////////////
// The app's pattern checker function
///////////////////////////////////////////////////////////////////////////////

/**
 * Tells whether or not a guess was correct.
 * @param {string[]} guess A guess of three integers in string form.
 */
function pattern(guess: string[]): boolean {
  if (guess.length !== 3) return false;
  if (guess[0].length < 1 || guess[1].length < 1 || guess[2].length < 1)
    return false;
  if (parseInt(guess[0]) >= parseInt(guess[1])) return false;
  if (parseInt(guess[1]) >= parseInt(guess[2])) return false;
  return true;
}

///////////////////////////////////////////////////////////////////////////////
// Set up the app by giving the browser a function to run when the window loads
///////////////////////////////////////////////////////////////////////////////

window.onload = () => {
  // (1) On window load, get the reference to the button  
  const maybeTryButton: null | HTMLElement =
    document.getElementById("try-button");
  // At this point, Typescript doesn't know for sure this element exists, and if
  // it exists, it might not be a button. So we use *narrowing* to handle those cases.
  if (maybeTryButton == null) {
    console.log("Couldn't find the button");  
  } else if (!(maybeTryButton instanceof HTMLButtonElement)) {
    console.log("Found the 'button' but it wasn't really a button element.");
  } else {
    // Now this is safe to do, because TypeScript can be sure this is a button.
    tryButton = maybeTryButton; 

    // (2) Make it so that whenever the button is clicked, our pattern function 
    // runs and the page is updated. A click listener takes no arguments, so 
    // we pass a 0-argument function that, when called, updates history using
    // our specific pattern function.
    tryButton.addEventListener("click", () => updateHistoryAndRender(pattern));
  }
};

///////////////////////////////////////////////////////////////////////////////
// Helpers to update app state and/or the page
///////////////////////////////////////////////////////////////////////////////

/**
 * Updates the history array and re-renders the page's HTML.
 */
function updateHistoryAndRender(patternChecker: PatternFunction) {
  // Update the *STATE* of the script, which remembers prior guesses
  updateGuessesState();
  // Update the *PAGE* (until this is called, nothing changes on the page)
  renderOldGuesses(patternChecker);
}

/**
 * Update the internal state of the app (not the page HTML)
 */
function updateGuessesState() {
  // Again, we'll need to use narrowing to make _sure_ the type is correct:
  const firstGuess: HTMLElement | null = document.getElementById("num-1");
  const secondGuess: HTMLElement | null = document.getElementById("num-2");
  const thirdGuess: HTMLElement | null = document.getElementById("num-3");
  if (firstGuess == null || secondGuess == null || thirdGuess == null) {
    console.log("One or more missing input elements");
  } else if (
    !(firstGuess instanceof HTMLInputElement) ||
    !(secondGuess instanceof HTMLInputElement) ||
    !(thirdGuess instanceof HTMLInputElement)
  ) {
    console.log("One or more input elements were not really input elements");
  } else {
    history.push([firstGuess.value, secondGuess.value, thirdGuess.value]);
    console.log("Guesses:", history);
  }
}

// If there's a constant text/label we want to search for, export it as a constant!
// (then tests will use it and changing it won't break tests)
export const CONST_success_try = "Guess was correct!"

/**
 * Re-renders the HTML based on the current history array.
 * (If called without updating history, any guess in progress will be erased.)
 */
function renderOldGuesses(patternChecker: PatternFunction) {
  // The new HTML block to build for displaying this guess and its result
  let newHtml: string = "";

  // For every guess array in GUESSES...
  history.forEach((sequence: string[], tryNumber: number) => {
    // Is it a correct guess?
    const correct: boolean = patternChecker(sequence);
    // Append a correct or incorrect guess <div> to newHtml
    // Note that back-quoted strings in JS let us
    //    (a) use the double-quote character (") without escaping it
    //    (b) use ${} expressions to splice in the result of JS expressions
    newHtml += `<div class="${correct ? "correct-try" : "incorrect-try"}">
            <p>Guess ${tryNumber + 1} was ${correct ? "correct" : "incorrect"}</p>
            <input value="${sequence[0]}" readonly />
            <input value="${sequence[1]}" readonly />
            <input value="${sequence[2]}" readonly />
        </div>`;
  });

  // Replace the contents of the old-rounds <div> with the HTML we generated above.  
  const oldRoundsElements: HTMLCollectionOf<Element> =
    document.getElementsByClassName("old-rounds");
  if (oldRoundsElements == null || oldRoundsElements.length < 1) {
    console.log("Could not find old-rounds element");
    return;
  }

  const oldRoundsElement = oldRoundsElements[0];
  oldRoundsElement.innerHTML = newHtml;
}

///////////////////////////////////////////////////////////////////////////////
// Helpers added specifically for testing and automation
///////////////////////////////////////////////////////////////////////////////

function clearHistory() {
  history = [];
}

function getHistory() {
  // defensive copy!
  return history.slice();
}

///////////////////////////////////////////////////////////////////////////////
// What does this module export? (I.e., what can be used in another module?)
///////////////////////////////////////////////////////////////////////////////

// For testing purposes, export everything we need
export { pattern, updateHistoryAndRender, clearHistory, getHistory };
export type { PatternFunction };
