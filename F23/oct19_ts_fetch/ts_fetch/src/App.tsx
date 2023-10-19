import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>

      <button onClick={printGridInfo}>Print grid info</button>
      <button onClick={notThreadsDemo}>Not threads demo</button>
    </>
  )
}

export default App


/** 
 * Interface to represent "grid" responses from NWS.
 * 
 * We don't care about other fields; we can just leave them out 
 */
interface NWSGridResponse {
  properties: NWSGridResponseProperties    
}
/**
* Interface to represent "properties" sub-objects in NWS grid responses.
* 
* We don't care about other fields; we can just leave them out 
*/
interface NWSGridResponseProperties {
  gridX: number
  gridY: number
  gridId: string
  forecast: string
}

export function printGridInfo() {    
  const lat: number = 39.7456
  const lon: number = -97.0892
  const url: string = `https://api.weather.gov/points/${lat},${lon}`
  console.log(`Requesting: ${url}`)

  /* 
    Try #1
  */
  // const fetched: Promise<Response> = fetch(url)
  // console.log(fetched)

  /* 
    Try #2
    (Note syntax for giving types for lambda parameters)
  */
    // const fetched: Promise<Response> = fetch(url)
    // fetched.then(
    //   (response: Response) => console.log(response)) 




  /* 
    Try #3
    Note that .json() returns _any_. Not great! 
    We have no idea what's in that Json... but at least we have it.
  */
    // fetch(url)
    // .then((response: Response) => response.json()) 
    // .then((responseObject) => {         
    //      console.log(responseObject) 
    //   })

  /* 
    Try #4
  */
  fetch(url)
     .then((response: Response) => response.json()) 
     // This is no help, because of how `any` works in TS:
     //.then((responseObject: NWSGridResponse) => {
    .then((responseObject) => {
        // Beware, **STILL**:
        // The type system isn't giving us protection here---response.json()
        // produces a Promise<any>, so TS is happy to trust the type declared
        // or inferred. The type annotation on the input variable is no help. 

        // Instead, check dynamically:          
        if(!isNWSGridResponse(responseObject)) { 
          console.log('not a response')
          console.log(responseObject)
        } else {
          // Note: mouseover reports that `responseObject` here is 
          // an NWSGridResponse. Narrowing has happened, via type predicate.
          //  (Without the type predicate, this would still be `any`)
          console.log(responseObject.properties.gridId)
          console.log(responseObject.properties.gridX)
          console.log(responseObject.properties.gridY)
        }
      })
      .catch(problem => console.log(problem))

}

// "Type predicates" for our Json response shapes
// eslint-disable-next-line @typescript-eslint/no-explicit-any
function isNWSGridResponse(rjson: any): rjson is NWSGridResponse {    
  if(!('properties' in rjson)) return false
  if(!isNWSGridResponseProperties(rjson['properties'])) return false
  return true
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
function isNWSGridResponseProperties(rjson: any): rjson is NWSGridResponseProperties {    
  if(!('gridX' in rjson)) return false    
  if(!('gridY' in rjson)) return false    
  if(!('gridId' in rjson)) return false    
  if(!('forecast' in rjson)) return false    
  // we could check for more, but these 4 are all we care about today
  return true
}


/** 
*     Demo of how threads are not the same as promises, along with
*     how to create promises.
* 
*     Technically you can get true concurrency in JavaScript, but 
*     doing so is fairly advanced and we aren't covering it. 
*     (If you're curious, look up, e.g., "web workers".)
*
*     Ordinary JavaScript (and thus TypeScript) has only one thread. 
*     Promises don't create new threads; they enable asynchonous 
*     execution. The computation that runs when the promise resolves 
*     is registered in the callback queue, and so:
*       - it cannot run until the current execution is finished; and
*       - once pulled from the queue and run, nothing else can run
*         until it finishes.
*/ 
function notThreadsDemo() {
  console.log('starting demo')

  // The promise constructor takes a function of two arguments:
  //   - "resolve", which resolves the promise when called
  //   - "reject", which rejects the promise when called
  const promise = new Promise((resolve, reject) => {    
    // This performs the slow computation and then resolves 
    // the promise with the returned value. But the computation
    // is not delayed---so everything else waits until this finishes.
    resolve(slowComputationFib(10, 'from inside promise and resolve'))
  })
  .then(value => {
    console.log(`promise resolved, in 'then' callback. value=${value}`)
  })

  console.log(`promise created. promise=${promise}`) 

  slowComputationFib(11, 'outside promise')

  // If these were separate threads, we'd probably expect to see 
  //   their execution interleaved, but we don't!

  // If we want to delay the execution of the promise function itself,
  // we can use setTimeout to queue it:
  new Promise((resolve, reject) => {
    console.log('inside promise (2nd) function, but outside timeout')
    setTimeout( () => {      
      resolve(slowComputationFib(12, 'from inside promise (2nd) and resolve'))
    }) 
  })
  .then(value => {
    console.log(`promise (2nd) resolved, in 'then' callback. value=${value}`)
  })

  console.log('main function done')

}

// Fibonacci without caching
function slowComputationFib(n: number, label: string): number {
  if(n % 2 === 0)  // reduce spam
    console.log(`${label}: ${n}`)
  if(n <= 1) return 1
  return slowComputationFib(n-1, label) + slowComputationFib(n-2, label)
}

