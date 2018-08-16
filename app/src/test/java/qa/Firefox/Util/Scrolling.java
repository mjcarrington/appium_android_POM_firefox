package qa.Firefox.Util;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
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
        action.press(PointOption.point(startX, endY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(startX, startY))
                .release()
                .perform();
    }

    public void scrollDown() {
        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }
}
