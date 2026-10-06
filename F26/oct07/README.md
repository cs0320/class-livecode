# React NYT Puzzle Example

This webapp uses:
* TypeScript
* React
* Vite
* Playwright (for end-to-end testing)

To run: `npm install` and then `npm run start`.

I've taken notes on building this example over the past couple of semesters.

## Notes on Files 

I've put each React component into its own file, often with a corresponding interface that defines what props the component expects. The nesting of components is roughly:

- `index.html` (99 percent boilerplate)
  - `index.tsx` (99 percent boilerplate)
    - `App.tsx` (the top-level application component)
      - `Puzzle.tsx` (the puzzle itself)
        - `OldRound.tsx` (one of these for each previous round of the puzzle)
        - `NewRound.tsx` (one of these for entering a new round)
          - `ControlledInput.tsx` (used so React can manage the state of the input box)
  
### Packages

* `npm outdated` will give you a table of requested version numbers vs. current version. 

## Notes on Playwright

See [README.tests.md](./README.tests.md). The tests that I wrote for this example are all in [tests/app.spec.ts](./tests/app.spec.ts).

You may need to run `npx playwright install` if you haven't yet. This downloads browser information, etc.

## Notes on React 

### Types of Setters

React manages state via setters and getters. More accurately, when you call `useState`, you get back both a _variable_ that will contain the current value and a _function_ that can be used to update that value. If the setter is called, React updates the variable when it gets a chance to *if the value has changed*. Note that adding an element to a list, but keeping the list reference the same, will not trigger a React update!  

You'll need to pass the setter around. It has a type that's kind of annoying to use, but just try to run with it. E.g., if I've created a `string` state variable, I'll have:
* a `string` variable; and 
* a `Dispatch<SetStateAction<string>>` variable (the setter).
VSCode autocompletion is recommended for filling these in.

### Typing hooks

Hooks need type annotations where the type of the starting value is ambiguous. E.g., `const [guesses, setGuesses] = useState<string[][]>([]);` rather than just `const [guesses, setGuesses] = useState([]);` to tell Typescript that the value is an array of string arrays. If you don't do this, TypeScript will not infer the type of the contents of the array.

### Typing anonymous-function arguments

Similarly, I had to add `string[]` as a type annotation to the function sent in the `addGuess` prop of `NewRound` so that TypeScript would infer properly.
