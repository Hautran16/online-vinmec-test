package pages.familyProfile;

import model.RelativeModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.Page;

public class DeleteRelativePopup extends Page {
    public DeleteRelativePopup(WebDriver dr){
        super(dr);
        this.driverWeb = dr;
    }

    By btnDeleteRelatives = By.xpath("//span[text() = '" + RelativeModel.name + "'] // ancestor:: div[@class = 'content'] // div[@class = 'col-6'][2]");
    By btnSubmitDeleteRelatives = By.xpath("//div[@class = 'form-actions']//input[contains(@id, 'edit-submit')]");
    By btnCancelDeleteRelatives = By.xpath("//div[@class = 'form-actions']//input[contains(@id, 'edit-cancel')]");


    public void deleteRelativesSuccessfully(){
        waitElementForVisible(btnDeleteRelatives);
        driverWeb.findElement(btnDeleteRelatives).click();
        waitElementForVisible(btnSubmitDeleteRelatives);
        driverWeb.findElement(btnSubmitDeleteRelatives).click();
    }

    public void cancelDeleteRelatives(){
        waitElementForVisible(btnDeleteRelatives);
        driverWeb.findElement(btnDeleteRelatives).click();
        waitElementForVisible(btnCancelDeleteRelatives);
        driverWeb.findElement(btnCancelDeleteRelatives).click();
    }
}
