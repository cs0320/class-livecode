
/*
  Let's understand anonymous functions a bit better. 
*/

function example1() {

    // Here's a mutable variable, containing the string "Tim"
    let myName: string = "Tim"

    // Now we'll create an anonymous function that will print the 
    // value of myName to the JS console, and tell JS to call it in 
    // 100 milliseconds.
    setTimeout(() => console.log(`1: Hello, my name is ${myName}`), 100)

    // Having created this anonymous function, we'll immediately change 
    // the value of myName. 
    myName = "Nim"
    // Will we get "Tim" or "Nim" at the console? 
}
example1()






/* 
  We get "Nim". This is because the anonymous function kept a reference
  to the variable, and will reference it, even if that variable isn't 
  a parameter to the function. This is called _closing_ over that variable.

  For more information (from the JavaScript perspective) see this page:
  https://developer.mozilla.org/en-US/docs/Web/JavaScript/Closures
*/

/*
  What if we wanted to keep a snapshot of the variable at the time the 
  function was created? We'd need to avoid this "closure" property of 
  variables referenced by anonymous function declarations. 

  One common pattern you'll see in JavaScript and TypeScript is called 
  the "Immediately Invoked Function Expression" or IIFE. 
  
  https://developer.mozilla.org/en-US/docs/Glossary/IIFE
  
  Let's invent the idea.
*/

function example2() {
    let myName = "Nim"

    // Here's what we had before. We somehow need to break the connection 
    // between the variable myName and the value in the anonymous function body.
    // const myFunction = () => console.log(myName)

    // We could create a different variable, and either make it a const or 
    // just trust it will never be updated:
    const frozenMyName = myName; 
    setTimeout(() => console.log(`2: Hello, my name is ${frozenMyName}`), 100)
    myName = "Telson"

    // As expected, we see "Nim" again. So we've successfully "frozen"
    // the value. But this clutters the current scope: we've added this 
    // new constant that will be floating around...

    // What in the world is this?? It's a function that, when called, 
    // returns another function. 
    const makeFrozenNamePrinter = () => {
        const frozen = myName
        return () => console.log(`3: Hello, my name is ${frozen}`) // NOT myName!
    }
    setTimeout(makeFrozenNamePrinter(), 100)
    myName = "Nelson"

    // If we had called the outer function here, we'd have the same problem. 
    // makeFrozenNamePrinter()() 
    // But we created the inner function before the change!

    // We still see Telson, because the _first_ function call returns a function 
    // that closes over the `frozen` variable, not the `myName` variable.
}
example2()

/*
  Closures are powerful. They can be super useful, but if you aren't 
  aware of them you will see "fun" bugs in your code. It's very common
  to make the following mistake. 
*/
function example3() {
    const myList = [0,1,2,3,4,5]
    for(const val of myList) {
        setTimeout( () => console.log(val), 100)
    }
    // This is fine: there's a _new variable_ val for each iteration.
    // We sometimes say that "const has _block scope_".

    for(let val of myList) {
        setTimeout( () => console.log(val), 100)
    }
    // So is this. Variables declared with `let` have block scope, too.

    for(var val of myList) {
        setTimeout( () => console.log(val), 100)
    }
    // This, however, isn't. There's only ever one `val` variable
    // created (this is my usual answer to "what's the difference 
    // between `let` and `var` in JS?")

    // This is also the first of the loops to print all 5s. 
    // Why? Closures!

    // If you're stuck using `var` for whatever reason, you can use the IIFE
    // trick here, too. 

    // We don't even have to name the factory function! 
    // It can be anonymous, and then immediately called.
    // (Hence IIFE)
    for(var val of myList) {
        setTimeout( (() => {const ii2 = val; return () => console.log(ii2)})(), 100)
    }

    // Note: the `var` vs. `let` distinction matters for the _loops_. But 
    // we used `let` in the non-loop examples above and saw the closure 
    // behavior. The difference is just about whether a "new" variable 
    // is created; closures happen regardless.
}
example3()


