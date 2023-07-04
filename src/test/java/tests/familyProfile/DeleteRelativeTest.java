package tests.familyProfile;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.familyProfile.AddNewRelativesPopup;
import pages.familyProfile.DeleteRelativePopup;
import pages.familyProfile.FamilyProfilePage;
import tests.TestCase;

public class DeleteRelativeTest extends TestCase {

    String name = "Phạm Hồng Hà";
    String birthday = "20/12/1999";
    String gender = "Nữ";
    String phoneNumber = "0977986532";
    String relationship = "Con";
    @BeforeClass
    public void navigateToFamilyProfilePage() {
        HomePage homePage = new HomePage(testBasic.driver);
        homePage.clickIconProfile();
        FamilyProfilePage familyProfilePage = homePage.clickFamilyProfile();
    }

    public void addNewRelatives(String name, String birthday, String gender, String phoneNumber, String relationship) {
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        AddNewRelativesPopup addNewRelativesPopup = familyProfilePage.clickAddNewRelatives();
        addNewRelativesPopup.showPopupProfileFamily();
        addNewRelativesPopup.addNewRelatives(name, birthday, gender, phoneNumber, relationship);
    }
    @Test
    public void deleteRelativesSuccessfully(){
        addNewRelatives(name, birthday, gender, phoneNumber, relationship);
        DeleteRelativePopup deleteRelativePopup = new DeleteRelativePopup(testBasic.driver);
        deleteRelativePopup.deleteRelativesSuccessfully();
        testBasic.driver.findElement(By.xpath("//div[@id='toast-container']"));

    }

}
