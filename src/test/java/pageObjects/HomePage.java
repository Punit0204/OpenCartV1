package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
	
  public HomePage(WebDriver driver) 
  {
		super(driver);
		
  }


  
  
  @FindBy(xpath="//span[normalize-space()='My Account']")
  WebElement lnkMyaccount;
  
  @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Register']")
  WebElement lnkRegister;
  
  @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']")
  WebElement lnkLogin;
  
  
  public void clickMyAccount()
  {
	  lnkMyaccount.click();
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    wait.until(ExpectedConditions.elementToBeClickable(lnkMyaccount));
	  
  }
  
  // Register Test
  
  public void clickRegister()
  {
	  lnkRegister.click();
  }
  
  // Login Test
  
  public void clickLogin()
  {
	  lnkLogin.click();
	 
  }
  
}
