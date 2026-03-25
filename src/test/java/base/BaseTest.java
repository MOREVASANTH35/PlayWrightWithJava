package base;

import org.testng.annotations.*;
import utils.DriverFactory;

public class BaseTest {

    @BeforeMethod
    public void setup() {
        DriverFactory.initBrowser();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.closeBrowser();
    }
}