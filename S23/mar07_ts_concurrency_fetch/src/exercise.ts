function sayHello(): number {
    let toReturn: number = 0
    setTimeout(() => {
        toReturn = 500
    }, 0)
    setTimeout(() => {
        console.log('updating toReturn')
        toReturn = 100
    }, 5000)
    return toReturn
}

function main(): void {
    const num: number = sayHello()
    console.log("Returned:", num)
}

export default main;
