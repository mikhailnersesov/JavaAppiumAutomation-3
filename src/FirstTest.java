import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/Users/Mikhail Nersesov/Desktop/JavaAppiumAutomation-2/apks/org.wikipedia.apk");
        capabilities.setCapability("noReset", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {

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

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by Java",
                15
        );

        System.out.println("First test run completed");
    }


    @Test
    public void testCancelSearch() {
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

    @Test
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

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title'",
                15
        );

        String article_element = title_element.getAttribute("text");

        Assert.assertEquals(
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
                "Appium",
                "Cannot find 'Search…' input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' in search",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title'",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20
        );
    }

    @Test
    public void testSwipeTillTrendingHeaderAndCountOpenArticleAndSwipeToTheEnd(){
        waitForElementPresent(
                By.id("org.wikipedia:id/search_container"),
                "Search field not found",
                5
        );

        swipeUpToFindElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_card_header_title'][@text='Trending']"),
                "'Trending' Header not found",
                20
        );

        List<WebElement> Elements = driver.findElements(By.id("org.wikipedia:id/view_list_card_item_title"));

        System.out.println("Number of Trending articles equals to: " + Elements.size());

        waitForElementPresent(
                By.id("org.wikipedia:id/view_list_card_item_title"),
                "item title not found",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Boris')]"),
                "Boris title not found",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title'",
                15
        );

        swipeUpToFindElement(
                By.id("content_block_0"),
                "Cannot find the block",
                20
        );

        waitForElementPresent(
                By.xpath("//*[contains(@text,'since July 2019')]"),
                "Cannot find the part of text'",
                15
        );
        System.out.println("Test run - successfull!");
    }

    @Test
    public void testSaveFirstArticleToMyList(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
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

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/create_button"),
                "Cannot find 'Create new' button",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot clean the 'Name of the list' field",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot find 'Name of the list' input",
                5
        );

        waitForElementAndClear(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot press 'OK' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );

//workaround IF only 1 list:
        waitForElementAndClick(
                By.id("org.wikipedia:id/item_container"),
                "Cannot find 'Create new' button",
                5
        );
//        waitForElementAndClick(
//                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Learning programming']"),//By.xpath("//android.widget.TextView[@text='Learning programming']"),//" + name_of_folder + "
//                "Cannot find created folder",
//                5
//        );

        swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot swipe left to delete the element"
        );

        waitForElementNotPresent(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Item was not deleted properly",
                5
        );

        System.out.println("Test run - successfull!");
    }

    @Test
    public void testAmountOfNotEmptySearch(){

      waitForElementAndClick(
              By.id("org.wikipedia:id/search_container"),
              "Cannot find the Search field",
              5
      );

      String name_of_search_item = "Linkin Park discograph";
      waitForElementAndSendKeys(
        By.id("org.wikipedia:id/search_src_text"),
        name_of_search_item,
              "Cannot type in the search phrase",
              5
      );

      String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
      waitForElementPresent(
              By.xpath(search_result_locator),
              "Cannot find any search results for" + search_result_locator,
              15
      );

      int amount_of_search_results = getAmountOfElements(
              By.xpath(search_result_locator)
      );

      Assert.assertTrue(
              "We found too few results",
              amount_of_search_results >0
      );

        System.out.println("Test run - successfull!");

    }

    @Test
    public void testAmountEmptySearch(){

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                5
        );

        String search_line = "Lasdlkhjd";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot write the input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label ="//*@text='No result found'";
        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request" + search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "Results found by request: " + search_line
        );


    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
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

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) { //method to clear the text we have written
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private WebElement assertElementHasText(By by, String value, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
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

    private int getAmountOfElements(By by){
     List elements = driver.findElements(by);
     return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0){
            String default_message = "An element'" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }

    }

}
