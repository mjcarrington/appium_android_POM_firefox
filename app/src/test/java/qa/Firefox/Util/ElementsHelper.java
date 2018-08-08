package qa.Firefox.Util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;

import qa.Firefox.Pages.AbstractScreen;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class ElementsHelper extends AbstractScreen {

    public ElementsHelper(AppiumDriver driver) {
        super(driver);
    }

    // these are general use utilities for dealing with elements
    // this utility grabs the actual text contents of a WebElement ID and returns string in lower case
    public static String getElementText(WebElement element) {
        String actualText = element.getText().toLowerCase();
        return actualText;
    }

    // ASSERT HELPERS //
    // this function compares text between actual and expected and provides "True" if matching
    public static void assertElementNameTrue(String actualText, String expectedText) {
        Assert.assertTrue(actualText.contains(expectedText), "EXPECTED: " + expectedText + ", ACTUAL: " + actualText);
    }

    // this function compares if an element is displayed, provided a web element
    public static void assertElementDisplayed(WebElement expectedElement) {
        Assert.assertEquals(true, expectedElement.isDisplayed(), "EXPECTED: " + expectedElement);
    }

    // this function deals with explicit waits
    public void explicitWait(WebElement waitElement, int waitTime) {
        new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOf(waitElement));
    }

    // this function grabs the date / time and outputs in a specific format
    public static String getDateTime() {
        Date now = new Date();
        DateFormat shortDf = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

        String currentDate = shortDf.format(now).toString().replace("/", ".");
        currentDate = currentDate.toString().replace(" ", "_");
        currentDate = currentDate.toString().replace(":", "_");
        return currentDate;
    }

    // catch-all assert method
    public void assertElement(WebElement expectedElement) {
        try {
            explicitWait(expectedElement, 2);
        } catch (NoSuchElementException ex) {
            explicitWait(expectedElement, 3);
        }
        assertElementDisplayed(expectedElement);
    }

    public static String getUniqueIdentifier() {
        String uniqueID = UUID.randomUUID().toString().substring(0, 6);
        return uniqueID;
    }

    // wait and click
    public void waitForElementAndClick(WebElement waitElement, int waitTime) {
        new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOf(waitElement));
        waitElement.click();
    }
}
