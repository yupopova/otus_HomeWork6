package factory;

import exceptions.BrowserNotSupportedException;
import factory.impl.ChromeDriverSettings;
import factory.impl.FirefoxDriverSettings;
import factory.impl.IDriverSettings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private String browserName = System.getProperty("browser.name");
    private String[] arguments;
    public DriverFactory(String... arguments) {
        this.arguments = arguments;
    }

    public WebDriver create() {
        browserName = browserName.toLowerCase();

        IDriverSettings settings = null;

        switch(browserName) {
            case "chrome": {
                return new ChromeDriver((ChromeOptions) new ChromeDriverSettings().settings());
            }
            case "firefox": {
                return new FirefoxDriver((FirefoxOptions) new FirefoxDriverSettings().settings());
            }
        }
        throw new BrowserNotSupportedException(browserName);
    }

}