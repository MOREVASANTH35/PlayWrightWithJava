package tests;

import base.BaseTest;
import listeners.RetryAnalyzer;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import com.microsoft.playwright.Page;
import pages.LoginPage;
import utils.DriverFactory;
import utils.LoggerUtil;

public class LoginTest extends BaseTest {

    private static final Logger log = LoggerUtil.getLogger(LoginTest.class);

    @Test
    public void loginTest() {

        log.info("Starting Login Test");

        LoginPage login = new LoginPage();

        log.info("Entering credentials");

        login.login("admin", "admin123");

        log.info("Login test completed");
    }
}
