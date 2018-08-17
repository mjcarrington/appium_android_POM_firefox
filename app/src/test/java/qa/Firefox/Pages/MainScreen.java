package qa.Firefox.Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import qa.Firefox.Util.ElementsHelper;
import qa.Firefox.Util.Scrolling;

public class MainScreen extends AbstractScreen {

    // Element Definition
    @AndroidFindBy(id = "org.mozilla.firefox:id/url_bar_title")
    private MobileElement mainSearchButton;

    @AndroidFindBy(id = "org.mozilla.firefox:id/url_edit_text")
    private MobileElement mainSearchInput;

    @AndroidFindBy(id = "org.mozilla.firefox:id/site_security")
    private MobileElement mainSecurityInfoButton;

    @AndroidFindBy(id = "org.mozilla.firefox:id/counter_text")
    private MobileElement tabCount;

    // Driver setup
    public MainScreen(AppiumDriver driver) {
        super(driver);
    }

    ElementsHelper elementsHelper = new ElementsHelper(driver);
    Scrolling scrolling = new Scrolling(driver);

    // Tests
    public void mainNavigateToGoogle() {
        assertMainBrowserView();
        searchOnWeb("http://www.google.com");
    }

    public void openNewBrowserTabFromMenu() {
        assertMainBrowserView();
        MenuScreen menuScreen = new MenuScreen(driver);
        menuScreen.selectMenuOption("New tab");
        // assert that the tab count is now 2
        Assert.assertTrue(tabCount.getText().equals("2"));
    }

    // Helpers
    private void searchOnWeb(String searchQuery) {
        elementsHelper.waitForElementAndClick(mainSearchButton, 2);
        elementsHelper.explicitWait(mainSearchInput, 3);
        mainSearchInput.sendKeys(searchQuery);
    }

    // ASSERTS
    public void assertMainBrowserView() {
        try {
            elementsHelper.explicitWait(mainSecurityInfoButton, 2);
        } catch (TimeoutException ex) {
            elementsHelper.explicitWait(mainSecurityInfoButton, 2);
        }
        elementsHelper.assertElementDisplayed(mainSecurityInfoButton);
    }
}
