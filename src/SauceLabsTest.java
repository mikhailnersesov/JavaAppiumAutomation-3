import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

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


}
