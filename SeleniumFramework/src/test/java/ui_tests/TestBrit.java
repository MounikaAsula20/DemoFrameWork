package ui_tests;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import basetest.baseclass;
import pageobjects.homePageElements;
import utilities.TestUtils;
import utilities.elementfetch;

public class TestBrit  extends baseclass {
	elementfetch element = new elementfetch();
	
  @Test
  public void verifySearchFunctionality()throws InterruptedException {
	 
	  try {			
			//Accept all cookies popup
			TestUtils.clickElement(driver, "XPATH", homePageElements.cookies, "Accept Cookies");
			
			//wait for elements to load
			WebElement element=TestUtils.waitForElementToLoad(driver, "XPATH", homePageElements.search_culturetext, "waiting for Search Button...");

			//click on search button to open search input field
			TestUtils.clickElement(driver, "XPATH", homePageElements.home_Search, "Search Button");

			//input text into search input 
			TestUtils.enterText(driver, "XPATH", homePageElements.search_item, "Serach Input Field", "IFRS 17");

			//get the list of search results
			List<WebElement> searchResultsList = TestUtils.getListOfElements(driver, "XPATH",
					homePageElements.search_result);

			// assert search results count is 5 or not
			reportLog("Verifying Search Results count is Matched or not...");
			Assert.assertEquals(searchResultsList.size(), 5, "Serach Results count is Matched.");
			reportLog("Verified Search Results count is Matched.");

			// Assert search result list contain specific text
			String expectedText = "Interim results for the six months ended 30 June 2022";

			//loop through search results list items
			boolean isTextMatched = false;
			for (WebElement result : searchResultsList) {
				if (result.getText().equals(expectedText)) {
					isTextMatched = true;
					break;
				}
			}
			
			//verify search results contain specific text
			reportLog("Verifying Search Results contain " + expectedText + " or not...");
			Assert.assertTrue(isTextMatched,
					"Expected text '" + expectedText + "' was not found in the search results.");
			reportLog("Verified Search Results contain " + expectedText + " item in the list");
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Test failed due to: " + e.getMessage());
		}
	}
	
  
}
