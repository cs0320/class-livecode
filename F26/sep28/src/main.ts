import fc from 'fast-check'

async function main(): Promise<void> {
  // https://api.weather.gov/points/41.8268,-71.4029
  const hourlyResponse = await 
    fetch('https://api.weather.gov/gridpoints/BOX/64,75/forecast/hourly')
  const hourlyData = await hourlyResponse.json()
  //console.log(JSON.stringify(hourlyData))
}

main();




/*
  An fc.Arbitrary<T> is a generator of T-typed values.
  Do not confuse this with T itself. 

  Do not confuse this with a Zod schema, either.

*/
//function testExample(): fc.Arbitrary<any>  {
// function testExample(): fc.Arbitrary<any>  {
  // fc.record 
  // fc.chain

  //return fc.boolean()
  //return fc.record({x: fc.integer(), y: fc.integer()})
  
  fc.array(fc.integer())
  
  
}
