import React, { useState, Dispatch, SetStateAction } from 'react';
import { ControlledInput } from './ControlledInput';
import { TEXT_number_1_accessible_name, TEXT_number_2_accessible_name, TEXT_number_3_accessible_name, TEXT_try_button_accessible_name, TEXT_try_button_text } from './constants';

/**
 * TypeScript has done something odd here. The type of a _function_ needs to say what arguments it 
 * takes and what it returns. But in TypeScript syntax, we also need to give a name to the arguments. 
 * (This name doesn't have to be the same as in the actual functions.)
 */
export interface NewRoundProps {
    addGuess: (guess: string[]) => any,
    setNotification: Dispatch<SetStateAction<string>>
  }

  // You can also mix the interface (as type) with concrete field names, like this:
export function NewRound({addGuess, setNotification}: NewRoundProps) {
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