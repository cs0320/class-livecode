import { test, expect, type Page } from '@playwright/test';
import AxeBuilder from '@axe-core/playwright';
import {
  TEXT_number_1_accessible_name, TEXT_number_2_accessible_name, TEXT_number_3_accessible_name,
  TEXT_try_button_accessible_name,
} from '../src/constants';

const WCAG_TAGS = ['wcag2a', 'wcag2aa', 'wcag21a', 'wcag21aa', 'wcag22aa'];

type AxeResults = Awaited<ReturnType<AxeBuilder['analyze']>>;

function isWcag(v: { tags: string[] }) {
  return v.tags.some((t) => WCAG_TAGS.includes(t));
}

async function checkA11y(page: Page, label: string) {
  const results: AxeResults = await new AxeBuilder({ page }).analyze();
  for (const v of results.violations.filter((v) => !isWcag(v))) {
    console.log(`[${label}] best practice: ${v.id} (${v.impact}): ${v.help}`);
  }
  for (const v of results.incomplete) {
    console.log(`[${label}] needs review: ${v.id}: ${v.help} (${v.nodes.length} element(s))`);
  }
  const wcagFailures = results.violations.filter(isWcag).map((v) => ({
    rule: v.id, impact: v.impact, help: v.help, elements: v.nodes.map((n) => n.html),
  }));
  expect(wcagFailures).toEqual([]);
}

async function guess(page: Page, seq: [string, string, string]) {
  await page.getByRole('textbox', { name: TEXT_number_1_accessible_name }).fill(seq[0]);
  await page.getByRole('textbox', { name: TEXT_number_2_accessible_name }).fill(seq[1]);
  await page.getByRole('textbox', { name: TEXT_number_3_accessible_name }).fill(seq[2]);
  await page.getByRole('button', { name: TEXT_try_button_accessible_name }).click();
}

test('initial page has no WCAG violations', async ({ page }) => {
  await page.goto('/');
  await checkA11y(page, 'initial');
});

test('page with correct and incorrect past rounds has no WCAG violations', async ({ page }) => {
  await page.goto('/');
  await guess(page, ['2', '4', '8']);
  await guess(page, ['3', '2', '1']);
  await expect(page.getByLabel('correct sequence', { exact: true })).toBeVisible();
  await expect(page.getByLabel('incorrect sequence', { exact: true })).toBeVisible();
  await checkA11y(page, 'after rounds');
});
