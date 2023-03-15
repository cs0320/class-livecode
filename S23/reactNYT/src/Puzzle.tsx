import './Puzzle.css';
import React, { useState, Dispatch, SetStateAction } from 'react';

// When we write tests, we'll be searching using accessible names. So let's
// use the same constant identifier; that way if we decide to change the text
// in the app, it won't break our tests.
export const TEXT_try_button_accessible_name = 'try your sequence'
export const TEXT_number_1_accessible_name = 'first number in sequence'
export const TEXT_number_2_accessible_name = 'second number in sequence'
export const TEXT_number_3_accessible_name = 'third number in sequence'
export const TEXT_try_button_text =  'Try it!'

export function pattern(guess: string[]): boolean {
  console.log(guess)
  if(guess.length !== 3) return false;
  if(parseInt(guess[0]) >= parseInt(guess[1])) return false;
  if(parseInt(guess[1]) >= parseInt(guess[2])) return false;
  return true;
}

// Remember that parameter names don't necessarily need to overlap;
// I could use different variable names in the actual function.
interface ControlledInputProps {
  value: string, 
  // This type comes from React+TypeScript. VSCode can suggest these.
  //   Concretely, this means "a function that sets a state containing a string"
  setValue: Dispatch<SetStateAction<string>>,
  ariaLabel: string 
}

// Input boxes contain state. We want to make sure React is managing that state,
//   so we have a special component that wraps the input box.
function ControlledInput({value, setValue, ariaLabel}: ControlledInputProps) {
  return (
    <input value={value} 
           onChange={(ev) => setValue(ev.target.value)}
           aria-label={ariaLabel}
           ></input>
  );
}

// We don't always need an interface for props; without one we need to use this
// syntax, which expects an object with a "guess" field of string[] type.
//   (This is NOT the same as accepting a string[]).
function OldRound( {guess}: {guess: string[]}) {
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

interface NewRoundProps {
  addGuess: (guess: string[]) => any,
  setNotification: Dispatch<SetStateAction<string>>
}
// You can also mix the interface (as type) with concrete field names, like this:
function NewRound({addGuess, setNotification}: NewRoundProps) {
  // Remember: let React manage state in your webapp. The current guesses are string fields.
  // You don't always need the <...> annotation, but I like to include it for clarity.
  const [value0, setValue0] = useState<string>('');
  const [value1, setValue1] = useState<string>('');
  const [value2, setValue2] = useState<string>('');
  return (
    <div className="new-round">
      <div className="guess-round-current"> 
      {/* This is a comment within the JSX. Notice that it's a TypeScript comment wrapped in
          braces, so that React knows it should be interpreted as TypeScript */}
      
      {/* I opted to use this HTML tag; you don't need to. It structures multiple input fields
          into a single unit, which makes it easier for screenreaders to navigate. */}
      <fieldset>
        <legend>Enter a 3-number sequence:</legend>
        <ControlledInput value={value0} setValue={setValue0} ariaLabel={TEXT_number_1_accessible_name}/>
        <ControlledInput value={value1} setValue={setValue1} ariaLabel={TEXT_number_2_accessible_name}/>
        <ControlledInput value={value2} setValue={setValue2} ariaLabel={TEXT_number_3_accessible_name}/>
      </fieldset>   
      </div>
      <div>
        <button onClick={() => {  
            if(!isNaN(parseInt(value0)) && !isNaN(parseInt(value1)) && !isNaN(parseInt(value2))) {
              addGuess([value0,value1,value2])
              setValue0('')
              setValue1('')
              setValue2('')
              setNotification('')
            } else {
              setNotification('Please provide a full 3-number sequence.')
            }
          }}
          aria-label={TEXT_try_button_accessible_name}>
          
          {/* The text displayed on the button */}
          {TEXT_try_button_text}
        </button>
      </div>
    </div>
  );  
}

export default function Puzzle() {
  const [guesses, setGuesses] = useState<string[][]>([]);
  const [notification, setNotif] = useState('');
  return (
    <div className="App">   
      { guesses.map( (guess,guessNumber) => 
        <OldRound           
          guess={guess}
          key={guessNumber} />)}
      <NewRound         
        setNotification={setNotif}
        addGuess={(guess: string[]) => {          
          const newGuesses = guesses.slice(); 
          newGuesses.push(guess)
          setGuesses(newGuesses) }} />
      {notification}   
    </div>
  );
}
