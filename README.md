# Playwright Java Test Automation Framework

This project is a robust test automation framework built using Playwright for Java, designed for end-to-end web UI testing. It leverages TestNG for test orchestration, ExtentReports for detailed reporting, and includes utilities for screenshots, video recording, logging, and configuration management.

## Features

- **Page Object Model (POM)**: Organized page classes (e.g., `GooglePage`, `LoginPage`) for maintainable test scripts.
- **Cross-Browser Testing**: Supports multiple browsers via Playwright's driver factory.
- **Reporting**: Generates ExtentReports with screenshots and video attachments.
- **Screenshots & Videos**: Automatic capture on test pass/fail, with video links in reports.
- **Logging**: Integrated logging with Log4j2 and custom utilities.
- **Configuration**: Centralized config management via properties files.
- **Retry Mechanism**: Built-in retry analyzer for flaky tests.
- **Test Data**: JSON-based test data support.

## Project Structure

```
PWJ/
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/java/
│   │   ├── config/ConfigManager.java    # Configuration management
│   │   ├── pages/                       # Page Object Model classes
│   │   │   ├── BasePage.java
│   │   │   ├── GooglePage.java
│   │   │   └── LoginPage.java
│   │   └── utils/                       # Utility classes
│   │       ├── ConfigReader.java
│   │       ├── Constants.java
│   │       ├── DriverFactory.java
│   │       ├── ExtentManager.java
│   │       ├── LoggerUtil.java
│   │       ├── ScreenshotUtil.java
│   │       └── VideoUtil.java
│   └── test/java/
│       ├── base/BaseTest.java           # Base test class
│       ├── listeners/                   # TestNG listeners
│       │   ├── RetryAnalyzer.java
│       │   └── TestListener.java
│       ├── tests/                       # Test classes
│       │   ├── GoogleTest.java
│       │   ├── LoginTest.java
│       │   └── PlaywrightTest.java
│       └── utils/ReportLogger.java
├── src/test/resources/
│   ├── config.properties               # Configuration properties
│   ├── log4j2.xml                      # Logging configuration
│   └── testdata.json                   # Test data
├── target/                             # Build output (reports, screenshots, videos)
│   ├── extent-report.html              # Generated ExtentReport
│   ├── screenshots/                    # Captured screenshots
│   └── videos/                         # Recorded videos
└── screenshots/                        # Additional screenshots
```

## Prerequisites

- **Java**: JDK 11 or higher
- **Maven**: For dependency management and build
- **Playwright**: Browsers will be installed automatically via Maven
- **IDE**: IntelliJ IDEA or Eclipse (recommended for Java development)

## Setup Instructions

1. **Clone or Download the Project**:
   - Place the project in your desired directory (e.g., `D:\PlayWright_Java\PWJ`).

2. **Install Dependencies**:
   - Open a terminal in the project root.
   - Run: `mvn clean install`
   - This will download all dependencies, including Playwright browsers.

3. **Configure Properties**:
   - Edit `src/test/resources/config.properties` to set browser, URLs, etc.
   - Example:
     ```
     browser=chromium
     headless=false
     baseUrl=https://www.google.com
     ```

4. **Run Tests**:
   - To run all tests: `mvn test`
   - To run a specific test class: `mvn test -Dtest=GoogleTest`
   - Reports will be generated in `target/extent-report.html`.

## Usage Examples

### Writing a Test
Tests extend `BaseTest` and use page objects. Example from `GoogleTest.java`:

```java
public class GoogleTest extends BaseTest {
    @Test
    public void testGoogleSearch() {
        GooglePage googlePage = new GooglePage(page);
        googlePage.navigateToGoogle();
        googlePage.searchFor("Playwright Java");
        // Assertions here
    }
}
```

### Page Object Example
From `GooglePage.java`:

```java
public class GooglePage extends BasePage {
    private final Page page;

    public GooglePage(Page page) {
        this.page = page;
    }

    public void navigateToGoogle() {
        page.navigate("https://www.google.com");
    }

    public void searchFor(String query) {
        page.locator("textarea[name='q']").fill(query);
        page.locator("input[value='Google Search']").click();
    }
}
```

### Reporting
- After test execution, open `target/extent-report.html` in a browser.
- Reports include test status, screenshots, and video links (e.g., "🎥 Video: [Watch Video](path/to/video.webm)").

## Configuration

- **Browsers**: Set in `config.properties` (chromium, firefox, webkit).
- **Logging**: Configured via `log4j2.xml`.
- **Test Data**: Edit `testdata.json` for parameterized tests.

## Troubleshooting

- **Browser Installation Issues**: Run `mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"`
- **Video Not Recording**: Ensure `page.video()` is enabled in `DriverFactory.java`.
- **ExtentReport Not Generating**: Check `ExtentManager.java` for initialization issues.

## Contributing

- Follow the Page Object Model for new pages.
- Add tests in `src/test/java/tests/`.
- Update utilities in `src/main/java/utils/` as needed.

## License

This project is for educational purposes. Ensure compliance with Playwright and TestNG licenses.

## Notes

- **Video Integration**: In `TestListener.java`, videos are captured on test failure and linked in ExtentReports using `test.get().info("Video: " + videoPath);`. Ensure the video path is correctly resolved (e.g., relative to `target/videos/`). If videos aren't attaching, verify `VideoUtil.java` for path generation.
- **Screenshot Handling**: Screenshots are automatically taken on failure via `ScreenshotUtil.java`. They are stored in `target/screenshots/` and embedded in reports.
- **BaseTest Setup**: All tests inherit from `BaseTest.java`, which initializes the Playwright page and browser via `DriverFactory.java`. Override `setUp()` if needed for custom pre-test logic.
- **Retry Logic**: `RetryAnalyzer.java` retries failed tests up to a configured limit. Useful for handling intermittent failures in CI/CD.
- **Date Context**: As of March 25, 2026, ensure all timestamps in logs/reports align with system time. Screenshots in the root `screenshots/` folder appear to be dated from test runs (e.g., `testGoogleSearch_20260317_162159.png`).
- **Performance Tips**: Run tests in headless mode for faster execution. Monitor video files, as they can consume disk space quickly.
- **Extensions**: Consider adding data-driven tests using `testdata.json` for parameterized inputs. Integrate with CI tools like Jenkins for automated runs.
- **Known Issues**: If ExtentReports don't load videos/screenshots, check file permissions and relative paths in the HTML output.
