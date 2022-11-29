/*
  See StarterMain.java for more comments.
 */

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.List;

public class DemoMain {
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

        // lots of ways to "get an element"; this should resemble Jest:
        WebElement new_round = driver.findElement(By.className("new-round"));
        System.out.println(new_round);

        // Don't get _all_ inputs, just the inputs in the new-round div
        List<WebElement> inputs = new_round.findElements(By.tagName("input"));
        WebElement submit = new_round.findElement(By.tagName("button"));

        System.out.println("Found "+ inputs.size() + " inputs.");

        // Send keystrokes (again, similar to Jest or RTL)
        int val = 1;
        for(WebElement in : inputs) {
            in.sendKeys(String.valueOf(val));
            val++;
        }
        submit.click();


        // Close the Firefox window
        driver.quit();
    }
}