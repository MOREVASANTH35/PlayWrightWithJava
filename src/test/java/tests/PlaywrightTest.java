package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.Test;

public class PlaywrightTest {
@Test
    public static void testMethod() {

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));

            Page page = browser.newPage();

            page.navigate("https://example.com");

            System.out.println(page.title());

            browser.close();
        }
    }
}