/**
 * A Thermometer is just a function that, when called, returns a temperature.
 */
export type Thermometer = () => number

/**
 * A constant mock thermometer: it always returns the same temperature.
 * (Temperatures may be assumed to use the F scale, but it doesn't matter for this example.)
 * @param fixedTemp The temperature to return every time this mock is called.
 * @returns the given constant temperature
 */
export function makeConstantThermometer(fixedTemp: number): Thermometer {
    return () => fixedTemp
    // You could also do this. Same thing:
    // return function() {return fixedTemp}
}
/**
 * An oscillating mock thermometer: it bounces back and forth between two 
 * endpoints over time as it is called. The temperature is either increasing 
 * or decreasing at any given time, and the direction only changes when the temperature
 * hits min or max. For the reported temperature T,
 *   min >= T >= max
 *   T' = T+1 (if T < max and increasing)
 *   T' = T- (if T > min and decreasing)
 * @param startTemp The temperature to report first
 * @param max The maximum temperature to report
 * @param min The minimum temperature to report
 * @returns The current mock temperature
 */
export function makeOscillatingThermometer(
  startTemp: number, max: number, min: number): Thermometer {
  
    /********* FILL IN HERE, replacing the () => 0 **********/
    return () => 0
}
