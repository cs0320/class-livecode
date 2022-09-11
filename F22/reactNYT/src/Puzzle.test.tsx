import React from 'react';
import { render, screen } from '@testing-library/react';
import Puzzle, { TEXT_number_1_accessible_name, TEXT_number_2_accessible_name, TEXT_number_3_accessible_name, TEXT_try_button_accessible_name, TEXT_try_button_text } from './Puzzle';
import userEvent from '@testing-library/user-event';

test('renders guess button', () => {
  render(<Puzzle />);
  // case insensitive regex:
  //const buttonElement = screen.getByText(/Try it!/i);
  // Better, less brittle:
  const buttonElement = screen.getByText(new RegExp(TEXT_try_button_text));
  // That's not very selective, though. We'll do better in the next test.
  expect(buttonElement).toBeInTheDocument();
});

// RTL discourages searching by class name, although it's possible to do. 
// Instead, it encourages search based on user-accessible properties like labels
// and ARIA (accessibility) annotations. This might seem annoying, but in this case
// let's ask ourselves something. We've got 3 input boxes on the page. Shouldn't we 
// provide some annotations to help (say) screenreaders disambiguate them without
// relying on relative positioning? 
test('renders guess input fields', () => {
    render(<Puzzle />);    
    // ...not this way:
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

// TODO discuss: is this the right way to use labels? 
//   aria-label attribute vs. a <label ...> element with a "for" value?
test('entering correct guess', () => {
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
    const correctBlock = screen.getByRole(/.*/, {name: 'correct guess'})    
    expect(correctBlock).toBeInTheDocument()
    // "query" version will not error if nothing found, will just be null
    const incorrectBlock = screen.queryByRole(/.*/, {name: 'incorrect guess'})    
    expect(incorrectBlock).toBeNull()
});

test('entering incorrect guess (same values)', () => {
    render(<Puzzle />);    // Don't forget this :-) 

    const guess0 = screen.getByRole("textbox", {name: TEXT_number_1_accessible_name})    
    const guess1 = screen.getByRole("textbox", {name: TEXT_number_2_accessible_name})    
    const guess2 = screen.getByRole("textbox", {name: TEXT_number_3_accessible_name})  
    const submitButton = screen.getByRole("button", {name: TEXT_try_button_accessible_name})  

    userEvent.type(guess0, '100')
    userEvent.type(guess1, '100')
    userEvent.type(guess2, '100')
    userEvent.click(submitButton)

    const incorrectBlock = screen.getByRole(/.*/, {name: 'incorrect guess'})    
    expect(incorrectBlock).toBeInTheDocument()
    const correctBlock = screen.queryByRole(/.*/, {name: 'correct guess'})    
    expect(correctBlock).toBeNull()
});

  
