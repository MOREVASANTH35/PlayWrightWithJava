package pages;

import com.microsoft.playwright.Page;
import utils.DriverFactory;

public class BasePage {

    protected Page page = DriverFactory.getPage();



    public void click(String locator) {
        page.click(locator);
    }

    public void type(String locator, String text) {
        page.fill(locator, text);
    }
}