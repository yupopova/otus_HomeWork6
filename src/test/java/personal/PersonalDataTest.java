package personal;

import com.github.javafaker.Faker;
import components.Header;
import components.popups.SignInPopup;
import data.cities.ICityData;
import data.cities.RussiaCityData;
import data.englishLevel.EnglishLevel;
import data.personalData.PersonalData;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import pages.MainPage;
import pages.PersonalCabinetPage;
import pages.PersonalPage;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PersonalDataTest {

    private Logger logger ;
    private WebDriver driver;
    protected Faker faker = new Faker();

    @BeforeEach
    public void initDriver() {
        driver = new DriverFactory().create();
        logger = LogManager.getLogger(PersonalDataTest.class);
        logger.info("Start driver");
    }

    @AfterEach
    public void driverQuit() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Quit driver");
    }

    @Test
    public void PersonalDataTest() throws IOException {
        auth();
        PersonalPage personalPage = new PersonalPage(driver);
        personalPage.clearFieldsOfPersonalData(PersonalData.FNAME);
        personalPage.inputFieldsOfPersonalData(PersonalData.FNAME, faker.name().firstName());
        String fname = personalPage.getFieldValueOfPersonalData (PersonalData.FNAME);
        logger.info("Successful input FIRSTNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.FNAME_LATIN);
        personalPage.inputFieldsOfPersonalData(PersonalData.FNAME_LATIN, faker.name().firstName());
        String fname_latin = personalPage.getFieldValueOfPersonalData (PersonalData.FNAME_LATIN);
        logger.info("Successful input LATIN FIRSTNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.LNAME);
        personalPage.inputFieldsOfPersonalData(PersonalData.LNAME, faker.name().lastName());
        String lname = personalPage.getFieldValueOfPersonalData (PersonalData.LNAME);
        logger.info("Successful input FIRST LASTNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.LNAME_LATIN);
        personalPage.inputFieldsOfPersonalData(PersonalData.LNAME_LATIN, faker.name().lastName());
        String lname_latin = personalPage.getFieldValueOfPersonalData (PersonalData.LNAME_LATIN);
        logger.info("Successful input LATIN LASTNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.BLOG_NAME);
        personalPage.inputFieldsOfPersonalData(PersonalData.BLOG_NAME, faker.name().name());
        String blog_name = personalPage.getFieldValueOfPersonalData (PersonalData.BLOG_NAME);
        logger.info("Successful input BLOGNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.DATE_OF_BIRTH);
        personalPage.inputFieldsOfPersonalData(PersonalData.DATE_OF_BIRTH, faker.date().birthday().toInstant().atZone(ZoneId.
                systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        String date_of_birth = personalPage.getFieldValueOfPersonalData (PersonalData.DATE_OF_BIRTH);
        logger.info("Successful input DATE OF BIRTH");

        ICityData[] cityData = RussiaCityData.values();
        ICityData city = faker.options().nextElement(cityData);

        personalPage.selectCountry(city);
        personalPage.selectCity(city);

        personalPage.selectEnglishLevel(EnglishLevel.INTERMEDIATE);

        personalPage.deleteContacts();
        personalPage.clickButtonSavePersonalData();

        PersonalCabinetPage personalCabinetPage = new PersonalCabinetPage(driver);
        personalCabinetPage.openMenuAboutMe();
        personalPage.addContactClick();
        personalPage.addContact(PersonalData.TELEGRAM, faker.name().username());
        String telegram = personalPage.getFieldValueOfContact();

        personalPage.clickButtonSavePersonalData();
        Header header = new Header(driver);
        header.exitFromOtus();
        driver.close();
        logger.info("Close driver");

        driver = new DriverFactory().create();
        logger.info("Start driver");
        auth();
        PersonalPage personalPageVerify = new PersonalPage(driver);

        personalPageVerify.checkFieldsOfPersonalData(PersonalData.FNAME, fname);
        logger.info("Firstname is check");
        personalPageVerify.checkFieldsOfPersonalData(PersonalData.FNAME_LATIN, fname_latin);
        logger.info("Latin Firstname is check");
        personalPageVerify.checkFieldsOfPersonalData(PersonalData.LNAME, lname);
        logger.info("Lastname is check");
        personalPageVerify.checkFieldsOfPersonalData(PersonalData.LNAME_LATIN, lname_latin);
        logger.info("Latin Lastname is check");
        personalPageVerify.checkFieldsOfPersonalData(PersonalData.BLOG_NAME, blog_name);
        logger.info("Blog_name is check");
        personalPageVerify.checkFieldsOfPersonalData(PersonalData.DATE_OF_BIRTH, date_of_birth);
        logger.info("Date of birth is check");
        personalPageVerify.checkFieldsOfContacts(telegram);
        logger.info("Telegram is check");
        logger.info("Successful check of Personal Data");
   }

    private void auth() {
        new MainPage(driver).open("/");
        logger.info("Open main page");
        Header header = new Header(driver);
        SignInPopup signInPopup = new SignInPopup(driver);
        signInPopup.popupShouldNotBeVisible();

        header.auth();
        signInPopup.popupShouldBeVisible();
        signInPopup.inputLogin();
        signInPopup.inputPassword();
        signInPopup.clickSignInButton();

        header.checkForUserSignIn();
        header.openUserMenu();
        header.openPersonalCabinet();

        PersonalCabinetPage personalCabinetPage = new PersonalCabinetPage(driver);
        personalCabinetPage.openMenuAboutMe();
    }
}