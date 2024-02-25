package events;

import data.events.EventTypeData;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CalendarEventsPage;

public class EventsPageTest {

    private Logger logger ;
    private WebDriver driver;
    private CalendarEventsPage calendarEventsPage;

    @BeforeEach
    public void initDriver() {
        this.driver = new DriverFactory().create();
        logger = LogManager.getLogger(EventsPageTest.class);
        logger.info("Start driver");

        this.calendarEventsPage = new CalendarEventsPage(driver);
        calendarEventsPage.open();
    }

    @AfterEach
    public void driverQuit() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Quit driver");
    }

    @Test
    public void eventsTiles() {
        calendarEventsPage
                .checkEventsTylesShouldBeVisible()
                .checkStartEventDate();
    }

    @Test
    public void selectEventsOfType() {
        calendarEventsPage
                .selectSortedEventsType(EventTypeData.OPEN)
                .checkEventsTylesShouldBeVisible()
                .checkEventsType(EventTypeData.OPEN);
    }
}
