package pageobjects;

// Interface for storing web element locators on the Home Page
public interface homePageElements {

    // Locator for the search button in the navigation bar
    String home_Search = "//*[@id='wrapper']/form/nav/div[3]/button";

    // Locator for the search input field where the user enters the search term
    String search_item = "//*[@class='header--search']/input";
    
    String search_culturetext = "//span[contains(text(),'4')]";
    
    // Locator for the search results section showing links or items matching the search query
    String search_result = "//div[@class='header--search__results']/div/a";

    // Locator for the "Allow All Cookies" button in the cookie consent dialog
    String cookies = "//*[@id=\"CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll\"]";

}