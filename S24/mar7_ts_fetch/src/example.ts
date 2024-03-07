
// Just pure JavaScript, not Typescript, so we can paste into the 
// browser console directly: 

function sayHello(){
    let toReturn = 0
    setTimeout(() => {
        toReturn = 500
    }, 0)
    setTimeout(() => {
        toReturn = 100
    }, 5000)
    return toReturn
}
console.log(sayHello())