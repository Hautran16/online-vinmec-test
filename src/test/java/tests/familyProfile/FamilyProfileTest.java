package tests.familyProfile;

import org.testng.annotations.Test;
import pages.familyProfile.FamilyProfilePage;
import pages.HomePage;
import tests.TestCase;

public class FamilyProfileTest extends TestCase {
    @Test
    public void verifyFamilyProfile(){
        HomePage homePage = new HomePage(testBasic.driver);
        homePage.clickIconProfile();
        FamilyProfilePage familyProfilePage = homePage.clickFamilyProfile();
    }

}
