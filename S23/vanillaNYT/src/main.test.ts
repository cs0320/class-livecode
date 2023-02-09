// We're using the "ECM" module system. This is a "named import":
import { pattern } from "./main";

/*
 * Unit tests for the `pattern` function
 */

test("pattern false if all empty", () => {
  const input = ["", "", ""];
  const output = pattern(input);
  expect(output).toBe(false);
});

test("pattern false if any empty", () => {
  expect(pattern(["1", "", ""])).toBe(false);
  expect(pattern(["", "1", ""])).toBe(false);
  expect(pattern(["", "", "1"])).toBe(false);
  expect(pattern(["1", "2", ""])).toBe(false);
  expect(pattern(["", "1", "2"])).toBe(false);
  expect(pattern(["1", "", "2"])).toBe(false);
});

test("pattern false for decreasing", () => {
  expect(pattern(["1", "2", "1"])).toBe(false);
  expect(pattern(["1", "0", "1"])).toBe(false);
});

test("pattern false for nonincreasing", () => {
  expect(pattern(["1", "1", "2"])).toBe(false);
});

test("pattern true for increasing", () => {
  expect(pattern(["1", "2", "3"])).toBe(true);
});

test("negative inputs handled correctly", () => {
  expect(pattern(["-3", "-2", "-1"])).toBe(true);
  expect(pattern(["-1", "-2", "-3"])).toBe(false);
});

/**
 * This was added as a regression test: a real bug existed last year.
 */
test("compare as numbers, not by length", () => {
  const input = ["3", "22", "111"];
  const output = pattern(input);
  expect(output).toBe(true);
});
