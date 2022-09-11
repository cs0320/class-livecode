import './Puzzle.css';
import React, { useState, Dispatch, SetStateAction } from 'react';

export const TEXT_try_button_accessible_name = 'try your sequence'
export const TEXT_number_1_accessible_name = 'first number in sequence'
export const TEXT_number_2_accessible_name = 'second number in sequence'
export const TEXT_number_3_accessible_name = 'third number in sequence'
export const TEXT_try_button_text =  'Try it!'

export function pattern(guess: string[]): boolean {
  console.log(guess)
  if(guess.length !== 3) return false;
  if(guess[0] >= guess[1]) return false;
  if(guess[1] >= guess[2]) return false;
  return true;
}

// Remember that the parameter names don't necessarily need to overlap.
interface ControlledInputProps {
  value: string, 
  setValue: Dispatch<SetStateAction<string>>,
  ariaLabel: string 
}

function ControlledInput({value, setValue, ariaLabel}: ControlledInputProps) {
  return (
    <input value={value} 
           onChange={(ev) => setValue(ev.target.value)}
           aria-label={ariaLabel}
           ></input>
  );
}

function OldRound( {guess}: {guess: string[]}) {
  const result: boolean = pattern(guess)
  const label: string = result ? 'correct guess' : 'incorrect guess'
  return (
    <div className={"guess-round-"+result}
         aria-label={label}>
      <input value={guess[0]} readOnly/>
      <input value={guess[1]} readOnly/>
      <input value={guess[2]} readOnly/>
    </div>
  );  
}

// Remember that the parameter names don't necessarily need to overlap.
interface NewRoundProps {
  addGuess: (guess: string[]) => any,
  setNotification: Dispatch<SetStateAction<string>>
}

function NewRound({addGuess, setNotification}: NewRoundProps) {
  const [value0, setValue0] = useState('');
  const [value1, setValue1] = useState('');
  const [value2, setValue2] = useState('');
  return (
    <div className="new-round">
      <div className="guess-round-current">  
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
          {TEXT_try_button_text}
        </button>
      </div>
    </div>
  );  
}

export default function Puzzle() {
  const [guesses, setGuesses] = useState<string[][]>([]);
  const [notification, setNotification] = useState('');
  return (
    <div className="App">   
      { guesses.map( (guess,guessNumber) => 
        <OldRound           
          guess={guess}
          key={guessNumber} />)}
      <NewRound         
        setNotification={setNotification}
        addGuess={(guess: string[]) => {          
          const newGuesses = guesses.slice(); 
          newGuesses.push(guess)
          setGuesses(newGuesses) }} />
      {notification}   
    </div>
  );
}
