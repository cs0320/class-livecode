package edu.brown.cs32.spring22.livecode.apr05Starter;

// Remember to add the Maven dependency! You may also
// need to option-enter (alt-enter) and pick a
// "Maven: add ... to classpath" option.
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Main {
    public static void main(String[] args) {
        // There will be various messages in the console...
        //   (By default, Selenium prints a lot of info)

        // Use WDM, not manual download
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        FirefoxDriver driver = new FirefoxDriver(options);
        driver.get("https://tnelson.github.io/reactNYT/");
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
