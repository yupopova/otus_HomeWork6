package pages;

import data.cities.ICityData;
import data.englishLevel.EnglishLevel;
import data.personalData.PersonalData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

public class PersonalPage extends AbsBasePage {

    public PersonalPage(WebDriver driver) {
        super(driver, "/lk/biography/personal/");
    }

    public void clearFieldsOfPersonalData(PersonalData... personalData) {
        for (PersonalData fieldData : personalData) {
            driver.findElement(By.cssSelector(String.format("input[name='%s']", fieldData.getName()))).clear();
        }
    }

    public void inputFieldsOfPersonalData(PersonalData personalData, String data) {
        driver.findElement(By.cssSelector(String.format("input[name='%s']", personalData.getName())))
                .sendKeys(data);
    }

    public String getFieldValueOfPersonalData (PersonalData personalData) {
        return driver.findElement(By.cssSelector(String.format("input[name='%s']",
                personalData.getName()))).getAttribute("value");
    }

    public void selectCountry(ICityData cityData) {
        WebElement countrySelectElement = driver.findElement(By.cssSelector("[data-slave-selector='.js-lk-cv-dependent-slave-city']"));
        countrySelectElement.click();
        WebElement countryListContainer = countrySelectElement
                .findElement(By.xpath(".//*[contains(@class, 'js-custom-select-options-container')]"));
        waitTools.waitNotAttributeContains(countryListContainer, "class", "hide");
        driver.findElement(By.cssSelector(String.format("[title='%s']",
                cityData.getCountriesData().getNameCountry()))).click();
        waitTools.waitAttributeContains(countryListContainer, "class", "hide");
        logger.info("Country selected");
    }

    public void selectCity(ICityData cityData) {
        WebElement citySelectElement = driver.findElement(By.xpath("//*[contains(@class, 'js-lk-cv-dependent-slave-city')]"));
        citySelectElement.click();
        WebElement cityListContainer = citySelectElement
                .findElement(By.xpath(".//*[contains(@class, 'js-custom-select-options-container')]"));
        waitTools.waitNotAttributeContains(cityListContainer, "class", "hide");
        driver.findElement(By.cssSelector(String.format("[title='%s']", cityData.getName()))).click();
        waitTools.waitAttributeContains(cityListContainer, "class", "hide");
        logger.info("City selected");
    }

    public void selectEnglishLevel(EnglishLevel englishLevel) {
        WebElement englishLevelSelectElement = driver.findElement(By
                .xpath("//input[@name='english_level']/ancestor:: div[contains(@class, 'js-lk-cv-custom-select')]"));
        englishLevelSelectElement.click();

        WebElement levelListContainer = englishLevelSelectElement
                .findElement(By.xpath(".//*[contains(@class, 'js-custom-select-options-container')]"));
        waitTools.waitNotAttributeContains(levelListContainer, "class", "hide");

        driver.findElement(By.cssSelector(String.format("[title*='%s']", englishLevel.getEnglishLevel()))).click();
        logger.info("Level of English selected");
    }

    public void deleteContacts() throws IOException {
        List<WebElement> buttonsDelete = driver.findElements(By.cssSelector("[class*='delete']"));
        for (int i = 0; i < buttonsDelete.size(); i++) {
            if (buttonsDelete.get(i).isDisplayed()) {
                buttonsDelete.get(i).click();
            }
        }
    }

    public void addContactClick() {
        driver.findElement(By.xpath("//button[.='Добавить']")).click();
    }

    public void addContact(PersonalData personalData, String value) {
        String addContactLocator = "//*[contains(@class, 'placeholder')][.='Способ связи']";
        driver.findElement(By.xpath(addContactLocator)).click();
        driver.findElement(By.xpath(String.format("//button[@title='%s']", personalData.getName()))).click();
            WebElement fieldContact = driver.findElement(By.id("id_contact-0-value"));
            fieldContact.click();
            fieldContact.sendKeys(value);
            logger.info("Contact entered");
    }

    public String getFieldValueOfContact () {
        String value = driver.findElement(By.id("id_contact-0-value")).getAttribute("value");
        return value;
    }

    public void clickButtonSavePersonalData() {
        driver.findElement(By.name("continue")).click();

        Assertions.assertTrue(waitTools.waitElementPresent(By.xpath("//*[contains(@class, 'success')]")));
        logger.info("Successful save of Personal Data");
    }

    public void checkFieldsOfPersonalData (PersonalData personalData, String data) {
        Assertions.assertEquals(driver.findElement(By.cssSelector(String.format("input[name='%s']",
                personalData.getName()))).getAttribute("value"), data);
   }

    public void checkFieldsOfContacts (String data) {
        Assertions.assertEquals(driver.findElement(By.id("id_contact-0-value")).getAttribute("value"), data);
    }

}