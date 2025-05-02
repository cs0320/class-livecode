import { NWSGridResponse, NWSGridResponseBranded, zNWSGridResponse, zNWSGridResponseBranded } from './validation'


export function ZodDemo() {
    /* Try #5: let's use a schema validation library to help us */

    return (<button onClick={ () => {
        const lat: number = 39.7456
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

            // But now I have some questions about the inferred interface. 
            const test: NWSGridResponse = {properties: {
                gridX: 10,
                gridY: 10, 
                gridId: 'Foo',
                // this doesn't conform to the schema, but it works -- because the URL 
                // requirement isn't expressed in the static type system. 
                forecast: 'dfdfdfd' 
            }}
            console.log(test)
        })
        // This deals with problems at the fetch level. 
        .catch(problem => console.log(problem))

        // Takeaway? The schema is _more fine-grained_ than the static type can express. 
        // So the schema can over-approximate itself to infer a typescript type!

        // ...Except we _can_ use branding to help enforce that data only ever 
        // arrives pre-approved by Zod. Let's try that again. 

        fetch(url).then((response: Response) => response.json())
                  .then(responseJson => {
                    const parsedObject = zNWSGridResponseBranded.safeParse(responseJson)
                    if(parsedObject.success) {
                        const responseObject: NWSGridResponseBranded = parsedObject.data

                        // The above works the same as before. But now if I try to "skip" Zod...
                        // ...I get a confusing-ish error, but still an error (about the missing
                        // brand in the object)
                        /*const test: NWSGridResponseBranded = {properties: {
                            gridX: 10,
                            gridY: 10, 
                            gridId: 'Foo',
                            forecast: 'dfdfdfd',
                        }}*/
                        // This means we can enforce this at compile time, assuming
                        // we trust Zod to parse only for the correct shape of data.

                    }
                  } )

    }}> Zod Demo </button>)

}