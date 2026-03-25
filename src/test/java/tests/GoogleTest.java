package tests;

import base.BaseTest;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;
import pages.GooglePage;
import utils.DriverFactory;
import utils.ReportLogger;

public class GoogleTest extends BaseTest {

    @Test
    public void testGoogleSearch() {

        // Get Page
        Page page = DriverFactory.getPage();

        ReportLogger.info("🚀 Test Started");

        // Initialize Page Object
        GooglePage google = new GooglePage(page);

        // Navigate
        page.navigate("https://www.google.com");
        ReportLogger.info("Navigated to Google");

        // Action
        google.search("Playwright Java");
        ReportLogger.info("Entered search keyword");

        ReportLogger.info("✅ Test Passed");
    }
}