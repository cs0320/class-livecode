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
  const [sequences, setSequences] = useState<string[][]>([]);
  // State: the current notification string, if any.
  const [notification, setNotif] = useState('');

  // The app has two pieces:
  //   * some number of old rounds, displayed one after another
  //   * one new round, which needs to know how to update the state
  return (
    <div className="Puzzle">  
      {/* Add an OldRound for every element of guesses 
          I added the console.log so you can see the guessNumber growing 
          based on questions on Oct 5 2023. Notice that anonymous functions 
          don't need braces or explicit return statements if they don't need 
          multiple lines... Here, I now needed to add braces, and an explicit 
          return statement! */} 
      { sequences.map( (sequence,sequenceNumber) => {
        // This will go to the JS console in the browser
        console.log(`In the sequences.map; guessNumber=${sequenceNumber}`)
        return (<OldRound           
          seq={sequence}
          key={sequenceNumber} />)
        })}

      {/* Add a single NewRound. Rather than passing in the full setter
          setGuesses, we'll instead pass a more restrictive function. 
          Notice that we're blurring ideas from strategy and proxy/adapter 
          here: we tell the component how to update the state, but that update
          strategy is weaker than it normally would be. 
          
          The subcomponent can only *ADD* a new sequence, not delete any old ones. */} 
      <NewRound         
        setNotification={setNotif}

        addSeq={(sequence: string[]) => {
          // Copy the guesses into a new object. If we don't change the 
          // object reference used, React will not re-render since the 
          // "value" of the "guesses" array will be the same reference.
          const newSeqs: string[][] = sequences.slice(); 
          // Add the new guess to the new list
          newSeqs.push(sequence)
          // Finally, call the setter with the new reference.
          setSequences(newSeqs) 
          // Note that the state will not be updated synchronously! 
          // If we print out the state's current value, it will be the OLD list
          console.log(`The React state won't be updated yet. Currently still: ${sequences}`)
          // The current control flow needs to finish, and control returned to the browser,
          // before the state updates can take effect. Since this is a callback function,
          // that should happen once the callback function returns. But NOT BEFORE.
          }} />

      {/* At the bottom, we'll sometimes display a status message.*/}
      {notification}   
    </div>
  );
}
