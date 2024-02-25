package factory.impl;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class FirefoxDriverSettings implements IDriverSettings {

    {
        WebDriverManager.firefoxdriver().setup();
    }

    @Override
    public AbstractDriverOptions settings() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        firefoxOptions.addArguments("--no-sandbox");
        firefoxOptions.addArguments("--no-first-run");
        firefoxOptions.addArguments("--enable-extensions");
        firefoxOptions.addArguments("--homepage=about:blank");
        firefoxOptions.addArguments("--ignore-certificate-errors");

        return firefoxOptions;

    }
}