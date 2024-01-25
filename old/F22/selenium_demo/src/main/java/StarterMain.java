// Remember to add the Maven dependency! You may also
// need to option-enter (alt-enter) and pick a
// "Maven: add ... to classpath" option, so that the WebDriverManager
// is available in both test and compile contexts.
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class StarterMain {
    public static void main(String[] args) {
        // There will be various messages in the console...
        //   (By default, Selenium prints a lot of info)

        // Use WDM, not manual download. I like Firefox for this:
        WebDriverManager.firefoxdriver().setup();
        // If you want to use Safari, you'll need to enable an option in
        // your menu -- Safari provides its own driver, but requires you
        // to enable it before Selenium can use it.
        // https://developer.apple.com/documentation/webkit/testing_with_webdriver_in_safari

        FirefoxOptions options = new FirefoxOptions();
        FirefoxDriver driver = new FirefoxDriver(options);
        driver.get("https://tnelson.github.io/reactNYT/");
        System.out.println(driver.getTitle());
        driver.quit();
    }
}