// We don't always need an interface for props; without one we need to use this
// syntax, which expects an object with a "guess" field of string[] type.

import { pattern } from "./pattern";

//   (This is NOT the same as accepting a string[]).
export function OldRound( {guess}: {guess: string[]}) {
    const result: boolean = pattern(guess)
    const label: string = result ? 'correct sequence' : 'incorrect sequence'
    const label_symbol: string = result ? "T   " : "F   "
    return (
      <div className={"guess-round-"+result}
           aria-label={label}>
        {label_symbol}
        <input value={guess[0]} readOnly/>
        <input value={guess[1]} readOnly/>
        <input value={guess[2]} readOnly/>
      </div>
    );  
  }