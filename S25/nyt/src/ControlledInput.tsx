import React, { Dispatch, SetStateAction } from 'react';

/*
   This is an ordinary HTML <input/> element, but its value is controlled by React. 
   This means that there is a react state variable that contains the input field's 
   current value. The alternative would be letting the Browser DOM hold the value,
   which wouldn't require a state variable, but would require some work to reference 
   the HTML element in our React program. 

   If this were a one-time-use web form, I wouldn't use a controlled input. But since 
   this is interactive and values are changing often, I like keeping the state in React.  

*/

export interface ControlledInputProps {
    /** The current value in the input box. */
    value: string, 
    // This type comes from React+TypeScript. VSCode can suggest these.
    //   Concretely, this means "a function that sets a state containing a string"
    /** The setter to notify React of changes with */
    setValue: Dispatch<SetStateAction<string>>,
    /** ARIA metadata to apply (use for testing, accessibility) */
    ariaLabel: string 
}
  
// Input boxes contain state. We want to make sure React is managing that state,
//   so we have a special component that wraps the input box. 
export function ControlledInput({value, setValue, ariaLabel}: ControlledInputProps) {
    return (
      <input 
             //  The displayed value is always what's in the React state variable.
             value={value} 
             // Whenever the value changes (e.g., a new character is entered), notify 
             // React via the setter for this state. 
             onChange={(ev) => setValue(ev.target.value)}
             // Apply the ARIA metadata given in the props
             aria-label={ariaLabel}
             ></input>
    );
  }
  