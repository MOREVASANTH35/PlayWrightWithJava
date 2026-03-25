package utils;

import com.microsoft.playwright.*;

import java.io.File;
import java.nio.file.Paths;

public class DriverFactory {

    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();


    public static Page initBrowser() {

        // Create folders if not present
        new File("target/videos").mkdirs();
        new File("target/screenshots").mkdirs();

        playwright.set(Playwright.create());

        browser.set(
                playwright.get().chromium().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(false)
                                .setSlowMo(50) // optional
                )
        );

        // Incognito context (no cookies, no cache)
        context.set(
                browser.get().newContext(
                        new Browser.NewContextOptions()
                                .setRecordVideoDir(Paths.get("target/videos"))
                                .setRecordVideoSize(1280, 720)
                                .setViewportSize(1920, 1080)
                                .setIgnoreHTTPSErrors(true)
                )
        );

        page.set(context.get().newPage());

        return page.get();
    }


    public static Page getPage() {
        return page.get();
    }


    public static BrowserContext getContext() {
        return context.get();
    }


    public static void clearCookies() {
        if (context.get() != null) {
            context.get().clearCookies();
        }
    }


    public static void closeBrowser() {

        if (page.get() != null) {
            page.get().close();
        }

        if (context.get() != null) {
            context.get().close(); // video saved here
        }

        if (browser.get() != null) {
            browser.get().close();
        }

        if (playwright.get() != null) {
            playwright.get().close();
        }
    }
}