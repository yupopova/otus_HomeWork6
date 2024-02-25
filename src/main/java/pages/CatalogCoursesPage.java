package pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.List;

public class CatalogCoursesPage extends AbsBasePage {

    public CatalogCoursesPage(WebDriver driver) {
        super(driver, "/catalog/courses");
    }

    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')][.//h6]")
    private List<WebElement> coursesTiles;

    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')]/h6/following-sibling::div")
    private List<WebElement> courseDuration;

    public void coursesTilesNumberShouldBeSameAs(int number) {
        Assertions.assertEquals(
                number,
                coursesTiles.size(),
                String.format("Number of courses tiles should be %d", number)
        );
    }

    public String clickRandomCourseTile() {
        String expectedHeaderCourse = null;
        if(coursesTiles.size() > 0) {
            WebElement randomCardCourse = faker.options().nextElement(coursesTiles);
            expectedHeaderCourse = randomCardCourse.findElement(By.xpath(".//h6")).getText();

            try {
                randomCardCourse.click();
            } catch (StaleElementReferenceException e) {
                randomCardCourse = driver.findElement(
                        By.xpath("//section//div[not(@style)]/a[contains(@href, '/lessons/')]/h6/div[contains(text(), '"
                                + expectedHeaderCourse + "')]"));
                randomCardCourse.click();
            }
        } else {
            System.out.println("Список курсов пустой");
        }
        return expectedHeaderCourse;
    }

    public int getCoursesNumber() {
        return coursesTiles.size();
    }

    public String getCourseNameByIndex(int index) {
        return coursesTiles.get(--index).findElement(By.xpath(".//h6")).getText();
    }

    public String getCourseDuration(int index) {
        return courseDuration.get(--index).getText();
    }

    private Document getDomPage(int index) throws IOException {
        String url = coursesTiles.get(--index).getAttribute("href");
        return Jsoup.connect(url).get();
    }

    public void checkHeaderCourseByIndex(int index, String expectedHeader) throws IOException {
        Document dom = getDomPage(index);
        Element headerCourserPageElement = dom.selectFirst("h1");

        Assertions.assertEquals(expectedHeader, headerCourserPageElement.text(), "Header of course is empty");
    }

    public void checkDescriptionCourseByIndex(int index) throws IOException {
        Elements elements = getDomPage(index).selectXpath("//h1/following-sibling::div[text()]");
        if (elements.isEmpty()) {
            elements = getDomPage(index).selectXpath("//h1/following-sibling::div/p[text()]");
        }
        Element headerCourserPageElement = elements.get(0);

        Assertions.assertFalse(headerCourserPageElement.text().isEmpty(), "Description of course is empty");
    }

    public void checkCourseDuration(int index, String expectedDuration) throws IOException {
        Element HeaderCoursePageElement = getDomPage(index)
                .selectXpath("//div/following-sibling::p[contains(text(), 'месяц')]")
                .get(0);

        Assertions.assertEquals(expectedDuration
                        .replaceAll("^.*?·\\s*", ""),
                HeaderCoursePageElement.text(), "Duration of course is empty");
    }

    public void checkCourseFormat(int index, String format) throws IOException {
        Element formatCourseElement = getDomPage(index)
                .selectXpath(String.format("//p[contains(text(), '%s')]", format))
                .get(0);

        Assertions.assertFalse(formatCourseElement.text().isEmpty(), "Format оf course is empty");
    }
}
