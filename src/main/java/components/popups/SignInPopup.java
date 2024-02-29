package components.popups;

import common.AbsCommon;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPopup extends AbsCommon implements IPopup {

    public SignInPopup(WebDriver driver) {
        super(driver);
    }

    private final String login = System.getProperty("login");
    private final String password = System.getProperty("password");
    private String loginSelector = "input[name='email']";
    private String passwordXpath = "//div[./input[@type='password']]";
    private String passwordSelector = "input[type='password']";
    private String signInPopupSelector = "#__PORTAL__ > div";

    @Override
    public void popupShouldBeVisible() {
        WebElement authPopupElement = driver.findElement(By.cssSelector(signInPopupSelector));
        Assertions.assertTrue(waitTools.waitElementVisible(authPopupElement),
                "Error! SignInPopup is not visible");
    }

    @Override
    public void popupShouldNotBeVisible() {
        Assertions.assertTrue(waitTools.waitNotElementOfVisibilityLocated(By.cssSelector(signInPopupSelector)),
                "Error! SignInPopup is opened");
    }

    public void inputLogin() {
        String loginXpath = "//div[./input[@name='email']]";
        driver.findElement(By.xpath(loginXpath)).click();

        WebElement emailInputField = driver.findElement(By.cssSelector(loginSelector));
        waitTools.waitElementVisible(emailInputField);

        emailInputField.sendKeys(login);
        logger.info("Login entered");
    }

    public void inputPassword() {
        WebElement passwordInputField = driver.findElement(By.cssSelector(passwordSelector));

        driver.findElement(By.xpath(passwordXpath)).click();
        waitTools.waitElementVisible(passwordInputField);
        passwordInputField.sendKeys(password);
        logger.info("Password entered");
    }

    public void clickSignInButton() {
        driver.findElement(By.cssSelector(signInPopupSelector)).click();
    }
}
