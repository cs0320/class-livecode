// Note this is a .ts file, not .tsx. It's TypeScript, but not React.

/**
 * Defines the kinds of sequence we are thinking of---the answer to 
 * the puzzle. We might imagine swapping in many of these and using 
 * the same puzzle infrastructure. 
 * 
 * @param guess A 3-number sequences
 * @returns true or false, depending on if the sequence matches
 */
export function pattern(guess: string[]): boolean {
  console.log(guess)
  if(guess.length !== 3) return false;
  if(parseInt(guess[0]) >= parseInt(guess[1])) return false;
  if(parseInt(guess[1]) >= parseInt(guess[2])) return false;
  return true;
}

