import './Puzzle.css';
import React, { useState, Dispatch, SetStateAction } from 'react';
import { NewRound } from './NewRound';
import { OldRound } from './OldRound';

/**
 * Encapsulates state and sub-components for the puzzle itself.
 * Should be rendered via the App component's body.
 * @returns JSX for the puzzle component
 */
export default function Puzzle() {
  // State: a list of lists of strings for the guessed numbers
  // (starts with the empty list, and is gradually updated)
  // ** Notice what happens if you leave out the <string[][]> **
  const [guesses, setGuesses] = useState<string[][]>([]);
  // State: the current notification string, if any.
  const [notification, setNotif] = useState('');

  // The app has two pieces:
  //   * some number of old rounds, displayed one after another
  //   * one new round, which needs to know how to update the state
  return (
    <div className="App">  
      {/* Add an OldRound for every element of guesses */} 
      { guesses.map( (guess,guessNumber) => 
        <OldRound           
          guess={guess}
          key={guessNumber} />)}

      {/* Add a single NewRound. Rather than passing in the full setter
          setGuesses, we'll instead pass a more restrictive function. 
          Notice that we're blurring ideas from strategy and proxy/adapter 
          here: we tell the component how to update the state, but that update
          strategy is weaker than it normally would be. */} 
      <NewRound         
        setNotification={setNotif}
        addGuess={(guess: string[]) => {
          // Copy the guesses into a new object. If we don't change the 
          // object reference used, React will not re-render since the 
          // "value" of the "guesses" array will be the same reference.
          const newGuesses: string[][] = guesses.slice(); 
          // Add the new guess to the new list
          newGuesses.push(guess)
          // Finally, call the setter with the new reference
          setGuesses(newGuesses) }} />

      {/* At the bottom, we'll sometimes display a status message.*/}
      {notification}   
    </div>
  );
}
