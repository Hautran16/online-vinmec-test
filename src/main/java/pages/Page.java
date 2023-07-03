package pages;

import common.TestBasic;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class Page {
	protected WebDriver driverWeb;
	
	public Page (WebDriver dr) {
		this.driverWeb = dr;
	}
	
	public void verifyElementDisplay(By elementLocated) {
		driverWeb.findElement(elementLocated).isDisplayed();
	}

	public void verifyTextPresent(String actualResult, String expectedResult) {
		assertEquals(actualResult, expectedResult);
	}

	public void uploadImg(By elementLocated, String imageFolderPath) {
		driverWeb.findElement(elementLocated).sendKeys(imageFolderPath);
	}
	
	public void selectElemetFromDropdown(By elementLocated, String textInput) {
		Select selectOption = new Select(driverWeb.findElement(elementLocated));
		selectOption.selectByVisibleText(textInput);
	}
	
	public boolean elementDisplayed(By elementLocated) {
		boolean result = driverWeb.findElement(elementLocated).isDisplayed();
		return result;
	}
	
	public boolean elementSelected(By elementLocated) {
		boolean result = driverWeb.findElement(elementLocated).isSelected();
		return result;
	}
	
	public String[] splitString(String originalString, String str) {
		String result[] = originalString.split(str);
		return result;
	}

	public Long getTimeOutFromFileConfig(){
		TestBasic testBasic = new TestBasic();
		Long timeOut = testBasic.getTimeOutFromFileConfig();
		return timeOut;
	}

	public WebElement waitElementForVisible(By elementLocated){
		Long timeOut = getTimeOutFromFileConfig();
		driverWeb.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
		WebElement myDynamicElement = driverWeb.findElement(elementLocated);
		return myDynamicElement;
	}

	public void waitForPageToLoad(){
		Long timeOut = getTimeOutFromFileConfig();
		driverWeb.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
	}

	public void waitLoadingHidden(){
		WebDriverWait wait = new WebDriverWait(driverWeb, Duration.ofSeconds(getTimeOutFromFileConfig()));
		wait.until(ExpectedConditions.and(
				ExpectedConditions.presenceOfElementLocated(By.id("load")),
				ExpectedConditions.attributeToBe(By.id("load"), "style", "")
		));
		wait.until(ExpectedConditions.and(
				ExpectedConditions.presenceOfElementLocated(By.id("load")),
				ExpectedConditions.attributeToBe(By.id("load"), "style", "visibility: hidden;")
		));
	}

}
 