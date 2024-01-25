/*
  Example of using Currying/dependency-injection to mock 
  making real API calls for testing.

  The function arguments/types don't perfectly match the sprint, but
  this should serve as a proof-of-concept. 
*/

type DatasourceFunction = (url: string) => Promise<any>
type CommandFunction = (args: string[]) => Promise<any>


/**
 * At registration time, this function gets called, and returns the 
 * command-function to register for the 'weather' command.
 * 
 * Why do it this way? A full mock wouldn't test all the non-API 
 * logic. Instead, we'll inject the datasource dependency: either 
 * the real API query function, or a mocked one. 
 * @param args 
 */
export function make_weather_function(datasource: DatasourceFunction): CommandFunction {
    return (args: string[]) => { 
        // weather expects <some number of arguments>; error handling could be improved
        if(args.length < 3) 
            return Promise.resolve({error: 'insufficient arguments'})
        return datasource(`http://web.site.com/query?${args[1]},${args[2]}`)
    }
}

// To register, we'd call something like
// registerCommand(make_weather_function(real_api_query))
// or
// registercommand(make_weather_function(fake_api_query_responder({...constant response...})))

/**
 * Executes a real API query vs. the url given
 * @param url the URL to make the API request to
 * @returns the result of the API query
 */
function real_api_query(url: string): Promise<any> {
    return fetch(url)
      .then((response: Response) => response.json())
      .catch(problem => console.log(problem))
}

/**
 * When called, returns a function of the same type signature as 
 * `real_api_query`, to be used as a source of mocked API responses.
 * @param value The value to always return when the mocked function is called
 * @returns the mock function
 */
function fake_api_query_responder(value: any): DatasourceFunction {
    return (url: string) => Promise.resolve(value)
}
