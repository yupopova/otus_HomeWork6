package components;

import common.AbsCommon;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Header extends AbsCommon {

    private String signInButtonLocator = "//button[text()='Войти']";
    private String userMenuSelector = "img[src*='blue-owl']";
    private String userMenuLocator = "//*[contains(@class, 'Component fgPsmr')]";
    private String personalCabinetLocator ="//*[contains(@href, 'learning')][.='Личный кабинет']";
    private String exitLocator= "//*[contains(@class, 'logout')]";

    public Header(WebDriver driver) {
        super(driver);
    }

    public void auth() {
        waitTools.waitElementPresent(By.xpath(signInButtonLocator));
        waitTools.waitElementToBeClicable(By.xpath(signInButtonLocator));

        WebElement signInButton = driver.findElement(By.xpath(signInButtonLocator));
        signInButton.click();
    }

    public void checkForUserSignIn() {
        Assertions.assertTrue(waitTools.waitElementPresent(By.cssSelector(userMenuSelector)));
        logger.info("Successful authorization");
    }

    public void openUserMenu() {
        waitTools.waitElementPresent(By.xpath(userMenuLocator));
        waitTools.waitElementToBeClicable(By.xpath(userMenuLocator));

        WebElement UserMenu = driver.findElement(By.xpath(userMenuLocator));
        UserMenu.click();
        logger.info("User menu is open");
    }

    public void openPersonalCabinet() {
        waitTools.waitElementPresent(By.xpath(personalCabinetLocator));
        waitTools.waitElementToBeClicable(By.xpath(personalCabinetLocator));

        WebElement PersonalCabinet = driver.findElement(By.xpath(personalCabinetLocator));
        PersonalCabinet.click();
        logger.info("Personal cabinet is open");
    }

    public void exitFromOtus() {
        WebElement UserMenu = driver.findElement(By.cssSelector(userMenuSelector));
        UserMenu.click();
        WebElement exit = driver.findElement((By.xpath(exitLocator)));
        exit.click();
        logger.info("Successful logout");
    }
}
