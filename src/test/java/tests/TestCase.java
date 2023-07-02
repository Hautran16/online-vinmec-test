package tests;

import common.TestBasic;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import pages.HomePage;
import pages.LoginPage;

public class TestCase {
	public TestBasic testBasic = new TestBasic();

	@Parameters({"browser"})
	@BeforeTest
	public void openWeb(String browser) {
		testBasic.openWebsite(browser);
		testBasic.maximizeBrowser();
	}

	@BeforeClass
	public HomePage loginSuccessfully(){
		LoginPage loginPage = new LoginPage(testBasic.driver);
		String email = testBasic.getEmailFormTestConfigByEnv();
		String password = testBasic.getPasswordFormTestConfigByEnv();
		loginPage.loginSuccessfully(email, password);
		return new HomePage(testBasic.driver);
	}

//	@Parameters("browser")
//	@AfterTest
//	public void closeBrowser(String browser) {
//		testBasic.closeBrowser();
//	}

}
