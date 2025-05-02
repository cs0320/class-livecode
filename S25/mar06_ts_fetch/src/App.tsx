import './App.css'
import { ZodDemo } from './ZodDemo'


/**
 * Using a starter Vite app as a platform for this demo. We won't be 
 * looking at it much, just clicking buttons. 
 * 
 */
function App() {
  return (
    <>
      <p>Click buttons to activate each demo, and see the console for output.</p>
      <button onClick={printGridInfo}>Print grid info</button>
      <button onClick={notThreadsDemo}>Not threads demo</button>
      <button onClick={resolvePromiseDemo}>Promise resolution delay?</button>
      <hr/>
      <ZodDemo/>
    </>
  )
}

export default App


/** 
 * Interface to represent "grid" responses from NWS.
 * 
 * We don't care about other fields; we can just leave them out! 
 * We'll use this interface for type-checking, but we don't get 
 * it applied automatically -- see below.
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
  const url: string = 
    `https://api.weather.gov/points/${lat},${lon}`
  console.log(`Requesting: ${url}`)

  /* 
    Try #1
  */
  const fetched: Promise<Response> = fetch(url)
  console.log(fetched)

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
  // fetch(url)
  //    .then((response: Response) => response.json()) 
  //    // This is no help, because of how `any` works in TS:
  //   //.then((responseObject: NWSGridResponse) => {
  //   .then((responseObject) => {
  //       // Beware, **STILL**:
  //       // The type system isn't giving us protection here---response.json()
  //       // produces a Promise<any>, so TS is happy to trust the type declared
  //       // or inferred. The type annotation on the input variable is no help. 

  //       // Instead, check dynamically:          
  //       if(!isNWSGridResponse(responseObject)) { 
  //         console.log('not a response')
  //         console.log(responseObject)
  //       } else {
  //         // Note: mouseover reports that `responseObject` here is 
  //         // an NWSGridResponse. Narrowing has happened, via type predicate.
  //         //  (Without the type predicate, this would still be `any`)
  //         console.log(responseObject.properties.gridId)
  //         console.log(responseObject.properties.gridX)
  //         console.log(responseObject.properties.gridY)
  //       }
  //     })
  //     .catch(problem => console.log(problem))

}

// "Type predicates" for our Json response shapes
// eslint-disable-next-line @typescript-eslint/no-explicit-any
function isNWSGridResponse(rjson: any): rjson is NWSGridResponse {    
  if(!('properties' in rjson)) return false
  if(!isNWSGridResponseProperties(rjson['properties'])) 
    return false
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

// Fibonacci without caching
function slowComputationFib(n: number, label: string|undefined): number {
  if(label) console.log(label)
  if(n <= 1) return 1
  return slowComputationFib(n-1, undefined) + slowComputationFib(n-2, undefined)
}

function notThreadsDemo() {
  console.log('starting demo')

  // Let's go a level deeper. We'll use the Promise constructor directly.

  // The promise constructor takes a function of two arguments:
  //   - "resolve", which resolves the promise when called
  //   - "reject", which rejects the promise when called
  const promise = new Promise((resolve, reject) => { 
    console.log('inside body of function passed to Promise constructor')   
    // This performs the slow computation and then resolves 
    // the promise with the returned value. But the computation
    // is not delayed---so everything else waits until this finishes.
    resolve(slowComputationFib(40, 'from inside promise and resolve'))
  })
  .then(value => {
    console.log(`promise resolved, in 'then' callback. value=${value}`)
  })

  console.log(`promise created, value: promise=${promise}`) 

  //slowComputationFib(11, 'outside promise')

  // If these were separate threads, we'd probably expect to see 
  //   their execution interleaved, but we don't! The computation
  // is done right there...

  // If we want to delay the execution of the promise function itself,
  // we can use setTimeout to queue it (although this isn't how, e.g., 
  // fetch works! but it suffices for the demo)
  // new Promise((resolve, reject) => {
  //   console.log('inside promise (2nd) function, but outside timeout')
  //   setTimeout( () => {      
  //     resolve(slowComputationFib(12, 'from inside promise (2nd) and resolve'))
  //   }) 
  // })
  // .then(value => {
  //   console.log(`promise (2nd) resolved, in 'then' callback. value=${value}`)
  // })

  // console.log('main function done')

}

/**
 * Ok, but what about the resolution of a promise? Does the promise itself 
 * need to wait before it's resolved? The demo above might make us think 
 * "yes", but since the *browser itself* has multiple threads, it's worth
 * experimenting a bit to make sure we really understand. 
 */
function resolvePromiseDemo() {
  const lat: number = 39.7456
  const lon: number = -97.0892
  const url: string = 
    `https://api.weather.gov/points/${lat},${lon}`

  const fetched: Promise<Response> = fetch(url)
  // Instead of printing the promise immediately, do some work and then
  // print it. Maybe that will cause enough delay for the network 
  // request + response to go through in the meantime?
  const timestamp = Date.now()
  // Adjust the number lower if it's taking too long on your computer / browser.
  slowComputationFib(42, `causing a delay (timestamp=${timestamp})...`)
  console.log(`${Date.now()-timestamp} ms delay`)

  // Notice that we can't really get at the data. There's no field for the result or status.
  // But (at least in Safari/Firefox), the promise is still pending at this point, even seconds later.
  console.log(fetched)

}