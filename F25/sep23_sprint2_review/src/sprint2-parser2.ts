/*
  IN PROGRESS CSV PARSER (Example 2)
*/


import * as fs from "fs";
import * as readline from "readline";
import { Readable } from "stream";
import z, { ZodSafeParseError, ZodSafeParseSuccess } from 'zod'

/** A wrapper object for the result of parsing a single row. 
 *  @field headers a string[] containing header values, or undefined if no headers
 *  @field result a Zod success or failure (if a schema is provided), a string[] otherwise.
*/
export type CSVParseResult<T> = { 
    headers: undefined | string[], 
    result: (ZodSafeParseSuccess<T> | ZodSafeParseError<T> | string[]) 
}

/** CSV parser function. Accepts:
 *  @param data a Readable object to read CSV data from
 *  @param hasHeaders a boolean true if the first row is a header row
 *  @param givenSchema an optional Zod schema for validation and translation 
 * 
 *  @template T the type of the Zod schema, if any. (NOT the type it returns)
  */
export async function* parseCSV<T extends z.ZodType>(
  data: Readable, hasHeaders: boolean = false, givenSchema?: T): 
    AsyncGenerator<CSVParseResult<z.output<T>>, null> {
      
  // Note: the z.output<T> above is the type that the schema produces.
  // This is somewhat different from the notes, but makes some things 
  // easier later on. 

  const rl = readline.createInterface({
    input: data,
    crlfDelay: Infinity, // handle different line endings
  });

  // Separate the rows, hopefully even when newlines are present in fields
  const rows = assembleRows(rl)

  let headers = undefined
  for await (const row of rows) {
    // Not-entirely-perfect regular expression
    const matches = row.matchAll(/(?:,|\n|^)("(?:(?:"")*[^"]*)*"|[^",\n]*|(?:\n|$))/g)

    const fields = Array.from(matches).map((arr) => postProcess(arr[0]))
    // console.log(row)
    // console.log(fields)
    if(hasHeaders && headers === undefined) {
        headers = fields
        continue
    }
    if(givenSchema !== undefined) {
        // Assigning an intermediate variable makes the type easier to see, here. 
        const result = givenSchema.safeParse(fields)
        yield {result: result, headers: headers} 
    }
    else 
        yield {result: fields, headers: headers}
  }
  return null
}

/** Opens a file and returns a Readable for its contents.  */
export function readableFromFilename(path: string): Readable {
    return fs.createReadStream(path);
}

/** Produces a Readable from an input string */
export function readableFromString(data: string): Readable {
    return Readable.from(data)
}

/** Helper function to build a final list of rows for easy testing */
export async function drainFiniteGenerator<T>(gen: AsyncIterable<T>) {
  const results = []
  for await (const row of gen) {
    results.push(row)
  }
  return results
} 

/** Helper to extract a row. All we do is look line-by-line, and if the data ends
 *  within a field, we read the next line, and so on. 
 * 
 *  This function and countUnescapedQuotes were written together with Copilot. */
export async function* assembleRows(rl: readline.Interface): AsyncGenerator<string, void> {
  let recordBuffer = "";
  let isFirstLine = true;

  for await (const line of rl) {
    if (isFirstLine) {
      recordBuffer = line;
      isFirstLine = false;
    } else {
      recordBuffer += "\n" + line;
    }

    // Count unescaped quotes in the record buffer
    const quoteCount = countUnescapedQuotes(recordBuffer);

    // If we have an even number of quotes, the record is complete
    if (quoteCount % 2 === 0) {
      yield recordBuffer;
      recordBuffer = "";
      isFirstLine = true;
    }
  }

  // If there's remaining data in the buffer (malformed CSV), yield it
  if (recordBuffer !== "") {
    yield recordBuffer;
  }
}

export function countUnescapedQuotes(text: string): number {
  let count = 0;
  let i = 0;
  
  while (i < text.length) {
    if (text[i] === '"') {
      // Check if this quote is escaped (followed by another quote)
      if (i + 1 < text.length && text[i + 1] === '"') {
        // This is an escaped quote, skip both characters
        i += 2;
      } else {
        // This is an unescaped quote
        count++;
        i++;
      }
    } else {
      i++;
    }
  }
  
  return count;
}

/** Regular expressions match; we'll trim out the commas ourselves
 * 
 *   WHAT MIGHT BE MISSING HERE?
 * 
 */
function postProcess(arg: string): string {
  let result = arg
  if(result.length > 0 && result.charAt(0) === ',')
    result = result.slice(1)
  return result
}
  