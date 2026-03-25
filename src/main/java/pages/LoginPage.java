package pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {


    public void login(String user, String pass) {
        type("#username", user);
        type("#password", pass);
        click("#loginBtn");
    }
}