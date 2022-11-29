/*
  This example largely duplicates the sorts of tests you're used to writing
  in Jest/RTL already. However,
  (1) because we're running from JUnit, it's much easier to automate
      a real backend server (starting it up via setup method, etc.)
  (2) Selenium gives us a testing environment much closer to reality,
      where we're able to control which browser is being used, etc. If
      your project works on Browser X but fails on Browser Y, this will
      sometimes help you find the problem.
 */

import static org.junit.Assert.assertEquals;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.time.Duration;
import java.util.List;

public class TestNYTPuzzleSelenium {
    private RemoteWebDriver driver = null;
    private String indexPath = "https://tnelson.github.io/reactNYT/";

    /**
     * Set up for a new test
     */
    @Before
    public void setup() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        // Headless = no UI displayed; good for CI
        options.addArguments("--headless");
        this.driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        // Setup _backend_ state too, if any
    }

    /**
     * Remove references created by a test, reset to old state, etc.
     */
    @After
    public void tearDown() {
        driver.quit();

        // Teardown _backend_ state too, if any.
    }

    @Test
    public void testCorrect() {
        driver.get(indexPath);
        assertEquals("Can You Guess The Sequence?", driver.getTitle());

        WebElement newRound = driver.findElement(By.className("new-round"));
        List<WebElement> inputs = newRound.findElements(By.tagName("input"));
        WebElement submit = newRound.findElement(By.tagName("button"));

        // Enter a correct guess
        int val = 1;
        for(WebElement in : inputs) {
            in.sendKeys(String.valueOf(val));
            val++;
        }
        submit.click();

        ///////////////////////////////////////////////////////////////////////
        // PROPERTY: we can see the same old inputs in a "guess-round-true" DIV
        ///////////////////////////////////////////////////////////////////////

        // Note that when run as a JUnit test, any exception here will cause the test to fail.
        WebElement oldRound = driver.findElement(By.className("guess-round-true"));
        List<WebElement> oldInputs = oldRound.findElements(By.tagName("input"));
        assertEquals(oldInputs.size(), 3);
        assertEquals("1", oldInputs.get(0).getAttribute("value"));
        assertEquals("2", oldInputs.get(1).getAttribute("value"));
        assertEquals("3", oldInputs.get(2).getAttribute("value"));

        // ? Are there other things we might want to check after a correct guess?
    }

    @Test
    public void testWrong() {
        driver.get(indexPath);

        WebElement newRound = driver.findElement(By.className("new-round"));
        List<WebElement> inputs = newRound.findElements(By.tagName("input"));
        WebElement submit = newRound.findElement(By.tagName("button"));

        // Enter a wrong guess
        int val = 3;
        for(WebElement in : inputs) {
            in.sendKeys(String.valueOf(val));
            val--;
        }
        submit.click();

        ///////////////////////////////////////////////////////////////////////
        // PROPERTY: we can see the same old inputs in a "guess-round-false" DIV
        ///////////////////////////////////////////////////////////////////////

        // Note that when run as a JUnit test, any exception here will cause the test to fail.
        WebElement oldRound = driver.findElement(By.className("guess-round-false"));
        List<WebElement> oldInputs = oldRound.findElements(By.tagName("input"));
        assertEquals(oldInputs.size(), 3);
        assertEquals("3", oldInputs.get(0).getAttribute("value"));
        assertEquals("2", oldInputs.get(1).getAttribute("value"));
        assertEquals("1", oldInputs.get(2).getAttribute("value"));

        // ? Are there other things we might want to check after a wrong guess?
    }





}