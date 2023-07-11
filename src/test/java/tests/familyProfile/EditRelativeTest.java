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


public class EditRelativeTest extends TestCase {

    public String excelFilePath = "src/test/resources/testData/testCases/AddNewRelatives.xlsx";

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
        RelativeModel relativeModel = new RelativeModel("Nguyễn Ánh Hồng Hà Trang","20/12/1994","Nam","0975321456","Con");
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        AddNewRelativesPopup addNewRelativesPopup = familyProfilePage.clickAddNewRelatives();
        addNewRelativesPopup.showPopupProfileFamily();
        addNewRelativesPopup.addNewRelatives();
    }

    public void editNewRelatives() {
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.addNewRelatives();
    }

    @BeforeClass
    public void navigateToFamilyProfilePage() {
        System.out.println("BeforeClass");
        HomePage homePage = new HomePage(testBasic.driver);
        homePage.clickIconProfile();
        homePage.clickFamilyProfile();
    }

    @BeforeMethod
    public void showPopupEdit(){
        System.out.println("BeforeMethod");
        addNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.hidePopupProfileFamily();
        FamilyProfilePage familyProfilePage= new FamilyProfilePage(testBasic.driver);
        familyProfilePage.clickEditRelative();
        addNewRelativesPopup.clearInput();
    }

    @Test(groups = "Success", dataProvider = "EditRelatives")
    public void verifyEditRelativesSuccessfully(RelativeModel relativeModel){
        editNewRelatives();
        FamilyProfilePage familyProfilePage= new FamilyProfilePage(testBasic.driver);
        familyProfilePage.verifyTextPresent(familyProfilePage.getName(), RelativeModel.name);
        familyProfilePage.verifyTextPresent(familyProfilePage.getPhoneNum(), RelativeModel.phoneNumber);
        familyProfilePage.verifyTextPresent(familyProfilePage.getRelationship(), RelativeModel.relationship);
    }

    @Test(groups = "Failure", dataProvider = "EditRelatives", priority = 1)
    public void verifyEditRelativesFailWhenNameInvalid(RelativeModel relativeModelEdit){
        editNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequireName(), RelativeModel.errMessage);
    }

    @Test(groups = "Failure", dataProvider = "EditRelatives", priority = 1)
    public void verifyEditRelativesFailWhenPhoneInvalid(RelativeModel relativeModelEdit){
        editNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), RelativeModel.errMessage);
    }

    @Test(groups = "Failure", dataProvider = "EditRelatives", priority = 1)
    public void verifyEditRelativesFailWhenGenderInvalid(RelativeModel relativeModelEdit){
        editNewRelatives();
        AddNewRelativesPopup addNewRelativesPopup = new AddNewRelativesPopup(testBasic.driver);
        addNewRelativesPopup.verifyTextPresent(addNewRelativesPopup.getErrRequirePhone(), RelativeModel.errMessage);
    }

    @AfterMethod(onlyForGroups = "Success", groups = "DeleteData")
    public void deleteRelativesSuccessfully(){
        DeleteRelativePopup deleteRelativePopup = new DeleteRelativePopup(testBasic.driver);
        deleteRelativePopup.deleteRelativesSuccessfully();
        FamilyProfilePage familyProfilePage = new FamilyProfilePage(testBasic.driver);
        familyProfilePage.waitLoadingHidden();
    }

}
