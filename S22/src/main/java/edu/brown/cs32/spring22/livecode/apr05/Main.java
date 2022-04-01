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

public class Main {
    public static void main(String[] args) {
        // There will be various messages in the console...
        //   (By default, Selenium prints a lot of info)
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        FirefoxDriver driver = new FirefoxDriver(options);
        driver.get("https://tnelson.github.io/reactNYT/");

        System.out.println(driver.getTitle());
        // Uh oh, Tim didn't give a better title to the app...
        // (what would have happened if the page hadn't finished loading?)

        //WebElement new_round = driver.findElement(By.name("new-round"));
        // org.openqa.selenium.NoSuchElementException: Unable to locate element: *[name='new-round']
        // lots of ways to "get an element"; we really meant CLASS name

        WebElement new_round = driver.findElement(By.className("new-round"));
        System.out.println(new_round);

        // Timeouts are subtle; in simple examples we can do this:
        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.quit();
    }
}
