package edu.brown.cs32.spring22.livecode.apr05;

// Remember to add the Maven dependency! You may also
// need to option-enter (alt-enter) and pick a
// "Maven: add ... to classpath" option.
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // There will be various messages in the console...
        //   (By default, Selenium prints a lot of info)
        WebDriverManager.firefoxdriver().setup();

        runExample("https://tnelson.github.io/reactNYT/");
        // Remember to CHANGE THIS if you want to run vs. a local file
        //   runExample("file:///Users/tim/repos/reactNYT/vanilla-app/index.html");
    }

    static void runExample(String path) {
        FirefoxOptions options = new FirefoxOptions();
        FirefoxDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        
        driver.get(path);
        System.out.println(driver.getTitle());

        // lots of ways to "get an element":
        WebElement new_round = driver.findElement(By.className("new-round"));
        System.out.println(new_round);

        // Don't get _all_ inputs, just the inputs in the new-round div
        List<WebElement> inputs = new_round.findElements(By.tagName("input"));
        WebElement submit = new_round.findElement(By.tagName("button"));

        System.out.println(inputs);
        int val = 1;
        for(WebElement in : inputs) {
            in.sendKeys(String.valueOf(val));
            val++;
        }
        submit.click();

        // Beware: what happens if we re-use the same WebElement values?
        for(WebElement in : inputs) {
            in.sendKeys(String.valueOf(val));
            val++;
        }
        submit.click();
        // We're actually OK! But this isn't guaranteed.
        // Selenium uses locators to define where elements live;
        // these can become stale, etc.

        driver.quit();
    }
}
