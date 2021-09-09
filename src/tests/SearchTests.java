package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {
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

//    @Test
//    public void testSwipeTillTrendingHeaderAndCountOpenArticleAndSwipeToTheEnd() {
//        MainPageObject.waitForElementPresent(
//                By.id("org.wikipedia:id/search_container"),
//                "Search field not found",
//                5
//        );
//
//        MainPageObject.swipeUpToFindElement(
//                By.xpath("//*[@resource-id='org.wikipedia:id/view_card_header_title'][@text='Trending']"),
//                "'Trending' Header not found",
//                20
//        );
//
//        List<WebElement> Elements = driver.findElements(By.id("org.wikipedia:id/view_list_card_item_title"));
//
//        System.out.println("Number of Trending articles equals to: " + Elements.size());
//
//        MainPageObject.waitForElementPresent(
//                By.id("org.wikipedia:id/view_list_card_item_title"),
//                "item title not found",
//                5
//        );
//
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[contains(@text,'Boris')]"),
//                "Boris title not found",
//                5
//        );
//
//        MainPageObject.waitForElementPresent(
//                By.id("org.wikipedia:id/view_page_title_text"),
//                "Cannot find article title'",
//                15
//        );
//
//        MainPageObject.swipeUpToFindElement(
//                By.id("content_block_0"),
//                "Cannot find the block",
//                20
//        );
//
//        MainPageObject.waitForElementPresent(
//                By.xpath("//*[contains(@text,'since July 2019')]"),
//                "Cannot find the part of text'",
//                15
//        );
//        System.out.println("Test run - successfull!");
//    }

    @Test
    public void testAmountOfNotEmptySearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String name_of_search_item = "Linkin Park discograph";
        SearchPageObject.typeSearchLine(name_of_search_item);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found null results",
                amount_of_search_results > 0
        );
        System.out.println("Test run 'testAmountOfNotEmptySearch'- successfull!");

    }

    @Test
    public void testAmountEmptySearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Lasdlkhjd";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultsOfSearch(search_line);

//        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
//        String empty_result_label = "//*@text='No result found'";
//        MainPageObject.waitForElementPresent(
//                By.xpath(empty_result_label),
//                "Cannot find empty result label by the request" + search_line,
//                15
//        );

//        MainPageObject.assertElementNotPresent(
//                By.xpath(search_result_locator),
//                "Results found by request: " + search_line
//        );


    }
}
