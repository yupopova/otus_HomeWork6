package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PersonalCabinetPage extends AbsBasePage {

    private String MenuAboutMeLocator ="//*[contains(@href, 'personal')][contains(@title, 'О себе')]";

    public PersonalCabinetPage(WebDriver driver) {
        super(driver, "/learning/");
    }

    public void openMenuAboutMe() {
        waitTools.waitElementPresent(By.xpath(MenuAboutMeLocator));
        waitTools.waitElementToBeClicable(By.xpath(MenuAboutMeLocator));

        WebElement MenuAboutMe = driver.findElement(By.xpath(MenuAboutMeLocator));
        MenuAboutMe.click();
        logger.info("Menu About Me is open");

        if (!driver.findElements(By.xpath("//div[@class='modal modal_slim modal_no-full lk-invites-modal-agreement']")).isEmpty()) {
            logger.info("Modal window about publication resume is open");
            driver.findElement(By.name("agreement")).click();
            logger.info("Agreement about publication resume is done");
        } else {
            logger.info("Modal window about about publication resume is not display"); }
    }
}