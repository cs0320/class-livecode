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


export default function Puzzle() {
  // NO
  //let guesses: number = 0
  const [guesses, setGuesses] = useState(0)
  return (
    <div className="Puzzle"> 
    {guesses}
    <button onClick={() => { 
        // NO! React won't see that you've made the change.
        //guesses = guesses + 1
        // YES! (But note "guesses" won't be updated yet, so
        //   this double-call fails
        // setGuesses(guesses + 1)
        // setGuesses(guesses + 1)
        // Alternative: pass an anonymous function. This works.
        setGuesses(g => g + 1)
        setGuesses(g => g + 1)
        console.log(guesses)
      }}></button>
    </div>
  );
}
