package qa.Firefox.Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import qa.Firefox.Util.ElementsHelper;
import qa.Firefox.Util.Scrolling;

public class MainScreen extends AbstractScreen {

    // Element Definition
    // Permission Dialog
    @AndroidFindBy(id = "org.mozilla.firefox:id/url_bar_title")
    private MobileElement mainSearchButton;

    @AndroidFindBy(id = "org.mozilla.firefox:id/url_edit_text")
    private MobileElement mainSearchInput;

    @AndroidFindBy(id = "org.mozilla.firefox:id/site_security")
    private MobileElement mainSecurityInfoButton;

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
        // DELETE HERE DOWN
        System.out.println("attempting scroll down");
        scrolling.scrollDown();
        System.out.println("scroll down done");

        System.out.println("attempting scroll up");
        scrolling.scrollUp();
        System.out.println("scroll up done");

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
        } catch (NoSuchElementException ex) {
            elementsHelper.explicitWait(mainSecurityInfoButton, 2);
        }
        elementsHelper.assertElementDisplayed(mainSecurityInfoButton);
    }
}
