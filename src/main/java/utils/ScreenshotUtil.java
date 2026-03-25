package utils;

import com.microsoft.playwright.Page;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String capture(Page page, String testName) {

        try {

            Path screenshotDir = Paths.get("target/screenshots");

            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            Path screenshotPath = screenshotDir.resolve(testName + "_" + timestamp + ".png");

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(screenshotPath)
                    .setFullPage(true));

            return screenshotPath.toString();

        } catch (Exception e) {
            throw new RuntimeException("Screenshot capture failed", e);
        }
    }
}