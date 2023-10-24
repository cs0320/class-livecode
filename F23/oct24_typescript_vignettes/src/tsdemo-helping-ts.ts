
/**
 * You're allowed to change the REPLFunction interface, provided you can explain why. 
 * Here's an example from office hours. I'm not saying this change is good or bad; 
 * rather, let's look at some type issues that often come up.
 * 
 * Thanks to the student(s) who volunteered their code for this! 
 */
export interface REPLFunction {
  // Was: (args: Array<string>): Promise<string>;
  (args: Array<string>): Promise<[string[], string[][]]>;
}

// Ignore the "could be async" message.
export const load: REPLFunction = (args: Array<string>) => {
  return fetch(
    "http://localhost:323/loadcsv?filepath=" + args[1] + "&headers=false"
  )
    .then((response: Response) => response.json())
    .then((responseObject) => {
      if (!isSuccessfulLoad(responseObject)) {
        const errorArray = [
          [],
          [["data not successully loaded"]],
        ];
        return errorArray;
      } else {
        const result = responseObject.properties.result;
        const resultArray = [[], [[result]]];
        return resultArray;
      }
    })
  .catch((problem) => console.log(problem)); 
};

////////////////////////////////////////////////////////
// Type Predicates and interfaces for JSON objects
//   Notice how these can be used to narrow in the command function.
////////////////////////////////////////////////////////

interface SuccessfulLoad {
  properties: SuccessfulLoadProperties;
}
interface SuccessfulLoadProperties {
  result: string;
  filepath: string;
}

function isSuccessfulLoad(rjson: any): rjson is SuccessfulLoad {
  if (!("properties" in rjson)) return false;
  if (!isSuccessfulLoadProperties(rjson["properties"])) return false;
  return true;
}

function isSuccessfulLoadProperties(rjson: any): rjson is SuccessfulLoadProperties {
  if (!("result" in rjson)) return false;
  if (!("filepath" in rjson)) return false;
  return true;
}

