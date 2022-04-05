package edu.brown.cs32.spring22.livecode.apr05StarterManual;

// Remember to add the Maven dependency! You may also
// need to option-enter (alt-enter) and pick a
// "Maven: add ... to classpath" option.
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Main {
    public static void main(String[] args) {
        // There will be various messages in the console...
        //   (By default, Selenium prints a lot of info)

        // Manual download of the driver. Put path here:
        String geckoURL = "/Users/Tim/repos/cs32-livecode/S22/geckodriver 3";
        System.setProperty("webdriver.gecko.driver", geckoURL);
        // If you forget this, you'll get an exit code 1 (not 0)
        //   and an error message that the driver couldn't be found.

        FirefoxOptions options = new FirefoxOptions();
        FirefoxDriver driver = new FirefoxDriver(options);
        driver.get("https://tnelson.github.io/reactNYT/");
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
