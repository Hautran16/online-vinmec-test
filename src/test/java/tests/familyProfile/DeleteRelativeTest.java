package tests.familyProfile;

import model.RelativeModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.familyProfile.AddNewRelativesPopup;
import pages.familyProfile.DeleteRelativePopup;
import pages.familyProfile.FamilyProfilePage;
import tests.TestCase;

import static org.testng.Assert.assertEquals;

public class DeleteRelativeTest extends TestCase {

    @BeforeClass
    public void navigateToFamilyProfilePage() {
        HomePage homePage = new HomePage(testBasic.driver);
        homePage.clickIconProfile();
        homePage.clickFamilyProfile();
        addNewRelatives();
    }

    public void addNewRelatives() {
        RelativeModel relativeModel = new RelativeModel("Nguyễn Ánh Hồng Hà Trang","20/12/1994","Nam","0975321456","Con");
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        AddNewRelativesPopup addNewRelativesPopup = familyProfilePage.clickAddNewRelatives();
        addNewRelativesPopup.showPopupProfileFamily();
        addNewRelativesPopup.addNewRelatives();
    }

    @Test()
    public void deleteRelativesSuccessfully(){
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.hidePopupProfileFamily();
        FamilyProfilePage familyProfilePage= new FamilyProfilePage(testBasic.driver);
        int countBlockCusBeforeDel = familyProfilePage.countBlockCustomer();
        DeleteRelativePopup deleteRelativePopup = new DeleteRelativePopup(testBasic.driver);
        deleteRelativePopup.deleteRelativesSuccessfully();
        addNewRelativesPopup.hidePopupProfileFamily();
        int countBlockCusAfterDel = familyProfilePage.countBlockCustomer();
        assertEquals(countBlockCusBeforeDel - 1, countBlockCusAfterDel);
    }

}
