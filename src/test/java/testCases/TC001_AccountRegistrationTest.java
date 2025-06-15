package testCases;

import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registartion() throws InterruptedException {
		logger.info("*************** Starting TC001_AccountRegistrationTest ***************");
		logger.debug("This is a Debug log message");

		try {
			HomePage hp = new HomePage(driver);

			// Visual delay to observe automation
			Thread.sleep(1000);
			hp.clickMyAccount();
			logger.info("Clicked on My Account Link");

			Thread.sleep(1000);
			hp.clickRegister();
			logger.info("Clicked on Register Link");

			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);

			logger.info("Registering the customer details");
			regpage.setFirstName(randomString().toUpperCase());
			regpage.setLastName(randomString().toLowerCase());
			regpage.setEmail(randomString() + "@gmail.com");
			regpage.setTelephone(randomNumber());

			String Password = randomAlphaNumeric();
			regpage.setFPassword(Password);
			regpage.setConfirmPassword(Password);

			Thread.sleep(1000);
			regpage.setPrivacyPolicy();

			Thread.sleep(1000);
			regpage.clickContinue();

			logger.info("Validating Expected message...");
			String confmsg = regpage.getConfirmationMsg();
			logger.info("Confirmation message: " + confmsg);

			Assert.assertEquals(confmsg, "Your Account Has Been Created!");

		} catch (Exception e) {
			logger.error("Test Failed: " + e.getMessage());
			Assert.fail();
		}

		logger.info("*************** Finishing TC001_AccountRegistrationTest ***************");
	}
}
