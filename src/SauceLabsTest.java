import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class SauceLabsTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "com.swaglabsmobileapp");
        capabilities.setCapability("appActivity", ".MainActivity");
        capabilities.setCapability("app", "C:/Users/Mikhail Nersesov/Documents/Education/Mobile Automation/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        capabilities.setCapability("noReset", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void firstTest() {

        waitForElementPresent(
                By.xpath("//android.view.ViewGroup[@content-desc='test-LOGIN']"),
                "Cannot find the 'Login' button on the Login Screen",
                5
        );

        String login_username = "standard_user";
        waitForElementAndSendKeys(
                By.xpath("//android.widget.EditText[@content-desc='test-Username']"),
                login_username,
                "Cannot type in the username",
                5
        );

        String login_password = "secret_sauce";
        waitForElementAndSendKeys(
                By.xpath("//android.widget.EditText[@content-desc='test-Password']"),
                login_password,
                "Cannot type in the username",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.view.ViewGroup[@content-desc='test-LOGIN']"),
                "Cannot find the 'Login' button on the Login Screen",
                5
        );

        waitForElementPresent(
                By.xpath("//android.view.ViewGroup[@content-desc='test-Cart drop zone']"),
                "Cannot find the 'Products' header on the Main Screen",
                5
        );

        swipeUpToFindElement(
                By.xpath("//android.widget.TextView[@content-desc='test-Price'][@text='$7.99']"),
                "'Onecie t-shirt($7.99)' item not found",
                20
        );

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@content-desc='test-Price'][@text='$7.99']"),
                "Cannot click 'Onecie t-shirt($7.99)' item",
                5
        );

        waitForElementNotPresent(
                By.xpath("//android.widget.TextView[@content-desc='test-Price'][@text='$7.99']"),
                "Item was not deleted properly",
                5
        );
        waitForElementPresent(
                By.xpath("//android.view.ViewGroup[@content-desc='test-Image Container']"),
                "Item details opened",
                5
        );

        swipeUpToFindElement(
                By.xpath("//android.view.ViewGroup[@content-desc='test-ADD TO CART']"),
                "'Add to cart' button not found",
                20
        );

        waitForElementAndClick(
                By.xpath("//android.view.ViewGroup[@content-desc='test-ADD TO CART']"),
                "Cannot click 'Add to cart' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.view.ViewGroup[@content-desc='test-Cart']/android.view.ViewGroup/android.widget.ImageView"),
                "Cannot click 'Add to cart' button",
                5
        );

        String cart_item_locator = "//android.view.ViewGroup[@content-desc='test-Amount']/android.widget.TextView";

        int amount_of_search_results = getAmountOfElements(
                By.xpath(cart_item_locator)
        );

        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results < 2
        );

        swipeElementToLeft(
                By.xpath("//android.view.ViewGroup[@content-desc='test-Description']/android.widget.TextView[1]"),
                "Cannot swipe left to delete the element"
        );

        waitForElementAndClick(
                By.xpath("//android.view.ViewGroup[@content-desc='test-Delete']/android.view.ViewGroup/android.view.ViewGroup"),
                "Cannot click 'Add to cart' button",
                5
        );

        waitForElementNotPresent(
                By.xpath(cart_item_locator),
                "Item was not deleted properly",
                5
        );

        System.out.println("Test succeeded");
    }

    @After
    public void tearDown() {

        driver.quit();
    }

    //Methods

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(2000);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    private int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    protected void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10
        );
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(150)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

}
