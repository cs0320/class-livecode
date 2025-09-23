
/**
 *  0320 Koan: "How do you know you're not a brain in a jar?"
 * 
 *  This one is messy at the moment, sorry. But ask yourself that 
 *  question. Mocking is all about asking that question for a function,
 *  class, or method that you write. 
 */


export const providence_lat = 41.8240;
export const providence_lon = -71.4128;

interface TemperatureResult {
    success: boolean,
    degrees?: number,
    scale?: 'F' | 'C'
}

/** Make an API call to the National Weather Service. I'm leaving out 
 *  Zod / careful validation here, because that's not the point of this 
 *  example. The point is that this function _calls a real web api_.
 */
async function getRealTemperature(lat: number, lon: number): Promise<TemperatureResult> {
    // As a demo, I'm using then/catch here instead of await.
    return await fetch(`https://api.weather.gov/points/${lat},${lon}`)
        .then(res => {
            if (!res.ok) return {success: false}
            return res.json();
        })
        .then(data => {
            const forecastUrl = data.properties.forecast;
            return fetch(forecastUrl);
        })
        .then(res => {
            if (!res.ok) return {success: false}
            return res.json();
        })
        .then(data => {
            const timePeriodsData = data?.properties?.periods;
            // This isn't actually right; it'll return the temp
            // for the _next time period_, not the current temp.
            // See: https://github.com/tnelson/pvd-weather/blob/main/src/App.tsx
            //console.log(`${timePeriodsData[0].temperature}`)
            return {success: true, 
                    degrees: timePeriodsData[0].temperature, 
                    scale: timePeriodsData[0].temperatureUnit} 
        })
        .catch(err => {
            return {success: false}
        });
}

/** But what if we want to do something with that temperature? */
async function weatherReportProvidence(source: WeatherSource) {
    const report = await source(providence_lat, providence_lon)
    console.log(`It's ${report.degrees} ${report.scale} out!`)
}

/** Any function with this shape works as a weather source. */
type WeatherSource = (latarg: number, lonarg: number) => Promise<TemperatureResult>

/** Build a "temperature source" that never actually asks the NWS, and just
 *  returns the provided constant value. */
function buildTemperatureMock(temp: number, scale: 'F' | 'C'): WeatherSource {
    // This builder returns an async function indistinguishable from the real source.
    // (Note: we need the explicit return type here to get TypeScript to infer...)
    return async function(lat: number, lon: number): Promise<TemperatureResult> {
        return {success: true, degrees: temp, scale: scale}
    }
}

const alwaysSunny = buildTemperatureMock(72, 'F')

/** Note: don't try this outside of an async function.*/
async function demo() {
    // weatherReportProvidence can't tell the difference.
    console.log(`real data:`)
    await weatherReportProvidence(getRealTemperature)
    console.log(`mock data:`)
    // for prototyping/testing
    await weatherReportProvidence(alwaysSunny)
}
demo()