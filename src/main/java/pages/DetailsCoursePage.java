package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DetailsCoursePage extends AbsBasePage {

    public DetailsCoursePage(WebDriver driver, String coursesPath) {
        super(driver, String.format("/lessons/%s", coursesPath));
    }

    @FindBy(xpath = "//h1[contains(text(), '')]")
    private WebElement titleCardCourseLocator;

    @FindBy(xpath = "//div/following-sibling::p[contains(text(), 'месяц')]")
    private WebElement durationCardCourseLocator;

    @FindBy(xpath = "//h1/following-sibling::div[contains(text(), '')]")
    private WebElement descriptionCardCourseLocator;

    @FindBy(xpath = "//p[contains(text(), 'Онлайн')]")
    private WebElement formatCardCourseLocator;

    public void checkTitleCourse(String expectedTitleCourse) {
        String result = titleCardCourseLocator.getText();
        Assertions.assertEquals(expectedTitleCourse, result, "Title of course is empty");
    }

    private DetailsCoursePage checkDescriptionCourseIsNotEmpty() {
        String descriptionCardCourse = descriptionCardCourseLocator.getText();
        Assertions.assertFalse(descriptionCardCourse.isEmpty(), "Description of course is empty");
        return this;
    }

    private DetailsCoursePage checkDurationCourseIsNotEmpty() {
        Assertions.assertFalse(durationCardCourseLocator.getText().isEmpty(), "Duration of course is empty");
        return this;
    }

    private DetailsCoursePage checkFormatCourse() {
        Assertions.assertFalse(formatCardCourseLocator.getText().isEmpty(), "Format of course is empty");
        return this;
    }

    public DetailsCoursePage checkDetailsCardCourse() {
        this.checkDescriptionCourseIsNotEmpty()
                .checkDurationCourseIsNotEmpty()
                .checkFormatCourse();

        return this;
    }
}

