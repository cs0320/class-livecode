import React, { useState, Dispatch, SetStateAction } from 'react';

// Parameter names don't necessarily need to overlap.
// I could use different variable names in the actual function.
export interface ControlledInputProps {
    value: string, 
    // This type comes from React+TypeScript. VSCode can suggest these.
    //   Concretely, this means "a function that sets a state containing a string"
    //   It's OK to just use this type without feeling you understand 100%.
    setValue: Dispatch<SetStateAction<string>>,
    ariaLabel: string 
}
  
// Input boxes contain state. We want to make sure React is managing that state,
//   so we have a special component that wraps the input box.
export function ControlledInput({value, setValue, ariaLabel}: ControlledInputProps) {
    return (
      <input value={value} 
             onChange={(ev) => setValue(ev.target.value)}
             aria-label={ariaLabel}
             ></input>
    );
  }
  