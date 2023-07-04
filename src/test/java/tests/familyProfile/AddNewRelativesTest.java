package tests.familyProfile;

import org.testng.annotations.*;
import pages.HomePage;
import pages.familyProfile.AddNewRelativesPopup;
import pages.familyProfile.DeleteRelativePopup;
import pages.familyProfile.FamilyProfilePage;
import tests.TestCase;

import java.io.IOException;
import java.lang.reflect.Method;

public class AddNewRelativesTest extends TestCase {
    String excelFilePath = "src/test/resources/testData/testCases/AddNewRelatives.xlsx";

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

    @DataProvider(name = "addNewRelativesTest")
    public Object[][] getTestData(Method method) throws IOException {
        Object[][] data = null;
        switch (method.getName()){
            case ("verifyAddNewRelativesSuccessfully"): data = testBasic.getTestData(excelFilePath, "Success");
                break;
            case ("verifyAddNewRelativesFailWhenNameInvalid"): data = testBasic.getTestData(excelFilePath, "Failure_Name");
                break;
            case ("verifyAddNewRelativesFailWhenPhoneInvalid"): data = testBasic.getTestData(excelFilePath, "Failure_Phone");
                break;
            case ("verifyAddNewRelativesFailWhenGenderInvalid"): data = testBasic.getTestData(excelFilePath, "Failure_Gender");
                break;
        }
        return data ;
    }

    @Test(groups = "Success", dataProvider = "addNewRelativesTest")
    public void verifyAddNewRelativesSuccessfully(String name, String birthday, String gender, String phoneNumber, String relationship){
        addNewRelatives(name, birthday,gender, phoneNumber, relationship);
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        familyProfilePage.verifyTextPresent(familyProfilePage.getName(), name);
        familyProfilePage.verifyTextPresent(familyProfilePage.getPhoneNum(), phoneNumber);
        familyProfilePage.verifyTextPresent(familyProfilePage.getRelationship(), relationship);
    }

    @Test(groups = "Failure", dataProvider = "addNewRelativesTest", priority = 1)
    public void verifyAddNewRelativesFailWhenNameInvalid(String name, String birthday, String gender, String phoneNumber, String relationship, String errMessage){
        addNewRelatives(name, birthday,gender, phoneNumber, relationship);
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequireName(), errMessage);
    }

    @Test(groups = "Failure", dataProvider = "addNewRelativesTest", priority = 1)
    public void verifyAddNewRelativesFailWhenPhoneInvalid(String name, String birthday, String gender, String phoneNumber, String relationship, String errMessage){
        addNewRelatives(name, birthday,gender, phoneNumber, relationship);
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), errMessage);
    }

    @Test(groups = "Failure", dataProvider = "addNewRelativesTest", priority = 1)
    public void verifyAddNewRelativesFailWhenGenderInvalid(String name, String birthday, String gender, String phoneNumber, String relationship, String errMessage){
        addNewRelatives(name, birthday,gender, phoneNumber, relationship);
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), errMessage);
    }

    @AfterMethod(onlyForGroups = "Success", groups = "DeleteData")
    public void deleteRelativesSuccessfully(){
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        DeleteRelativePopup deleteRelativePopup = new DeleteRelativePopup(testBasic.driver);
        deleteRelativePopup.deleteRelativesSuccessfully();
        familyProfilePage.waitLoadingHidden();
    }

    @AfterMethod(onlyForGroups = "Failure")
    public void closePopup(){
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.clickClose();
    }
}
