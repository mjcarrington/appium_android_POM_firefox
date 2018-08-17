package qa.Firefox.Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import qa.Firefox.Util.ElementsHelper;
import qa.Firefox.Util.Scrolling;

public class MenuScreen extends AbstractScreen {

    // Element Definition
    // Permission Dialog
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@resource-id='org.mozilla.firefox:id/menu' and @index='4']")
    private MobileElement menuButton;

    @AndroidFindBy(id = "org.mozilla.firefox:id/menu_panel")
    private MobileElement menuPanel;

    // Driver setup
    public MenuScreen(AppiumDriver driver) {
        super(driver);
    }

    ElementsHelper elementsHelper = new ElementsHelper(driver);
    Scrolling scrolling = new Scrolling(driver);

    // Tests

    // Helpers
    public void selectMenuOption(String menuOption) {
        elementsHelper.waitForElementAndClick(menuButton, 3);
        assertMenuVisible();
        WebElement customOption = driver.findElementByXPath("//*[@resource-id='org.mozilla.firefox:id/menu_panel']//descendant::android.widget.TextView[@text='"+menuOption+"']");
        customOption.click();
    }

    // ASSERTS
    public void assertMenuVisible() {
        try {
            elementsHelper.explicitWait(menuPanel, 2);
        } catch (TimeoutException ex) {
            elementsHelper.explicitWait(menuPanel, 2);
        }
        elementsHelper.assertElementDisplayed(menuPanel);
    }
}
