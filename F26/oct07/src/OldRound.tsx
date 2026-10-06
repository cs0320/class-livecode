import { pattern } from "./pattern";

/**
 * Component for displaying a "frozen" prior sequence tried, and the result. 
 * Note that the type {seq: string[]} is NOT THE SAME as accepting a string[].
 * 
 * We don't always need an interface for props; without one we can use this syntax, 
 * which expects an object with a "seq" field of string[] type and then automatically 
 * populates a new variable of that name with the value within the props object.
 */
export function OldRound( {seq}: {seq: string[]}) {
    const result: boolean = pattern(seq)
    const label: string = result ? 'correct sequence' : 'incorrect sequence'
    const label_symbol: string = result ? "T   " : "F   "
    return (
      <div className={"guess-round-"+result}
           aria-label={label}>
        {label_symbol}
        <input value={seq[0]} readOnly/>
        <input value={seq[1]} readOnly/>
        <input value={seq[2]} readOnly/>
      </div>
    );  
  }