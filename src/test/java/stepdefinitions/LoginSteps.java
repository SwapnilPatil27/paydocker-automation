package stepdefinitions;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

import pages.LoginPage;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @Before
    public void setUp() {

        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Launch Chrome
        driver = new ChromeDriver();

        // Maximize browser
        driver.manage().window().maximize();

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Initialize Login Page
        loginPage = new LoginPage(driver);
    }

    @Given("the user navigates to the PayDocker login page")
    public void navigateToLoginPage() {

        driver.get("https://dashboard.devpaydocker.cloud/login");

        System.out.println("Login page opened successfully.");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    @When("the user enters email {string} and password {string}")
    public void enterCredentials(String email, String password) {

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);

        System.out.println("Email and password entered successfully.");
    }

    @And("clicks the Continue button")
    public void clickContinueButton() {

        loginPage.clickContinue();

        System.out.println("Continue button clicked.");
    }

    @Then("the user should be redirected to the dashboard")
    public void verifyRedirect() {

        // Wait until URL contains dashboard
        wait.until(ExpectedConditions.urlContains("dashboard"));

        // Get actual URL
        String actualUrl = driver.getCurrentUrl();

        System.out.println("======================================");
        System.out.println("Actual URL after login: " + actualUrl);
        System.out.println("Page Title: " + driver.getTitle());
        System.out.println("======================================");

        // Validate dashboard URL
        Assert.assertTrue(
                "Expected dashboard URL but actual URL was: " + actualUrl,
                actualUrl.contains("dashboard")
        );

        System.out.println("Login successful - Dashboard displayed.");
    }

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed successfully.");
        }
    }
}