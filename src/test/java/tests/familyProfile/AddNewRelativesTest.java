package tests.familyProfile;

import model.RelativeModel;
import org.testng.annotations.*;
import pages.HomePage;
import pages.familyProfile.AddNewRelativesPopup;
import pages.familyProfile.DeleteRelativePopup;
import pages.familyProfile.FamilyProfilePage;
import tests.TestCase;

import java.io.IOException;
import java.lang.reflect.Method;

public class AddNewRelativesTest extends TestCase {
    public String excelFilePath = "src/test/resources/testData/testCases/AddNewRelatives.xlsx";

    @BeforeClass
    public void navigateToFamilyProfilePage() {
        HomePage homePage = new HomePage(testBasic.driver);
        homePage.clickIconProfile();
        homePage.clickFamilyProfile();
    }

    public void addNewRelatives() {
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        AddNewRelativesPopup addNewRelativesPopup = familyProfilePage.clickAddNewRelatives();
        addNewRelativesPopup.showPopupProfileFamily();
        addNewRelativesPopup.addNewRelatives();
    }

    @DataProvider(name = "addNewRelativesTest")
    public Object[][] getTestData(Method method) throws IOException {
        Object[][] relativeModelList = null;
        switch (method.getName()){
            case ("verifyAddNewRelativesSuccessfully"): relativeModelList = testBasic.getDataFromExcelObject(excelFilePath, "Success", RelativeModel.class);
                break;
            case ("verifyAddNewRelativesFailWhenNameInvalid"): relativeModelList = testBasic.getDataFromExcelObject(excelFilePath, "Failure_Name", RelativeModel.class);
                break;
            case ("verifyAddNewRelativesFailWhenPhoneInvalid"): relativeModelList = testBasic.getDataFromExcelObject(excelFilePath, "Failure_Phone", RelativeModel.class);
                break;
            case ("verifyAddNewRelativesFailWhenGenderInvalid"): relativeModelList = testBasic.getDataFromExcelObject(excelFilePath, "Failure_Gender", RelativeModel.class);
                break;
        }

        return relativeModelList;
    }

   @Test(groups = "Success", dataProvider = "addNewRelativesTest")
    public void verifyAddNewRelativesSuccessfully(RelativeModel relativeModel){
        addNewRelatives();
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        familyProfilePage.verifyTextPresent(familyProfilePage.getName(), RelativeModel.modelName);
        familyProfilePage.verifyTextPresent(familyProfilePage.getPhoneNum(), RelativeModel.modelPhoneNumber);
        familyProfilePage.verifyTextPresent(familyProfilePage.getRelationship(), RelativeModel.modelRelationship);
    }

    @Test(groups = "Failure", dataProvider = "addNewRelativesTest", priority = 1)
    public void verifyAddNewRelativesFailWhenNameInvalid(RelativeModel relativeModel){
        addNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequireName(), RelativeModel.modelErrMessage);
    }

    @Test(groups = "Failure", dataProvider = "addNewRelativesTest", priority = 1)
    public void verifyAddNewRelativesFailWhenPhoneInvalid(RelativeModel relativeModel){
        addNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), RelativeModel.modelErrMessage);
    }

    @Test(groups = "Failure", dataProvider = "addNewRelativesTest", priority = 1)
    public void verifyAddNewRelativesFailWhenGenderInvalid(RelativeModel relativeModel){
        addNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), RelativeModel.modelErrMessage);
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
