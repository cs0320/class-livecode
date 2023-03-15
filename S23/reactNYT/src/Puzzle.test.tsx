import React from 'react';
// Lets us render a component and send queries
import { render, screen } from '@testing-library/react';
// Outside the braces = "default export"; inside = other exports
import Puzzle, { TEXT_number_1_accessible_name, TEXT_number_2_accessible_name, TEXT_number_3_accessible_name, TEXT_try_button_accessible_name, TEXT_try_button_text } from './Puzzle';
// Lets us send user events
import userEvent from '@testing-library/user-event';
// Lets us check whether an element is within another element
import {within} from '@testing-library/dom'
// Lets us use 'toBeInTheDocument()' 
import '@testing-library/jest-dom'

// Why `async`? Suggested by RTL. Essentially, it allows the test function to 
// more easily use asynchrony (e.g. waiting for components to render), and the 
// testing library auto-handles the promise for us.
test('renders guess button', async () => {
  render(<Puzzle />);
  const buttonElement = screen.getByText(new RegExp(TEXT_try_button_text));  
  expect(buttonElement).toBeInTheDocument();
  // That's not very selective, though. We'll do better in the next test.
});

// RTL discourages searching by class name, although it's possible to do. 
// Instead, it encourages search based on user-accessible properties like labels
// and ARIA (accessibility) annotations. This might seem annoying, but in this case
// let's ask ourselves something. We've got 3 input boxes on the page. Shouldn't we 
// provide some annotations to help (say) screenreaders disambiguate them without
// relying on relative positioning? 
test('renders guess input fields', async () => {
    render(<Puzzle />);    
    // ...NOT this way:
    //const textboxElements = screen.getAllByRole("textbox")    
    // Instead, leverage accessibility tags we ought to be providing anyway
    // https://testing-library.com/docs/queries/byrole/
    const guess0 = screen.getByRole("textbox", {name: TEXT_number_1_accessible_name})
    const guess1 = screen.getByRole("textbox", {name: TEXT_number_2_accessible_name})
    const guess2 = screen.getByRole("textbox", {name: TEXT_number_3_accessible_name})
    expect(guess0).toBeInTheDocument()
    expect(guess1).toBeInTheDocument()
    expect(guess2).toBeInTheDocument()
});

// Note this is a "wide" test, because it actually clicks a button and inspects the result.
// (RTL makes this much easier, and using accessibility metadata well helps too.)
test('entering correct guess', async () => {
    render(<Puzzle />);    // Don't forget this :-) 

    const guess0 = screen.getByRole("textbox", {name: TEXT_number_1_accessible_name})    
    const guess1 = screen.getByRole("textbox", {name: TEXT_number_2_accessible_name})    
    const guess2 = screen.getByRole("textbox", {name: TEXT_number_3_accessible_name})  
    const submitButton = screen.getByRole("button", {name: TEXT_try_button_accessible_name})  

    // We don't have to set properties directly anymore, instead we have user events:
    userEvent.type(guess0, '100')
    userEvent.type(guess1, '200')
    userEvent.type(guess2, '300')
    userEvent.click(submitButton)

    // Now, do we see a correct guess block? 
    // For asynchronous updates, use "findBy" rather than "getBy" (but we dont seem to need it?)
    // We could go outside RTL and search for classes, but instead let's use accessibility again:
    // https://www.w3.org/TR/html-aria/#docconformance
    
    // Any role...
    const correctBlock = screen.getByRole(/.*/, {name: 'correct sequence'})    
    expect(correctBlock).toBeInTheDocument()
    // "query" version will not error if nothing found, will just be null
    const incorrectBlock = screen.queryByRole(/.*/, {name: 'incorrect sequence'})    
    expect(incorrectBlock).toBeNull()
});

test('entering incorrect guess (same values)', async () => {
    render(<Puzzle />);    // Don't forget this :-) 

    const guess0 = screen.getByRole("textbox", {name: TEXT_number_1_accessible_name})    
    const guess1 = screen.getByRole("textbox", {name: TEXT_number_2_accessible_name})    
    const guess2 = screen.getByRole("textbox", {name: TEXT_number_3_accessible_name})  
    const submitButton = screen.getByRole("button", {name: TEXT_try_button_accessible_name})  

    userEvent.type(guess0, '100')
    userEvent.type(guess1, '100')
    userEvent.type(guess2, '100')
    userEvent.click(submitButton)

    // I had originally left these as the wrong strings to motivate string mismatch,
    //   but we didn't have time in class. So I just fixed these to say "sequence":

    const incorrectBlock = screen.getByRole(/.*/, {name: 'incorrect sequence'})    
    expect(incorrectBlock).toBeInTheDocument()
    const correctBlock = screen.queryByRole(/.*/, {name: 'correct sequence'})    
    expect(correctBlock).toBeNull()
});

/*
  Discuss: what tests are missing here?
*/ 
  

/*
  Questions from class/after:
    - how can I turn a string into a promise to produce it?
       answered in 2 example tests below
    - how can I check that an element is inside another element using RTL?
       answered in an example test below
    - what do the various imports above do?
       answered in comments above the imports
*/

test('create a promise from a value, way 1', async () => {
  // Create a promise to return 'happy tuesday!'
  const myString: string = 'happy tuesday!'
  const myPromise: Promise<string> = new Promise( (resolve) => {resolve(myString)})
  
  // See: https://jestjs.io/docs/asynchronous
  return myPromise.then(s => expect(s).toBe(myString))
})

test('create a promise from a value, way 2', async () => {
  // Create a promise to return 'happy tuesday!'
  const myString: string = 'happy tuesday!'
  const myPromise: Promise<string> = Promise.resolve(myString)
  
  // See: https://jestjs.io/docs/asynchronous
  return myPromise.then(s => expect(s).toBe(myString))
})

test('check that the text fields are within the fieldset', async () => {
  render(<Puzzle />);    // Don't forget this :-) 

  const fieldSet = screen.getByRole(/.*/, {name: 'Enter a 3-number sequence:'})  
  
  // We will use 'within' here
  // https://testing-library.com/docs/dom-testing-library/api-within/
  const guess0 = within(fieldSet).getByRole("textbox", {name: TEXT_number_1_accessible_name})    
  const guess1 = within(fieldSet).getByRole("textbox", {name: TEXT_number_2_accessible_name})    
  const guess2 = within(fieldSet).getByRole("textbox", {name: TEXT_number_3_accessible_name})  
  
  expect(guess0).not.toBeNull()
  expect(guess1).not.toBeNull()
  expect(guess2).not.toBeNull()

  // Just in case, let's check for an element that _isn't_ contained within the fieldset
  // Again, we'll use 'queryByRole' because that returns null, rather than erroring, if the
  //   target doesn't exist.
  const submitButton = within(fieldSet).queryByRole("button", {name: TEXT_try_button_accessible_name})  
  expect(submitButton).toBeNull()

})


