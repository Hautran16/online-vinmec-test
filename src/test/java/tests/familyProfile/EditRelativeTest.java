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


public class EditRelativeTest extends TestCase {

    public String excelFilePath = "src/test/resources/testData/testCases/AddNewRelatives.xlsx";
    private ITestContext testContext;
    @DataProvider(name = "EditRelatives")
    public Object[][] getTestData(Method method) throws IOException {
        Object[][] relativeModelList = null;
        switch (method.getName()){
            case ("verifyEditRelativesSuccessfully"): relativeModelList = testBasic.getDataFromExcelObject(excelFilePath, "Success", RelativeModel.class);
                break;
            case ("verifyEditRelativesFailWhenNameInvalid"): relativeModelList = testBasic.getDataFromExcelObject(excelFilePath, "Failure_Name", RelativeModel.class);
                break;
            case ("verifyEditRelativesFailWhenPhoneInvalid"): relativeModelList = testBasic.getDataFromExcelObject(excelFilePath, "Failure_Phone", RelativeModel.class);
                break;
            case ("verifyEditRelativesFailWhenGenderInvalid"): relativeModelList = testBasic.getDataFromExcelObject(excelFilePath, "Failure_Gender", RelativeModel.class);
                break;
        }

        for (Object[] data : relativeModelList) {
            for (Object item : data) {
                System.out.println("DataProvider");
                System.out.println(item.toString());
            }
        }

        return relativeModelList;
    }

    public void addNewRelatives() {
        RelativeModel relativeModel = new RelativeModel("Nguyễn Ánh Hồng Hà Trang2","20/12/1994","Nam","0975321456","Con");
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        AddNewRelativesPopup addNewRelativesPopup = familyProfilePage.clickAddNewRelatives();
        addNewRelativesPopup.showPopupProfileFamily();
        addNewRelativesPopup.addNewRelatives(relativeModel);
    }

    public void editNewRelatives() {
//        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
//        addNewRelativesPopup.addNewRelatives();
    }

    @BeforeClass
    public void navigateToFamilyProfilePage() {
        System.out.println("BeforeClass");
        HomePage homePage = new HomePage(testBasic.driver);
        homePage.clickIconProfile();
        homePage.clickFamilyProfile();
    }

    @BeforeMethod
    public void showPopupEdit(ITestContext context){
        System.out.println("BeforeMethod");
        testContext = context;
        addNewRelatives();
    }

    @Test(groups = "Success", dataProvider = "EditRelatives")
    public void verifyEditRelativesSuccessfully(RelativeModel relativeModel){
        testContext.setAttribute("relativeModel", relativeModel);
//        System.out.println(relativeModel.name);
//        new RelativeModel("Nguyễn Ánh Hồng Hà Trang3","20/12/1995","Nam","0975321457","Con");
        RelativeModel oldRelativeModel = new RelativeModel("Nguyễn Ánh Hồng Hà Trang2","20/12/1994","Nam","0975321456","Con");
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.hidePopupProfileFamily();
        FamilyProfilePage familyProfilePage= new FamilyProfilePage(testBasic.driver);
        familyProfilePage.clickEditRelative(oldRelativeModel);
        addNewRelativesPopup.clearInput();
        addNewRelativesPopup.addNewRelatives(relativeModel);
//        editNewRelatives();
//        FamilyProfilePage familyProfilePage= new FamilyProfilePage(testBasic.driver);
        familyProfilePage.verifyTextPresent(familyProfilePage.getName(relativeModel), relativeModel.getName());
        familyProfilePage.verifyTextPresent(familyProfilePage.getPhoneNum(), relativeModel.getPhoneNumber());
        familyProfilePage.verifyTextPresent(familyProfilePage.getRelationship(), relativeModel.getRelationship());
    }

    @Test(groups = "Failure", dataProvider = "EditRelatives", priority = 1)
    public void verifyEditRelativesFailWhenNameInvalid(RelativeModel relativeModel){
        editNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequireName(), relativeModel.getName());
    }

    @Test(groups = "Failure", dataProvider = "EditRelatives", priority = 1)
    public void verifyEditRelativesFailWhenPhoneInvalid(RelativeModel relativeModel){
        editNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), relativeModel.getErrMessage());
    }

    @Test(groups = "Failure", dataProvider = "EditRelatives", priority = 1)
    public void verifyEditRelativesFailWhenGenderInvalid(RelativeModel relativeModel){
        editNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), relativeModel.getErrMessage());
    }

    @AfterMethod(onlyForGroups = "Success", groups = "DeleteData")
    public void deleteRelativesSuccessfully(ITestResult result){
        RelativeModel relativeModel = (RelativeModel) testContext.getAttribute("relativeModel");
        System.out.println(relativeModel.getName());
        DeleteRelativePopup deleteRelativePopup = new DeleteRelativePopup(testBasic.driver);
        deleteRelativePopup.deleteRelativesSuccessfully(relativeModel);
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        familyProfilePage.waitLoadingHidden();
    }

}
