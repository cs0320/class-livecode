

function pattern(guess: string[]): boolean {
    if(guess.length !== 3) return false;
    if(guess[0].length < 1 || 
       guess[1].length < 1 ||
       guess[2].length < 1) return false;
    if(parseInt(guess[0]) >= parseInt(guess[1])) return false;
    if(parseInt(guess[1]) >= parseInt(guess[2])) return false;
    return true;
}


window.onload = () => {
    const guessButton: HTMLElement | null  = 
      document.getElementById("try-button") // as HTMLButtonElement :-(

    if(guessButton == null) {
        console.log('error! no such button')
    } else if (!(guessButton instanceof HTMLButtonElement)) { 
        console.log('error! no such button; element found but wrong type')
    } else {
        guessButton.addEventListener("click", () => 
        {console.log('clicked!')})
    }

}


export {pattern}