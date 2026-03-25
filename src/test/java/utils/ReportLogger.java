package utils;


import com.aventstack.extentreports.ExtentTest;
import listeners.TestListener;

public class ReportLogger {

    public static void info(String message) {
        ExtentTest test = TestListener.getTest();
        if (test != null) {
            test.info(message);
        }
    }
}