package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;

public class SearchPageObjectSauceLabs extends MainPageObjectSauceLabs {

    private static final String
            LOGIN_INIT_ELEMENT_VIEW = "//android.view.ViewGroup",
            LOGIN_INIT_ELEMENT_CONTENT = "[@content-desc='test-LOGIN']",
            LOGIN_INPUT_WIDGET = "//android.widget.EditText",
            LOGIN_INPUT_USERNAME = "[@content-desc='test-Username']",
            LOGIN_INPUT_PASSWORD = "[@content-desc='test-Password']",
            MENU_CART = "[@content-desc='test-Cart drop zone']",

    public SearchPageObjectSauceLabs(AppiumDriver driver) {
        super(driver);
    }

    public void Login(String login_username, String login_password) {
        //Verify to be on the start screen: find Login Button
        this.waitForElementPresent(By.xpath(LOGIN_INIT_ELEMENT_VIEW + LOGIN_INIT_ELEMENT_CONTENT), "Cannot find the 'Login' button on the Login Screen after clicking:LOGIN_INIT_ELEMENT", 5);
        //type in Username
        this.waitForElementAndSendKeys(By.xpath(LOGIN_INPUT_WIDGET + LOGIN_INPUT_USERNAME), login_username, "Cannot type in the username", 5);
        //type in Password
        this.waitForElementAndSendKeys(By.xpath(LOGIN_INPUT_WIDGET + LOGIN_INPUT_PASSWORD), login_password, "Cannot type in the username", 5);
        //click Login Button
        this.waitForElementAndClick(By.xpath(LOGIN_INIT_ELEMENT_VIEW + LOGIN_INIT_ELEMENT_CONTENT), "Cannot find the 'Login' button on the Login Screen after clicking:LOGIN_INIT_ELEMENT", 5);
    }

    public void PutIntoCart() {
        //verify to login successfully: find the Products Header
        this.waitForElementPresent(By.xpath(LOGIN_INIT_ELEMENT_VIEW + MENU_CART), "Cannot find the 'Products' header on the Main Screen", 5);
        //scroll till the item with the price 7.99
        this.swipeUpToFindElement(By.xpath("//android.widget.TextView[@content-desc='test-Price'][@text='$7.99']"), "'Onecie t-shirt($7.99)' item not found", 20);
        //click on the item 7.99
        this.waitForElementAndClick(By.xpath("//android.widget.TextView[@content-desc='test-Price'][@text='$7.99']"), "Cannot click to open 'Onecie t-shirt($7.99)' item", 5);
        //wait till the screen changes: no more price
        //is it a good practice to verify if the screen you were leaving is gone instead of only checking if the new screen is there?
        this.waitForElementNotPresent(By.xpath("//android.widget.TextView[@content-desc='test-Price'][@text='$7.99']"), "Item was not opened and price stays", 5);
        //wait for the item details to be shown
        this.waitForElementPresent(By.xpath("//android.view.ViewGroup[@content-desc='test-Image Container']"), "Item details screen was not opened", 5);
        //swipe till the button "Add to cart"
        this.swipeUpToFindElement(By.xpath("//android.view.ViewGroup[@content-desc='test-ADD TO CART']"), "'Add to cart' button not found after a swipe to the bottom", 20);
        //click "add to cart" button
        this.waitForElementAndClick(
                By.xpath("//android.view.ViewGroup[@content-desc='test-ADD TO CART']"),
                "Cannot click 'Add to cart' button",
                5
        );
        this.waitForElementAndClick(
                By.xpath("//android.view.ViewGroup[@content-desc='test-Cart']/android.view.ViewGroup/android.widget.ImageView"),
                "Cannot click 'Cart' symbol",
                5
        );
    }

    public void CleanTheCart() {

        String cart_item_locator = "//android.view.ViewGroup[@content-desc='test-Amount']/android.widget.TextView";

        //find how many items are in the cart
        int amount_of_search_results = this.getAmountOfElements(
                By.xpath(cart_item_locator)
        );
        //should be 1 or 0
        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results < 2
        );
        //swipe the item to the left to delete it
        this.swipeElementToLeft(
                By.xpath("//android.view.ViewGroup[@content-desc='test-Description']/android.widget.TextView[1]"),
                "Cannot swipe left to delete the element"
        );
        //click on delete icon to delete the item
        this.waitForElementAndClick(
                By.xpath("//android.view.ViewGroup[@content-desc='test-Delete']/android.view.ViewGroup/android.view.ViewGroup"),
                "Cannot click 'Add to cart' button",
                5
        );
        //check if the item not present any more
        this.waitForElementNotPresent(
                By.xpath(cart_item_locator),
                "Item was not deleted properly",
                5
        );
    }
}
