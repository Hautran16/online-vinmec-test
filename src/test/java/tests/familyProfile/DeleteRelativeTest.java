package tests.familyProfile;

import model.RelativeModel;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.familyProfile.AddNewRelativesPopup;
import pages.familyProfile.DeleteRelativePopup;
import pages.familyProfile.FamilyProfilePage;
import tests.TestCase;

import java.io.IOException;
import java.lang.reflect.Method;

public class DeleteRelativeTest extends TestCase {

    public String excelFilePath = "src/test/resources/testData/testCases/AddNewRelatives.xlsx";

    @BeforeClass
    public void navigateToFamilyProfilePage() {
        HomePage homePage = new HomePage(testBasic.driver);
        homePage.clickIconProfile();
        homePage.clickFamilyProfile();
        addNewRelatives();
    }
    public void addNewRelatives() {
        RelativeModel relativeModel = new RelativeModel("Hậu","20/12/1994","Nam","0975321456","Con");
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        AddNewRelativesPopup addNewRelativesPopup = familyProfilePage.clickAddNewRelatives();
        addNewRelativesPopup.showPopupProfileFamily();
        addNewRelativesPopup.addNewRelatives();
    }

    @Test()
    public void deleteRelativesSuccessfully(){
        DeleteRelativePopup deleteRelativePopup = new DeleteRelativePopup(testBasic.driver);
        deleteRelativePopup.deleteRelativesSuccessfully();
        FamilyProfilePage familyProfilePage= new FamilyProfilePage(testBasic.driver);
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.hidePopupProfileFamily();
//        familyProfilePage.waitElementForVisible(By.xpath("//div[@class='toast-message']"));
//        String actualSuccessMess = testBasic.driver.findElement(By.xpath("//div[@class='toast-message']")).getText();
//        familyProfilePage.verifyTextPresent(actualSuccessMess, successMessage);
    }

}
