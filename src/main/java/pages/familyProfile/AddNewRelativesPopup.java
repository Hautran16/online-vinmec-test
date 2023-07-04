package pages.familyProfile;

import common.TestBasic;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.Page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddNewRelativesPopup extends Page {
    public AddNewRelativesPopup(WebDriver dr){
        super(dr);
        this.driverWeb = dr;
    }

    By txtName = By.xpath("//*[contains(@id, 'edit-name')]");
    By errRequireName = By.xpath("//*[contains(@id, 'edit-name')]// following :: div[1]");
    By dateBirthDay = By.xpath("//*[contains(@id, 'edit-birthday')]");
    By datepickerCurrMonthYear = By.xpath("//div[@class = 'datepicker-days']//th[@class = 'datepicker-switch']");
    By datepickerPrev = By.xpath("//div[@class = 'datepicker-months']//th[@class = 'prev']");
    By ddGender = By.xpath("//*[contains(@id, 'edit-gender')]");
    By txtPhoneNumber = By.xpath("//*[contains(@id, 'edit-phone')]");
    By errRequirePhone = By.xpath("//*[contains(@id, 'edit-phone')]// following :: div[1]");
    By ddRelationship = By.xpath("//*[contains(@id, 'edit-relationship')]");
    By btnSubmit = By.xpath("//div[@class = 'modal-footer']");
    By btnClose = By.xpath("//*[@id = 'modal-profile-family-label']// following :: button[@class = 'close']");
    By popupProfileFamily = By.xpath("//div[@class = 'modal fade modal-profile-family-form show']");

    public void showPopupProfileFamily(){
        waitElementForVisible(popupProfileFamily);
    }

    public void inputName(String name){
        waitElementForVisible(txtName);
        driverWeb.findElement(txtName).sendKeys(name);
    }

    public void inputBirthday(String birthday){
        waitElementForVisible(dateBirthDay);
        driverWeb.findElement(dateBirthDay).click();
        Date dateBirthdayInput = null;
        try {
            dateBirthdayInput = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int day = dateBirthdayInput.getDate();
        int month = dateBirthdayInput.getMonth();
        int year = dateBirthdayInput.getYear();
        Date currentDate = new Date();
        int currentYear = currentDate.getYear();
        driverWeb.findElement(datepickerCurrMonthYear).click();
        WebElement datepickerPrevElement = driverWeb.findElement(datepickerPrev);
        for(int i = 0; i < currentYear - year; i++){
            datepickerPrevElement.click();
        }
        driverWeb.findElement(dateBirthDay).click();
        TestBasic testBasic = new TestBasic();
        String[] months = testBasic.getMonths();
        By lableMonth = By.xpath("//span[text() = '" + months[month] +"']");
        driverWeb.findElement(lableMonth).click();
        By lableDay = By.xpath("//table[@class = 'table-condensed']//td[text() = '" + day +"' and @class = 'day']");
        driverWeb.findElement(lableDay).click();
    }

    public void inputGender(String gender){
        waitElementForVisible(ddGender);
        driverWeb.findElement(ddGender).click();
        String elementLocator = "//*[contains(@id, 'edit-gender')]//option[@value = '']";
        if(Objects.equals(gender, "Nam")){
            elementLocator = "//option[@value = 'Male']";
        } else if(Objects.equals(gender, "Nữ")){
            elementLocator = "//option[@value = 'Female']";
        } else if(Objects.equals(gender, "Khác")) {
            elementLocator = "//*[contains(@id, 'edit-gender')]//option[@value = 'Other']";
        }
        By txtGender = By.xpath(elementLocator);
        waitElementForVisible(txtGender);
        driverWeb.findElement(txtGender).click();
    }

    public void inputPhoneNumber(String phoneNumber){
        waitElementForVisible(txtPhoneNumber);
        driverWeb.findElement(txtPhoneNumber).sendKeys(phoneNumber);
    }

    public void inputRelationship(String relationship){
        Map<String, String> map = new HashMap<String, String>();
        map.put("Bố", "Father");
        map.put("Mẹ", "Mother");
        map.put("Con", "Children");
        map.put("Vợ", "Wife");
        map.put("Chồng", "Husband");
        map.put("Khác", "Other");
        waitElementForVisible(ddRelationship);
        driverWeb.findElement(ddRelationship).click();
        String elementLocator = "//*[contains(@id, 'edit-relationship')]//option[@value = '']";
        if(map.containsKey(relationship)){
            elementLocator = "//*[contains(@id, 'edit-relationship')]//option[@value = '"+map.get(relationship)+"']";
        }
        By txtRelationship = By.xpath(elementLocator);
        waitElementForVisible(txtRelationship);
        driverWeb.findElement(txtRelationship).click();
        driverWeb.findElement(txtName).click();

    }

    public FamilyProfilePage clickSubmit(){
        waitElementForVisible(btnSubmit);
        driverWeb.findElement(btnSubmit).click();
        return new FamilyProfilePage(driverWeb);
    }

    public void addNewRelatives(String name, String birthday, String gender, String phoneNumber, String relationship) {
        inputName(name);
        inputBirthday(birthday);
        inputGender(gender);
        inputPhoneNumber(phoneNumber);
        inputRelationship(relationship);
        clickSubmit();
    }

    public String getErrRequireName() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String textErrRequireName = driverWeb.findElement(errRequireName).getText();
        return textErrRequireName;
    }

    public String getErrRequirePhone() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String textErrRequirePhone = driverWeb.findElement(errRequirePhone).getText();
        return textErrRequirePhone;
    }

    public void clickClose(){
        waitElementForVisible(btnClose);
        driverWeb.findElement(btnClose).click();
    }

}