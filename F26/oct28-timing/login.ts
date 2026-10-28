const PASSWORD = '1234567890'
export const PASSWORD_LENGTH = PASSWORD.length
export const ALPHABET = '0123456789'

function stringsEqual(given: string, actual: string): boolean {
  if (given.length !== actual.length) return false
  for (let i = 0; i < actual.length; i++) {
    if (given[i] !== actual[i]) return false
  }
  return true
}

export function tryLogin(password: string): boolean {
  return stringsEqual(password, PASSWORD)
}
