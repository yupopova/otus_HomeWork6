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
    public void inputPersonalDataTest() {
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

        PersonalPage personalPage = new PersonalPage(driver);

        personalPage.clearFieldsOfPersonalData(PersonalData.FNAME);
        personalPage.inputFieldsOfPersonalData(PersonalData.FNAME, "Тест");
        logger.info("Successful input FIRSTNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.FNAME_LATIN);
        personalPage.inputFieldsOfPersonalData(PersonalData.FNAME_LATIN, "Test");
        logger.info("Successful input LATIN FIRSTNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.LNAME);
        personalPage.inputFieldsOfPersonalData(PersonalData.LNAME, "Тестовин");
        logger.info("Successful input FIRST LASTNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.LNAME_LATIN);
        personalPage.inputFieldsOfPersonalData(PersonalData.LNAME_LATIN, "Testovin");
        logger.info("Successful input LATIN LASTNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.BLOG_NAME);
        personalPage.inputFieldsOfPersonalData(PersonalData.BLOG_NAME, "Blog_name");
        logger.info("Successful input BLOGNAME");

        personalPage.clearFieldsOfPersonalData(PersonalData.DATE_OF_BIRTH);
        personalPage.inputFieldsOfPersonalData(PersonalData.DATE_OF_BIRTH, "01.01.2000");
        logger.info("Successful input DATE OF BIRTH");

        ICityData[] cityData = RussiaCityData.values();
        ICityData city = faker.options().nextElement(cityData);

        personalPage.selectCountry(city);
        personalPage.selectCity(city);

        personalPage.selectEnglishLevel(EnglishLevel.INTERMEDIATE);

        personalPage.addContacts(PersonalData.CONTACT0VALUE, PersonalData.CONTACT1VALUE, "@Test_telegram", "+79998887766");

        personalPage.clickButtonSavePersonalData();

    }

    @Test
    public void verifyPersonalDataTest() {
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

        PersonalPage personalPage = new PersonalPage(driver);

        personalPage.checkFieldsOfPersonalData(PersonalData.FNAME, "Тест");
        logger.info("Firstname is check");
        personalPage.checkFieldsOfPersonalData(PersonalData.FNAME_LATIN, "Test");
        logger.info("Latin Firstname is check");
        personalPage.checkFieldsOfPersonalData(PersonalData.LNAME, "Тестовин");
        logger.info("Lastname is check");
        personalPage.checkFieldsOfPersonalData(PersonalData.LNAME_LATIN, "Testovin");
        logger.info("Latin Lastname is check");
        personalPage.checkFieldsOfPersonalData(PersonalData.BLOG_NAME, "Blog_name");
        logger.info("Blog_name is check");
        personalPage.checkFieldsOfPersonalData(PersonalData.DATE_OF_BIRTH, "01.01.2000");
        logger.info("Date of birth is check");
        personalPage.checkFieldsOfPersonalData(PersonalData.CONTACT0VALUE, "@Test_telegram");
        logger.info("Telegram is check");
        personalPage.checkFieldsOfPersonalData(PersonalData.CONTACT1VALUE, "+79998887766");
        logger.info("WhatsApp is check");
        logger.info("Successful check of Personal Data");
   }
}