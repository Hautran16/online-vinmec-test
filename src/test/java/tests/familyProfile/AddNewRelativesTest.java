package tests.familyProfile;

import org.testng.annotations.*;
import pages.HomePage;
import pages.familyProfile.AddNewRelativesPopup;
import pages.familyProfile.FamilyProfilePage;
import tests.TestCase;

import java.io.IOException;

public class AddNewRelativesTest extends TestCase {
    String excelFilePath = "src/test/resources/testData/testCases/addNewRelatives.xlsx";

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
        addNewRelativesPopup.inputName(name);
        addNewRelativesPopup.inputBirthday(birthday);
        addNewRelativesPopup.inputGender(gender);
        addNewRelativesPopup.inputPhoneNumber(phoneNumber);
        addNewRelativesPopup.inputRelationship(relationship);
        addNewRelativesPopup.clickSubmit();
    }

    @DataProvider(name = "addNewRelativesSuccessfully")
    public Object[][] getTestData() throws IOException {
        return testBasic.getTestData(excelFilePath, "Success");
    }

    @Test(groups = "Success", dataProvider = "addNewRelativesSuccessfully")
    public void verifyAddNewRelativesSuccessfully(String name, String birthday, String gender, String phoneNumber, String relationship){
        addNewRelatives(name, birthday,gender, phoneNumber, relationship);
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        familyProfilePage.verifyTextPresent(familyProfilePage.getName(), name);
        familyProfilePage.verifyTextPresent(familyProfilePage.getPhoneNum(), phoneNumber);
        familyProfilePage.verifyTextPresent(familyProfilePage.getRelationship(), relationship);
    }

    @DataProvider(name = "getTestDataWithNameInvalid")
    public Object[][] getTestDataWithNameInvalid() throws IOException {
        return testBasic.getTestData(excelFilePath, "Failure_Name");
    }

    @Test(groups = "Failure", dataProvider = "getTestDataWithNameInvalid", priority = 1)
    public void verifyAddNewRelativesFailWhenNameInvalid(String name, String birthday, String gender, String phoneNumber, String relationship, String errMessage){
        addNewRelatives(name, birthday,gender, phoneNumber, relationship);
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequireName(), errMessage);
    }

//    @DataProvider(name = "getTestDataWithPhoneInvalid")
//    public Object[][] getTestDataWithPhoneInvalid() throws IOException {
//        return testBasic.getTestData(excelFilePath, "Failure_Phone");
//    }
//
//    @Test(groups = "Failure", dataProvider = "getTestDataWithPhoneInvalid", priority = 1)
//    public void verifyAddNewRelativesFailWhenPhoneInvalid(String name, String birthday, String gender, String phoneNumber, String relationship, String errMessage){
//        addNewRelatives(name, birthday,gender, phoneNumber, relationship);
//        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
//        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequireName(), errMessage);
//    }

    @AfterMethod(onlyForGroups = "Success")
    public void deleteRelativesSuccessfully(){
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        familyProfilePage.deleteRelatives();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    @AfterMethod(onlyForGroups = "Failure")
    public void closePopup(){
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.clickClose();
    }
}
