
/**
 * This is a *function type* interface. It's what we provided.
 */
export interface REPLFunction {
    (args: Array<string>): Promise<string>;    
}

/**
 * Example taken from office hours. 
 * Thanks to the student who volunteered this!
 */
class Search implements REPLFunction {
  async execute(args: string[]): Promise<string> {
    const response = await fetch(
      "http://localhost:3000/search?searchTerm=" +
        args[0] +
        "&allColumns=" +
        args[1] +
        "&hasHeaders=" +
        args[2] +
        "&inputColumn=" +
        args[3] +
        "&indexOrHeader=" +
        args[4]
    );
    const data = await response.json();
    // console.log(data);
    if (data.responseType == "failure") {
      return data.errorType + ": " + data.errorDesc;
    } else {
      console.log("dataL: ",  data)
      return data.data; // success
      // TODO: return params??
    }
  }
}
export default Search;