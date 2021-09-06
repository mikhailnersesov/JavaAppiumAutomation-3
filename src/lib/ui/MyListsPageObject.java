package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject{

    public static final String CREATE_BUTTON = "org.wikipedia:id/item_container";
    public static final String ARTICLE_BY_TITLE_TPL = "//android.widget.TextView[@text='Java (programming language)']";

    public MyListsPageObject(AppiumDriver driver){
        super(driver);
    }

    public void FindCreateButton(){
        this.waitForElementAndClick(
                By.id(CREATE_BUTTON),
                "Cannot find 'Create new' button",
                5
        );
    }

    public void SwipeByArticleToDelete(){
        this.swipeElementToLeft(
                By.xpath(ARTICLE_BY_TITLE_TPL),
                "Cannot swipe left to delete the element"
        );
    }
    public void waitForArticleToDisappearByTitle() {
        this.waitForElementNotPresent(
                By.xpath(ARTICLE_BY_TITLE_TPL),
                "Item was not deleted properly",
                5
        );
    }

}
