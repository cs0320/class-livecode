# React NYT Puzzle (Fall 2023)

This webapp uses:
* TypeScript
* React
* Vite

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
  
## Notes on React 

### Typing props

Last year I used a single `props` argument to all the function components, and since it was untyped, this never caused a problem in the demo. But now we want props to be typed (and not typed as `any`!). I've started decomposing fields in the function header and typing by interface, taking something like this: 

```typescript
function OldRound(props) {
    ...
}
```
to this:
```typescript
interface OldRoundProps {
  guess: string[]
}

function OldRound( {guess}: OldRoundProps) {
    ...
}
```

In this example, it seems silly and verbose, but I think it's a lot more clear. If we wanted to, we could leave out the `interface` entirely and just write the type as `{guess: string[]}`, which makes sense in this case. So that's what I did. In other cases, the separate interface is appropriate.

#### NewRound

This scrolls off the screen, which is bad for a live demo:

```typescript
function ControlledInput({value, setValue}: {value: string, setValue: Dispatch<SetStateAction<string>>}) {
    ...
}
```

So I used an explicit interface. Note the `Dispatch<SetStateAction<string>>` is just the type of the setter provided by a `useState` hook for `string` state. I imported `Dispatch` and `SetStateAction` to shorten the type (would have to use `React.Dispatch` etc. otherwise). VSCode autocompletion is recommended for filling these in.

### Typing hooks

Hooks need type annotations where the type of the starting value is ambiguous. E.g., `const [guesses, setGuesses] = useState<string[][]>([]);` rather than just `const [guesses, setGuesses] = useState([]);` to tell Typescript that the value is an array of string arrays.

### Typing anonymous-function arguments

Similarly, I had to add `string[]` as a type annotation to the function sent in the `addGuess` prop of `NewRound`. 

### Exporting 

I no longer `export default` separately; now it's just `export default function Puzzle ...` in the declaration of `Puzzle`. 

## CSS

I moved the puzzle-specific CSS to `Puzzle.css`, to keep it separate from the CRA boilerplate.

## Testing 

We aren't using RTL anymore; [Playwright](https://playwright.dev) will prove to be more widely useful in 0320, since students will want to do end-to-end testing on their term projects and later sprints.