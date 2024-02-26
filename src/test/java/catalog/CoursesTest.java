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
    public void CoursesTileNumbers() {
        catalogCoursesPage.coursesTilesNumberShouldBeSameAs(10);
        logger.info("Number of courses is calculated");
    }

    @Test
    public void checkDataOnCoursesPage() throws IOException {
        for(int i = 1; i < catalogCoursesPage.getCoursesNumber(); i++) {
            String expectedHeader = catalogCoursesPage.getCourseNameByIndex(i);
            String expectedCourseDuration = catalogCoursesPage.getCourseDuration(i);
            catalogCoursesPage.checkHeaderCourseByIndex(i, expectedHeader);
            logger.info("Header of courses  is checked");
            catalogCoursesPage.checkDescriptionCourseByIndex(i);
            logger.info("Description of courses is checked");
            catalogCoursesPage.checkCourseDuration(i, expectedCourseDuration);
            logger.info("Duration of courses is checked");
            catalogCoursesPage.checkCourseFormat(i, "Онлайн");
        }

        String expectedHeaderCourse = catalogCoursesPage.clickRandomCourseTile();
        DetailsCoursePage detailsCoursePage = new DetailsCoursePage(driver, "");
        detailsCoursePage.checkTitleCourse(expectedHeaderCourse);
        detailsCoursePage.checkDetailsCardCourse("Онлайн");
    }
}
