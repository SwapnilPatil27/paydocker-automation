package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Email field
    @FindBy(xpath = "//input[@type='email' or @name='email']")
    private WebElement emailInput;

    // Password field
    @FindBy(xpath = "//input[@type='password' or @name='password']")
    private WebElement passwordInput;

    // Continue button
    @FindBy(xpath = "//button[.//span[contains(normalize-space(), 'Continue')]]")
    private WebElement continueButton;

    // Constructor
    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );

        PageFactory.initElements(driver, this);
    }

    // Enter email
    public void enterEmail(String email) {

        wait.until(
                ExpectedConditions.visibilityOf(emailInput)
        );

        emailInput.clear();
        emailInput.sendKeys(email);
    }

    // Enter password
    public void enterPassword(String password) {

        wait.until(
                ExpectedConditions.visibilityOf(passwordInput)
        );

        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    // Click Continue
    public void clickContinue() {

        wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        );

        continueButton.click();
    }
}