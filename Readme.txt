import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver; // such dataType is imported extra from java client
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities; // such dataType is imported from selenium
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {
    private AppiumDriver driver; // There is a dataType "AppiumDriver", while there are many of them. We announces a new one, called "driver"

    @Before // markers for the JUnit to understand how and where to start, f.e. TestNG has "@BeforeTest"
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");//Mobile OS version
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/Users/Mikhail Nersesov/Desktop/JavaAppiumAutomation-2/apks/org.wikipedia.apk");
		capabilities.setCapability("noReset",true); //отключает обрубание теста/не выключает аппиум "Do not stop app, do not clear app data, and do not uninstall apk", избегаем ошибки с отсутвием соединения

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); // start the AndroidDriver with such capabilities. AppiumDriver variable "driver" creates new object AndroidDriver
        /*
        to turn on the Android driver;
        Host "0.0.0.0" means we are hosting on the local server
        linking "capabilites" to the arguments of the new object we send the info with which capabilites the androiddriver should start
         */
    }

    @After
    public void tearDown() {
        driver.quit();
    } // variable "driver" linked to the method to quit

    @Test
    public void firstTest() {

        waitForElementAndClick(
                "//*[contains(@text,'Search Wikipedia')]",
                "Cannot find 'Search Wikipedia' input",
                5
        )

        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text,'Search Wikipedia')]");// все такие элементы имеют класс/тип данных WebElement; "//" - любая вложенность элемента, то есть не обязательно с начала структуры; "*" - любой элемент
        element_to_init_search.click();

        //WebElement element_to_enter_search_line = driver.findElementByXPath("//*[contains(@text,'Search…')]");

        WebElement element_to_enter_search_line = waitForElementPresentByXpath(
                "//*[contains(@text,'Search…')]",
                "Cannot find the search input");
// using in the test code the method defined later (waitForElementPresentByXpath)
        element_to_enter_search_line.sendKeys("Java");

        waitForElementPresentByXpath(
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']",
                "Cannot find 'Object-oriented programming language' topic searching by Java",
                15
        );

        System.out.println("First test run completed");
    }
	
	    @Test
    public void testCancelSearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot clear 'Search…' field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'X' to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "'X' is still present on the page'",
                5
        );
    }
	
	@Test//тест сравнивает название статьи с ожидаемым и отдает ошибку если оно не совпадает
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        WebElement title_element = waitForElementPresent(//получаем заголовок статьи в наши тесты, создав переменную
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title'",
                15
        );

        String article_element = title_element.getAttribute("text"); //получаем название статьи через вызов аттрибута сохранненого вебэлемента

        Assert.assertEquals(// assertEquals: assertEquals("failure - strings are not equal", "text", "text"); assert.True: assertTrue("failure - should be true", true);
                "We see unexpected title!",
                "Java (programming language)",
                article_element
        );

        System.out.println("Result for testCompareArticleTitle: " + article_element);
    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title'",
                15
        );

        swipeUp(
                2000 //the more secons, the slower the swipe
        );
		
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar']//*[@class='android.widget.ImageButton']"),//@content-desc='Navigate up'
                "Cannot find 'Close' button",
                5
        );
    }

    //idea: increasing test stability with "wait"; tech: create template to be filled
    private WebElement waitForElementPresentByXpath(// create method looking for an element by its Xpath and waiting for it appear
                                                    String xpath, // parameter showing the method which xpath to look for
                                                    String error_message, // message if did not come in the defined period
                                                    long timeoutInSeconds // timeout to wait for the element
    ) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);// on the basis of the selenium class, create a new object using arguments AndroidDriver & timeOut
        wait.withMessage(error_message + "/n"); // method wait using the "withMessage" method and error_message; tech:"/n" to start at the new line
        By by = By.xpath(xpath); // defining parameter to wait for. "By" is naem for the locator or object to be taken by "Find method". By.name - search by text; By.classname - search by class.
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)// we are waiting till this condition fullfils and we receive desired xpath
        );
    }

    private WebElement waitForElementPresentByXpath(String xpath, String error_message) {
        return waitForElementPresentByXpath(xpath, error_message, 5);// timeoutTime is now constant
    }

    private WebElement waitForElementAndClick (String xpath, String error_message,long timeoutInSeconds){
            WebElement element = waitForElementPresentByXpath(xpath, error_message, timeoutInSeconds);
            element.click();
            return.element;
            // тесты дожидатся элемента, а потом клик
        }

    private WebElement waitForElementAndSendKeys (String xpath,String value,String error_message,long timeoutInSeconds){
            WebElement element = waitForElementPresentByXpath(xpath, error_message, timeoutInSeconds);
            element.sendKeys(value);
            return.element;
            // тесты дожидатся элемента, а потом клик
        }
		
	private WebElement waitForElementPresentById(String id, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        By by = By.id(id);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementByIdAndClick(String id, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresentById(id, error_message, timeoutInSeconds); //сначала создали waitForElementPresentById для нахождения элемента и теперь используем чтобы от него оттолкнувшись кликать: метод найти, метод кликнуть, в класс загнал.
        element.click();
        return element;
    }
	
	private boolean waitForElementNotPresent(String id, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        By by = By.id(id);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by) // element should be invisiable
        );
		
	private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){ //method to clear the text we have written
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }
	
    private WebElement assertElementHasText(By by, String value, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
//swipe from the floor to the roof
    protected void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize(); //sending to the variable "size" parameters of our screen
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8); // 80% from top to bottom (a little bit uper from the bottom of the screen)
        int end_y = (int) (size.height * 0.2);
        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform(); //"perform" send all the line of actions to be proceeded
    }

}

// question: how to automate the start of appium server?
// how to clear the app to begin from the cleansetup?