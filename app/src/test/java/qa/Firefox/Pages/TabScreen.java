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

    // Tab View Menu
    @AndroidFindBy(id = "org.mozilla.firefox:id/tabs_menu")
    private MobileElement tabMenuButton;

    @AndroidFindBy(id = "org.mozilla.firefox:id/menu_panel")
    private MobileElement tabMenuPanel;

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

    public void clearAllTabs() {
        MainScreen mainScreen = new MainScreen(driver);
        mainScreen.navigateToTabView();
        assertTabPageView();
        selectTabMenuOption("Close All Tabs");
        // NOTE: Edge case here where if we only have 1 tab open, there is no way to actually know if the tab was cleared.
        // This would need a separate test.
        mainScreen.navigateToTabView();
        Assert.assertEquals(grabTabCount(), 1);
    }

    // Helpers
    private int grabTabCount() {
        int tabCount = driver.findElementsById("org.mozilla.firefox:id/info").size();
        return tabCount;
    }

    public void selectTabMenuOption(String menuOption) {
        elementsHelper.waitForElementAndClick(tabMenuButton, 3);
        assertTabMenuVisible();
        WebElement customOption = driver.findElementByXPath("//*[@resource-id='org.mozilla.firefox:id/menu_panel']//descendant::android.widget.TextView[@text='"+menuOption+"']");
        customOption.click();
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

    public void assertTabMenuVisible() {
        try {
            elementsHelper.explicitWait(tabMenuPanel, 2);
        } catch (TimeoutException ex) {
            elementsHelper.explicitWait(tabMenuPanel, 2);
        }
        elementsHelper.assertElementDisplayed(tabMenuPanel);
    }
}
