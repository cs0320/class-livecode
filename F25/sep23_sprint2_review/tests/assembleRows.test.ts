import * as readline from "readline";
import { readableFromString, assembleRows } from "../src/sprint2-parser2";

describe("assembleRows", () => {
  // Helper function to test assembleRows
  async function testAssembleRows(input: string): Promise<string[]> {
    const readable = readableFromString(input);
    const rl = readline.createInterface({
      input: readable,
      crlfDelay: Infinity,
    });
    
    const generator = assembleRows(rl);
    const results: string[] = [];
    for await (const row of generator) {
      results.push(row);
    }
    return results;
  }

  describe("Case A: Self-contained records", () => {
    test("single simple record", async () => {
      const input = "name,age,city";
      const result = await testAssembleRows(input);
      expect(result).toEqual(["name,age,city"]);
    });

    test("multiple simple records", async () => {
      const input = "name,age,city\nJohn,25,NYC\nJane,30,LA";
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,age,city",
        "John,25,NYC", 
        "Jane,30,LA"
      ]);
    });

    test("record with quoted fields (no newlines)", async () => {
      const input = 'name,description\n"John","A simple person"';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,description",
        '"John","A simple person"'
      ]);
    });
  });

  describe("Case B & C: Records spanning multiple lines", () => {
    test("quoted field with newline", async () => {
      const input = 'name,description\n"John","This is a long\ndescription"\n"Jane","Short desc"';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,description",
        '"John","This is a long\ndescription"',
        '"Jane","Short desc"'
      ]);
    });

    test("multiple newlines within quoted field", async () => {
      const input = 'name,bio\n"Alice","Line 1\nLine 2\nLine 3"';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,bio",
        '"Alice","Line 1\nLine 2\nLine 3"'
      ]);
    });

    test("newline within quoted field", async () => {
      const input = 'name,notes\n"Bob","Note with\nnewline inside"';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,notes",
        '"Bob","Note with\nnewline inside"'
      ]);
    });
  });

  describe("Case D: Lines entirely within fields", () => {
    test("middle lines of multi-line field", async () => {
      const input = 'name,story\n"Emma","Once upon a time\nthere was a person\nwho lived happily"';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,story",
        '"Emma","Once upon a time\nthere was a person\nwho lived happily"'
      ]);
    });
  });

  describe("Complex cases", () => {
    test("multiple fields with newlines in same record", async () => {
      const input = 'name,address,notes\n"John","123 Main St\nApt 4B","Personal note:\nVery nice person"';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,address,notes",
        '"John","123 Main St\nApt 4B","Personal note:\nVery nice person"'
      ]);
    });

    test("escaped quotes within fields", async () => {
      const input = 'name,quote\n"Shakespeare","To be or not to be,\nthat is the ""question"""';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,quote",
        '"Shakespeare","To be or not to be,\nthat is the ""question"""'
      ]);
    });

    test("empty fields and trailing commas", async () => {
      const input = 'name,middle,last\n"John",,"Doe"\n"Jane","","Smith"';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,middle,last",
        '"John",,"Doe"',
        '"Jane","","Smith"'
      ]);
    });

    test("mixed quoted and unquoted fields with newlines", async () => {
      const input = 'id,name,description\n1,"Alice","A person\nwith details"\n2,Bob,Simple';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "id,name,description",
        '1,"Alice","A person\nwith details"',
        "2,Bob,Simple"
      ]);
    });
  });

  describe("Edge cases", () => {
    test("empty input", async () => {
      const input = "";
      const result = await testAssembleRows(input);
      expect(result).toEqual([]);
    });

    test("single newline", async () => {
      const input = "\n";
      const result = await testAssembleRows(input);
      expect(result).toEqual([""]);
    });

    test("record ending with quoted empty field", async () => {
      const input = 'name,suffix\nJohn,""';
      const result = await testAssembleRows(input);
      expect(result).toEqual([
        "name,suffix",
        'John,""'
      ]);
    });

    test("unclosed quote (malformed CSV)", async () => {
      const input = 'name,description\n"John","Unclosed quote\nShould this work?';
      const result = await testAssembleRows(input);
      // This is a malformed CSV - behavior may vary depending on implementation
      // The test documents expected behavior rather than prescribing it
      expect(result).toEqual([
        "name,description",
        '"John","Unclosed quote\nShould this work?'
      ]);
    });
  });
});