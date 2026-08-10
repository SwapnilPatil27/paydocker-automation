package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;

import java.time.Duration;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        loginPage = new LoginPage(driver);
    }

    @Given("the user navigates to the PayDocker login page")
    public void navigateToLoginPage() {
        driver.get("https://dashboard.devpaydocker.cloud/login");
    }

    @When("the user enters email {string} and password {string}")
    public void enterCredentials(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @And("clicks the Continue button")
    public void clickContinueButton() {
        loginPage.clickContinue();
    }

    @Then("the user should be redirected to the dashboard")
    public void verifyRedirect() {
        assert !driver.getCurrentUrl().contains("/login");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
