package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCaseSauceLabs extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumUrl = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "com.swaglabsmobileapp");
        capabilities.setCapability("appActivity", ".MainActivity");
        capabilities.setCapability("app", "C:/Users/Mikhail Nersesov/Desktop/JavaAppiumAutomation-2/apks/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        capabilities.setCapability("noReset", true);

        driver = new AndroidDriver(new URL(AppiumUrl), capabilities);
    }

    @Override
    protected void tearDown() throws Exception {

        driver.quit();

        super.tearDown();
    }
}
