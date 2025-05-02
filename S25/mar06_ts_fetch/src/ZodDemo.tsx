import { NWSGridResponse, zNWSGridResponse } from './validation'


export function ZodDemo() {
    /* Try #5: let's use a schema validation library to help us */

    return (<button onClick={ () => {
        const lat: number = 0 // 39.7456
        const lon: number = -97.0892
        const url: string = 
            `https://api.weather.gov/points/${lat},${lon}`
        console.log(`Requesting: ${url}`)

        fetch(url)
        .then((response: Response) => response.json()) 
        .then((responseJson) => {
            const parsedObject = zNWSGridResponse.safeParse(responseJson)
        
            // Notice: we can't do this yet.
            // const test: NWSGridResponse = parsedObject.data

            if(parsedObject.success) {
                // Now we can!
                // The NWSGridResponse here is the TS interface _inferred_ by Zod from our schema.
                // We're not using the interface we defined manually in App.tsx.
                const responseObject: NWSGridResponse = parsedObject.data
                console.log(responseObject.properties.gridId)
                console.log(responseObject.properties.gridX)
                console.log(responseObject.properties.gridY)
            } else {
                // If the JSON does not conform to the schema, Zod gives us an error object. 
                // The object contains useful data, like the type expected and where in the 
                // recursive descent things went wrong. This deals with problems at the 
                // JSON-parsing level (we got a response from fetch, no problems there).
                console.log('Error!')
                console.log(parsedObject.error)
            }
        })
        // This deals with problems at the fetch level. 
        .catch(problem => console.log(problem))
    }}> Zod Demo </button>)

}