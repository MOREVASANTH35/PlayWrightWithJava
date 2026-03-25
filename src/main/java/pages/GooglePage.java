package pages;

import com.microsoft.playwright.Page;

public class GooglePage {

    Page page;

    public GooglePage(Page page) {
        this.page = page;
    }

    public void search(String text) {
        page.fill("//textarea[@name='q']", text);
        page.keyboard().press("Enter");
    }
}