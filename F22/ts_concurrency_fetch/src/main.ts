/*
  Let's use the NWS API as an _example_ of how to fetch from a web API in 
  TypeScript. (Sprints will still only request from the NWS via the backend,
  not the frontent.)

  Their example grid request:
    https://api.weather.gov/points/39.7456,-97.0892
*/

// For demo added Oct 26
// Note annoying .js addition
import { doSomethingWithForecastURL } from './bewareAsync.js'


window.onload = () => {
    const button: HTMLElement | null = document.getElementById("try-button")
    if(button == null) return;
    if(!(button instanceof HTMLButtonElement)) return;
    button.addEventListener('click', printGridInfo)

    doSomethingWithForecastURL()
}


/* We don't care about other fields; we can just leave them out */
interface NWSGridResponse {
    properties: NWSGridResponseProperties    
}
interface NWSGridResponseProperties {
    gridX: number
    gridY: number
    gridId: string
    forecast: string
}

function dynamicCheck_NWSGridResponse(rjson: any): boolean {    
    if(!('properties' in rjson)) return false
    if(!dynamicCheck_NWSGridResponseProperties(rjson['properties'])) return false
    return true
}
function dynamicCheck_NWSGridResponseProperties(rjson: any): boolean {    
    if(!('gridX' in rjson)) return false    
    if(!('gridY' in rjson)) return false    
    if(!('gridId' in rjson)) return false    
    if(!('forecast' in rjson)) return false    
    return true
}

export function printGridInfo() {    
    const lat: number = 39.7456
    const lon: number = -97.0892
    const url: string = `https://api.weather.gov/points/${lat},${lon}`
    console.log(`Requesting: ${url}`)

    /* 
      Try #1
    */
    const json = fetch(`https://api.weather.gov/points/${lat},${lon}`)
    console.log(json)

    /* 
      Try #2
    */
    fetch(url)
       .then(response => console.log(response)) 

    /* 
      Try #3
    */
      fetch(url)
      .then(response => response.json()) 
      .then(responseObject => {         
           console.log(responseObject)         
       })       

    /* 
      Try #4
    */
    fetch(url)
       .then(response => response.json()) 
       .then((responseObject: NWSGridResponse) => {
          // Beware:
          // The type system isn't giving us great protection here---response.json()
          // produces a Promise<any>, so TS is happy to trust the type declared or inferred.
          // Yes, even the type annotation on the input variable is no help. 
          // Instead, check dynamically:          
          if(!dynamicCheck_NWSGridResponse(responseObject)) { 
            console.log('not a response')
            console.log(responseObject)
          } else {
            console.log(responseObject.properties.gridId)
            console.log(responseObject.properties.gridX)
            console.log(responseObject.properties.gridY)
          }
        })
        .catch(problem => console.log(problem))

    /* Added Oct 21 to resolve a question about 'threads'. 
    
       Technically you can get true concurrency in JavaScript, but 
       doing so is fairly advanced and we aren't covering it. 
       (If you're curious, look up "web workers".)

       Ordinary JavaScript (and thus TypeScript) has only one thread. 
       Promises don't create new threads; they enable asynchonous 
       execution. The computation queued in the promise is part of 
       the callback queue, and so:
         - it cannot run until the current execution is finished; and
         - once pulled from the queue and run, nothing else can run
           until it finishes.
    */ 
    notThreadsDemo()

}

// See call site for comment re: purpose
function notThreadsDemo() {
  console.log('starting demo')
  let value = 0

  // This performs the slow computation and then resolves 
  // the promise with the resulting value. Thus, nothing after
  // this block can execute until it's finished. It isn't 
  // creating a new thread.
  let promise = new Promise((resolve, reject) => {
    resolve(slowComputationFib(10, 'from promise'))
  })
  // ^ We're also throwing out the result  
  console.log(promise) // Promise { <state>: "fulfilled", <value>: 89 }

  value = 5
  console.log(`value=${value}`)

  slowComputationFib(10, 'outside promise')
  // ^ throwing out the result of this, also
  // If these were separate threads, we'd probably expect to see 
  //   their execution interleaved, but we don't!
}

// Fibonacci without caching
function slowComputationFib(n: number, label: string): number {
  console.log(`${label}: ${n}`)
  if(n <= 1) return 1
  return slowComputationFib(n-1, label) + slowComputationFib(n-2, label)
}
