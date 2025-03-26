package utilities;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basetest.baseclass;

public class TestUtils extends baseclass {

	public static void clickElement(WebDriver driver, String locatorType, String locatorValue, String elementName) {
		WebElement element = waitForElementToLoad(driver,locatorType, locatorValue, elementName);
		try {
			element.click();
			reportLog("clicked on: " + elementName);

		} catch (Throwable clickLinkException) {

			reportLog("Fail : Error while clicking on - '" + elementName + "' : " + clickLinkException.getMessage());
		}
	}

	public static void enterText(WebDriver driver, String locatorType, String locatorValue, String elementName,
			String inputText) {
		WebElement ele = waitForElementToLoad(driver,locatorType, locatorValue, elementName);
		
		try {
			ele.sendKeys(inputText);
			reportLog("Entered " + "inputText " + "text into : " + elementName);


		} catch (Throwable enterTextException) {

			reportLog("Fail : Error while Entering value into - '" + elementName + "' : "
					+ enterTextException.getMessage());
		}
	}
	
	public static WebElement waitForElementToLoad(WebDriver driver, String locatorType, String locatorValue,
			String elementName) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			if (locatorType.equalsIgnoreCase("XPATH")) {
				element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorValue)));
			} else if (locatorType.equalsIgnoreCase("CSS")) {
				element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locatorValue)));
			}

			reportLog("waiting for " + elementName+ " to load on WebPage");
			return element;

		} catch (Throwable ElementNotFoundException) {

			reportLog("Fail : Error while clicking on - '" + elementName + "' : "+ ElementNotFoundException.getMessage());
			return null;
		}
	}

	public static List<WebElement> getListOfElements(WebDriver driver, String locatorType, String locatorValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		if (locatorType.equalsIgnoreCase("XPATH")) {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("CSS")) {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(locatorValue)));
		}
		return null;
	}

}
