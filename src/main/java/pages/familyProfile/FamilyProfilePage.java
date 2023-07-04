package pages.familyProfile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.Page;

public class FamilyProfilePage extends Page {
    public FamilyProfilePage(WebDriver dr){
        super(dr);
        this.driverWeb = dr;
    }

    By btnAddNewRelatives = By.xpath("//div[@class = 'col-sm-7']//div[5]");
    By textName = By.xpath("//div[@class = 'col-sm-8']//div[@class = 'field field-name'] //span[1]");
    By textAge = By.xpath("");
    By textPhoneNum = By.xpath("//div[@class = 'col-sm-8']//div[@class = 'field field-phone-number'] //span[2]");
    By textRelationship = By.xpath("//div[@class = 'col-sm-8']//div[@class = 'field field-relationship'] //span[2]");


    public AddNewRelativesPopup clickAddNewRelatives() {
        waitElementForVisible(btnAddNewRelatives);
        driverWeb.findElement(btnAddNewRelatives).click();
        return new AddNewRelativesPopup(driverWeb);
    }

    public String getName(){
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

}
