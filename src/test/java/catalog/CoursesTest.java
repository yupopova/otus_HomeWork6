package catalog;

import data.catalog.LessonsCategoryData;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CatalogCoursesPage;
import pages.DetailsCoursePage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoursesTest {
    private Logger logger ;
    private WebDriver driver;
    private CatalogCoursesPage catalogCoursesPage;

    @BeforeEach
    public void initDriver() {
        this.driver = new DriverFactory().create();
        logger = LogManager.getLogger(CoursesTest.class);
        logger.info("Start driver");

        List<String> queryParams = new ArrayList<>();
        queryParams.add(String.format("categories=%s", LessonsCategoryData.TESTING.name().toLowerCase()));

        this.catalogCoursesPage = new CatalogCoursesPage(driver);
        catalogCoursesPage.open(queryParams);
        logger.info("Open catalog of courses");
    }

    @AfterEach
    public void driverQuit() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Quit driver");
    }

    @Test
    public void checkDataOnCoursesPageTest() throws IOException {
        CoursesTileNumbers(); // Проверка количества курсов в разделе Тестирование

        // Проверка информации о каждом курсе в каталоге курсов
        for(int i = 1; i < catalogCoursesPage.getCoursesNumber(); i++) {
            // Проверка названия
            String expectedHeader = catalogCoursesPage.getCourseNameByIndex(i);
            catalogCoursesPage.checkHeaderCourseByIndex(i, expectedHeader);
            logger.info(String.format("Header of course '%s' is checked - '%s'", i, expectedHeader));
            // Проверка описания
            catalogCoursesPage.checkDescriptionCourseByIndex(i);
            logger.info(String.format("Description of course '%s' is checked", i));
            // Проверка длительности обучения
            String expectedCourseDuration = catalogCoursesPage.getCourseDuration(i);
            catalogCoursesPage.checkCourseDuration(i, expectedCourseDuration);
            logger.info(String.format("Duration of course '%s' is checked", i));
            // Проверка формата обучения
            catalogCoursesPage.checkCourseFormat(i, "Онлайн");
            logger.info(String.format("Format of course '%s' is checked", i));
        }
        // Переход на рандомную карточку курса
        String expectedHeaderRandomCourse = catalogCoursesPage.clickRandomCourseTile();
        DetailsCoursePage detailsCoursePage = new DetailsCoursePage(driver, "");
        logger.info(String.format("Details of course '%s' is opened", expectedHeaderRandomCourse));
        // Проверка карточки курса
        // Проверка названия
        detailsCoursePage.checkTitleCourse(expectedHeaderRandomCourse);
        logger.info("Header of course is checked");
        // Проверка описания, длительности и формата обучения
        detailsCoursePage.checkDetailsCardCourse();
        logger.info("Description, duration and format of course is checked");
    }

    private void CoursesTileNumbers() {
        catalogCoursesPage.coursesTilesNumberShouldBeSameAs(10);
        logger.info("Number of courses is calculated and it is 10");
    }

}