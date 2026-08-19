package stepdefinitions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import org.junit.Assert;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import pages.LoginPage;

public class LoginSteps {

    private WebDriver driver;

    private LoginPage loginPage;

    private WebDriverWait wait;

    // ============================================================
    // SETUP
    // ============================================================

    @Before
    public void setUp() {

        System.out.println("======================================");
        System.out.println("Starting Chrome Browser...");
        System.out.println("======================================");

        ChromeOptions options = new ChromeOptions();

        /*
         * Do NOT add --headless here.
         *
         * This keeps Chrome visible when Jenkins
         * is running in an interactive Windows session.
         */

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        // Implicit wait
        driver.manage()
              .timeouts()
              .implicitlyWait(Duration.ofSeconds(10));

        // Explicit wait
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );

        // Initialize Page Object
        loginPage = new LoginPage(driver);

        System.out.println("Chrome browser started successfully.");
    }

    // ============================================================
    // GIVEN
    // ============================================================

    @Given("the user navigates to the PayDocker login page")
    public void navigateToLoginPage() {

        driver.get(
            "https://dashboard.devpaydocker.cloud/login"
        );

        wait.until(
            ExpectedConditions.urlContains("/login")
        );

        System.out.println(
            "Login page opened successfully."
        );

        System.out.println(
            "Current URL: " + driver.getCurrentUrl()
        );

        System.out.println(
            "Page Title: " + driver.getTitle()
        );
    }

    // ============================================================
    // WHEN
    // ============================================================

    @When("the user enters email {string} and password {string}")
    public void enterCredentials(
            String email,
            String password) {

        loginPage.enterEmail(email);

        loginPage.enterPassword(password);

        System.out.println(
            "Email and password entered successfully."
        );
    }

    // ============================================================
    // AND
    // ============================================================

    @And("clicks the Continue button")
    public void clickContinueButton() {

        loginPage.clickContinue();

        System.out.println(
            "Continue button clicked."
        );
    }

    // ============================================================
    // THEN
    // ============================================================

    @Then("the user should be redirected to the dashboard")
    public void verifyRedirect() {

        try {

            /*
             * Wait until the login URL changes.
             *
             * We don't directly wait for "dashboard"
             * because the application may redirect to
             * another dashboard URL format.
             */

            wait.until(
                driver ->
                    !driver.getCurrentUrl().contains("/login")
            );

            String actualUrl =
                    driver.getCurrentUrl();

            String actualTitle =
                    driver.getTitle();

            System.out.println(
                "======================================"
            );

            System.out.println(
                "After Login"
            );

            System.out.println(
                "Actual URL   : " + actualUrl
            );

            System.out.println(
                "Page Title   : " + actualTitle
            );

            System.out.println(
                "======================================"
            );

            /*
             * Dashboard validation
             */

            Assert.assertTrue(

                "Expected dashboard URL but actual URL was: "
                + actualUrl,

                actualUrl
                    .toLowerCase()
                    .contains("dashboard")
            );

            System.out.println(
                "Login successful - Dashboard displayed."
            );

        } catch (Exception e) {

            System.out.println(
                "======================================"
            );

            System.out.println(
                "LOGIN VALIDATION FAILED"
            );

            System.out.println(
                "Current URL: "
                + driver.getCurrentUrl()
            );

            System.out.println(
                "Page Title: "
                + driver.getTitle()
            );

            System.out.println(
                "======================================"
            );

            takeScreenshot("Login_Failure");

            throw e;
        }
    }

    // ============================================================
    // TEARDOWN
    // ============================================================

    @After
    public void tearDown(Scenario scenario) {

        /*
         * Take screenshot when scenario fails.
         */

        if (scenario.isFailed()) {

            System.out.println(
                "Scenario failed. Taking screenshot..."
            );

            takeScreenshot("Failed_Scenario");
        }

        /*
         * Close browser.
         */

        if (driver != null) {

            driver.quit();

            System.out.println(
                "Browser closed successfully."
            );
        }
    }

    // ============================================================
    // SCREENSHOT
    // ============================================================

    private void takeScreenshot(String fileName) {

        try {

            if (driver instanceof TakesScreenshot) {

                File source =
                    ((TakesScreenshot) driver)
                        .getScreenshotAs(
                            OutputType.FILE
                        );

                File destination =
                    new File(
                        "target/screenshots/"
                        + fileName
                        + ".png"
                    );

                destination
                    .getParentFile()
                    .mkdirs();

                Files.copy(
                    source.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );

                System.out.println(
                    "Screenshot saved: "
                    + destination.getAbsolutePath()
                );
            }

        } catch (IOException e) {

            System.out.println(
                "Unable to save screenshot: "
                + e.getMessage()
            );
        }
    }
}