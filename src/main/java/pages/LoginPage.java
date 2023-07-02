package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class LoginPage extends Page {
	By txtEmail = By.id("email");
	By txtPassword = By.id("password");

	By btnLogin = By.id("btn-login");
	public LoginPage(WebDriver dr) {
		super(dr);
		this.driverWeb = dr;
	}
	public void inputEmail(String email){
		driverWeb.findElement(txtEmail).sendKeys(email);
	}

	public void inputPassword(String password){
		driverWeb.findElement(txtPassword).sendKeys(password);
	}

	public void clickLogin(){
		driverWeb.findElement(btnLogin).click();
	}

	public void clearData(){
		driverWeb.findElement(txtEmail).clear();
		driverWeb.findElement(txtPassword).clear();
	}

	public void loginSuccessfully(String email, String password){
		HomePage homePage = new HomePage(driverWeb);
		homePage.clickBtnClose();
		homePage.clickIconProfile();
		LoginPage loginPage = homePage.clickLoginItem();
		loginPage.inputEmail(email);
		loginPage.inputPassword(password);
		loginPage.clickLogin();
	}

}
