
import { PersonRowSchema, SafePerson, SafePersonRowSchema } from "../src/schemas";
import { drainFiniteGenerator, parseCSV, readableFromFilename, readableFromString } from "../src/sprint2-parser2";
import * as path from "path";

const PEOPLE_CSV_PATH = path.join(__dirname, "../data/people.csv");

/*******************/
/* WITHOUT HEADERS */
/*******************/

describe('Header/schema behavior: WITH headers; file', () => {
 test("mixed whitespace handling", async () => {
    const csvData = 'normal, "  quoted with spaces  " ,  unquoted  \nvalue,"  preserved  ",  trimmed  ';
    const results = await drainFiniteGenerator(parseCSV(readableFromString(csvData), true));

    expect(results).toHaveLength(1);
    expect(results[0]).toMatchObject({
      headers: ["normal", "  quoted with spaces  ", "  unquoted  "],
      result: ["value", "  preserved  ", "  trimmed  "]
    });
  });

  test("single field records", async () => {
    const csvData = 'header\nvalue1\n"quoted value"\n"value with, comma"\n"value with\nnewline"';
    const results = await drainFiniteGenerator(parseCSV(readableFromString(csvData), true));

    expect(results).toHaveLength(4);
    expect(results[0]).toMatchObject({
      headers: ["header"],
      result: ["value1"]
    });
    expect(results[1]).toMatchObject({
      headers: ["header"],
      result: ["quoted value"]
    });
    expect(results[2]).toMatchObject({
      headers: ["header"],
      result: ["value with, comma"]
    });
    expect(results[3]).toMatchObject({
      headers: ["header"],
      result: ["value with\nnewline"]
    });
  });

  test("trailing commas", async () => {
    const csvData = 'a,b,c,\nvalue1,value2,value3,\n"quoted",,,"trailing"';
    const results = await drainFiniteGenerator(parseCSV(readableFromString(csvData), true));

    expect(results).toHaveLength(2);
    expect(results[0]).toMatchObject({
      headers: ["a", "b", "c", ""],
      result: ["value1", "value2", "value3", ""]
    });
    expect(results[1]).toMatchObject({
      headers: ["a", "b", "c", ""],
      result: ["quoted", "", "", "trailing"]
    });
  });

  test("leading commas", async () => {
    const csvData = ',a,b,c\n,value1,value2,value3\n,"quoted",,"end"';
    const results = await drainFiniteGenerator(parseCSV(readableFromString(csvData), true));
    
    console.log(results)
    expect(results).toHaveLength(2);
    
    expect(results[0]).toMatchObject({
      headers: ["", "a", "b", "c"],
      result: ["", "value1", "value2", "value3"]
    });
    expect(results[1]).toMatchObject({
      headers: ["", "a", "b", "c"],
      result: ["", "quoted", "", "end"]
    });
  });

  test("malformed CSV with unmatched quotes", async () => {
    // This tests the parser's behavior with malformed CSV where quotes aren't properly closed
    const csvData = 'normal,"unclosed quote field,another\n"properly quoted","another field"';
    const results = await drainFiniteGenerator(parseCSV(readableFromString(csvData), false));

    // The parser should handle this gracefully, though the exact behavior depends on implementation
    expect(results).toHaveLength(1);
    // The malformed record should be yielded as-is when the buffer is drained
    expect(results[0].result).toEqual(['normal,"unclosed quote field,another\n"properly quoted"', "another field"]);
  });
});

describe('Header/schema behavior: WITHOUT headers; file', () => {
  test("parseCSV: no headers, no schema", async () => {
    const results = await drainFiniteGenerator(parseCSV(readableFromFilename(PEOPLE_CSV_PATH)))

    expect(results).toHaveLength(5);
    expect(results[0]).toMatchObject({
      headers: undefined, 
      result: ["name", "age"]
    })
    expect(results[1]).toMatchObject({
      headers: undefined, 
      result: ["Alice", "23"]
    })
    expect(results[2]).toMatchObject({
      headers: undefined, 
      result: ["Bob", "thirty"]
    })
    expect(results[3]).toMatchObject({
      headers: undefined, 
      result: ["Charlie", "25"]
    })
    expect(results[4]).toMatchObject({
      headers: undefined, 
      result: ["Nim", "22"]
    })
  });

  test("parseCSV: no headers, schema (unbranded)", async () => {
    const results = await drainFiniteGenerator(
      parseCSV(readableFromFilename(PEOPLE_CSV_PATH), false, PersonRowSchema) 
    )
    expect(results).toHaveLength(5);
    expect(results[0]).toMatchObject({
      headers: undefined, 
      result: {success: false}
    })
    expect(results[1]).toMatchObject({
      headers: undefined, 
      result: {success: true, data: {name: "Alice", age: 23}}
    })
    expect(results[2]).toMatchObject({
      headers: undefined, 
      result: {success: false}
    })
    expect(results[3]).toMatchObject({
      headers: undefined, 
      result: {success: true, data: {name: "Charlie", age: 25}}
    })
    expect(results[4]).toMatchObject({
      headers: undefined, 
      result: {success: true, data: {name: "Nim", age: 22}}
    })
  })

  test("parseCSV: no headers, schema (branded); check only brand", async () => {
    const results = await drainFiniteGenerator(
      parseCSV(readableFromFilename(PEOPLE_CSV_PATH), false, SafePersonRowSchema) 
    )
    expect(results).toHaveLength(5);
    
    // Should only need to check this once, assuming the types are set up properly. 
    // This really only matters pre-runtime.
    if("success" in results[0].result && results[0].result.data != undefined) {
      const checkBrand: SafePerson = (results[0]).result.data
    }

    // The actual values are already checked in 
    //   "parseCSV: no headers, schema (unbranded)"
  })
})