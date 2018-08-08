package qa.Firefox.Testcases;

import org.testng.annotations.*;
import qa.Firefox.Pages.*;
import qa.Firefox.Util.ElementsHelper;
import qa.Firefox.Util.Listeners.TestNGITestListener;
import qa.Firefox.Util.TestSettings;

@Listeners(TestNGITestListener.class)

public class RegressionQuick extends TestSettings {

    private static final String testRunName = "Automated (" + ElementsHelper.getUniqueIdentifier() + ")";

    @BeforeTest()
    public void completeInitialSetup() {
        SetupScreen setupScreen = new SetupScreen(driver);
        setupScreen.setupProceedThroughInitialScreens();
    }

    @Test(priority = 1)
    public void testUrlResolution() {
        MainScreen mainScreen = new MainScreen(driver);
        mainScreen.mainNavigateToGoogle();
    }
}