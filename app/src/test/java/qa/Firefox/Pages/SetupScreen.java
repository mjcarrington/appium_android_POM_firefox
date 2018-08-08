package qa.Firefox.Pages;

import io.appium.java_client.MobileElement;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import qa.Firefox.Util.ElementsHelper;

public class SetupScreen extends AbstractScreen {

    // Element Definition
    // Permission Dialog
    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
    public WebElement setupPermissionAllowButton;

    @AndroidFindBy(id = "org.mozilla.firefox:id/firstrun_image")
    private MobileElement setupInitialIcon;

    @AndroidFindBy(id = "org.mozilla.firefox:id/firstrun_link")
    private MobileElement setupNextButton;

    @AndroidFindBy(id = "org.mozilla.firefox:id/welcome_browse")
    private MobileElement setupStartBrowsingButton;


    // Driver setup
    public SetupScreen(AppiumDriver driver) {
        super(driver);
    }

    ElementsHelper elementsHelper = new ElementsHelper(driver);

    // Tests
    public void setupProceedThroughInitialScreens() {

        // welcome
        assertSetupView();
        elementsHelper.waitForElementAndClick(setupNextButton, 2);
        // privacy
        assertSetupView();
        elementsHelper.waitForElementAndClick(setupNextButton, 2);
        // customize
        assertSetupView();
        elementsHelper.waitForElementAndClick(setupNextButton, 2);
        // sync
        assertSetupView();
        elementsHelper.waitForElementAndClick(setupStartBrowsingButton, 2);
    }

    // Helpers

    // ASSERTS
    public void assertSetupView() {
        try {
            elementsHelper.explicitWait(setupInitialIcon, 2);
        } catch (NoSuchElementException ex) {
            elementsHelper.explicitWait(setupInitialIcon, 2);
        }
        elementsHelper.assertElementDisplayed(setupInitialIcon);
    }
}
