/*
  Vanilla TypeScript (non React) clone of the basic NYT puzzle functionality

  Remember to run tsc to compile this into JavaScript, otherwise index.html will
  load but won't actually do anything when the button is clicked.
*/

// First, make this code generic over different pattern-testing functions
// This is useful both for extensibility *and* testing.

// TypeScript does something unusual here: the type contains an argument name
// However, this turns out not to pattern (the function doesn't need to use the
//   same argument name as the type does, so long as the types correspond.)
type PatternFunction = (a_guess_argument: string[]) => boolean

////////////////////////////

// Each guess is stored as an array of strings. GUESSES stores all previous guesses.
let GUESSES: Array<Array<string>> = []
// A reference to the guess button, which we'll set below
let guessButton: HTMLButtonElement

window.onload = () => {
    // On window load, get the reference to the guess button...
    guessButton = document.getElementById("guess-button") as HTMLButtonElement
    // ...and make it so that whenever the button is clicked, our pattern function runs
    guessButton.addEventListener("click", () => updateGuesses(pattern))
}

/**
 * Tells whether or not a guess was correct.
 * @param {string[]} guess A guess of three integers in string form.
 */
function pattern(guess: string[]): boolean {
    if(guess.length !== 3) return false;
    if(guess[0].length < 1 || 
       guess[1].length < 1 ||
       guess[2].length < 1) return false;
    if(parseInt(guess[0]) >= parseInt(guess[1])) return false;
    if(parseInt(guess[1]) >= parseInt(guess[2])) return false;
    return true;
}

/**
 * When the guess button is clicked, updates the GUESSES array and re-renders HTML.
 */
function updateGuesses(patternChecker: PatternFunction) {
    // Update the *STATE* of the script
    updateGuessesState()
    // Update the *PAGE* (until this is called, nothing changes on the page)
    renderOldGuesses(patternChecker) 
}

function updateGuessesState() {
    const firstGuess = document.getElementById("guess-1") as HTMLInputElement
    const secondGuess = document.getElementById("guess-2") as HTMLInputElement
    const thirdGuess = document.getElementById("guess-3") as HTMLInputElement
    GUESSES.push([firstGuess.value, secondGuess.value, thirdGuess.value])
    console.log("Guesses:", GUESSES)
}

/**
 * Re-renders the HTML based on the current GUESSES array. 
 * (If called without updating GUESSES, any guess in progress will be erased.)
 */
function renderOldGuesses(patternChecker: PatternFunction) {
    let newHtml: string = ''    

    // For every guess array in GUESSES...
    GUESSES.forEach((guess: string[], guessNumber: number) => {
        
      // Is it a correct guess?
        const correct: boolean = patternChecker(guess)
        // Append a correct or incorrect guess <div> to newHtml
        // Note that back-quoted strings in JS let us 
        //    (a) use the double-quote character (")") without escaping it
        //    (b) use ${} expressions to splice in the result of JS expressions
        newHtml += `<div class="${correct ? "correct-guess" : "incorrect-guess"}">
            <p>Guess #${guessNumber + 1}</p>
            <input value="${guess[0]}" readonly />
            <input value="${guess[1]}" readonly />
            <input value="${guess[2]}" readonly />
        </div>`
    })

    // Replace the contents of the old-rounds <div> with the HTML we generated above    
    // Since I prefer not to use "id" and use "class" instead, we have to do [0]
    const oldGuesses = document.getElementsByClassName("old-rounds")[0] as HTMLDivElement
    oldGuesses.innerHTML = newHtml
}

function clearGuesses() {
  GUESSES = []
}

// For testing purposes
export {pattern, updateGuesses, clearGuesses};
export type {PatternFunction};