/*
 IN PROGRESS CSV PARSER (Example 1)
*/

import * as fs from "fs";
import * as readline from "readline";
import { z, ZodIssue } from "zod";

/**
 * Generator-based CSV parser that yields one row at a time.
 * This allows processing very large files without loading all rows into memory at once.
 *
 * @param path - The path to the CSV file to be parsed.
 * @param options - An object that contains parsing options. Fields:
 *   headers: is the first row a header row?
 *   schema: (optional) a Zod schema for validation or transformation.
 * @yields An object containing a single row's data. Fields:
 *   header: the header row (a string[])
 *   value: the parsed and validated data
 *   error: error message if no value could be produced

 */
export async function* parseCSVGenerator(
  path: string,
  options: { headers: boolean; schema?: z.ZodSchema<any> }
): AsyncGenerator<{value: any; error?: string; header?: string[];
                   rowIndex?: number; fields?: Array<{
                     fieldName?: string;
                     columnIndex?: number;
                     zodIssues: ZodIssue[];
                     issues: Array<{
                     code: string;
                     message: string;
                     path: (string | number)[];
                     expected?: unknown;
                     received?: unknown;
                     validation?: unknown;}>;}>;}> {
  const fileStream = fs.createReadStream(path);
  const rl = readline.createInterface({
    input: fileStream,
    crlfDelay: Infinity, // Handle different line endings (e.g., Windows, Unix)
  });

  let headers: string[] | undefined = undefined;
  let rowIndex = 0; 

  for await (const line of rl) { 
    const values = line.split(",").map((v) => v.trim()); 

   // Always capture the first row as headers
    if (!headers && options.headers) {
      headers = values;
      continue; 
    }

    rowIndex++;

    // Map the row to an object using headers, or keep it as an array if no headers are present
    const row = headers
      ? Object.fromEntries(headers.map((h, i) => [h, values[i]]))
      : values;

    // Validate the row against the provided schema (if any)
    if (options.schema) {
      const result = options.schema.safeParse(row);
      if (result.success) {
        yield { value: result.data, header: headers }; // Yield validated row
      } else {
        // Group Zod issues by field (first element in issue.path)
        const issuesByField = new Map<string | number, ZodIssue[]>();
        for (const issue of result.error.issues) {
          const key = (issue.path?.[0] ?? "") as string | number;
          const list = issuesByField.get(key) ?? [];
          list.push(issue);
          issuesByField.set(key, list);
        }

        const fields = Array.from(issuesByField.entries()).map(([key, zodIssues]) => {
          let fieldName: string | undefined = undefined;
          let columnIndex: number | undefined = undefined;

          if (typeof key === "string" && key.length > 0) {
            fieldName = key;
            columnIndex = Array.isArray(headers) ? headers.indexOf(key) : undefined;
          } else if (typeof key === "number") {
            columnIndex = key;
          }

          const issues = zodIssues.map((iss) => ({
            code: iss.code as unknown as string,
            message: iss.message,
            path: iss.path as (string | number)[],
            expected: (iss as any).expected,
            received: (iss as any).received,
            validation: (iss as any).validation,
          }));

          return { fieldName, columnIndex, zodIssues, issues };
        });

        // Yield row with validation error details (backward-compatible: keep `error` and `header`)
        yield { value: row, error: result.error.message, header: headers, rowIndex, fields };
      }
    } else {
      yield { value: row, header: headers };
    }
  }
}