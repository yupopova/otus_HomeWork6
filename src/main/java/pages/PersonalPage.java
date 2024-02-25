package pages;

import data.cities.ICityData;
import data.englishLevel.EnglishLevel;
import data.personalData.PersonalData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    public void addContacts(PersonalData personalData1, PersonalData personalData2, String data1, String data2) {
        String addContactLocator = "//*[contains(@class, 'placeholder')][.='Способ связи']";
        if (!driver.findElements(By.xpath(addContactLocator)).isEmpty()) {
            driver.findElement(By.xpath(addContactLocator)).click();
            driver.findElement(By.xpath("//button[@title='Тelegram']")).click();
            driver.findElement(By.cssSelector(String.format("input[name='%s']", personalData1.getName())))
                    .sendKeys(data1);
            logger.info("Telegram entered");
            driver.findElement(By.xpath("//button[.='Добавить']")).click();
            driver.findElement(By.xpath(addContactLocator)).click();
            driver.findElement(By.xpath("(//*[contains(@class, 'lk-cv-block__select-option_selected')][@title='WhatsApp']")).click();
            driver.findElement(By.cssSelector(String.format("input[name='%s']", personalData2.getName())))
                    .sendKeys(data2);
            logger.info("WhatsApp entered");
        } else {
            logger.info("Contacts already exists");
        }
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
}