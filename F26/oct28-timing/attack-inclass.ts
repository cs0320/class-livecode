import { tryLogin, PASSWORD_LENGTH, ALPHABET} from './login'

/*
   This is what we ended up with at the end of class today. 
   I've only changed it to add comments (which I wrote myself :-)) and 
   to add a demo of the brute-force version.

   I also corrected an error I made: warming up AFTER the 
   measurements. Oops!
*/

/**
 * A dictionary to record lists of times, indexed by prefix string.
 * This TypeScript syntax says "The key can be any string; the value must be an
 * array of bignums". (A bignum is like an integer, but there's no maxint.)
 */
interface Results {
  [key: string]: bigint[];
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

function trial(results: Results) {
  for(const first of ALPHABET) {
    const guess = first + generateRandomString(9, ALPHABET)
    const start = process.hrtime.bigint() 
    tryLogin(guess)
    const total = process.hrtime.bigint() - start 
    if(results[first] === undefined) results[first] = []
    results[first].push(total)
    //console.log(`${guess}: ${total}`);
  }
}

/**
 * We could always brute-force the password, if we can iterate through all possibilities. 
 * 
 * 
 * Our strategy:
 *   - try all 1-character prefixes, and find "the slowest". But two challenges:
 *       * noise: wrong-prefix might appear longest 
 *       * suffix choice: after the _test prefix_ , what do you append?
 * 
 */
function main() {


  // Don't do this, it's bad idea on a reasonably-large password search space. 
  // If the space is small, go for it! Here it actually is small enough FOR THIS PASSWORD. :)
  // If our password wasn't so early in the enumeration, we'd be waiting much longer.
  // Uncomment and try it.
  // const counter = {c: 0}
  // bruteForce('', counter)
  // console.log(`Found after ${counter.c}`)

  // But what if we could use STATISTICS to guide our brute-force search?
  // That's the essence of this attack.

  const fake: Results = {}
  for(let ii = 0; ii < 10; ii++)
    trial(fake)


  const results: Results = {}  
  for (let i = 0; i < 1000; i++) {
    trial(results)
  }  
  
  // For every prefix
  //console.log(results)
  for(const first of ALPHABET) {
    console.log(`${first}: avg = ${averageBigInt(results[first])}`)
  }

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


/**
 * Iterate through every possible password. Stop when it's found.
 * We'll do this recursively, adding 1 character to the prefix for every
 * recursive call.
 * @param prefix build atop this prefix (or check it, if it's long enough)
 * @param counter count how many recursive calls before we find the password
 * @returns password found
 */
function bruteForce(prefix: string, counter: {c: number}) {
  if(counter.c % 100000 === 0) console.log(`Trial # ${counter.c}`)
  counter.c++
  if(prefix.length === PASSWORD_LENGTH) 
    return tryLogin(prefix)
  for(const char of ALPHABET) {
    if(bruteForce(prefix+char, counter) === true) return true
  }
  return false
}