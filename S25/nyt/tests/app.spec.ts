import { test, expect } from '@playwright/test';

// Rather than break tests if we change text values in the app...
import {TEXT_number_1_accessible_name, TEXT_number_2_accessible_name, TEXT_number_3_accessible_name,
        TEXT_try_button_accessible_name} from '../src/constants'

// This port number needs to be the same as in playwright.config.ts
// Use "localhost" here rather than "127.0.0.1" to avoid some issues on Windows 
const url = 'http://localhost:8000'

/**
 * DEMO: test function with Playwright, expect, await
 * 
 * The "test" function creates a new test case. Its argument is the function to call
 * to run the test. In Playwright, the function has one argument: the web page!
 */
test('title is as expected', async ({ page }) => {
  await page.goto(url);      
  // Watch out for timing: we'll cover the "await" keyword later, but without
  // it, the test won't wait for (e.g.) to page to load.    
  await expect(page).toHaveTitle(/Can You Guess/); // substring
});


/**
 * DEMO: 3 different ways to locate elements:
 *   - by visible data or accessibility metadata 
 *   - by test ID
 *   - by selector
 * 
 * See these docs for more "getBy..." functions:
 * https://playwright.dev/docs/locators
 */
test('header text is visible on initial page', async ({ page }) => {
  await page.goto(url);
    
  // A big part of writing these tests is locating elements on the page. 
  // There are a few ways to locate elements. You don't need to do all 3 yourself; 
  // I'm writing the same kind of test 3 times to demonstrate them.

  // Preferred approach: by visible data or accessibility metadata a user could interact with
  //   (Tests that approximate user experience are better than ones that don't.)
  await expect(page.getByText('my function returns true on the sequence: 2, 4, 8.')).toBeVisible();

  // But sometimes that doesn't work well, or you can't add metadata reasonably. 
  // You can use a "test ID" field if accessibility metadata isn't suitable.
  // NOTE WELL React's strange prop name for this.
  // We'd pass in the JSX: data-testid="test:header-text"
  await expect(page.getByTestId('test:header-text')).toBeVisible()

  // Last resort: use a locator to find by (say) class ID using a CSS selector. 
  // But this really should be a last resort. If you write tests this way, you're 
  // not making use of helpers that could make your testing experience better.
  await expect(page.locator('.App-header')).toBeVisible() // class = App-header
});

/**
 * DEMO: getByRole (ARIA metadata)
 * https://playwright.dev/docs/locators#locate-by-role
 * 
 * Looking for our 3 input elements, these won't have visible text (yet). 
 * Instead, search for them based on ARIA (accessibility) annotations. 
 * 
 * To do this, we added aria-label values to these components in the React app. 
 * This might seem annoying, but: we've got 3 input boxes on the page. Shouldn't we 
 * provide some annotations to help (say) screenreaders disambiguate them without
 * relying on relative positioning? 
 * 
 */
test('renders guess input fields', async ({ page }) => {
  await page.goto(url);    
  // Leverage accessibility tags we ought to be providing anyway  
  const guess0 = page.getByRole("textbox", {name: TEXT_number_1_accessible_name})
  const guess1 = page.getByRole("textbox", {name: TEXT_number_2_accessible_name})
  const guess2 = page.getByRole("textbox", {name: TEXT_number_3_accessible_name})
  await expect(guess0).toBeVisible()
  await expect(guess1).toBeVisible()
  await expect(guess2).toBeVisible()
});

/**
 * DEMO: Start *interacting* with the page!
 */
test('entering correct guess', async ({ page }) => {
  await page.goto(url);

  const guess0 = page.getByRole("textbox", {name: TEXT_number_1_accessible_name})    
  const guess1 = page.getByRole("textbox", {name: TEXT_number_2_accessible_name})    
  const guess2 = page.getByRole("textbox", {name: TEXT_number_3_accessible_name})  
  const submitButton = page.getByRole("button", {name: TEXT_try_button_accessible_name})  

  // Script some actions (remember to "await" their execution):
  await guess0.fill('100');    
  await guess1.fill('200');    
  await guess2.fill('300');
  await submitButton.click()    

  // Now, do we see a correct guess block? 
  // Use accessibility metadata again (after it's added to the React code!)
  // https://www.w3.org/TR/html-aria/#docconformance
  
  // Label is different from Text (see mouseover). In short, it searches for an associated 
  // <label> element or for an aria-label attribute.
  const correctBlock = page.getByLabel('correct sequence')    
  await expect(correctBlock).toBeVisible()  
  const incorrectBlock = page.getByLabel('incorrect sequence')    
  await expect(incorrectBlock).not.toBeVisible()

  // We can also count the number of such elements by calling .all(). But note this warning:
  // "locator.all() does not wait for elements to match the locator, and instead immediately 
  // returns whatever is present in the page." 
  // https://playwright.dev/docs/api/class-locator#locator-all
  const correctBlocks = await page.getByLabel('correct sequence').all()
  expect(correctBlocks.length).toBe(1)
});

/*
  Exercise for the reader: what else should be tested?
  Focus on interactions that modify state...
*/