/*
  Async/await can make your asynch code look more "normal", which is
  great, but you're always dealing (invisibly) with promises. Demo:
*/

export interface NWSPointsResponseProperties {
    gridId: string,
    gridX: string,
    gridY: string,
    forecast: string
    // .. and many more, but we don't need those other fields
}

export interface NWSPointsResponse {
    properties: NWSPointsResponseProperties
}

// Async functions *APPEAR* to return a normal value, but don't be fooled.
// their return type is Promise; the syntax just auto-wraps the return.
async function getNWSForecastURL(): Promise<string> {
  // This is a raw API response
  const response: Response = await fetch('https://api.weather.gov/points/41.8268,-71.4029')
  // This is of type "any" by default. Danger! Suggest adding a dynamic check in a full example
  const responseJson: NWSPointsResponse = await response.json()
  const url: string = responseJson.properties.forecast
  // Since this is an async function, the url string will be *auto-wrapped* in a promise
  return url 
}

// But now, if we want to actually _use_ the result of getNWSForecastURL, we need to
// treat that result like a promise. We can either 'await' it (in the context of an
// _another_ async function!) or provide a callback function to call on resolution:

export function doSomethingWithForecastURL(): void {
  const result_promise: Promise<void> = getNWSForecastURL().then((url: string) => console.log(url))
  console.log(result_promise) // still a promise
} 

// Some versions allow the use of 'await' directly on the top level, like this:
// const result_url = await getNWSForecastURL()
// but that's not *generally* useful, and we're not working in one of those versions.

// You _always_ eventually have to admit you've got a promise. But you can put anything
// you want in those callbacks. E.g., if you had a setURLString setter from React, 
// you could do things like 
// getNWSForecastURL().then((url: string) => setURLString(url))
// That would update the React-manage state and trigger a render _when new data is available_.