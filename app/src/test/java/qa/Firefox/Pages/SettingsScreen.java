package qa.Firefox.Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import qa.Firefox.Util.ElementsHelper;
import qa.Firefox.Util.Scrolling;

public class SettingsScreen extends AbstractScreen {

    // Element Definition
    // Permission Dialog
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='org.mozilla.firefox:id/action_bar']//descendant::android.widget.TextView[@text='Settings']")
    private MobileElement settingsHeader;

    @AndroidFindBy(id = "android:id/list")
    private MobileElement settingsContainer;

    // Driver setup
    public SettingsScreen(AppiumDriver driver) {
        super(driver);
    }

    ElementsHelper elementsHelper = new ElementsHelper(driver);
    Scrolling scrolling = new Scrolling(driver);

    // Tests
    public void viewAppSettingsList() {
        MenuScreen menuScreen = new MenuScreen(driver);
        menuScreen.selectMenuOption("Settings");
    }

    public void viewAppSettingsGeneral() {
        MenuScreen menuScreen = new MenuScreen(driver);
        menuScreen.selectMenuOption("Settings");
        navigateSubSettings("General");
    }

    // Helpers
    private void navigateSubSettings(String settingOption) {
        assertSettingsScreen();
        WebElement settingsEntry = driver.findElementByXPath("//*[@resource-id='android:id/title' and @text='"+settingOption+"']");
        settingsEntry.click();
        elementsHelper.explicitWait(settingsContainer, 3);
        WebElement settingsEntryHeader = driver.findElementByXPath("//*[@resource-id='org.mozilla.firefox:id/action_bar']//descendant::android.widget.TextView[@text='"+settingOption+"']");
        elementsHelper.assertElement(settingsEntryHeader);
    }

    // ASSERTS
    public void assertSettingsScreen() {
        try {
            elementsHelper.explicitWait(settingsHeader, 2);
        } catch (TimeoutException ex) {
            elementsHelper.explicitWait(settingsHeader, 2);
        }
        elementsHelper.assertElementDisplayed(settingsHeader);
    }
}
