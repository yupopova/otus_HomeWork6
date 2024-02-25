package components;

import common.AbsCommon;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Header extends AbsCommon {

    private String signInButtonLocator = "//button[text()='Войти']";
    private String userMenuSelector = "img[src*='blue-owl']";
    private String UserMenuLocator = "//div[@class='sc-r03h0s-5 sc-1youhxc-2 bYKNcH imWQF sc-1og4wiw-0-Component fgPsmr']";
    private String PersonalCabinetLocator ="//*[contains(@href, 'learning')][.='Личный кабинет']";

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
        waitTools.waitElementPresent(By.xpath(UserMenuLocator));
        waitTools.waitElementToBeClicable(By.xpath(UserMenuLocator));

        WebElement UserMenu = driver.findElement(By.xpath(UserMenuLocator));
        UserMenu.click();
        logger.info("User menu is open");
    }

    public void openPersonalCabinet() {
        waitTools.waitElementPresent(By.xpath(PersonalCabinetLocator));
        waitTools.waitElementToBeClicable(By.xpath(PersonalCabinetLocator));

        WebElement PersonalCabinet = driver.findElement(By.xpath(PersonalCabinetLocator));
        PersonalCabinet.click();
        logger.info("Personal cabinet is open");
    }
}
