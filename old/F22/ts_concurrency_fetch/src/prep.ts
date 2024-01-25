/*
  Let's use the NWS API as an _example_ of how to fetch from a web API in 
  TypeScript. (Sprints will still only request from the NWS via the backend,
  not the frontent.)

  Their example grid request:
    https://api.weather.gov/points/39.7456,-97.0892
*/


window.onload = () => {
    const button: HTMLElement | null = document.getElementById("try-button")
    if(button == null) return;
    if(!(button instanceof HTMLButtonElement)) return;
    button.addEventListener('click', printGridInfo)
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
}

