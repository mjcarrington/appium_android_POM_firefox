package qa.Firefox.Pages;


import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import qa.Firefox.Util.ElementsHelper;
import qa.Firefox.Util.TestSettings;

import java.util.List;

public class AbstractScreen {

    protected final AppiumDriver driver;

    public AbstractScreen(AppiumDriver driver) {
        this.driver = driver;

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        // PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);

    }

    public MobileElement findElementWithTimeout(By by, int timeOutInSeconds) {
        return (MobileElement)(new WebDriverWait(driver, timeOutInSeconds)).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    // Elements used after each test
    // TODO: Update
    @AndroidFindBy(xpath = "TBD")
    private WebElement homeButton;

    // Universal test methods
    public void postTestClear () {
        ElementsHelper elementsHelper = new ElementsHelper(driver);

        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            // no KB to hide
        }

        try {
            elementsHelper.explicitWait(homeButton, 3);
        } catch (NoSuchElementException ex) {
            driver.navigate().back();
        }

        try {
            homeButton.click();
        } catch (NoSuchElementException ex2) {
            // assume we are in a bad state and relaunch
            SetupScreen setupScreen = new SetupScreen(driver);
            ((AndroidDriver<MobileElement>) driver).startActivity(TestSettings.APPPACKAGE, TestSettings.APPACTIVITY, TestSettings.APPPACKAGE, TestSettings.APPACTIVITY);
        }
    }

    public void detectAppCrashRelaunch() {
        SetupScreen setupScreen = new SetupScreen(driver);
        String activity = ((AndroidDriver<MobileElement>) driver).currentActivity();
        if (!activity.matches("org.mozilla.gecko.BrowserApp.*")) {
            System.out.println("App crashed!");
            System.out.println("Current activity running: " + activity);
            getCrashLogs();
            ((AndroidDriver<MobileElement>) driver).startActivity(TestSettings.APPPACKAGE, TestSettings.APPACTIVITY, TestSettings.APPPACKAGE, TestSettings.APPACTIVITY);
        }
    }

    private void getCrashLogs() {
        List<LogEntry> crashLog;
        List<LogEntry> logEntries = driver.manage().logs().get("logcat").getAll();
        for(int i=0;i<logEntries.size();++i){
            if (logEntries.get(i).getMessage().contains("FATAL EXCEPTION: main")) {
                crashLog = logEntries.subList(i, i+15);
                crashLog.forEach(System.out::println); // prints out results
            }
        }
    }
}
