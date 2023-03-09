# Prep for Reactified NYT Puzzle (Fall 2022)

This is a fresh version of the app previously deployed [here](https://github.com/tnelson/reactNYT). I'm documenting changes made for 2022 and making notes that might be useful in course content.

If you're reading this as a student, note that much of the readme is meant for _me_ (Tim) for framing the demo in class and calling out potential issues.

To run: `npm install` and then `npm start`.

## Initialization

Although we had the example app from last year, I created a fresh React app first via `create-react-app react-nyt --template typescript`. This seems more likely to work with Typescript and other dependencies than carrying over a year-old example. 

Note that to _deploy_ the new version, this code needs to be moved over as a commit or PR to the reactNYT Github repo, and then the deployment script needs to be run. This repo is for storing the livecode.

I then copied the main `App` component's "class prep" version from last year to `Puzzle` here. I think I misnamed the file extension previously (it should have been `.tsx` or at minimum `.jsx`.) Regardless, we didn't use Typescript very much last semester, so the component needed updating anyway.

## React 

### Typing props

Last year I used a single `props` argument to all the function components, and since it was untyped, this never caused a problem in the demo. But now we want props to be typed (and not typed as `any`!). I've started liking decomposing fields in the function header and typing by interface, taking something like this: 

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

#### OldRound

I'm not sure why the old version passed a `setGuesses` prop to `OldRound` (which was just a thunk---a function of no arguments---returning `undefined`). It's not necessary; I took it out.

#### NewRound

This scrolls off the screen:

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

## Testing via RTL

See `Puzzle.test.tsx`.

