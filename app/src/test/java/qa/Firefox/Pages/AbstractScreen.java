package qa.Firefox.Pages;


import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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
    @AndroidFindBy(id = "org.mozilla.firefox:id/home_pager")
    private WebElement homeView;

    // Universal test methods
    public void postTestClear () {
        ElementsHelper elementsHelper = new ElementsHelper(driver);

        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            // no KB to hide
        }

        try {
            elementsHelper.explicitWait(homeView, 3);
        } catch (NoSuchElementException | TimeoutException ex) {
            driver.navigate().back();
        }
    }

    public void detectAppCrashRelaunch() {
        SetupScreen setupScreen = new SetupScreen(driver);
        String activity = ((AndroidDriver<MobileElement>) driver).currentActivity();
        if (!activity.matches("org.mozilla.gecko.*")) {
            System.out.println("App crashed!");
            System.out.println("Current activity running: " + activity);
            getCrashLogs();
            restartAppActivity();
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

    private void restartAppActivity() {
        Activity activity = new Activity(TestSettings.APPPACKAGE, TestSettings.APPACTIVITY);
        activity.setAppWaitPackage(TestSettings.APPPACKAGE);
        activity.setAppWaitActivity(TestSettings.APPACTIVITY);
        ((AndroidDriver<MobileElement>) driver).startActivity(activity);
    }
}
