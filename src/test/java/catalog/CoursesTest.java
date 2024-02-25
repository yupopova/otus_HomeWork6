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
    }

    @Test
    public void checkDataOnCoursesPage() throws IOException {
        for(int i = 1; i < catalogCoursesPage.getCoursesNumber(); i++) {
            String expectedHeader = catalogCoursesPage.getCourseNameByIndex(i);
            String expectedCourseDuration = catalogCoursesPage.getCourseDuration(i);
            catalogCoursesPage.checkHeaderCourseByIndex(i, expectedHeader);
            catalogCoursesPage.checkDescriptionCourseByIndex(i);
            catalogCoursesPage.checkCourseDuration(i, expectedCourseDuration);
            catalogCoursesPage.checkCourseFormat(i, "Онлайн");
        }

        catalogCoursesPage.clickRandomCourseTile();
        DetailsCoursePage detailsCoursePage = new DetailsCoursePage(driver, "");
       // detailsCoursePage.checkTitleCourse()

    }
}
