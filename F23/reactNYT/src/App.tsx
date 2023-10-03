import React from 'react';
import Puzzle from './Puzzle'
import './App.css';

/**
 * Top-level application component. Shows the question prompt, and then
 * loads the Puzzle component within it.
 * @returns JSX for the App component
 */
function App() {
  return (
    <div className="App">
      <p className="App-header">
        I'm thinking of a function that either accepts or rejects sequences of 3 numbers. 
        For example, my function returns true on the sequence: 2, 4, 8.
        Can you figure out what it is? Try it out and see what it returns on your sequences...
      </p>
      <Puzzle />      
    </div>
  );
}

export default App;
