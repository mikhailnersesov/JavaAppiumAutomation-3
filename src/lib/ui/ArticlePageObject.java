package lib.ui;


import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']";//"//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.id(TITLE), "Cannot find article title on page", 15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "Cannot find the end of the article", 20);
    }

    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find 'More' button",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find 'Add to reading list' button",
                5
        );

        this.waitForElementAndClick(
                By.id("org.wikipedia:id/create_button"),
                "Cannot find 'Create new' button",
                5
        );

        this.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot clean the 'Name of the list' field",
                5
        );

        this.waitForElementAndClick(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find 'Create new' button",
                5
        );

        this.waitForAndroidElementAndSetValue(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot find " + name_of_folder + " input",
                5
        );

        this.waitForElementAndClear(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot press 'OK' button",
                5
        );
    }

}
