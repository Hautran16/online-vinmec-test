package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.familyProfile.FamilyProfilePage;

public class HomePage extends Page {
	By iconProfile = By.id("user-shortcut");
	By btnLogin = By.xpath("//*[@id = 'user-shortcut']//*[@id = 'sign-in']");
	By btnSignUp = By.id("sign-up");

	By btnClose = By.xpath("//div[@class = 'popup-close']");

	By txtFamilyProfile = By.xpath("//div[@id = 'user-shortcut']//div[1]//div[1]//a[1]");
	
	public HomePage(WebDriver dr) {
		super(dr);
	}

	public void clickBtnClose()  {
		if(driverWeb.findElement(btnClose).isDisplayed() == true){
			driverWeb.findElement(btnClose).click();
		}
	}
	public void clickIconProfile(){
		waitElementForVisible(iconProfile);
		driverWeb.findElement(iconProfile).click();
	}
	public LoginPage clickLoginItem() {
		clickIconProfile();
		waitElementForVisible(btnLogin);
		driverWeb.findElement(btnLogin).click();
		return new LoginPage(driverWeb);
	}

	public FamilyProfilePage clickFamilyProfile(){
		waitElementForVisible(txtFamilyProfile);
		driverWeb.findElement(txtFamilyProfile).click();
		return new FamilyProfilePage(driverWeb);
	}


	public boolean txtFamilyProfileDisplayed() {
		clickIconProfile();
		waitElementForVisible(txtFamilyProfile);
		return elementDisplayed(txtFamilyProfile);
	}

}
 