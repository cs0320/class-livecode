import z from 'zod'

/* Starter version */

export const providence_lat = 41.8240;
export const providence_lon = -71.4128;

interface TemperatureResult {
    success: boolean,
    error?: 'fetchPoints' | 'fetchForecast' | 'validatePoints' | 'validateForecast',
    degrees?: number,
    scale?: 'F' | 'C'
}

const pointsResponseSchema = z.object({
    properties: z.object({forecast: z.string()})})
const timePeriodSchema = z.object({
    temperature: z.number(), 
    temperatureUnit: z.literal('C').or(z.literal('F'))})
const forecastResponseSchema = z.object({
    properties: z.object({periods: z.array(timePeriodSchema)})})

/** Make an API call to the National Weather Service. I'm leaving out 
 *  Zod / careful validation here, because that's not the point of this 
 *  example. The point is that this function _calls a real web api_.
 */
async function getRealTemperature(lat: number, lon: number): Promise<TemperatureResult> {
    // Step 1: ask the NWS for the grid point data about this location
    const result: Response = await fetch(`https://api.weather.gov/points/${lat},${lon}`)
    if (!result.ok) return {success: false, error: 'fetchPoints'}
    const json = await result.json();
    const validatedJson = await pointsResponseSchema.safeParse(json)
    if(!validatedJson.success) return {success: false, error: 'validatePoints'}
    
    // Step 2: ask the NWS for a week-long forecast for this location
    const forecastUrl = validatedJson.data.properties.forecast;
    const forecastResult = await fetch(forecastUrl);
    if (!forecastResult.ok) return {success: false, error: 'fetchForecast'}
    const forecastJSON = await forecastResult.json()
    const validatedForecastJSON = await forecastResponseSchema.safeParse(forecastJSON)
    if(!validatedForecastJSON.success) return {success: false, error: 'validateForecast'}
    
    // Step 3: report the temperature from the forecast
    //   (This isn't quite right; it returns the temp for the _next time period_,
    //    not the current temperature value.)
    //    See: https://github.com/tnelson/pvd-weather/blob/main/src/App.tsx
    const timePeriodsData = validatedForecastJSON.data.properties.periods
    return {success: true, 
            degrees: timePeriodsData[0].temperature, 
            scale: timePeriodsData[0].temperatureUnit} 
}

/** Let's do something with that temperature! */
async function weatherReportProvidence() {
    const report = await getRealTemperature(providence_lat, providence_lon)
    console.log(`It's ${report.degrees} ${report.scale} out!`)
}