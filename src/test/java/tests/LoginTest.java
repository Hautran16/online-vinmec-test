package tests;

import org.testng.annotations.*;

import pages.HomePage;
import pages.LoginPage;

import java.io.IOException;

public class LoginTest extends TestCase {

    @DataProvider(name = "testData")
    public Object[][] getTestData() throws IOException {
        String excelFilePath = "src/main/resources/testData/testData.xlsx";
        return testBasic.getTestData(excelFilePath, "Sheet1");
    }

    @BeforeClass
    public void beforeClass() {
        HomePage homePage = new HomePage(testBasic.driver);
        homePage.clickLoginItem();
    }

    @Test(dataProvider = "testData")
    public void verifyLoginFail(String email, String password) throws Exception {
        System.out.println("Testing");
        LoginPage loginPage = new LoginPage(testBasic.driver);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
    }

    @AfterMethod
    public void AfterMethod() {
        System.out.println("AfterMethod");
        LoginPage loginPage = new LoginPage(testBasic.driver);
        loginPage.clearData();
    }
}
