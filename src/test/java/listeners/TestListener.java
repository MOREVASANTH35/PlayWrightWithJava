package listeners;

import org.testng.*;
import com.aventstack.extentreports.*;

import com.microsoft.playwright.Page;
import utils.DriverFactory;
import utils.ExtentManager;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();


    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest extentTest = extent.createTest(result.getName());
        test.set(extentTest);

        test.get().info("🚀 Test Started : " + result.getName());
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        attachScreenshotAndVideo("PASS", result);
    }


    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshotAndVideo("FAIL", result);
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test Skipped : " + result.getName());
    }


    @Override
    public void onFinish(ITestContext context) {

        extent.flush(); // Generates Extent Report

        System.out.println("Extent Report generated in target folder");
    }


    public void logInfo(String message) {
        if (test.get() != null) {
            test.get().info(message);
        }
    }


    private void attachScreenshotAndVideo(String status, ITestResult result) {

        Page page = DriverFactory.getPage();

        if (page == null) {
            test.get().warning("Page object is null. Screenshot not captured.");
            return;
        }

        try {

            page.waitForLoadState();

            // Capture screenshot
            byte[] screenshot = page.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );

            String base64 = java.util.Base64.getEncoder().encodeToString(screenshot);

            if ("PASS".equals(status)) {

                test.get().pass("✅ Test Passed : " + result.getName(),
                        MediaEntityBuilder
                                .createScreenCaptureFromBase64String(base64)
                                .build());

            } else {

                test.get().fail(result.getThrowable(),
                        MediaEntityBuilder
                                .createScreenCaptureFromBase64String(base64)
                                .build());
            }

            // Attach video
            try {

                if (page.video() != null) {

                    String videoPath = page.video().path().toString().replace("\\", "/");

                    test.get().info(
                            "🎥 Video: <a href='" + videoPath + "' target='_blank'>Watch Video</a>"
                    );
                }

            } catch (Exception e) {
                test.get().info("No video available");
            }

        } catch (Exception e) {

            test.get().info("Screenshot capture failed: " + e.getMessage());
        }
    }
}