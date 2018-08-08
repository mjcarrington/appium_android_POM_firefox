package qa.Firefox.Util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.io.File;

import io.appium.java_client.AppiumDriver;
import qa.Firefox.Pages.AbstractScreen;

public class ScreenShots extends AbstractScreen {

    public ScreenShots(AppiumDriver driver) {
        super(driver);
    }

    public void captureScreenshot(String testName) throws Exception {
        String deviceName = driver.getCapabilities().getCapability("deviceName").toString();
        String fileName = "failure_" + testName + "_" + ElementsHelper.getDateTime() + "_" + deviceName;

        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(TestSettings.LOGFOLDER + fileName + ".png");
        FileUtils.copyFile(scrFile, targetFile);
        System.out.println("Screenshot written: " + targetFile.toString());
    }
}