package qa.Firefox.Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import qa.Firefox.Util.ElementsHelper;
import qa.Firefox.Util.Scrolling;

public class TabScreen extends AbstractScreen {

    // Element Definition
    // Permission Dialog
    @AndroidFindBy(id = "org.mozilla.firefox:id/normal_tabs")
    private MobileElement tabContainer;

    @AndroidFindBy(id = "org.mozilla.firefox:id/add_tab")
    private MobileElement tabAddNewButton;

    // Driver setup
    public TabScreen(AppiumDriver driver) {
        super(driver);
    }

    ElementsHelper elementsHelper = new ElementsHelper(driver);
    Scrolling scrolling = new Scrolling(driver);

    // Tests
    public void addNewTab() {
        MainScreen mainScreen = new MainScreen(driver);
        mainScreen.navigateToTabView();
        assertTabPageView();
        int expectedTabCount = grabTabCount() + 1;
        tabAddNewButton.click();
        mainScreen.navigateToTabView();
        int actualNewTabCount = grabTabCount();
        Assert.assertEquals(expectedTabCount, actualNewTabCount);
    }

    // Helpers
    private int grabTabCount() {
        int tabCount = driver.findElementsById("org.mozilla.firefox:id/info").size();
        return tabCount;
    }

    // ASSERTS
    public void assertTabPageView() {
        try {
            elementsHelper.explicitWait(tabContainer, 2);
        } catch (TimeoutException ex) {
            elementsHelper.explicitWait(tabContainer, 2);
        }
        elementsHelper.assertElementDisplayed(tabContainer);
    }
}
