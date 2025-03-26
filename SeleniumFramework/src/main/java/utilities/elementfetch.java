package utilities;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import basetest.baseclass;

public class elementfetch {

	public WebElement getWebElement(String identifierType,String identifierValue) {
		switch(identifierType) {
		case "XATH":
			return baseclass.driver.findElement(By.xpath(identifierValue));
		case "CSS":
			return baseclass.driver.findElement(By.cssSelector(identifierValue));
		case "ID":
			return baseclass.driver.findElement(By.id(identifierValue));
		case "NAME":
			return baseclass.driver.findElement(By.name(identifierValue));
		case "TAGNAME":
			return baseclass.driver.findElement(By.tagName(identifierValue));
		case "LINKTEXT":
			return baseclass.driver.findElement(By.linkText(identifierValue));
		case "PARTIALLINKTEXT":
			return baseclass.driver.findElement(By.partialLinkText(identifierValue));
			
			default: 
			  return null;
			
		}
	}
	public List<WebElement> getWebElements(String identifierType,String identifierValue) {
		switch(identifierType) {
		case "XATH":
			return baseclass.driver.findElements(By.xpath(identifierValue));
		case "CSS":
			return baseclass.driver.findElements(By.cssSelector(identifierValue));
		case "ID":
			return baseclass.driver.findElements(By.id(identifierValue));
		case "NAME":
			return baseclass.driver.findElements(By.name(identifierValue));
		case "TAGNAME":
			return baseclass.driver.findElements(By.tagName(identifierValue));
		case "LINKTEXT":
			return baseclass.driver.findElements(By.linkText(identifierValue));
		case "PARTIALLINKTEXT":
			return baseclass.driver.findElements(By.partialLinkText(identifierValue));
			
			default: 
			  return null;
			
		}
	}

}
