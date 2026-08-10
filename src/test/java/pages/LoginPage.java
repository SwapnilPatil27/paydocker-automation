package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(xpath = "//input[@type='email' or @name='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@type='password' or @name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/form[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/button[1]/div[1]/div[2]/span[1]/span[1]")
    private WebElement continueButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickContinue() {
        continueButton.click();
    }
}
