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
        calendarEventsPage.open("/events/near/");
    }

    @AfterEach
    public void driverQuit() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Quit driver");
    }

    @Test
    public void eventsPageTest() {
        // Проверка отображения ближайших мероприятий в календаре
        calendarEventsPage.checkEventsTylesShouldBeVisible();
        logger.info("Events is visible");
        // Проверка, что даты мероприятий больше или равны текущей дате
        calendarEventsPage.checkStartEventDate();
        logger.info("Date of events is later than today");
        // Сортировка мероприятий по типу Открытые вебинары
        calendarEventsPage.selectSortedEventsType(EventTypeData.OPEN);
        logger.info("Success sorted events by type OPEN WEBINAR");
        // Проверка отображения ближайших мероприятий в календаре
        calendarEventsPage.checkEventsTylesShouldBeVisible();
        logger.info("Events is visible");
        // Проверка отображения типа "Открытый вебинар" на всех карточках мероприятий
        calendarEventsPage.checkEventsType(EventTypeData.OPEN);
        logger.info("All of events have type 'OPEN WEBINAR'");
    }
}
