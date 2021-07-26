import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

        System.out.println("testSearch was successful");
    }


    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

        System.out.println("testCancelSearch was successful");
    }

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        String article_title = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                article_title
        );

        System.out.println("testCompareArticleTitle was successful");
    }

    @Test
    public void testSwipeArticle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();

        System.out.println("Test run 'testSwipeArticle'- successful!");
    }

    @Test
    public void testSwipeTillTrendingHeaderAndCountOpenArticleAndSwipeToTheEnd(){
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_container"),
                "Search field not found",
                5
        );

        MainPageObject.swipeUpToFindElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_card_header_title'][@text='Trending']"),
                "'Trending' Header not found",
                20
        );

        List<WebElement> Elements = driver.findElements(By.id("org.wikipedia:id/view_list_card_item_title"));

        System.out.println("Number of Trending articles equals to: " + Elements.size());

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_list_card_item_title"),
                "item title not found",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Boris')]"),
                "Boris title not found",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title'",
                15
        );

        MainPageObject.swipeUpToFindElement(
                By.id("content_block_0"),
                "Cannot find the block",
                20
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text,'since July 2019')]"),
                "Cannot find the part of text'",
                15
        );
        System.out.println("Test run - successfull!");
    }

    @Test
    public void testSaveFirstArticleToMyList(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_value = "Java";
        SearchPageObject.typeSearchLine(search_value);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);



        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );

//workaround IF only 1 list:
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/item_container"),
                "Cannot find 'Create new' button",
                5
        );
//        waitForElementAndClick(
//                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Learning programming']"),//By.xpath("//android.widget.TextView[@text='Learning programming']"),//" + name_of_folder + "
//                "Cannot find created folder",
//                5
//        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot swipe left to delete the element"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Item was not deleted properly",
                5
        );

        System.out.println("Test run - successfull!");
    }

    @Test
    public void testAmountOfNotEmptySearch(){

        MainPageObject.waitForElementAndClick(
              By.id("org.wikipedia:id/search_container"),
              "Cannot find the Search field",
              5
      );

      String name_of_search_item = "Linkin Park discograph";
        MainPageObject.waitForElementAndSendKeys(
        By.id("org.wikipedia:id/search_src_text"),
        name_of_search_item,
              "Cannot type in the search phrase",
              5
      );

      String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        MainPageObject.waitForElementPresent(
              By.xpath(search_result_locator),
              "Cannot find any search results for" + search_result_locator,
              15
      );

      int amount_of_search_results = MainPageObject.getAmountOfElements(
              By.xpath(search_result_locator)
      );

      Assert.assertTrue(
              "We found too few results",
              amount_of_search_results >0
      );

        System.out.println("Test run 'testAmountOfNotEmptySearch'- successfull!");

    }

    @Test
    public void testAmountEmptySearch(){

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                5
        );

        String search_line = "Lasdlkhjd";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot write the input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label ="//*@text='No result found'";
        MainPageObject.waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request" + search_line,
                15
        );

        MainPageObject.assertElementNotPresent(
                By.xpath(search_result_locator),
                "Results found by request: " + search_line
        );


    }

    @Test
    public void testChangeScreenOrientationOnSearchResults(){

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find the Search field",
                5
        );

        String name_of_search_item = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                name_of_search_item,
                "Cannot type in the search phrase",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searched by: " + name_of_search_item,
                15
        );

        String title_before_rotation = MainPageObject.waitForElementAngGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find the title of the article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = MainPageObject.waitForElementAngGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find the title of the article",
                15
        );

        Assert.assertEquals(
                "Title has changed!",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = MainPageObject.waitForElementAngGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find the title of the article",
                15
        );

        Assert.assertEquals(
                "Title has changed!",
                title_before_rotation,
                title_after_second_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        System.out.println("Test run 'testChangeScreenOrientationOnSearchResults'- successfull!");
    }

    @Test
    public void testSearchArticleInBackground(){
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find the Search field",
                5
        );

        String name_of_search_item = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                name_of_search_item,
                "Cannot type in the search phrase",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searched by: " + name_of_search_item,
                15
        );

        driver.runAppInBackground(5);

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find find article after returning from background",
                15
        );

        System.out.println("Test run 'testChangeScreenOrientationOnSearchResults'- successfull!");
    }

}
