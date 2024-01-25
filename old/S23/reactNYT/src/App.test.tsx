import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';
import '@testing-library/jest-dom'

test('renders instructions', () => {
  render(<App />);
  // The "i" modifier means a case-insensitive match
  const instructionElement = screen.getByText(/I'm thinking of a function/i);
  expect(instructionElement).toBeInTheDocument();
});
