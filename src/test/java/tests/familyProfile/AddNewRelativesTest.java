package tests.familyProfile;

import model.RelativeModel;
import org.testng.ITestContext;
import org.testng.ITestResult;
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

    private ITestContext testContext;

    @BeforeClass
    public void navigateToFamilyProfilePage() {
        HomePage homePage = new HomePage(testBasic.driver);
        homePage.clickIconProfile();
        homePage.clickFamilyProfile();
    }

    @BeforeMethod
    public void setup(ITestContext context) {
        testContext = context;
    }

    public void addNewRelatives(RelativeModel relativeModel) {
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        AddNewRelativesPopup addNewRelativesPopup = familyProfilePage.clickAddNewRelatives();
        addNewRelativesPopup.showPopupProfileFamily();
        addNewRelativesPopup.addNewRelatives(relativeModel);
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
        testContext.setAttribute("relativeModel", relativeModel);
        addNewRelatives(relativeModel);
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        familyProfilePage.verifyTextPresent(familyProfilePage.getName(relativeModel), relativeModel.getName());
        familyProfilePage.verifyTextPresent(familyProfilePage.getPhoneNum(), relativeModel.getPhoneNumber());
        familyProfilePage.verifyTextPresent(familyProfilePage.getRelationship(), relativeModel.getRelationship());
    }

    @Test(groups = "Failure", dataProvider = "addNewRelativesTest", priority = 1)
    public void verifyAddNewRelativesFailWhenNameInvalid(RelativeModel relativeModel){
        addNewRelatives(relativeModel);
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequireName(), relativeModel.getErrMessage());
    }

    @Test(groups = "Failure", dataProvider = "addNewRelativesTest", priority = 1)
    public void verifyAddNewRelativesFailWhenPhoneInvalid(RelativeModel relativeModel){
        addNewRelatives(relativeModel);
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), relativeModel.getErrMessage());
    }

    @Test(groups = "Failure", dataProvider = "addNewRelativesTest", priority = 1)
    public void verifyAddNewRelativesFailWhenGenderInvalid(RelativeModel relativeModel){
        addNewRelatives(relativeModel);
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), relativeModel.getErrMessage());
    }

    @AfterMethod(onlyForGroups = "Success", groups = "DeleteData")
    public void deleteRelativesSuccessfully(ITestResult result){
        RelativeModel relativeModel = (RelativeModel) testContext.getAttribute("relativeModel");
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        DeleteRelativePopup deleteRelativePopup = new DeleteRelativePopup(testBasic.driver);
        deleteRelativePopup.deleteRelativesSuccessfully(relativeModel);
        familyProfilePage.waitLoadingHidden();
    }

    @AfterMethod(onlyForGroups = "Failure")
    public void closePopup(){
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.clickClose();
    }
}
