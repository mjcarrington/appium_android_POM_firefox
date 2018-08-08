package qa.Firefox.Util;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import qa.Firefox.Pages.AbstractScreen;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import java.time.Duration;


public class Scrolling extends AbstractScreen {

    public Scrolling(AppiumDriver driver) {
        super(driver);
    }

    // this gets the screen dimension and length
    Dimension size = driver.manage().window().getSize();
    private int startX = (int) (size.width * 0.10);
    private int startY = (int) (size.height * 0.80);
    private int endY = (int) (size.height * 0.20);

    // Scrolling
    public void scrollUp() {
        TouchAction action = new TouchAction(driver);
        action.press(startX, endY).waitAction(2000).moveTo(startX, startY).release().perform();
    }

    public void scrollDown() {
        TouchAction action = new TouchAction(driver);
        action.press(startX, startY).waitAction(2000).moveTo(startX, endY).release().perform();
    }
}
