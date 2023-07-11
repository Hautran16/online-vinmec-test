package pages.familyProfile;
import model.RelativeModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.Page;

public class FamilyProfilePage extends Page {
    public FamilyProfilePage(WebDriver dr){
        super(dr);
        this.driverWeb = dr;
    }
    By btnAddNewRelatives = By.xpath("//div[@class = 'col-sm-7']//div[5]");
//    By textName = By.xpath("//span[text() = '" + relativeModel.getName() + "']");
    By textAge = By.xpath("");
    By textPhoneNum = By.xpath("//div[@class = 'col-sm-8']//div[@class = 'field field-phone-number'] //span[2]");
    By textRelationship = By.xpath("//div[@class = 'col-sm-8']//div[@class = 'field field-relationship'] //span[2]");
    By blockCustomer = By.xpath("//*[@id = 'main-content-right']//div[@class = 'block-customer-info']");
//    By btnEditRelatives = By.xpath("//span[text() = '" + relativeModel.getName() + "'] // ancestor:: div[@class = 'content'] // div[@class = 'col-6'][1]");


    public AddNewRelativesPopup clickAddNewRelatives() {
        waitElementForVisible(btnAddNewRelatives);
        driverWeb.findElement(btnAddNewRelatives).click();
        return new AddNewRelativesPopup(driverWeb);
    }

    public String getName(RelativeModel relativeModel){
        By textName = By.xpath("//span[text() = '" + relativeModel.getName() + "']");
        String name = driverWeb.findElement(textName).getText();
        return name;
    }

    public String getPhoneNum(){
        String phoneNum = driverWeb.findElement(textPhoneNum).getText();
        phoneNum = phoneNum.replace(".","");
        return phoneNum;
    }

    public String getRelationship(){
        String relationship = driverWeb.findElement(textRelationship).getText();
        return relationship;
    }

    public int countBlockCustomer(){
        return getArrayListElement(blockCustomer).size();
    }

    public void clickEditRelative(RelativeModel relativeModel){
        By btnEditRelatives = By.xpath("//span[text() = '" + relativeModel.getName() + "'] // ancestor:: div[@class = 'content'] // div[@class = 'col-6'][1]");
        waitElementForVisible(btnEditRelatives);
        driverWeb.findElement(btnEditRelatives).click();
    }

}
