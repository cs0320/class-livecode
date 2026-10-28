import { tryLogin, PASSWORD_LENGTH, ALPHABET} from './login'

function main() {
  
  const guess = '0'.repeat(10)
  // Or this style as well
  // '12'.padEnd(10, '0')

  const start = process.hrtime.bigint() 
  tryLogin(guess)
  const total = process.hrtime.bigint() - start 

  console.log(`${guess}: ${total}`);
  
}

main()
