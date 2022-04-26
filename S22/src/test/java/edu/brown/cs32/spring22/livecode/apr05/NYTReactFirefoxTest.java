package edu.brown.cs32.spring22.livecode.apr05;

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

public class NYTReactFirefoxTest {
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
    }

    /**
     * Remove references created by a test, reset to old state, etc.
     */
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testCorrect() {
        driver.get(indexPath);
        assertEquals(driver.getTitle(), "React App");

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
        assertEquals(driver.getTitle(), "React App");

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
