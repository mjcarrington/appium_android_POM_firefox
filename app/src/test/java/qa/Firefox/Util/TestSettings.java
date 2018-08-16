package qa.Firefox.Util;


import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import qa.Firefox.Pages.AbstractScreen;

public class TestSettings {

    protected AppiumDriver driver;

    public static final String APPPACKAGE = "org.mozilla.firefox";
    public static final String APPACTIVITY = "org.mozilla.gecko.BrowserApp";
    public static final String LOGFOLDER = setLogFolder();

    @Parameters({"port","device","os"})
    @BeforeTest(alwaysRun=true)
    // the real parameters are used for parallel testing
    // the optional parameter entries are used for single device testing
    public void setUp(@Optional("8123") String port, @Optional("ce0117112c812b2704") String device, @Optional("7.0")String os) // run S7 - dev device
    throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", device);
        capabilities.setCapability("platformVersion", os);
        capabilities.setCapability("appPackage", APPPACKAGE);
        capabilities.setCapability("appActivity", APPACTIVITY );
        capabilities.setCapability("automationName", "uiautomator2");
        capabilities.setCapability("systemPort", Integer.parseInt(port));
        URL serverAddressLocal = (new URL("http://127.0.0.1:" + port + "/wd/hub"));
        this.driver = new AndroidDriver(serverAddressLocal, capabilities);
    }

    // determine home folder for logging:
    public static String setHomeFolder() {
        return System.getProperty("user.home");
    }

    // create the directory for the logging
    public static String setLogFolder() {
        String runDate = ElementsHelper.getDateTime();
        String logFolder = setHomeFolder()+"/Desktop/Tests/FF_AndroidTest_"+runDate;
        new File(logFolder).mkdir();
        return logFolder + "/";
    }

    // This grabs screenshots + tries to get the app back to a state where it can run further tests
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws IOException {
        ScreenShots screenShots = new ScreenShots(driver);
        AbstractScreen abstractScreen = new AbstractScreen(driver);

        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                screenShots.captureScreenshot(result.getName());

            } catch (Exception e) {
                e.printStackTrace();
            }
            abstractScreen.detectAppCrashRelaunch();
            abstractScreen.postTestClear();
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            abstractScreen.detectAppCrashRelaunch();
            abstractScreen.postTestClear();
        } else if (result.getStatus() == ITestResult.SKIP) {
            abstractScreen.detectAppCrashRelaunch();
            abstractScreen.postTestClear();
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}